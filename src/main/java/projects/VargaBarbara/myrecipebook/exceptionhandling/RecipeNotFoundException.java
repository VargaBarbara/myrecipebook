package projects.VargaBarbara.myrecipebook.exceptionhandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RecipeNotFoundException extends RuntimeException{
    private Integer recipeId;
}
