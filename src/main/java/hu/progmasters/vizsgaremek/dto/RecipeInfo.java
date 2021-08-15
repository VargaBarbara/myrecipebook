package hu.progmasters.vizsgaremek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeInfo {

    @Schema(description = "the recipe's id", example = "1")
    private Integer id;

    @Schema(description = "the preparation of the food",
            example = "Szóval ez ide, azt meg oda, majd összekevered, megsütöd és kész.")
    private String preparation;

    @Schema(description = "some notes for the preparation",
            example = "Érdemes belőle 2 adagot készíteni, hogy a későn érkezőknek is jusson.")
    private String note;

    @Schema(description = "the creator user's id", example = "1")
    private Integer creatorId;

    @Schema(description = "the date on which the recipe was created", example = "2021-08-07")
    private LocalDate creationDate;

    @Schema(description = "the last date on which the recipe was updated", example = "2021-08-08")
    private LocalDate lastEditDate;

    @Schema(description = "the average rating for the recipe", example = "7.25")
    private Double rating;

}
