package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.RegisterReq;
import com.example.effectivemobiletask.dto.mapper.UserMapper;
import com.example.effectivemobiletask.model.Role;
import com.example.effectivemobiletask.model.UserProfile;
import com.example.effectivemobiletask.repository.UserRepository;
import com.example.effectivemobiletask.service.AuthService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
/**

 * Implementation of AuthService interface for user authentication and registration.
 */

@Service
public class AuthServiceImpl implements AuthService {
    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;


    public AuthServiceImpl(UserDetailsManager manager, UserRepository userRepository) {
        this.manager = manager;
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    /**

     Authenticates a user based on the provided username and password.
     @param username the username to authenticate.
     @param password the password to authenticate.
     @return true if the authentication was successful, false otherwise.
     */
    @Override
    public boolean login(String username, String password) {
        // logger.info("Invoke method login");
        if (!manager.userExists(username)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(username);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
    }


    /**

     * Registers a new user with the provided registration information and assigns the given role to them.
     * @param registerReq The registration information of the new user
     * @param role The role to be assigned to the new user
     *@return true if the user is successfully registered, false otherwise
     */
    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        //logger.info("Invoke method register");
        if (manager.userExists(registerReq.getUsername())) {
            return false;
        }
        manager.createUser(
                User.withDefaultPasswordEncoder()
                        .password(registerReq.getPassword())
                        .username(registerReq.getUsername())
                        .roles(role.name())
                        .build()
        );
        UserProfile user = UserMapper.INSTANCE.registerReqToUser(registerReq);
        user.setEmail(registerReq.getEmail());
        user.setActive(true);
        userRepository.save(user);

        return true;
    }
}
