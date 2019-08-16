package len.android.basic.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.support.annotation.StringRes;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import len.android.basic.R;
import len.tools.android.AndroidUtils;

public class InputDialog extends Dialog implements OnDismissListener {

    protected EditText mEditText;

    public InputDialog(Context context) {
        this(context, null,null,0f);
    }

    public InputDialog(Context context, String title) {
        this(context, title, null,0f);
    }

    public InputDialog(Context context,@StringRes int resIdOfTitle) {
        this(context, context.getString(resIdOfTitle), null,0f);
    }

    public InputDialog(Context context, String title, String hint) {
        this(context,title,hint,0f);
    }

    public InputDialog(Context context,@StringRes int resIdOfTitle,@StringRes int resIdOfHint) {
        this(context,context.getString(resIdOfTitle),context.getString(resIdOfHint),0f);
    }

    public InputDialog(Context context, String title, String hint, float dialogWidthRatio) {
        super(context,dialogWidthRatio);
        setTitle(title);
        setContentView(R.layout.dialog_input);
        mEditText = (EditText) findViewById(R.id.dialog_input);
        mEditText.setHint(hint);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setOnDismissListener(this);
    }

    public InputDialog setInputType(int type) {
        mEditText.setInputType(type);
        return this;
    }

    /**
     * 设置TextView的内容并将光标移到最后面
     */
    public void setContent(String content) {
        if (mEditText != null && !TextUtils.isEmpty(content)) {
            mEditText.setText(content);
            mEditText.setSelection(content.length());
        }
    }

    public InputDialog setGravity(int gravity) {
        mEditText.setGravity(gravity);
        return this;
    }

    public InputDialog setMaxLenth(int line, int maxLength) {
        mEditText.setSingleLine(false);
        mEditText.setLines(line);
        if (maxLength > -1) {
            mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
        return this;
    }

    public void show(String text) {
        mEditText.setText(text);
        if (text != null) {
            mEditText.setSelection(mEditText.getText().toString().length());
        }
        show();
    }

    public EditText getEditText() {
        return mEditText;
    }

    @Override
    public InputDialog setDefaultButton() {
        super.setDefaultButton();
        return this;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        AndroidUtils.hideSoftKeyboard(getContext(), mEditText);
    }

    @Override
    public void onClick(View view) {
        AndroidUtils.hideSoftKeyboard(getContext(), mEditText);
        if (view.getId() == R.id.dialog_button_confirm) {
            onConfirm(mEditText.getText().toString());
        } else {
            onCancel();
        }
    }

    public void onConfirm(String input) {
        super.onConfirm();
    }
}