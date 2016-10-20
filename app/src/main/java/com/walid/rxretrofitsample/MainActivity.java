package com.walid.rxretrofitsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.walid.rxretrofit.interfaces.SimpleHttpCallback;
import com.walid.rxretrofitsample.network.api.news.InsService;

public class MainActivity extends AppCompatActivity {

    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);
        InsService.latest(new SimpleHttpCallback<Object>() {
            @Override
            public void onNext(Object o) {
                tvContent.setText("Datas = \n" + o.toString());
                Log.e("MainActivity", "Datas = " + o.toString());
            }
        });
    }
}
