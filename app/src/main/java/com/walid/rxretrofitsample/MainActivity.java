package com.walid.rxretrofitsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.walid.rxretrofit.HttpManager;
import com.walid.rxretrofit.interfaces.SimpleHttpCallback;
import com.walid.rxretrofitsample.app.App;
import com.walid.rxretrofitsample.network.api.news.IInsApi;
import com.walid.rxretrofitsample.network.api.news.bean.InsuranceVo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);

        HttpManager.getInstance().toSubscribe(HttpManager.getInstance().getApiService(IInsApi.class).list("ANDROID"), App.instalce, new SimpleHttpCallback<List<InsuranceVo>>() {
            @Override
            public void onNext(List<InsuranceVo> insuranceVos) {
                tvContent.setText("Datas = \n" + insuranceVos.toString());
            }
        }, false);

//        InsService.list(new SimpleHttpCallback<List<InsuranceVo>>() {
//            @Override
//            public void onNext(List<InsuranceVo> insuranceVos) {
//                tvContent.setText("Datas = \n" + insuranceVos.toString());
//            }
//        });
    }

}
