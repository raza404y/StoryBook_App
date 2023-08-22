package com.blueroom.englishstories.models;

public class CategoriesModel {

    private String categoryName;
    private String categoryId;
    private int categoryImage;

    public CategoriesModel(String categoryName, String categoryId, int categoryImage) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.categoryImage = categoryImage;
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

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

}