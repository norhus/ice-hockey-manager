package cz.muni.fi.pa165.core.entity;

import cz.muni.fi.pa165.model.shared.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 1024)
    @NotNull
    @Column(name = "email", nullable = false, length = 1024)
    private String email;

    @Size(max = 64)
    @NotNull
    @Column(name = "password", length = 64)
    private String password;

    @NotNull
    @Column(name = "role", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;
}
