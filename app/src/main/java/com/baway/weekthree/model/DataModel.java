package com.baway.weekthree.model;

import com.baway.weekthree.model.bean.JavaBean;
import com.baway.weekthree.net.Api;
import com.baway.weekthree.net.ApiService;
import com.baway.weekthree.net.RetrofitUtils;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 郑文杰 on 2017/11/18.
 */

public class DataModel {

    public void getData(final OnFinish onFinish, String uri){
        ApiService apiService = RetrofitUtils.getInstance().getApiService(Api.url, ApiService.class);
        Observable<JavaBean> observable = apiService.getNews(uri);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JavaBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JavaBean javaBean) {
                        onFinish.OnSuccess(javaBean);
                    }
                });
    }
}
