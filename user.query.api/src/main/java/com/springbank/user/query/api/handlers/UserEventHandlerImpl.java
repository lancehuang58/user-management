package com.springbank.user.query.api.handlers;

import com.springbank.user.core.events.UserRegisteredEvent;
import com.springbank.user.core.events.UserRemovedEvent;
import com.springbank.user.core.events.UserUpdatedEvent;
import com.springbank.user.query.api.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserRepository userRepository;

    @Autowired
    public UserEventHandlerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventHandler
    @Override
    public void on(UserRegisteredEvent event) {
        userRepository.save(event.getUser());
        log.info("user {} register at {}", event.getUser().getFirstname() + " " + event.getUser().getLastname(), LocalDateTime.now());
    }

    @EventHandler
    @Override
    public void on(UserUpdatedEvent event) {
        userRepository.save(event.getUser());
        log.info("user {} update at {}", event.getUser().getFirstname() + " " + event.getUser().getLastname(), LocalDateTime.now());
    }

    @EventHandler
    @Override
    public void on(UserRemovedEvent event) {
        log.info("remove user {}", event.getId());
        userRepository.deleteById(event.getId());
        log.info("user id [{}] removed at [{}]", event.getId(), LocalDateTime.now());
    }
}
