package hu.progmasters.vizsgaremek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RatingInfo {

    @Schema(description = "the rating's id", example = "1")
    private Integer id;

    @Schema(description = "the user's id", example = "1")
    private Integer userId;

    @Schema(description = "the receipt's id", example = "1")
    private Integer receiptId;

    @Schema(description = "rating - the number of licked fingers", example = "7")
    private Integer fingers;

}
