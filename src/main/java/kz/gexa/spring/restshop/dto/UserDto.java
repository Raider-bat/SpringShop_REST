package kz.gexa.spring.restshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kz.gexa.spring.restshop.entity.user.Role;
import kz.gexa.spring.restshop.entity.user.User;


import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private long id;
    private String username;
    private String password;
    private boolean active;
    private Set<Role> roles;

    public UserDto(long id, String username,boolean active, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.active = active;
        this.roles = roles;
    }

    public UserDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User toUser(){
        return new User(id,username,active,roles);
    }

    public static UserDto fromUser(User user){
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.isActive(),
                user.getRoles()
        );
    }


    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
}
