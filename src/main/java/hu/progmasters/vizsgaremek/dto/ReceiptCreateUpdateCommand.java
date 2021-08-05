package hu.progmasters.vizsgaremek.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReceiptCreateUpdateCommand {

    //private List<String> ingredients;
    private String preparation;
    private String note;
    //private LocalDate lastEditDate;

}
