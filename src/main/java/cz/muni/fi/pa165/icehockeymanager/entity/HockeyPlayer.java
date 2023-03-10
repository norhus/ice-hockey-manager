package cz.muni.fi.pa165.icehockeymanager.entity;

import cz.muni.fi.pa165.icehockeymanager.entity.Ability;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
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

    @ManyToMany
    @JoinTable(name = "hockey_player_x_ability",
            joinColumns = @JoinColumn(name = "hockey_player_id"),
            inverseJoinColumns = @JoinColumn(name = "ability_id"))
    private Set<Ability> abilities = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getSkating() {
        return skating;
    }

    public void setSkating(Integer skating) {
        this.skating = skating;
    }

    public Integer getPhysical() {
        return physical;
    }

    public void setPhysical(Integer physical) {
        this.physical = physical;
    }

    public Integer getShooting() {
        return shooting;
    }

    public void setShooting(Integer shooting) {
        this.shooting = shooting;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getPuckSkills() {
        return puckSkills;
    }

    public void setPuckSkills(Integer puckSkills) {
        this.puckSkills = puckSkills;
    }

    public Integer getSenses() {
        return senses;
    }

    public void setSenses(Integer senses) {
        this.senses = senses;
    }

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(Set<Ability> abilities) {
        this.abilities = abilities;
    }

}