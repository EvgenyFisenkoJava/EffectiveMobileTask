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
     * Метод авторизации пользователя
     * <br>
     *
     * @param userName логин пользователя
     * @param password пароль пользователя
     * @return авторизованный пользователь
     */
    @Override
    public boolean login(String userName, String password) {
        // logger.info("Invoke method login");
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
    }

    /**
     * Метод регистрации пользователя
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param registerReq логин пользователя
     * @param role        роль пользователя
     * @return зарегистрированный пользователь
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
        userRepository.save(user);

        return true;
    }

}
