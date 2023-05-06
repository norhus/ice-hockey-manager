package cz.muni.fi.pa165.client;

import cz.muni.fi.pa165.model.dto.TeamDto;

import java.time.Instant;

public class HockeyPlayerForm {

        private Long id;

        private String firstName;

        private String lastName;

        private Instant dateOfBirth;

        private String position;

        private Integer skating;

        private Integer physical;

        private Integer shooting;

        private Integer defense;

        private Integer puckSkills;

        private Integer senses;

        private TeamDto teamDto;

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

        public TeamDto getTeamDto() {
                return teamDto;
        }

        public void setTeamDto(TeamDto teamDto) {
                this.teamDto = teamDto;
        }
}
