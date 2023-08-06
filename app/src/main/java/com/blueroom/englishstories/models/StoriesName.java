package com.blueroom.englishstories.models;

public class StoriesName {

    String storyTitle;
    String StoryText;


    public StoriesName(String storyTitle, String storyText) {
        this.storyTitle = storyTitle;
        StoryText = storyText;
    }

    public StoriesName() {
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
}
