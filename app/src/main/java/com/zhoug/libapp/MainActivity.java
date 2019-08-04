package com.zhoug.libapp;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhoug.permission.PermissionHelper;
import com.zhoug.permission.util.PermissionUtil;
import com.zhoug.widget.activities.BaseActivity;
import com.zhoug.widget.dialog.EditDialogFragment;
import com.zhoug.widget.divider.DividerItemWithoutLastItem;
import com.zhoug.widget.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<>();
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void initData() {
        mData.clear();
        mData.add("EditDialogFragment");
        mData.add("测试MFileProvider");
        mData.add("测试PermissionHelper");
        mData.add("测试PermissionUtil");

    }

    private View.OnClickListener listener = v -> {
        int pos = (int) v.getTag();
        switch (pos) {
            case 0:
                EditDialogFragment editDialogFragment = new EditDialogFragment();
                editDialogFragment.setConfirmOnclickListener(value -> {
                    Toast.makeText(this, "值:" + "value", Toast.LENGTH_SHORT).show();
                });
                Log.d(TAG,  EditDialogFragment.class.getName());
                editDialogFragment.show(getSupportFragmentManager(), EditDialogFragment.class.getName());
                break;
            case 1:
                AppUtil.getFileProvideIntent(this,"" ,"image/*" );


                break;
            case 2:
                new PermissionHelper().addContext(this)
                        .addPermissions(Manifest.permission.CAMERA)
                        .addCallback(new PermissionHelper.OnPermissionCallback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure() {
                                Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_SHORT).show();

                            }
                        })
                        .request();


                break;
            case 3:
                    PermissionUtil permissionUtil=new PermissionUtil();
                    permissionUtil.addPermission(
                            PermissionUtil.STORAGE,
                            PermissionUtil.PHONE,
                            PermissionUtil.CAMERA,
                            PermissionUtil.LOCATION);
                    permissionUtil.request();

                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION

                },102);
                break;
            case 4:

                break;
            case 5:

                break;
        }


    };


    private void init() {
        mRecyclerView = findViewById(R.id.recyclerView);
        initData();
        mAdapter = new Adapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemWithoutLastItem(this, OrientationHelper.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(getLayoutInflater().inflate(R.layout.list_main, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(mData.get(position));
            holder.itemView.setClickable(true);
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(listener);

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            ViewHolder(View itemView) {
                super(itemView);
                init(itemView);
            }

            private void init(View itemView) {
                textView = itemView.findViewById(R.id.tv_list_main);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==102){
            for(int i=0;i<permissions.length;i++){
                Log.d(TAG, ": "+permissions[i]);
                Log.d(TAG, ": "+grantResults[i]);
                Log.d(TAG, ": >>>>>>>>>>>>>>>>>>>>>>>>>");

            }

        }


    }

}
