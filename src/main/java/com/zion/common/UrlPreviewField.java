package com.zion.common;

public enum UrlPreviewField {
    DESCRIPTION("description"),
    FAVICON("favicon"),
    IMAGE("image"),
    IMAGEHEIGHT("imageHeight"),
    IMAGEWIDTH("imageWidth"),
    SITE_NAME("site_name"),
    TITLE("title"),
    TYPE("type"),
    URL("url");

    private String field;

    private UrlPreviewField(String field) {
        this.field = field;
    }

    public String getFieldName() {
        return field;
    }
}
