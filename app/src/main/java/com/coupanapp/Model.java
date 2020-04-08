
package com.coupanapp;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Model {

    @SerializedName("copuntxt")
    private String mCopuntxt;
    @SerializedName("imgurl")
    private String mImgurl;

    public String getCopuntxt() {
        return mCopuntxt;
    }

    public void setCopuntxt(String copuntxt) {
        mCopuntxt = copuntxt;
    }

    public String getImgurl() {
        return mImgurl;
    }

    public void setImgurl(String imgurl) {
        mImgurl = imgurl;
    }

}
