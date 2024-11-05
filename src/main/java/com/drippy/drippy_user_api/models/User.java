package com.drippy.drippy_user_api.models;

import com.drippy.drippy_user_api.enums.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private UUID id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 16, unique = true)
    private String username;

    @Column(nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private Profile profile;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false, name = "is_email_verified")
    private Boolean isEmailVerified;

    @Column(nullable = false, name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
