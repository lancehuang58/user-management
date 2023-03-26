package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.commands.RegisterUserCommand;
import com.springbank.user.cmd.api.dto.RegisterUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/registerUser")
@RequiredArgsConstructor
public class RegisterUserController {
    private final CommandGateway commandGateway;

    @PostMapping
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUserCommand command) {
        command.setId(UUID.randomUUID().toString());
        log.info("user register {}", command);
        try {
            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(
                    new RegisterUserResponse("user successfully registered!"), HttpStatus.CREATED);
        } catch (Exception e) {
            String safeErrorMessage = "error while processing register user for id" + command.getId();
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new RegisterUserResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
