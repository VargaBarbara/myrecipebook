package hu.progmasters.vizsgaremek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RatingCreateUpdateCommand {

    @Schema(description = "how many fingers would you lick after eating this", example = "7")
    private Integer fingers;

}
