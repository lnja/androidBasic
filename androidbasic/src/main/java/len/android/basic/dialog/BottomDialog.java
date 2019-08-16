package len.android.basic.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import len.android.basic.R;
import len.tools.android.DimenUtils;

public class BottomDialog extends Dialog implements OnClickListener {

    private LinearLayout mContentLayout;

    public BottomDialog(Context context) {
        super(context,1.0f);
        mContentLayout = new LinearLayout(context);
        mContentLayout.setOrientation(LinearLayout.VERTICAL);
        mContentLayout.setBackgroundColor(0xfff6f6f6);
        setContentView(mContentLayout);
        LayoutParams lp = getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.BottomWindowAnimation;
    }

    public LinearLayout getContentLayout() {
        return mContentLayout;
    }
}
