package com.project.blog.payloads;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.blog.entites.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min=4,message = "Username must be min of 4 characters")
    private String name;
    @Email(message = "Email address is not valid")
    private String email;
    @NotEmpty
    @Size(min=5,message = "Password must be min of 5 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotEmpty
    private String about;
    private Set<RoleDto> roles=new HashSet<>();

}
