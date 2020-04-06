package com.example.mymovieslist;

public class Trailer {
    private String  name;
    private String key;
    String id;

    Trailer(String name,String key)
    {
        this.name=name;
        this.key=key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
