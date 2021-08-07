package hu.progmasters.vizsgaremek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipeCreateUpdateCommand {

    @Schema(description = "the preparation of the food",
            example = "Szóval ez ide, azt meg oda, majd összekevered, megsütöd és kész.")
    private String preparation;

    @Schema(description = "some notes for the preparation",
            example = "Érdemes belőle 2 adagot készíteni, hogy a későn érkezőknek is jusson.")
    private String note;

}
