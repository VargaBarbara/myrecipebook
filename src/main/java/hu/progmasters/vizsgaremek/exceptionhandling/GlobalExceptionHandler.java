package hu.progmasters.vizsgaremek.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //NoAuthorityForActionException                             ok
    //RatingNotFoundException                                   ok
    //RecipeNotFoundException                                   ok
    //UserNotFoundException                                     ok
    //EmailIsAlreadyInUseException                              ok
    //MethodArgumentNotValidException                           ok

    //TODO log

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> handleValidationException(MethodArgumentNotValidException exception){
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailIsAlreadyInUseException.class)
    public ResponseEntity<List<ValidationError>> handleEmailIsAlreadyInUseException(EmailIsAlreadyInUseException exception){
        ValidationError validationError = new ValidationError("email", "the email address " +
                exception.getEmail() + " is already in use");
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleUserNotFoundException(UserNotFoundException exception) {
        ValidationError validationError = new ValidationError("userID", "user with id " +
                exception.getUserId() + " is not found");
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleRecipeNotFoundException(RecipeNotFoundException exception) {
        ValidationError validationError = new ValidationError("recipeId", "user with id " +
                exception.getRecipeId() + " is not found");
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RatingNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleRatingNotFoundException(RatingNotFoundException exception) {
        ValidationError validationError = new ValidationError("userId and recipeId", "rating by user " +
                exception.getUserId() + " for recipe " + exception.getRecipeId() + " is not found");
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAuthorityForActionException.class)
    public ResponseEntity<List<ValidationError>> handleNoAuthorityForActionException(NoAuthorityForActionException exception) {
        ValidationError validationError = new ValidationError("actionUserId", "action for user " +
                exception.getActionUserId() + " is not possible with user " + exception.getLoginUserId() + " logged in");
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }


}
