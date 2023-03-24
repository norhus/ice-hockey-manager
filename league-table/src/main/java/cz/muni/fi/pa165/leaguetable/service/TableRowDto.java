package cz.muni.fi.pa165.leaguetable.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class TableRowDto {

    @Schema(example= "Poprad")
    private String teamName;

    @Schema(example= "1")
    private int rank;

    @Schema(example= "3")
    private int matchesPlayed;

    @Schema(example= "2")
    private int won;

    @Schema(example= "0")
    private int drawn;

    @Schema(example= "1")
    private int lost;

    @Schema(example= "12")
    private int goalsFor;

    @Schema(example= "4")
    private int goalsAgainst;

    @Schema(example= "6")
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
