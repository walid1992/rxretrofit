package com.walid.rxretrofitsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.walid.rxretrofit.interfaces.SimpleHttpCallback;
import com.walid.rxretrofitsample.network.api.news.NewsService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewsService.latest(new SimpleHttpCallback<Object>() {
            @Override
            public void onNext(Object o) {
                Log.e("MainActivity", "Datas = " + o.toString());
            }
        });
    }
}
