package entities;

public class Tweet {
    private final long id;
    private final String content;
    private final String source;
    private final boolean isRetweet;
    private final String[] date;
    private final User user;
    /*private final String[] hashtags;*/
    private final Hashtag[] hashtags;
    private final String usuariosFavoritos;

    public Tweet(long id, String content, String source, boolean isRetweet, String date, User user, Hashtag[] hashtags, String usuariosFavoritos) {
        this.id = id;
        this.content = content.toLowerCase();
        this.source = source;
        this.isRetweet = isRetweet;
        this.date = date.split(" ")[0].split("-");
        this.user = user;
        this.hashtags = hashtags;
        this.usuariosFavoritos = usuariosFavoritos;
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

    public String[] getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public Hashtag[] getHashtags() {
        return hashtags;
    }

    public String getUsuariosFavoritos() {
        return usuariosFavoritos;
    }
}
