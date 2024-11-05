package com.drippy.drippy_user_api.services;

import com.drippy.drippy_user_api.models.User;
import com.drippy.drippy_user_api.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
