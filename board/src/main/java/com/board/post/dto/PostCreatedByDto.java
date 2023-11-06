package com.board.post.dto;

public class PostCreatedByDto {

    private String email;

    private String password;

    public boolean isMatch(String email, String password) {
        if(this.email.equals(email) && this.password.equals(password)) {
            return true;
        }
        return false;
    }
}
