package com.example.mymovieslist;

public class Trailer {
   //Decalration of member variables
    private String  name;
    private String key;
    String id;
    //Constructor
    Trailer(String name,String key)
    {
        this.name=name;
        this.key=key;
    }
    //getter methods
    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
