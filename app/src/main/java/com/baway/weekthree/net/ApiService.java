package com.baway.weekthree.net;

import com.baway.weekthree.model.bean.JavaBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 郑文杰 on 2017/11/18.
 */

public interface ApiService {

    //iYXEPGn4e9c6dafce6e5cdd23287d2bb136ee7e9194d3e9?uri=vedio
    @GET("iYXEPGn4e9c6dafce6e5cdd23287d2bb136ee7e9194d3e9")
    Observable<JavaBean> getNews(@Query("uri")String uri);
}
