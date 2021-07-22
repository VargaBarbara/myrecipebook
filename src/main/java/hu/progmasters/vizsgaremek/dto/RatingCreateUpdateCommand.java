package hu.progmasters.vizsgaremek.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RatingCreateUpdateCommand {

    private Integer userId;
    private Integer receiptId;
    private Integer fingers;

}
