package xyz.jessyu.studentrentalwebsite.repository;


import xyz.jessyu.studentrentalwebsite.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static final Map<String, User> users = new HashMap<>();
    static {
        users.put("test", new User("test", "test"));
        users.put("admin", new User("admin", "admin"));
        users.put("123", new User("123", "123"));
    }


    public Map<String, User> getUsers() {
        return users;
    }

    public void save(User user) {
        users.put(user.getUserName(), user);
    }

    public User findByUserName(String userName) {
        return users.get(userName);
    }
}
