package com.springbank.user.query.api.handlers;

import com.springbank.user.core.events.UserRegisteredEvent;
import com.springbank.user.core.events.UserRemovedEvent;
import com.springbank.user.core.events.UserUpdatedEvent;
import com.springbank.user.query.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserRepository userRepository;


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
        log.info("[EventHandler] remove user {}", event.getId());
        userRepository.deleteById(event.getId());
        log.info("user id [{}] removed at [{}]", event.getId(), LocalDateTime.now());
    }
}
