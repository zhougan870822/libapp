package com.zhoug.widget.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Activity 工具
 */
public class ActivityUtil {

    /**
     * 启动activity
     * @param context
     * @param cls
     */
    public static void startActivity(Context context,Class cls){
        Intent intent=new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class cls, Bundle bundle){
        Intent intent=new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


}
