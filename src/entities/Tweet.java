package entities;

import java.lang.reflect.Array;

public class Tweet {
    private final long id;
    private final String content;
    private final String source;
    private final boolean isRetweet;
    private final String date;
    private final User user;

    private String[] hashtags;

    public Tweet(long id, String content, String source, boolean isRetweet, String date, User user, String[] hashtag) {
        this.id = id;
        this.content = content.toLowerCase();
        this.source = source;
        this.isRetweet = isRetweet;
        this.date = date;
        this.user = user;
        this.hashtags = hashtag;
        System.out.println();
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getSource() {
        return source;
    }

    public boolean isRetweet() {
        return isRetweet;
    }

    public String getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }
}
