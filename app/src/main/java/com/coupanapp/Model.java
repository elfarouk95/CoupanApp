
package com.coupanapp;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Model {

    @SerializedName("copuntxt")
    private String mCopuntxt;
    @SerializedName("imgurl")
    private String mImgurl;
    @SerializedName("value")
    private String mvalue;
    @SerializedName("siteUrl")
    private String msiteUrl;

    public String getvalue() {
        return mvalue;
    }

    public void setvalue(String mvalue) {
        this.mvalue = mvalue;
    }

    public String getsiteUrl() {
        return msiteUrl;
    }

    public void setsiteUrl(String msiteUrl) {
        this.msiteUrl = msiteUrl;
    }



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
