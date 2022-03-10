package projects.VargaBarbara.myrecipebook.exceptionhandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RatingNotFoundException extends RuntimeException{
    private Integer userId;
    private Integer recipeId;
}
