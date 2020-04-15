package com.coupanapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.widget.SwipeRefreshLayout;

import android.widget.Toast;
import android.os.Handler;
import android.support.v7.widget.Toolbar;

import com.coupanapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Main2Activity extends AppCompatActivity {

    private RecyclerView recyclerView;


    private Toolbar toolbar;


    private SwipeRefreshLayout swipeRefreshRecyclerList;
    private RecyclerViewAdapter mAdapter;

    private ArrayList<Model> modelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        findViews();
        initToolbar("Coupons-Codes");
        setAdapter();

        swipeRefreshRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {



               new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (swipeRefreshRecyclerList.isRefreshing())
                            swipeRefreshRecyclerList.setRefreshing(false);
                    }
                }, 1000);

            }
        });

    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshRecyclerList = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_recycler_list);
    }

    public void initToolbar(String title) {
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(title);
    }

//0D:D8:47:92:31:28:2E:7D:5B:8C:79:B3:F4:32:97:1C:87:57:3D:F4
    private void setAdapter() {





        mAdapter = new RecyclerViewAdapter(Main2Activity.this, modelList);

        recyclerView.setHasFixedSize(true);


        final GridLayoutManager layoutManager = new GridLayoutManager(Main2Activity.this, 1);
        recyclerView.addItemDecoration(new GridMarginDecoration(Main2Activity.this, 2, 2, 2, 2));
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Model model) {

                //handle item click events here
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", model.getCopuntxt());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Main2Activity.this, "Copied", Toast.LENGTH_SHORT).show();


            }
        });

        Api.getClient().CoupuanApi().enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {

                modelList.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Toast.makeText(Main2Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}
