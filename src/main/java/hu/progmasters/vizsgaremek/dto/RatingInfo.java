package hu.progmasters.vizsgaremek.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RatingInfo {

    private Integer id;
    private Integer userId;
    private Integer receiptId;
    private Integer fingers;

}
