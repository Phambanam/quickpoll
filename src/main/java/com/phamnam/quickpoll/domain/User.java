package com.phamnam.quickpoll.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue
    @Column(name="USER_ID")
    private Long id;
    @Column(name="USERNAME")
    @NotEmpty
    private String username;
    @Column(name="PASSWORD")
    @NotEmpty
    @JsonIgnore
    private String password;
    @Column(name="FIRST_NAME")
    @NotEmpty
    private String firstName;
    @Column(name="LAST_NAME")
    @NotEmpty
    private String lastName;
    @Column(name="ADMIN", columnDefinition="char(3)")
    @Type(type="yes_no")
    @NotEmpty
    private boolean admin;
// Getters and Setters omitted for brevity
}