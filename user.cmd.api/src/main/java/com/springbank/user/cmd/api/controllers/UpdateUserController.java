package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.commands.UpdateUserCommand;
import com.springbank.user.cmd.api.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/updateUser")
@RequiredArgsConstructor
public class UpdateUserController {

    private final CommandGateway commandGateway;


    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable String id,
                                                   @Validated
                                                   @RequestBody UpdateUserCommand command) {
        try {
            command.setId(id);
            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(new BaseResponse("user update successfully."), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new BaseResponse("Error while processing update user request for id " + id)
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
