package hu.progmasters.vizsgaremek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingCreateUpdateCommand {

    @NotBlank(message = "cannot be blank")
    @Min(value = 1, message = "must be minimum 1")
    @Max(value = 10, message = "must be maximum 10")
    @Schema(description = "how many fingers would you lick after eating this", example = "7")
    private Integer fingers;

}
