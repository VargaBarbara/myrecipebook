package hu.progmasters.vizsgaremek.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@Entity
@Table(name = "receipt")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "preparation", length = 4000, nullable = false)
    private String preparation;

    @Column(name = "note", length = 255, nullable = true)
    private String note;

    @ManyToOne
    //@Column(name = "creator_id", nullable = false)                   //TODO  vagy joincolumn?
    private User creator;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "last_edit_date", nullable = false)
    private LocalDate lastEditDate;

    //@Column(name = "rating")
    //private Double rating;

}
