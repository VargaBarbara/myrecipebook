package hu.progmasters.vizsgaremek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateUpdateCommand {

    @Schema(description = "here comes the username", example = "SnowWhite1999")
    private String name;

    @Schema(description = "please use here a valid email address", example = "snow.white1999@example.com")
    private String email;

}
