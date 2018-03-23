package vk.com.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import vk.com.library.entities.UserRole;
import vk.com.library.validations.PasswordMatches;
import vk.com.library.validations.markers.CreateMarker;
import vk.com.library.validations.markers.UpdateMarker;
import vk.com.library.validations.markers.UpdatePasswordMarker;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@PasswordMatches(groups = { CreateMarker.class, UpdatePasswordMarker.class })
@JsonIgnoreProperties(value = {"password", "passwordConfirmation"}, allowSetters = true)
public class UserDto {
    @NotNull(groups = UpdateMarker.class)
    private Integer id;
    @NotNull(groups = CreateMarker.class)
    @Size(min = 3, max = 50, groups = CreateMarker.class)
    private String username;
    @NotNull(groups = { CreateMarker.class, UpdatePasswordMarker.class})
    @Size(min = 3, groups = { CreateMarker.class, UpdatePasswordMarker.class})
    private String password;
    private String passwordConfirmation;
    private Boolean active;
    private Set<UserRole> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
