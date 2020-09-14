package com.ftn.scientificcentral.payload.request;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private LocalDate dateOfBirth;

    private String address;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private String phoneNumber;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private Set<String> role;

}
