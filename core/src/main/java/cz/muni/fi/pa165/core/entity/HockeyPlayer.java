package cz.muni.fi.pa165.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "hockey_player")
public class HockeyPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 64)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;

    @Size(max = 64)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private Instant dateOfBirth;

    @Size(max = 16)
    @NotNull
    @Column(name = "position", nullable = false, length = 16)
    private String position;

    @NotNull
    @Column(name = "skating", nullable = false)
    private Integer skating;

    @NotNull
    @Column(name = "physical", nullable = false)
    private Integer physical;

    @NotNull
    @Column(name = "shooting", nullable = false)
    private Integer shooting;

    @NotNull
    @Column(name = "defense", nullable = false)
    private Integer defense;

    @NotNull
    @Column(name = "puck_skills", nullable = false)
    private Integer puckSkills;

    @NotNull
    @Column(name = "senses", nullable = false)
    private Integer senses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
