package hu.progmasters.vizsgaremek.exceptionhandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class EmailIsAlreadyInUseException extends RuntimeException{
    private String email;
}
