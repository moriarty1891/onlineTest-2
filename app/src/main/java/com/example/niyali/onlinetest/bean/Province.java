package com.example.niyali.onlinetest.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by niyali on 17/4/3.
 */

public class Province extends BmobObject{
    private int id;
    private int provinceId;
    private String provinceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }



}
