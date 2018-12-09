package com.alexeiboriskin.study.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@SuppressWarnings("unused")
@Entity
@DynamicUpdate
public class Role implements GrantedAuthority {

    private long id;
    private String role;

    public Role(String role) {
        this.role = role;
    }

    public Role() {
    }

    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Transient
    @JsonIgnore
    @Override
    public String getAuthority() {
        return role;
    }
}