package com.letsdogether.dogether;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    final String BASE_URL = "https://api.github.com/repos/";
    RecyclerView recyclerView;
    IssuesAdapter adapter;
    List<IssuesModel> issuesList;
    Subscription subscription;
    Retrofit retrofit;
    Button btnSearch;
    EditText txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        txtSearch = (EditText) findViewById(R.id.txtSearch);

        btnSearch.setOnClickListener(v -> {

                String strSearch = (txtSearch.getText()).toString();
                if (strSearch.equals("")){
                    Toast.makeText(this, "Please enter owner/respo", Toast.LENGTH_SHORT).show();
                }else {
                    getRxIssues(strSearch);
                }
            }
        );

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        issuesList = new ArrayList<>();

    }

    private void getRxIssues(String strSearch) {
        String FINAL_URL = BASE_URL + strSearch.trim() + "/";

        retrofit = new Retrofit.Builder()
                .baseUrl(FINAL_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Observable<List<IssuesModel>> observable = api.getIssues()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<List<IssuesModel>>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "Issues has been listed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "Repository Not Found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(List<IssuesModel> issuesModels) {
                try {
                    issuesList = issuesModels;

                    adapter = new IssuesAdapter(issuesList, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
