package com.blueroom.englishstories.models;

public class StoriesModel {

    String storyTitle;
    String StoryText;
    int StoryImg;
    String storyId;
    private String shortenedStoryText;

    public StoriesModel(String storyTitle, String storyText, int storyImg) {
        this.storyTitle = storyTitle;
        StoryText = storyText;
        StoryImg = storyImg;
    }

    public StoriesModel() {
    }

    public String getShortenedStoryText() {
        return shortenedStoryText;
    }

    public void setShortenedStoryText(String shortenedStoryText) {
        this.shortenedStoryText = shortenedStoryText;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
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
