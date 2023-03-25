package cz.muni.fi.pa165.leaguetable.service;

import io.swagger.v3.oas.annotations.media.Schema;

public record TableRowDto(

    @Schema(example= "Poprad")
    String teamName,

    @Schema(example= "1")
    int rank,

    @Schema(example= "3")
    int matchesPlayed,

    @Schema(example= "2")
    int won,

    @Schema(example= "0")
    int drawn,

    @Schema(example= "1")
    int lost,

    @Schema(example= "12")
    int goalsFor,

    @Schema(example= "4")
    int goalsAgainst,

    @Schema(example= "6")
    int points
) {

    public TableRowDto(String teamName) {
        this(teamName, 0, 0, 0, 0, 0, 0, 0, 0);
    }
}
