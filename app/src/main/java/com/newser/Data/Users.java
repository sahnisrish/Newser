package com.newser.Data;

import java.util.ArrayList;

/**
 * Created by sahni on 15/8/18.
 */

public class Users {
    private String email;
    private ArrayList<String> wishlist;

    public Users(String email){
        this.email=email;
        this.wishlist=null;
    }

    public String getEmail() {
        return email;
    }
    public ArrayList<String> getWishlist() {
        return wishlist;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setWishlist(ArrayList<String> wishlist) {
        this.wishlist = wishlist;
    }
}
