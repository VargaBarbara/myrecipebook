package projects.VargaBarbara.myrecipebook.service;

import projects.VargaBarbara.myrecipebook.domain.Rating;
import projects.VargaBarbara.myrecipebook.domain.Recipe;
import projects.VargaBarbara.myrecipebook.domain.User;
import projects.VargaBarbara.myrecipebook.dto.*;
import projects.VargaBarbara.myrecipebook.exceptionhandling.*;
import projects.VargaBarbara.myrecipebook.repository.RatingRepository;
import projects.VargaBarbara.myrecipebook.repository.RecipeRepository;
import projects.VargaBarbara.myrecipebook.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private RecipeRepository recipeRepository;
    private RatingRepository ratingRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserService(RecipeRepository recipeRepository,
                       RatingRepository ratingRepository,
                       UserRepository userRepository,
                       ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    //User methods----------------------------------------------------------------------------------------------------

    public UserInfo saveUser(UserCreateUpdateCommand command) {
        Optional<User> userWithEmail = userRepository.findByEmail(command.getEmail());
        if (userWithEmail.isPresent()) {
            throw new EmailIsAlreadyInUseException(command.getEmail());
        }
        User toSave = modelMapper.map(command, User.class);
        toSave.setId(null);
        toSave.setRecipes(new ArrayList<>());
        User saved = userRepository.save(toSave).get();
        return modelMapper.map(saved, UserInfo.class);
    }

    public List<UserInfo> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertUserToUserInfo).collect(Collectors.toList());
    }

    public UserInfo findUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return convertUserToUserInfo(user.get());
    }

    public UserInfo updateUser(Integer toUpdateId, Integer loggedInUserId, UserCreateUpdateCommand command) {
        Optional<User> oldUser = userRepository.findById(toUpdateId);
        User toUpdate = modelMapper.map(command, User.class);
        toUpdate.setId(toUpdateId);
        Optional<User> userWithCommandEmail = userRepository.findByEmail(command.getEmail());
        if (oldUser.isEmpty()) {
            throw new UserNotFoundException(toUpdateId);
        } else if(!toUpdate.getId().equals(loggedInUserId)) {
            throw new NoAuthorityForActionException(loggedInUserId, toUpdateId);
        } else if (userWithCommandEmail.isPresent() && !toUpdateId.equals(userWithCommandEmail.get().getId())) {
            throw new EmailIsAlreadyInUseException(command.getEmail());
        }
        toUpdate.setRecipes(oldUser.get().getRecipes());

        User saved = userRepository.update(toUpdate).get();
        return convertUserToUserInfo(saved);
    }

    public UserInfo deleteUser(Integer toDeleteId, Integer loggedInUserId, Boolean deleteRecipes) {
        Optional<User> toDelete = userRepository.findById(toDeleteId);
        UserInfo toReturn;
        if (toDelete.isEmpty()) {
            throw new UserNotFoundException(toDeleteId);
        } else if(!toDelete.get().getId().equals(loggedInUserId)) {
            throw new NoAuthorityForActionException(loggedInUserId, toDeleteId);
        } else if(deleteRecipes) {
            toReturn = deleteUserWithRecipes(toDelete.get());
        } else {
            toReturn = deleteUserWithoutRecipes(toDelete.get());
        }

        return toReturn;
    }

    public UserInfo deleteUserWithRecipes(User toDelete) {
        List<Recipe> toDeleteRecipes = toDelete.getRecipes();
        for (Recipe recipe : toDeleteRecipes) {
            recipeRepository.delete(recipe);
        }
        User deleted = userRepository.delete(toDelete).get();
        deleted.setRecipes(toDeleteRecipes);
        return convertUserToUserInfo(deleted);
    }

    public UserInfo deleteUserWithoutRecipes(User toDelete) {
        List<Recipe> toDeleteRecipes = toDelete.getRecipes();
        for (Recipe recipe : toDeleteRecipes) {
            recipe.setCreator(null);
        }
        User deleted = userRepository.delete(toDelete).get();
        deleted.setRecipes(List.of());
        return convertUserToUserInfo(deleted);
    }

    //Recipe methods--------------------------------------------------------------------------------------------------

    public RecipeInfo saveRecipe(Integer userId, LocalDate creationDate, RecipeCreateUpdateCommand command) {
        // Mapping to Recipe
        Recipe toSave = modelMapper.map(command, Recipe.class);
        toSave.setId(null);
        Optional<User> userToSet = userRepository.findById(userId);
        if (userToSet.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        toSave.setCreator(userToSet.get());
        toSave.setCreationDate(creationDate);
        toSave.setLastEditDate(creationDate);
        //Save and mapping to Info
        Optional<Recipe> saved = recipeRepository.save(toSave);
        Recipe savedRecipe = saved.get();
        return convertRecipeToRecipeInfo(savedRecipe);
    }

    public List<RecipeInfo> findAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(this::convertRecipeToRecipeInfo).collect(Collectors.toList());
    }

    public RecipeInfo findRecipeById(Integer recipeId) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException(recipeId);
        }
        return convertRecipeToRecipeInfo(recipe.get());
    }

    public RecipeInfo findRandomRecipe() {
        List<Recipe> recipes = recipeRepository.findAll();
        int random = ThreadLocalRandom.current().nextInt(0, recipes.size());
        return convertRecipeToRecipeInfo(recipes.get(random));
    }

    public List<RecipeInfo> findRecipesByUser(Integer userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        List<Recipe> recipes = recipeRepository.findByUser(userId);
        return recipes.stream()
                .map(this::convertRecipeToRecipeInfo).collect(Collectors.toList());
    }

    public RecipeInfo updateRecipe(Integer recipeId, Integer userId,
                                   LocalDate editDate, RecipeCreateUpdateCommand command) {

        Optional<Recipe> toUpdate = recipeRepository.findById(recipeId);
        if (toUpdate.isEmpty()) {
          throw new RecipeNotFoundException(recipeId);
        } else if (!userId.equals(toUpdate.get().getCreator().getId())) {
            throw new NoAuthorityForActionException(userId, toUpdate.get().getCreator().getId());
        }
        toUpdate.get().setPreparation(command.getPreparation());
        toUpdate.get().setNote(command.getNote());
        toUpdate.get().setLastEditDate(editDate);
        Recipe updated = recipeRepository.update(toUpdate.get()).get();
        return convertRecipeToRecipeInfo(updated);
    }

    public RecipeInfo deleteRecipe(Integer recipeId, Integer userId) {
        Optional<Recipe> toDelete = recipeRepository.findById(recipeId);
        if (toDelete.isEmpty()) {
            throw new RecipeNotFoundException(recipeId);
        } else if (!userId.equals(toDelete.get().getCreator().getId())) {
            throw new NoAuthorityForActionException(userId, toDelete.get().getCreator().getId());
        }
        Recipe deleted = recipeRepository.delete(toDelete.get()).get();
        return convertRecipeToRecipeInfo(deleted);
    }

    //Rating methods--------------------------------------------------------------------------------------------------

    public RatingInfo saveOrUpdateRating(Integer userId, Integer recipeId, RatingCreateUpdateCommand command) {
        RatingInfo toReturn;
        if (ratingRepository.findByUserAndRecipe(userId, recipeId).isEmpty()) {
            toReturn = saveRating(userId, recipeId, command);
        } else {
            toReturn = updateRating(userId, recipeId, command);
        }
        return toReturn;
    }

    public RatingInfo saveRating(Integer userId, Integer recipeId, RatingCreateUpdateCommand command) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        Optional<User> user = userRepository.findById(userId);
        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException(recipeId);
        } else if (user.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        Rating toSave = modelMapper.map(command, Rating.class);
        toSave.setUser(user.get());
        toSave.setRecipe(recipe.get());
        Rating saved = ratingRepository.saveRating(toSave).get();
        return convertRatingToRatingInfo(saved);
    }

    public RatingInfo findRatingByUserAndRecipe(Integer userId, Integer recipeId) {
        Optional<Rating> rating = ratingRepository.findByUserAndRecipe(userId, recipeId);
        if (rating.isEmpty()) {
            throw new RatingNotFoundException(userId, recipeId);
        }
        return convertRatingToRatingInfo(rating.get());
    }

    public List<RatingInfo> findAllRatingsByUser(Integer userId) {
        List<Rating> ratings = ratingRepository.findAllByUser(userId);
        return ratings.stream().map(this::convertRatingToRatingInfo).collect(Collectors.toList());
    }

    public RatingInfo updateRating(Integer userId, Integer recipeId, RatingCreateUpdateCommand command) {
        //itt nincs szükség jogosultság ellenőrzésre, mert a userId @PathVariable, nem @RequestParam
        Optional<Rating> toUpdate = ratingRepository.findByUserAndRecipe(userId, recipeId);
        if (toUpdate.isEmpty()) {
            throw new RatingNotFoundException(userId, recipeId);
        }
        toUpdate.get().setFingers(command.getFingers());
        Rating updated = ratingRepository.updateRating(toUpdate.get()).get();
        return convertRatingToRatingInfo(updated);
    }

    public RatingInfo deleteRating(Integer userId, Integer recipeId) {
        //itt nincs szükség jogosultság ellenőrzésre, mert a userId @PathVariable, nem @RequestParam
        Optional<Rating> toDelete = ratingRepository.findByUserAndRecipe(userId, recipeId);
        if (toDelete.isEmpty()) {
            throw new RatingNotFoundException(userId, recipeId);
        }
        Rating deleted = ratingRepository.deleteRating(toDelete.get()).get();
        return convertRatingToRatingInfo(deleted);
    }

    //Converting methods----------------------------------------------------------------------------------------------

    private UserInfo convertUserToUserInfo(User user) {
        UserInfo userInfo = modelMapper.map(user, UserInfo.class);
        List<RecipeInfo> recipeInfos = user.getRecipes().stream()
                .map(this::convertRecipeToRecipeInfo).collect(Collectors.toList());
        userInfo.setRecipes(recipeInfos);
        return userInfo;
    }

    private RecipeInfo convertRecipeToRecipeInfo(Recipe recipe) {
        RecipeInfo recipeInfo = modelMapper.map(recipe, RecipeInfo.class);
        try {
            recipeInfo.setCreatorId(recipe.getCreator().getId());
        } catch (NullPointerException exception) {
            recipeInfo.setCreatorId(0);
        }
        Optional<Double> averageRating = ratingRepository.getAverageRating(recipe.getId());
        if (averageRating.isEmpty()) {
            recipeInfo.setRating(0.0);
        } else {
            Double avgRating = (double) Math.round(averageRating.get()*100)/100;
            recipeInfo.setRating(avgRating);
        }
        return recipeInfo;
    }

    private RatingInfo convertRatingToRatingInfo(Rating rating) {
        RatingInfo ratingInfo = modelMapper.map(rating, RatingInfo.class);
        ratingInfo.setUserId(rating.getUser().getId());
        ratingInfo.setRecipeId(rating.getRecipe().getId());
        return ratingInfo;
    }

}
