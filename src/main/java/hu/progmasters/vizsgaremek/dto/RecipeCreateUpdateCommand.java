package hu.progmasters.vizsgaremek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCreateUpdateCommand {

    @NotBlank(message = "cannot be blank")
    @Schema(description = "the preparation of the food",
            example = "Szóval ez ide, azt meg oda, majd összekevered, megsütöd és kész.")
    private String preparation;

    @NotBlank(message = "cannot be blank")
    @Schema(description = "some notes for the preparation",
            example = "Érdemes belőle 2 adagot készíteni, hogy a későn érkezőknek is jusson.")
    private String note;

}
