package com.ftn.paymentsystem.dto;

import  java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private String address;

    private LocalDate dateOfBirth;

    private String phoneNumber;

    private boolean blocked;

    private boolean deleted;

    @NotBlank
    private String userType;

}