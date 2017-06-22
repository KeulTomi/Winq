package com.example.keult.networking.model;

import java.io.Serializable;

/**
 * Kép adatmodel
 */

public class ImageData implements Serializable {

    private String id;
    private String url;
    private String userid;
    private String added;
    private String comment;
    private String order;


    public ImageData(int pos) {

        if (pos % 2 == 0) {
            id = "0";
            url = "https://www.gstatic.com/webp/gallery3/2.png";
            userid = "17";
        } else {
            id = "1";
            url = "https://www.gstatic.com/webp/gallery3/3.png";
            userid = "17";
        }
    }
    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getUserId() {
        return userid;
    }

    public String getDateAdded() {
        return added;
    }

    public String getComment() {
        return comment;
    }

    public String getOrder() {
        return order;
    }

}

