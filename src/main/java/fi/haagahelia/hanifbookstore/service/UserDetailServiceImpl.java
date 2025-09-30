package fi.haagahelia.hanifbookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fi.haagahelia.hanifbookstore.domain.User;
import fi.haagahelia.hanifbookstore.domain.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User curruser = repository.findByUsername(username);

        if (curruser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        UserDetails user = org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(curruser.getPasswordHash())
                .roles(curruser.getRole())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

        return user;
    }
}