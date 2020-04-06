package com.example.mymovieslist;

public class UserReview {
    private String content;
    private String url;

    public UserReview(String content, String url)
    {
        this.content=content;
        this.url=url;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
