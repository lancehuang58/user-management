package com.springbank.user.query.api.handlers;

import com.springbank.user.core.models.User;
import com.springbank.user.query.api.dto.UserLookupResponse;
import com.springbank.user.query.api.queries.FindAllUserQuery;
import com.springbank.user.query.api.queries.FindUserByIdQuery;
import com.springbank.user.query.api.queries.SearchUsersQuery;
import com.springbank.user.query.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@AllArgsConstructor
@Service
public class UserQueryHandlerImpl implements UserQueryHandler {

    private final UserRepository userRepository;


    @QueryHandler
    @Override
    public UserLookupResponse getUserById(FindUserByIdQuery query) {
        Optional<User> optUser = userRepository.findById(query.getId());
        return new UserLookupResponse(optUser.orElse(null));
    }

    @QueryHandler
    @Override
    public UserLookupResponse searchUsers(SearchUsersQuery searchUsersQuery) {
        String filter = searchUsersQuery.getFilter();
        filter = String.format("^.*%s.*$", filter);
        log.info("filter {}", filter);
        List<User> users = new ArrayList<>(userRepository.findByFilterRegex(filter));
        return new UserLookupResponse(users);
    }

    @QueryHandler
    @Override
    public UserLookupResponse getAllUsers(FindAllUserQuery query) {
        List<User> users = new ArrayList<>(userRepository.findAll());
        return new UserLookupResponse(users);
    }
}
