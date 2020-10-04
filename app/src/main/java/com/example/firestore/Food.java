package com.example.firestore;

public class Food {
    String name ;
    String link;
    int key;

    public Food(){

    }
    public Food(String name , String link , int key){
        this.name = name;
        this.link = link;
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public int getKey(){
        return key;
    }
}
