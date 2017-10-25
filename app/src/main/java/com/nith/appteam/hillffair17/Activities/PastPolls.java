package com.nith.appteam.hillffair17.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.facebook.share.Share;
import com.nith.appteam.hillffair17.Adapters.PollAdapter;
import com.nith.appteam.hillffair17.Models.PollListModel;
import com.nith.appteam.hillffair17.Models.PollModel;
import com.nith.appteam.hillffair17.R;
import com.nith.appteam.hillffair17.Utils.SharedPref;
import com.nith.appteam.hillffair17.Utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastPolls extends AppCompatActivity {

    private RecyclerView list;
    private PollAdapter adapter;
    private ArrayList<PollListModel.Question>listPoll;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        listPoll = new ArrayList<>();
        uid=new SharedPref(this).getUserId();
        getPastPoll();
        list = (RecyclerView) findViewById(R.id.listPoll);
        adapter = new PollAdapter(listPoll,this);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }

    void getPastPoll() {
        Call<PollListModel> call = Utils.getRetrofitService().getAllPoll();
        call.enqueue(new Callback<PollListModel>() {
            @Override
            public void onResponse(Call<PollListModel> call, Response<PollListModel> response) {
               PollListModel model=response.body();
                ArrayList<PollListModel.Question> questions =model.getQuestions();
                for (PollListModel.Question q:questions){
                    listPoll.add(q);
                }
            }

            @Override
            public void onFailure(Call<PollListModel> call, Throwable t) {

            }
        });
    }
}
