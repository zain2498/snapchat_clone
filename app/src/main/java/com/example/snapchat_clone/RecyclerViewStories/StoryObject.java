package com.example.snapchat_clone.RecyclerViewStories;

import java.util.Objects;

public class StoryObject {

    private String email;
    private String Uid;
    private String chatOrStory;

    public StoryObject(String email, String uid, String chatOrStory) {
        this.email = email;
        Uid = uid;
        this.chatOrStory = chatOrStory;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        this.Uid = uid;
    }

    public String getChatOrStory() {
        return chatOrStory;
    }

    public void setChatOrStory(String chatOrStory) {
        this.chatOrStory = chatOrStory;
    }

    @Override
    public boolean equals(Object o) {
      boolean same = false;
      if (o != null && o instanceof StoryObject ){
          same = this.Uid == ((StoryObject) o).Uid;
      }
      return same;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.Uid == null ? 0 : this.Uid.hashCode());
        return result;
    }
}
