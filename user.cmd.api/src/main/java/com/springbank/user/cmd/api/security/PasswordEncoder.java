package com.springbank.user.cmd.api.security;

public interface PasswordEncoder {
    String hash(String password);
}
