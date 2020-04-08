package com.example.mymovieslist;

public class UserReview {
    //Member variables Declaration
    private String content;
    private String url;
    //Constructor
    public UserReview(String content, String url)
    {
        this.content=content;
        this.url=url;
    }
    //Getter methods
    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
