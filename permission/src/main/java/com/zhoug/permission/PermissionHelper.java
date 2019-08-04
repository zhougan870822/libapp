package com.zhoug.permission;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 创建日期:2019/1/23 on 14:44
 * 描述：权限管理工具类
 * 作者: zhougan
 */
public class PermissionHelper {
    private static final String TAG = "PermissionHelper----";
    private Context mContext;
    private String[] mPermissions;//权限组
    private String MNotAsking="必须要权限才能正常执行操作";//用户勾选了不在询问后的提示
    private OnPermissionCallback mCallback;//权限申请回掉接口
    private boolean showNotAskingDialig=true;//默认提示
    private RequestPermissionBroadcastReceiver mReceiver;

    public PermissionHelper() {

    }

    public PermissionHelper(Context context) {
        this.mContext=context;
    }

    public PermissionHelper(Context context,String[] permissions,OnPermissionCallback callback) {
        this(context);
        this.mPermissions=permissions;
        this.mCallback=callback;

    }

    public PermissionHelper(Context context,String[] permissions,String notAsking,OnPermissionCallback callback) {
        this(context,permissions,callback);
        if(notAsking!=null){
            showNotAskingDialig=true;
        }
    }

    /**
     * 添加权限回掉
     * @param callback
     * @return
     */
    public PermissionHelper addCallback(OnPermissionCallback callback){
        this.mCallback=callback;
        return this;
    }

    public PermissionHelper addPermissions(String...permissions){
       this.mPermissions=permissions;
        return this;
    }

    /**
     * 添加用户勾选了不在询问后的提示
     *
     * @param notAsking 不为null会显示提示框
     * @return
     */
    public PermissionHelper addNotAsking(String notAsking){
        if(notAsking!=null){
            this.MNotAsking=notAsking;
            showNotAskingDialig=true;
        }
        return this;
    }

    public PermissionHelper addContext(Context context){
        this.mContext=context;
        return this;
    }

    /**
     * 用户勾选了不在询问后,是否显示提示框
     * @param isShow
     * @return
     */
    public PermissionHelper showNotAskingDialig(boolean isShow){
        this.showNotAskingDialig=isShow;
        return this;
    }

    /**
     * 遍历,移除已有的权限
     */
    private void shouldRequestPermissions(){
        if (mPermissions == null || mPermissions.length == 0) {
            return ;
        }
        List<String> list=new ArrayList<>(Arrays.asList(mPermissions));
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String permission = iterator.next();
            //移除已经授权的
            if(ActivityCompat.checkSelfPermission(mContext,permission)==PackageManager.PERMISSION_GRANTED){
                iterator.remove();
            }
        }
        if(list.size()!=0){
            mPermissions=new String[list.size()];
            list.toArray(mPermissions);
        }else{
            mPermissions=null;
        }
    }

    /**
     * 检查需要的权限是否全部授权
     * @param context
     * @param permissions
     */
    public static boolean check(Context context,String[] permissions){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //没有添加权限,直接回掉
            if(permissions==null || permissions.length==0){
                return true;
            }else{
                for(String permission:permissions){
                    if(ActivityCompat.checkSelfPermission(context,permission)!=PackageManager.PERMISSION_GRANTED){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 开始权限申请
     */
    public void request(){
        if(mContext==null){
            Log.e(TAG, "start:Context 不能为null" );
            return;
        }
        /*if(mCallback==null){
            Log.e(TAG, "start:OnPermissionCallback 不能为null" );
            return;
        }*/
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            shouldRequestPermissions();
            if(mPermissions==null || mPermissions.length==0){
//                Log.i(TAG, "start: 都已授权");
                if(mCallback!=null)
                    mCallback.onSuccess();
            }else{
                //需要请求权限
//                Log.i(TAG, "start: 需要请求权限: ="+mPermissions.length+"个");
                startPermissionActivity();
            }
        }else{
            //6.0以下版本
            if(mCallback!=null)
                 mCallback.onSuccess();
        }

    }


    private void startPermissionActivity(){
        //注册广播


        Log.i(TAG, "startPermissionActivity: 注册广播");
        mReceiver = new RequestPermissionBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        String action = UUID.randomUUID().toString();
        filter.addAction(action);
        mContext.registerReceiver(mReceiver, filter);

        //启动权限请求Activity
        Log.i(TAG, "startPermissionActivity: 启动权限请求Activity");
        int requestCode=new Random().nextInt(1000);
        Intent intent=new Intent(mContext,PermissionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("permissions",mPermissions);
        intent.putExtra("requestCode",requestCode);
        intent.putExtra("action",action);
        intent.putExtra("NotAsking",MNotAsking);
        intent.putExtra("showNotAskingDialig",showNotAskingDialig);
        mContext.startActivity(intent);
    }


    /**
     * 权限申请回掉接口
     */
   public interface OnPermissionCallback{
        void onSuccess();
        void onFailure();
   }

   //自定义广播接受权限申请结果
   public  class RequestPermissionBroadcastReceiver extends BroadcastReceiver{

       @Override
       public void onReceive(Context context, Intent intent) {
           if(mCallback==null) {
               mContext.unregisterReceiver(mReceiver);
               return;
           }
           if (intent != null) {
               boolean result = intent.getBooleanExtra("result", false);
               if (result) {
                   mCallback.onSuccess();
               } else {
                   mCallback.onFailure();
               }
           } else {
               mCallback.onFailure();
           }
           mContext.unregisterReceiver(mReceiver);
           mReceiver=null;
       }
   }

    @Override
    protected void finalize() throws Throwable {
//        Log.i(TAG, "finalize: 销毁PermissionHelper对象");
        if(mReceiver!=null){
            mContext.unregisterReceiver(mReceiver);
            mReceiver=null;
        }
        super.finalize();
    }
}
