package com.vitaliyhtc.tasksboard.service;

import com.vitaliyhtc.tasksboard.repositories.UserRepository;
import com.vitaliyhtc.tasksboard.model.Role;
import com.vitaliyhtc.tasksboard.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link org.springframework.security.core.userdetails.UserDetailsService} interface
 *
 * @author Vitaliy Rozumyak
 * @version 1.0
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for(Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                user.isAccountNonLocked(),
                grantedAuthorities);
    }

    /*
    >>> org.springframework.security.core.userdetails.User;
    this(username, password, true, true, true, true, authorities);
    public User(
    String username,                +
    String password,                +
    boolean enabled,                +
    boolean accountNonExpired,      -
    boolean credentialsNonExpired,  -
    boolean accountNonLocked,       +
    Collection<? extends GrantedAuthority> authorities  +) {
    */
    /* see baeldung.com
    public UserDetails loadUserByUsername1(String email)
            throws UsernameNotFoundException {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException(
                        "No user found with username: " + email);
            }

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword().toLowerCase(),
                    user.isEnabled(),
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    getAuthorities(user.getRole().getRole()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    */
}
