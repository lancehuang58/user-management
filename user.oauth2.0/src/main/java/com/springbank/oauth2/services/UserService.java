package com.springbank.oauth2.services;

import com.springbank.oauth2.repositories.UserRepository;
import com.springbank.user.core.models.Account;
import com.springbank.user.core.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    Optional<User> userOptional = userRepository.findByUsername(s);
    User user =
        userOptional.orElseThrow(() -> new UsernameNotFoundException("user not found by " + s));
    Account account = user.getAccount();
    return org.springframework.security.core.userdetails.User.builder()
        .username(account.getUsername())
        .password(account.getPassword())
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
  }
}
