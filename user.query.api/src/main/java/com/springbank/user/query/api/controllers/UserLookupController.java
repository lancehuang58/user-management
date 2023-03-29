package com.springbank.user.query.api.controllers;

import com.springbank.user.query.api.dto.UserLookupResponse;
import com.springbank.user.query.api.queries.FindAllUserQuery;
import com.springbank.user.query.api.queries.FindUserByIdQuery;
import com.springbank.user.query.api.queries.SearchUsersQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/userLookup")
public class UserLookupController {

    private final QueryGateway queryGateway;

    @GetMapping()
    public ResponseEntity<UserLookupResponse> getAllUsers() {
        try {
            FindAllUserQuery query = new FindAllUserQuery();
            return returnByQuery(queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)));
        } catch (Exception e) {
            String safeErrorMessage = "Fail to complete get all users request";
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserLookupResponse> getUserById(@PathVariable String id) {
        try {
            FindUserByIdQuery query = new FindUserByIdQuery();
            query.setId(id);
            return returnByQuery(queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)));
        } catch (Exception e) {
            String safeErrorMessage = "Fail to complete get user by id request";
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("byFilter/{filter}")
    public ResponseEntity<UserLookupResponse> searchUserByFilter(@PathVariable String filter) {
        try {
            SearchUsersQuery query = new SearchUsersQuery();
            query.setFilter(filter);
            return returnByQuery(queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)));
        } catch (Exception e) {
            String safeErrorMessage = "Fail to complete search user by filter request";
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<UserLookupResponse> returnByQuery(CompletableFuture<UserLookupResponse> queryGateway) {
        UserLookupResponse response = queryGateway.join();
        if (response == null || response.getUsers() == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
