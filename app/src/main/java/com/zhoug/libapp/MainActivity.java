package com.zhoug.libapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhoug.widget.activities.BaseActivity;
import com.zhoug.widget.dialog.EditDialogFragment;
import com.zhoug.widget.divider.DividerItemWithoutLastItem;

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

                break;
            case 2:

                break;
            case 3:

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
}
