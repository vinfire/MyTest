package com.example.myutils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by GTR on 2017/2/24.
 */

public class DisplayUtil {

    /*
    * 将px值转换为dip或dp值，保证尺寸大小不变
    * */
    public static int px2dp(Context context, int pxValue){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue/density+0.5f);
    }

    /*
    * 将dip或dp值转换为px值，保证尺寸大小不变
    * */
    public static int dip2px(Context context, int dpValue){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue*density+0.5f);
    }

    /*
    * 将px值转换为sp值，保证尺寸大小不变
    * */
    public static int px2sp(Context context, int pxValue){
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/scaledDensity+0.5f);
    }

    /*
    * 将sp值转换为px值，保证尺寸大小不变
    * */
    public static int sp2px(Context context, int spValue){
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }

    //同时，系统也提供了TypedValue类帮助我们转换
}
