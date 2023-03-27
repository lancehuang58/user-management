package com.springbank.user.query.api.handlers;

import com.springbank.user.core.models.User;
import com.springbank.user.query.api.dto.UserLookupResponse;
import com.springbank.user.query.api.queries.FindAllUserQuery;
import com.springbank.user.query.api.queries.FindUserByIdQuery;
import com.springbank.user.query.api.queries.SearchUsersQuery;
import com.springbank.user.query.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserQueryHandlerImpl implements UserQueryHandler {

    private final UserRepository userRepository;


    @QueryHandler
    @Override
    public UserLookupResponse getUserById(FindUserByIdQuery query) {

        return null;
    }

    @QueryHandler
    @Override
    public UserLookupResponse searchUsers(SearchUsersQuery query) {

        return null;
    }

    @QueryHandler
    @Override
    public UserLookupResponse getAllUsers(FindAllUserQuery query) {
        List<User> users = new ArrayList<>(userRepository.findAll());
        return new UserLookupResponse(users);
    }
}
