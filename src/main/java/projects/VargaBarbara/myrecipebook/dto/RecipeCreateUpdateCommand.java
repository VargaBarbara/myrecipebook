package projects.VargaBarbara.myrecipebook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCreateUpdateCommand {

    @NotBlank(message = "cannot be blank")
    @Schema(description = "the preparation of the food",
            example = "Szóval ez ide, azt meg oda, majd összekevered, megsütöd és kész.")
    private String preparation;

    @Length(max = 100, message = "maximum length 100")
    @Schema(description = "some notes for the preparation",
            example = "Érdemes belőle 2 adagot készíteni, hogy a későn érkezőknek is jusson.")
    private String note;

}
