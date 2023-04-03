package com.filoshare.app.services.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.filoshare.app.repositories.user.UserRepository;
import com.filoshare.app.models.user.User;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> opt = userRepository.findByEmail(email);
        if(opt.isEmpty()) {
            throw new UsernameNotFoundException("User with email: "+ email + " does not exists!");
        } else {
            User user = opt.get();
            return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities());
        }
    }

    @Override
    public String saveUser(User user) {
        
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        user = userRepository.save(user);
        return user.getUserId();

    }
    
    
}
