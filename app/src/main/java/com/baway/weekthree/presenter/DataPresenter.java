package com.baway.weekthree.presenter;

import com.baway.weekthree.model.DataModel;
import com.baway.weekthree.model.OnFinish;
import com.baway.weekthree.model.bean.JavaBean;
import com.baway.weekthree.view.DataView;

/**
 * Created by 郑文杰 on 2017/11/18.
 */

public class DataPresenter implements OnFinish {

    private DataView dataView;
    private final DataModel dataModel;

    public DataPresenter(DataView dataView) {
        this.dataView=dataView;
        dataModel = new DataModel();
    }

    public void relevance(String uri){
        dataModel.getData(this,uri);
    }
    @Override
    public void OnSuccess(JavaBean javaBean) {
        dataView.getData(javaBean);
    }
}
