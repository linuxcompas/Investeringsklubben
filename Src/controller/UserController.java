package controller;

/* Controller / validation
Her fortæller vi java - hvad skal vi give videre til services?
HUSK - Her tjekker vi om input er rigtigt.

Controller = risky code.
Service antager at alt er i orden, fordi controlleren har godkendt det.

Low coupling
 */


import repositories.UserRepository;
import structure.User;

public class UserController {
    public UserRepository userRepository;
    public UserController(){
        this.userRepository = new UserRepository();
    }
    public User login (String username, String password) {
        if (username == null) || username.isEmpty()){
            throw new IllegalArgumentException("Brugernavn kan ikke være tomt.");

        }
        User user = Users.getId(username);
        if (user == null){
            throw new IllegalArgumentException("Bruger findes ikke: " + username);
        }

        String expctedPassword = "pw" + user.getId();
        if (!password.equals(expctedPassword)){
            throw new IllegalArgumentException("Forjert password.");

        }
        
        return user;

    }

    public User getUser(String userId) {
        if (userId == null || userId.isEmpty()){
            throw new IllegalArgumentException("User ID kan ikke være tomt.");
        }







//    public void login(){
//        String password = ("pw" + User.getID());
//    }
//
//    public void getUser(String User.getID()){
//
//    }
}
