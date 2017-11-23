package com.baway.weekthree.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 郑文杰 on 2017/11/18.
 */
@Entity
public class UserBean {

    private Long loadSize;

    @Generated(hash = 1099277894)
    public UserBean(Long loadSize) {
        this.loadSize = loadSize;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public Long getLoadSize() {
        return this.loadSize;
    }

    public void setLoadSize(Long loadSize) {
        this.loadSize = loadSize;
    }

}
