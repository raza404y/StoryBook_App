package com.blueroom.englishstories.models;

public class StoriesModel {

    String storyTitle;
    String StoryText;
    int StoryImg;


    public StoriesModel(String storyTitle, String storyText, int storyImg) {
        this.storyTitle = storyTitle;
        StoryText = storyText;
        StoryImg = storyImg;
    }

    public StoriesModel() {
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getStoryText() {
        return StoryText;
    }

    public void setStoryText(String storyText) {
        StoryText = storyText;
    }

    public int getStoryImg() {
        return StoryImg;
    }

    public void setStoryImg(int storyImg) {
        StoryImg = storyImg;
    }
}
