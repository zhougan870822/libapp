package com.zhoug.permission.util;

import android.Manifest;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述：android 权限申请工具类
 * zhougan
 * 2019/8/2
 **/
public class PermissionUtil {
    private static final String TAG = "PermissionUtil";

    /**
     * 日历
     */
    public static final int CALENDAR = 0;
    /**
     * 相机
     */
    public static final int CAMERA = 1;
    /**
     * 通讯录
     */
    public static final int CONTACTS = 2;
    /**
     * 位置
     */
    public static final int LOCATION = 3;
    /**
     * 麦克风
     */
    public static final int MICROPHONE = 4;
    /**
     * 电话
     */
    public static final int PHONE = 5;
    /**
     * 传感器
     */
    public static final int SENSORS = 6;
    /**
     * 短信
     */
    public static final int SMS = 7;
    /**
     * 存储
     */
    public static final int STORAGE = 8;

    //权限组
    private static final String[][] permissionGroup = new String[][]{
            {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
            {Manifest.permission.CAMERA},
            {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
            {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
            {Manifest.permission.RECORD_AUDIO, Manifest.permission.CAPTURE_AUDIO_OUTPUT, Manifest.permission.MODIFY_AUDIO_SETTINGS},
            {Manifest.permission.CALL_PHONE,  Manifest.permission.READ_PHONE_STATE},
            {Manifest.permission.BODY_SENSORS},
            {Manifest.permission.SEND_SMS,  Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS,Manifest.permission.BROADCAST_SMS},
            {Manifest.permission.WRITE_EXTERNAL_STORAGE,  Manifest.permission.READ_EXTERNAL_STORAGE},

    };

    /**
     *  用户申请的权限集合
     */
    private int[] permissions;

    private Callback callback;

    

    public void addPermission(int... permissions){
        this.permissions=permissions;
    }

    public void request(){
        if(permissions==null){
            //回掉
            return;
        }
        //真正的权限数组
        List<String> permissionArray=new ArrayList<>();

        for(int p: permissions){
            permissionArray.addAll(Arrays.asList(permissionGroup[p]));
        }
        for(String p:permissionArray){
            Log.d(TAG, "request: "+p);
        }
    }


    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
