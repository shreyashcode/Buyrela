package com.example.firestore.Modal;

public class UserSlots
{
    public String userslots;
    public String status;

    public UserSlots(String userslots, String status) {
        this.userslots = userslots;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserSlots(String userslots)
    {
        this.userslots = userslots;
    }

    public UserSlots() {
    }

    public String getUserslots() {
        return userslots;
    }

    public void setUserslots(String userslots) {
        this.userslots = userslots;
    }
}
