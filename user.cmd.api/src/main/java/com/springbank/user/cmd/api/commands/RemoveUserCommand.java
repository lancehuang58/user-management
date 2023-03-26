package com.springbank.user.cmd.api.commands;

import com.springbank.user.core.models.User;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class RemoveUserCommand {
    @TargetAggregateIdentifier
    private String id;
    private User user;
}
