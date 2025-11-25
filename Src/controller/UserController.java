package controller;

/* Controller / validation
Her fortæller vi java - hvad skal vi give videre til services?
HUSK - Her tjekker vi om input er rigtigt.

Controller = risky code.
Service antager at alt er i orden, fordi controlleren har godkendt det.

Low coupling
 */

import structure.User;

public class UserController {

    public void login(){
        String password = ("pw" + User.getID());
    }

    public void getUser(String User.getID()){

    }
}
