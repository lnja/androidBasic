package len.android.basic.view;

import android.content.Context;
import android.widget.LinearLayout;

public abstract class LoadMoreIndicator extends LinearLayout {

    public LoadMoreIndicator(Context context) {
        super(context);
    }

    public abstract void onLoadMoreStart();

    public abstract void onLoadMoreComplete();

}