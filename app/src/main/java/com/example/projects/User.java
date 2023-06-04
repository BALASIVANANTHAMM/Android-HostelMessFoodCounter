package com.example.projects;

public class User {

    String ID,Uid,ChickenCount,EggCount,Week,Time;

    public User(){}

    public User(String id,String Uid, String chickenCount, String eggCount, String week, String time) {
        this.ID = id;
        this.Uid=Uid;
        this.ChickenCount = chickenCount;
        this.EggCount = eggCount;
        this.Week = week;
        this.Time = time;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getID() {
        return ID;
    }

    public void setID(String id) {
        this.ID = id;
    }

    public String getChickenCount() {
        return ChickenCount;
    }

    public void setChickenCount(String chickenCount) {
        ChickenCount = chickenCount;
    }

    public String getEggCount() {
        return EggCount;
    }

    public void setEggCount(String eggCount) {
        EggCount = eggCount;
    }

    public String getWeek() {
        return Week;
    }

    public void setWeek(String week) {
        Week = week;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
