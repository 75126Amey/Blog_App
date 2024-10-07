package com.blog.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
private int id;
@NotEmpty
private String name;
@Email(message = "Email address is not Valid !!")
private String email;
@NotEmpty
@Size(min=6,max= 10,message = "Password must be min of 3 chars and max of 10 chars")
private String password;
@NotNull
private String about;
}
