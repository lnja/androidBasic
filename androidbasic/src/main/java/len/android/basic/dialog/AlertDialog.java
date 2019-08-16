package len.android.basic.dialog;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.TextView;
import len.android.basic.R;
import len.tools.android.StringUtils;

public class AlertDialog extends Dialog {

    public AlertDialog(Context context, @StringRes int resIdOfTitle) {
        this(context, context.getString(resIdOfTitle));
    }

    public AlertDialog(Context context, String title) {
        this(context, null, title);
    }

    public AlertDialog(Context context, @StringRes int resIdOfTitle, @StringRes int resIdOfContent) {
        this(context, context.getString(resIdOfTitle), context.getString(resIdOfContent));
    }

    public AlertDialog(Context context, String title, String content) {
        this(context, title, content, 0f);
    }

    public AlertDialog(Context context, String title, String content, float dialogWidthRatio) {
        super(context, dialogWidthRatio);
        setTitle(title);
        setContentView(R.layout.dialog_alert);
        ((TextView) findViewById(R.id.dialog_alert)).setText(content);
        if (StringUtils.isValid(title)) {
            ((TextView) findViewById(R.id.dialog_alert)).setTextColor(context.getResources().getColor(R.color.text_light));
        }
    }


    public String getContent() {
        return ((TextView) findViewById(R.id.dialog_alert)).getText().toString().trim();
    }

    public TextView getContentTextView() {
        return ((TextView) findViewById(R.id.dialog_alert));
    }

    public void setContextTextViewGravity(int gravity) {
        getContentTextView().setGravity(gravity);
    }
}