package com.zhoug.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


/**
 * 创建日期:2019/1/23 on 17:19
 * 描述：请求权限的页面
 * 作者: zhougan
 */
public class PermissionActivity extends Activity {
    private static final String TAG = "PermissionActivity----";
    private int mRequestCode=101;
    private String action="";
    private String MNotAsking;
    private boolean showNotAskingDialig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermission();
    }



    private void requestPermission(){
        Intent intent = getIntent();
        if(intent==null){
            Log.e(TAG, "getIntent():null ");
            finish();
        }else{
            Log.i(TAG, "requestPermission: ");
            String[] permissions = intent.getStringArrayExtra("permissions");
            mRequestCode=intent.getIntExtra("requestCode",101);
            action = intent.getStringExtra("action");
            showNotAskingDialig=intent.getBooleanExtra("showNotAskingDialig",false);
            MNotAsking=intent.getStringExtra("NotAsking");
            ActivityCompat.requestPermissions(this, permissions,mRequestCode );

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionsResult: ");
        if(requestCode==this.mRequestCode){
            boolean result=true;//全部权限是否授权
            if(grantResults.length>0){
                for(int grantResult:grantResults){
                    if(PackageManager.PERMISSION_GRANTED!=grantResult){
                        //有权限被拒绝,失败
                        result=false;
                        break;
                    }
                }
            }else{
                //失败
                result=false;
            }


            boolean show=false;//是否显示提示
            //请求失败,要显示提示
            if(!result && showNotAskingDialig){
                //全部权限都点击了不在询问才显示
                Log.i(TAG, "show=true: ");
                show=true;
                for(String per:permissions){
                    //只要有一个权限没勾选不再询问,就不显示
                    if(ActivityCompat.shouldShowRequestPermissionRationale(this,per)){
                        show=false;
                        break;
                    }
                }
            }

            if(show){
                showNotAskingDialog(result);
            }else{
                //发送广播同时权限申请完成
                sendBroadcast(result);
                finish();
            }

        }
    }

    /**
     * 用户点击了不再询问后显示的提示
     */
    private void showNotAskingDialog(final boolean result){
        Log.i(TAG, "showNotAskingDialog: ");
        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.permission_dialog)
                .setMessage(MNotAsking)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        try {
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        sendBroadcast(result);
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sendBroadcast(result);
                        finish();
                    }
                })
                .create();

        alertDialog.setCancelable(false);
        alertDialog.show();


    }

    private void sendBroadcast(boolean result){
        //发送广播同时权限申请完成
        Intent broadcastIntent=new Intent(action);
        broadcastIntent.putExtra("result",result);
        sendBroadcast(broadcastIntent);
    }

    @Override
    public void onBackPressed() {
        sendBroadcast(false);
        super.onBackPressed();
    }
}
