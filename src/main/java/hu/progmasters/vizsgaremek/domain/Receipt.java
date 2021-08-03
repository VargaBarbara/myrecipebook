package hu.progmasters.vizsgaremek.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
//import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //private List<String> ingredients;  //TODO
    private String preparation;
    private String note;

    @ManyToOne
    private User creator;
    private LocalDate creationDate;
    private LocalDate lastEditDate;

    //private Double rating;

}
