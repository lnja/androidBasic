package len.android.basic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import len.android.basic.R;
import len.android.basic.activity.BaseActivity;
import len.android.basic.dialog.ToastDialog;
import len.android.basic.view.LoadingView;

public class BaseFragment extends Fragment implements OnClickListener {
    protected String TAG;
    private BaseActivity mBaseActivity;
    private View mRootView;
    private LoadingView mLoadingView;

    public String getTAG() {
        if (TAG == null) {
            TAG = this.getClass().getSimpleName();
        }
        return TAG;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getTAG();
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = onCreateView(inflater, container);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();

        if (parent != null) {
            parent.removeView(mRootView);

        }
        return mRootView;

    }

    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public final void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mBaseActivity == null) {
            mBaseActivity = (BaseActivity) getActivity();
            onReady();
        }
        onInit();
        onFindViews();
        onBindListener();
    }

    protected void onInit() {

    }

    protected void onFindViews() {

    }

    protected void onBindListener() {

    }

    protected final View findViewById(int id) {
        if (mRootView != null) {
            return mRootView.findViewById(id);
        }
        return null;
    }

    public final BaseActivity getBaseActivity() {
        return mBaseActivity;
    }

    public final ToastDialog getToastDialog() {
        return mBaseActivity.getToastDialog();
    }

    protected final LoadingView getLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = (LoadingView) findViewById(R.id.loading_view);
            if (mLoadingView != null) {
                mLoadingView.findViewById(R.id.loading_retry).setOnClickListener(this);
            }
        }
        return mLoadingView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loading_retry) {
            onLoadingRetry();
        }
    }

    protected void onReady() {
    }

    protected void onLoadingRetry() {
    }

}