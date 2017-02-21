package com.example.materialtest;

/**
 * Created by GTR on 2017/2/19.
 */

public class People {

    private String name;
    private int imageId;

    public People(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
