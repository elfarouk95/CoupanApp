package com.coupanapp;

public class AbstractModel {

    private String ImgUrl;

    private String message;


    public AbstractModel(String title, String message) {
        this.ImgUrl = title;
        this.message = message;
    }

    public AbstractModel() {

    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.ImgUrl = imgUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
