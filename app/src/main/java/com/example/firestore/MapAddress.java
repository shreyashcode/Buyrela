package com.example.firestore;

public class MapAddress
{
    public String map;
    public String input;
    public MapAddress(String map, String input) {
        this.map = map;
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public MapAddress() {
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }
}
