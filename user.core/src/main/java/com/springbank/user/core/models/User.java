package com.springbank.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collation = "users")
public class User {
    @Id
    private String id;
    @NotBlank(message = "first name required.")
    private String firstname;
    @NotBlank(message = "last name required.")
    private String lastname;
    @Email(message = "please input validate email address.")
    private String emailAddress;
    @NotNull
    @Valid
    private Account account;
}
