package com.blueroom.englishstories.models;

public class CategoryModel {

    private String categoryName;
    private String categoryId;
    private int categoryImage;
    private String categorySubName;

    public CategoryModel(String categoryName, String categoryId, int categoryImage, String categorySubName) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.categoryImage = categoryImage;
        this.categorySubName = categorySubName;
    }

    public CategoryModel() {
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

    public String getCategorySubName() {
        return categorySubName;
    }

    public void setCategorySubName(String categorySubName) {
        this.categorySubName = categorySubName;
    }
}