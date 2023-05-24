package cz.muni.fi.pa165.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 64)
    @NotNull
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private User appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id")
    private League league;

    @OneToMany(mappedBy = "team")
    private Set<HockeyPlayer> hockeyPlayers = new LinkedHashSet<>();

    public void addHockeyPlayers(List<HockeyPlayer> hockeyPlayers) {
        this.hockeyPlayers.addAll(hockeyPlayers);
        hockeyPlayers.forEach(it -> it.setTeam(this));
    }

    public void removeHockeyPlayers(List<HockeyPlayer> hockeyPlayers) {
        hockeyPlayers.forEach(this.hockeyPlayers::remove);
        hockeyPlayers.forEach(it -> it.setTeam(null));
    }
}