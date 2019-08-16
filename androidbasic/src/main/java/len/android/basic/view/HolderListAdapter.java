package len.android.basic.view;

import android.content.res.Resources;
import android.view.View;
import len.tools.android.AndroidUtils;
import len.tools.android.StringUtils;
import len.tools.android.model.NoProguard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public abstract class HolderListAdapter<H extends NoProguard, D> extends ListAdapter<View, D> {

    private Class<H> mHolderClass;

    public HolderListAdapter(List<D> list, Class<H> holderClass) {
        super(list);
        mHolderClass = holderClass;
    }

    @Override
    protected final void onInitItemView(int position, View view) {
        H holder = createHolder(position, view);
        view.setTag(holder);
        onInitItemView(view, holder);
    }

    protected void onInitItemView(View view, H holder) {
    }

    @SuppressWarnings("unchecked")
    private final H createHolder(int position, View view) {
        try {
            Constructor<?> c = mHolderClass.getDeclaredConstructors()[0];
            c.setAccessible(true);
            final H holder = (H) c.newInstance(this);
            final Field[] fields = mHolderClass.getDeclaredFields();
            final int count = fields.length;
            final Resources res = view.getResources();
            final String packageName = view.getContext().getPackageName();
            Field field;
            int id;
            for (int i = 0; i < count; i++) {
                field = fields[i];
                if (field.getAnnotation(Ignore.class) != null) {
                    continue;
                }
                id = AndroidUtils.getIdentify(res, StringUtils.camelToUnderline(field.getName()), packageName);
                if (id > 0) {
                    field.setAccessible(true);
                    field.set(holder, view.findViewById(id));
                    if (field.getAnnotation(Clickable.class) != null) {
                        ((View) field.get(holder)).setOnClickListener(this);
                        /** 此处占用了view的Tag，如想View有多个Tag，调用setTag（key,value）*/
                        ((View) field.get(holder)).setTag(position);
                    }
                }
            }
            return holder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected final void onFillItemView(int position, View view, D data) {
        resetTag(position, view);
        onFillItemView(position, view, (H) view.getTag(), data);
    }

    private void resetTag(int position, View view) {
        H h = (H) view.getTag();
        for (Field field : mHolderClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(Clickable.class) != null) {
                try {
                    ((View) field.get(h)).setTag(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected abstract void onFillItemView(int position, View view, H holder, D data);

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface Clickable {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface Ignore {
    }

}