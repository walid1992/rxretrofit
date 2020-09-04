package com.walid.rxretrofitsample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.walid.rxretrofit.HttpSubscriber;
import com.walid.rxretrofit.interfaces.SimpleHttpCallback;
import com.walid.rxretrofit.obserable.RxSchedulers;
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
        tvContent = findViewById(R.id.tv_content);


        tvContent.setOnClickListener(v -> {
            App.httpManager
                    .service(IInsApi.class).list("Android")
                    .compose(RxSchedulers.observableToMain())
                    .map(RxSchedulers.dataCheckFunction())
                    .subscribe(HttpSubscriber.createWithToast(MainActivity.this, new SimpleHttpCallback<List<InsuranceVo>>() {
                        @Override
                        public void onNext(List<InsuranceVo> insuranceVos) {

                        }
                    }));

            App.httpManager.subscribe(App.httpManager.service(IInsApi.class).list("ANDROID"), new SimpleHttpCallback<List<InsuranceVo>>() {
                @Override
                public void onNext(List<InsuranceVo> insuranceVos) {
                    tvContent.setText("Datas = \n" + insuranceVos.toString());
                }

                @Override
                public void onError(int code, String message) {
                    super.onError(code, message);
                    tvContent.setText("Datas = \n" + message);
                }
            });
        });
//        InsService.list(new SimpleHttpCallback<List<InsuranceVo>>() {
//            @Override
//            public void onNext(List<InsuranceVo> insuranceVos) {
//                tvContent.setText("Datas = \n" + insuranceVos.toString());
//            }
//        });
    }

}
