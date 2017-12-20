package tom.myrecyclerrefreshlayout.tips;

import android.content.Context;

import tom.myrecyclerrefreshlayout.R;

/**
 * Created by 3dium on 20.12.2017.
 */

public enum TipsType {

    LOADING(R.layout.tips_loading),
    LOADING_FAILED(R.layout.tips_loading_failed),
    EMPTY(R.layout.tips_empty);

    protected int mLayoutRes;

    TipsType(int layoutRes) {
        this.mLayoutRes = layoutRes;
    }

    protected Tips createTips(Context context) {
        return new Tips(context, mLayoutRes);
    }
}
