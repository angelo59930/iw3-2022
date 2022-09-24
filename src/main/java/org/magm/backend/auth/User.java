package org.magm.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    private static final long serialVersionUID = -268680804253679932L;

    private boolean accountNonExpired = false;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled;

    public static final String VALIDATION_OK = "ok";
    public static final String VALIDATION_ACCOUNT_EXPIRED = "ACCOUNT EXPIRED";
    public static final String VALIDATION_CREDENTIALS_EXPIRED = "CREDENTIALS EXPIRED";
    public static final String VALIDATION_LOCKED = "LOCKED";
    public static final String VALIDATION_DISABLED = "DISABLED";

    public String validate(){
        if(!accountNonExpired)
            return VALIDATION_ACCOUNT_EXPIRED;
        if(!accountNonLocked)
            return VALIDATION_LOCKED;
        if(!credentialsNonExpired)
            return VALIDATION_CREDENTIALS_EXPIRED;
        if(!enabled)
            return VALIDATION_DISABLED;
        return VALIDATION_OK;
    }

    @Column(length = 255 , nullable = false , unique = true)
    private String email;

    @Id
    @GeneratedValue
    private Long idUser;

    @Column(length = 255 , unique = true)
    private String username;

    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "userroles", joinColumns = {
            @JoinColumn(name = "idUser" , referencedColumnName = "idUser")}, inverseJoinColumns = {
                @JoinColumn(name = "idRole" , referencedColumnName = "id") })
    private Set<Role> roles;

    @Transient
    public boolean isInRole(Role role) {
        return isInRole(role.getName());
    }
    @Transient
    public boolean isInRole(String role) {
        for (Role r : getRoles()) {
            if (r.getName().equals(role))
                return true;
        }
        return false;
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return authorities;
    }

    @Transient
    public List<String> getAuthoritiesStr() {
        List<String> authorities = getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());
        return authorities;
    }

}
