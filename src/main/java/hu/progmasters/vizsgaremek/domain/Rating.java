package hu.progmasters.vizsgaremek.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    //@JoinColumn(name = "user_id", nullable = false)
    private User user;  //TODO

    @ManyToOne
    //@JoinColumn(name = "receipt_id", nullable = false)      //TODO column vagy joincolumn?
    private Receipt receipt;  //TODO

    @Column(name = "fingers", nullable = false)
    private Integer fingers;                                    //TODO min-max

}
