package com.example.keult.networking.model;

/**
 * Profilkép válazs adatmodell
 */

class ProfileImagesResponseData {

    private String id;
    private String url;
    private String userid;
    private String added;
    private String comment;
    private String order;


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
