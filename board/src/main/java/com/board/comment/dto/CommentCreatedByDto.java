package com.board.comment.dto;

public class CommentCreatedByDto {

    private String email;

    private String password;


    public boolean isMatch(String email, String password) {
        if(this.email.equals(email) && this.password.equals(password)) {
            return true;
        }
        return false;
    }
}
