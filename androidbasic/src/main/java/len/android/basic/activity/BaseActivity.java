package len.android.basic.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import len.android.basic.R;
import len.android.basic.dialog.ToastDialog;
import len.android.basic.view.LoadingView;
import len.android.basic.view.TitleBar;

public class BaseActivity extends FragmentActivity implements OnClickListener {

    public static SharedPreferences settings;
    private TitleBar mTitleBar;
    private ToastDialog mToastDialog;
    private LoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInit();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        onFindViews();
        onBindListener();
        onFillDataToViews();
    }

    /**
     * initialization some resource
     */
    protected void onInit() {
        if (settings == null) {
            settings = getSharedPreferences("settings", MODE_PRIVATE);
        }
    }

    /**
     * put the code of finding views here
     */
    protected void onFindViews() {

    }

    /**
     * put the code of binding listener here
     */
    protected void onBindListener() {

    }

    /**
     * put the code of filling data to views here
     */
    protected void onFillDataToViews() {

    }

    @Override
    public Resources getResources() {
        //解决字体随系统调节而变化的问题
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        //noinspection ResourceType
        if (view.getId() == R.drawable.bg_title_back) {
            onBackPressed();
        } else if (view.getId() == R.id.loading_retry) {
            onLoadingRetry();
        }
    }

    public ToastDialog getToastDialog() {
        return mToastDialog != null ? mToastDialog : (mToastDialog = new ToastDialog(this));
    }

    protected TitleBar getTitleBar() {
        return mTitleBar != null ? mTitleBar : (mTitleBar = (TitleBar) findViewById(R.id.title_view));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public LoadingView getLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = (LoadingView) findViewById(R.id.loading_view);
        }
        return mLoadingView;
    }

    protected void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    protected void showToast(final String info) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            getToastDialog().showToast(info);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getToastDialog().showToast(info);
                }
            });
        }
    }

    protected void onLoadingRetry() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}