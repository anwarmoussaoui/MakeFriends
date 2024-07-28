package com.workshop.back.Controller;

import com.workshop.back.users.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RegisterRequest {
    private String name;
    private String email;
    private String lastName;
    private String password;
}
