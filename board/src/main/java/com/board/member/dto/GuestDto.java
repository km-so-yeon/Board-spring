package com.board.member.dto;

public class GuestDto {

    private String email;

    private String password;

    public String getEmail() { return this.email; }

    public String getPassword() { return this.password; }

    public Boolean isEmpty() {
        if(this.email == null) return true;
        if(this.password == null) return true;
        if(this.email.isBlank()) return true;
        if(this.password.isBlank()) return true;

        return false;
    }
}
