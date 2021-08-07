package hu.progmasters.vizsgaremek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserInfo {

    @Schema(description = "the user's id", example = "1")
    private Integer id;

    @Schema(description = "username", example = "SnowWhite1999")
    private String name;

    @Schema(description = "the user's email", example = "snow.white1999@example.com")
    private String email;

    private List<RecipeInfo> recipes;

}
