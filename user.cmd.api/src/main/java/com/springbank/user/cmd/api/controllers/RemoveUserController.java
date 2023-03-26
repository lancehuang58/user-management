package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.commands.RemoveUserCommand;
import com.springbank.user.cmd.api.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/removeUser")
public class RemoveUserController {
    private final CommandGateway gateway;

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable String id) {
        try {
            gateway.sendAndWait(new RemoveUserCommand(id));
            return new ResponseEntity<>(new BaseResponse("user remove successfully."), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new BaseResponse("Error while processing remove user request for id " + id)
                    , INTERNAL_SERVER_ERROR);
        }
    }
}
