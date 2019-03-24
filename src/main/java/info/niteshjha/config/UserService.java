package info.niteshjha.config;

import info.niteshjha.model.User;
import info.niteshjha.repository.UserCreateRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

@Service
public class UserService implements UserDetailsService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private UserCreateRepository userCreateRepository;

    public UserService(UserCreateRepository userCreateRepository) {
        this.userCreateRepository = userCreateRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userCreateRepository.findByEmail(email);

        if (user == null) {
            logger.info("No User found with email :: " + email);
            throw new UsernameNotFoundException("No user found with username :: " + email);
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities("ROLE_USER"));
    }


    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
