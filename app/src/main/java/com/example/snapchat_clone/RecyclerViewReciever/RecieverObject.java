package com.example.snapchat_clone.RecyclerViewReciever;

public class RecieverObject {

    private String email;
    private String Uid;
    private boolean recieve;

    public RecieverObject(String email, String uid, boolean recieve) {
        this.email = email;
        this.Uid = uid;
        this.recieve = recieve;
    }

    public boolean getRecieve() {
        return recieve;
    }

    public void setRecieve(boolean recieve) {
        this.recieve = recieve;
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
