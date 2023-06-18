import entities.Tweet;
import entities.User;
import uy.edu.um.prog2.adt.LinkedList.LinkedList;
import uy.edu.um.prog2.adt.Hash.Hash;
public class CSVReaderReturn {
    Tweet[] tweets;
    Hash<String, User> users;
    LinkedList<String> userNames;

    public CSVReaderReturn(Tweet[] tweets, Hash<String, User> users, LinkedList<String> userNames) {
        this.tweets = tweets;
        this.users = users;
        this.userNames = userNames;
    }

    public void clear() {
        this.tweets = null;
        this.users = null;
    }

    public Tweet[] getTweets() {
        return tweets;
    }

    public Hash<String, User> getUsers() {
        return users;
    }

    public LinkedList<String> getUserNames() {
        return userNames;
    }
}
