package len.android.basic.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import len.android.basic.R;
import len.tools.android.AndroidUtils;
import len.tools.android.DimenUtils;
import len.tools.android.Log;
import len.tools.android.extend.RequestUiHandler;

public class ToastDialog extends android.app.Dialog implements Runnable, OnCancelListener, RequestUiHandler {

    private ProgressBar mProgressBar;
    private ImageView mIconView;
    private TextView mInfoView;
    private Handler handler;
    private Toast toast;
    private Context mContext;

    public ToastDialog(Context context) {
        super(context, AndroidUtils.getDialogTheme(context, R.attr.toastDialogStyle));
        setContentView(R.layout.dialog_toast);
        mContext = context;
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mIconView = (ImageView) findViewById(R.id.icon);
        mInfoView = (TextView) findViewById(R.id.info);

        setWindowWidth((int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.8f));

        setOnCancelListener(this);
        handler = new Handler();
    }

    public void setWindowWidth(int windowWidth) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = windowWidth;
        getWindow().setAttributes(lp);
    }

    public void setDefaultLoadingWindowWidth() {
        setWindowWidth(DimenUtils.dp2px(getContext(), 450));
    }

    public void setDefaultInfoWindowWidth() {
        setWindowWidth((int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.8f));
    }

    public void setContentTextViewGravity(int gravity) {
        mInfoView.setGravity(gravity);
    }

    public void showLoading(@StringRes int resId) {
        showLoading(getContext().getString(resId));
    }

    public void showLoading(String info) {
        showLoading(info, 0);
    }

    public void showLoading(@StringRes int resId, long maxDurationMillis) {
        showLoading(getContext().getString(resId), maxDurationMillis);
    }

    public void showLoading(String info, long maxDurationMillis) {
        handler.removeCallbacks(this);
        mProgressBar.setVisibility(View.VISIBLE);
        mIconView.setVisibility(View.GONE);
        mInfoView.setText(info);
        if (maxDurationMillis > 0) {
            handler.postDelayed(this, maxDurationMillis);
        }
        if (!isShowing()) {
            setCanceledOnTouchOutside(false);
            setCancelable(false);
            show();
        }
    }

    public void show(@StringRes int resId) {
        showToast(0, resId);
    }

    public void show(String info) {
        show(0, info);
    }

    public void showSuccess(@DrawableRes int resId) {
        show(R.drawable.ic_success, resId);
    }

    public void showSuccess(String info) {
        show(R.drawable.ic_success, info);
    }

    public void showError(@DrawableRes int resId) {
        show(R.drawable.ic_error, resId);
    }

    public void showError(String info) {
        show(R.drawable.ic_error, info);
    }

    public void showInfo(@DrawableRes int resId) {
        show(R.drawable.ic_info, resId);
    }

    public void showInfo(String info) {
        show(R.drawable.ic_info, info);
    }

    public void show(@DrawableRes int iconRes, int resId) {
        show(iconRes, getContext().getString(resId));
    }

    public void show(@DrawableRes int iconRes, String info) {
        show(iconRes, info, 1000 + info.length() * 100);
    }

    public void show(@DrawableRes int iconRes, int resId, int dismissDelayMillis) {
        show(iconRes, getContext().getString(resId), dismissDelayMillis);
    }

    public void show(@DrawableRes int iconRes, String info, int dismissDelayMillis) {
        setWindowWidth((int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.8f));
        handler.removeCallbacks(this);
        mProgressBar.setVisibility(View.GONE);
        if (iconRes == 0) {
            mIconView.setVisibility(View.GONE);
        } else {
            mIconView.setVisibility(View.VISIBLE);
            mIconView.setImageResource(iconRes);
        }
        mInfoView.setText(info);
        setCanceledOnTouchOutside(true);
        handler.postDelayed(this, dismissDelayMillis);
        show();
    }

    public void showToast(String info) {
        showToast(info, Toast.LENGTH_SHORT);
    }

    public void showToast(@StringRes int resId) {
        showToast(getContext().getString(resId), Toast.LENGTH_SHORT);
    }

    public void showToast(@StringRes int resId, int duration) {
        showToast(getContext().getString(resId), duration);
    }

    public void showToast(String info, int duration) {
        if (toast == null) {
            View layout = getLayoutInflater().inflate(R.layout.view_toast, null);
            TextView textView = (TextView) layout.findViewById(R.id.message);
            textView.setText(info);
            toast = new Toast(getContext());
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
            toast.setView(layout);
        }
        toast.setDuration(duration);
        ((TextView) toast.getView().findViewById(R.id.message)).setText(info);
        toast.show();
    }

    @Override
    public void show() {
        try {
            if (mContext instanceof Activity) {
                if (!((Activity) mContext).isFinishing()) {
                    super.show();
                }
            } else {
                Log.d("dialog cannot be show to user");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dismiss() {
        try {
            if (mContext instanceof Activity) {
                if (!((Activity) mContext).isFinishing()) {
                    super.dismiss();
                }
            } else {
                super.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        handler.removeCallbacks(this);
    }

    @Override
    public void onStart(String hint) {
        showLoading(hint);
    }

    @Override
    public void onError(int errCode, String errMsg) {
        dismiss();
        showToast(errMsg);
    }

    @Override
    public void onSuccess() {
        dismiss();
    }
}