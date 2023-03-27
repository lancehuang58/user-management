package com.springbank.user.query.api.dto;

import com.springbank.user.core.dto.BaseResponse;
import com.springbank.user.core.models.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class UserLookupResponse extends BaseResponse {

    private final List<User> users;

    public UserLookupResponse(String message) {
        super(message);
        this.users = new ArrayList<>();
    }

    public UserLookupResponse( User user) {
        super(null);
        this.users = new ArrayList<>();
        if (user != null) {
            users.add(user);
        }
    }

    public UserLookupResponse(List<User> users) {
        super(null);
        this.users = new ArrayList<>(users);
    }

}
