package com.blueroom.englishstories.models;

import androidx.annotation.Keep;

@Keep
public class CategoriesModel {

    private String categoryName;
    private String categoryId;
    private String imageUrl;

    public CategoriesModel(String categoryName, String categoryId, String imageUrl) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
    }

    public CategoriesModel() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String categoryImage) {
        this.imageUrl = categoryImage;
    }
}