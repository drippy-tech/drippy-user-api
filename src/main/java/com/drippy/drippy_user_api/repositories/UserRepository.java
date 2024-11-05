package com.drippy.drippy_user_api.repositories;

import com.drippy.drippy_user_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
