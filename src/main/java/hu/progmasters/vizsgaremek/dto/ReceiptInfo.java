package hu.progmasters.vizsgaremek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReceiptInfo {

    @Schema(description = "the receipt's id", example = "1")
    private Integer id;

    @Schema(description = "the preparation of the food",
            example = "Szóval ez ide, azt meg oda, majd összekevered, megsütöd és kész.")
    private String preparation;

    @Schema(description = "some notes for the preparation",
            example = "Érdemes belőle 2 adagot készíteni, hogy a későn érkezőknek is jusson.")
    private String note;

    @Schema(description = "the creator user's id", example = "1")
    private Integer creatorId;

    @Schema(description = "the date on which the receipt was created", example = "2021-08-07")
    private LocalDate creationDate;

    @Schema(description = "the last date on which the receipt was updated", example = "2021-08-08")
    private LocalDate lastEditDate;

}
