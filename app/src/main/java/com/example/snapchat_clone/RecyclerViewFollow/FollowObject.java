package com.example.snapchat_clone.RecyclerViewFollow;

public class FollowObject {

    private String email;
    private String Uid;

    public FollowObject(String email, String uid) {
        this.email = email;
       this.Uid = uid;
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
}
