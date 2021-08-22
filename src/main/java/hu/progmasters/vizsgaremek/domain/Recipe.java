package hu.progmasters.vizsgaremek.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "preparation", length = 4000, nullable = false)
    private String preparation;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "last_edit_date", nullable = false)
    private LocalDate lastEditDate;

}
