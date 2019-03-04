package com.waracle.androidtest;

public final class CakeDataItem {
    private String      title;
    private String      description;
    private String      url;

    public int  size(CakeDataItem[] cakes) {
        return cakes.length;
    }

    public CakeDataItem(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String   getTitle() {
        return this.title;
    }

    public String   getDescription() {
        return this.description;
    }

    public String   getUrl() {
        return this.url;
    }

    @Override
    public String toString() {
        return title + " : " + description;
    }}

