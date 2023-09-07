package com.example.apirestsoccerplayers.user;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String addUser(User request){
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .roles(request.getRoles())
            .build();

        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e);
            return "User cannot saved";
        }
        
        return "User saved";
    }

    public String deleteUser(Integer id){
        userRepository.deleteById(id);
        return "User deleted";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user or password incorrect"));
    }
}
