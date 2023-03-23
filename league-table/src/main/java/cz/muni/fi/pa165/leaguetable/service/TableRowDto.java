package cz.muni.fi.pa165.leaguetable.service;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class TableRowDto {

    private String teamName;

    private int rank;

    private int matchesPlayed;

    private int won;

    private int drawn;

    private int lost;

    private int goalsFor;

    private int goalsAgainst;

    private int points;

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
        {
            return true;
        }

        if(!(obj instanceof TableRowDto)){
            return false;
        }

        TableRowDto row = (TableRowDto) obj;

        return (Objects.equals(teamName, row.teamName));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.teamName != null ? this.teamName.hashCode() : 0);
        return hash;
    }
}
