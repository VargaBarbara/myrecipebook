package hu.progmasters.vizsgaremek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserCreateUpdateCommand {

    @NotBlank(message = "cannot be blank")
    @Schema(description = "here comes the username", example = "SnowWhite1999")
    private String name;

    @NotBlank(message = "cannot be blank")
    @Email(message = "format must be email")
    @Schema(description = "please use here a valid email address", example = "snow.white1999@example.com")
    private String email;

}
