package tom.myrecyclerrefreshlayout.utils;

import android.content.Context;

/**
 * Created by 3dium on 20.12.2017.
 */

public class DensityUtil {

    public static float dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }
}
