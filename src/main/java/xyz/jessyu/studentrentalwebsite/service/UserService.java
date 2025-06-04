package xyz.jessyu.studentrentalwebsite.service;


import xyz.jessyu.studentrentalwebsite.model.User;
import xyz.jessyu.studentrentalwebsite.repository.UserRepository;

public class UserService {

    private UserRepository userRepository = new UserRepository();

    public boolean validateUser(String username, String password) {
        User user = userRepository.findByUserName(username);
        return user != null && user.getPassword().equals(password);
    }

    public boolean registerUser(String username, String password) {

        // Prevent same UserName exist
        if (userRepository.findByUserName(username) != null) {
            return false; // return false for failure
        }

        // Put New User into datalist
        User newUser = new User(username, password);
        userRepository.save(newUser);
        return true; // return true for success
    }

    //add Collection Logic
    public void addCollection(String username, String postId) {
        System.out.println("add collection");
    }

    //delete Collection Logic
    public void deleteCollection(String username, String postId) {
        System.out.println("delete collection");
    }

    // this function is not done
    public void getCollection(String username) {
        System.out.println("get collection");
    }
}
