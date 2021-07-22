package hu.progmasters.vizsgaremek.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Rating {

    private Integer id;
    private Integer userId;
    private Integer receiptId;
    private Integer fingers;

}
