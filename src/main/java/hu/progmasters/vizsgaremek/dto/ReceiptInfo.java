package hu.progmasters.vizsgaremek.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ReceiptInfo {

    private Integer id;
    private List<String> ingredients;
    private String preparation;
    private String note;
    private Integer creatorId;
    private LocalDateTime creationTime;
    private LocalDateTime lastEditTime;

}
