package entities;

public class Tweet {
    private final long id;
    private final String content;
    private final String source;
    private final boolean isRetweet;
    private final String date;
    private User user;

    public Tweet(long id, String content, String source, boolean isRetweet, String date, User user) {
        this.id = id;
        this.content = content.toLowerCase();
        this.source = source;
        this.isRetweet = isRetweet;
        this.date = date;
        this.user = user;
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
