package com.zhoug.widget.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

/**
 * 描述：Fragment 创建工厂
 * zhougan
 * 2019/3/24
 **/
public class FragmentFactory {
    private static final String TAG = "FragmentFactory";


    /**
     *获取存在的Fragment,如果不存在则创建新的
     * @param cls
     * @param fm
     * @param <T>
     * @return
     */
    public static <T extends Fragment> T findOrCreateByTag(Class<T> cls, FragmentManager fm, Bundle arguments){
        //已经存在
        Fragment fragment=  fm.findFragmentByTag(cls.getName());
        if(fragment!=null ){
            if(arguments!=null){
                fragment.setArguments(arguments);
            }
            Log.d(TAG, "findOrCreateByTag: 找到旧的:"+fragment);
            return (T) fragment;
        }else{
            T t=null;
            try {
                t = cls.newInstance();
                if(arguments!=null){
                    t.setArguments(arguments);
                }
                Log.d(TAG, "findOrCreateByTag: 创建新的:"+t);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }

            return t;
        }
    }



}
