package hu.progmasters.vizsgaremek.service;

import hu.progmasters.vizsgaremek.domain.Rating;
import hu.progmasters.vizsgaremek.domain.Receipt;
import hu.progmasters.vizsgaremek.domain.User;
import hu.progmasters.vizsgaremek.dto.*;
import hu.progmasters.vizsgaremek.exceptionhandling.NoAuthorityForActionException;
import hu.progmasters.vizsgaremek.exceptionhandling.RatingNotFoundException;
import hu.progmasters.vizsgaremek.exceptionhandling.ReceiptNotFoundException;
import hu.progmasters.vizsgaremek.exceptionhandling.UserNotFoundException;
import hu.progmasters.vizsgaremek.repository.RatingRepository;
import hu.progmasters.vizsgaremek.repository.ReceiptRepository;
import hu.progmasters.vizsgaremek.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private ReceiptRepository receiptRepository;
    private RatingRepository ratingRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserService(ReceiptRepository receiptRepository,
                       RatingRepository ratingRepository,
                       UserRepository userRepository,
                       ModelMapper modelMapper) {
        this.receiptRepository = receiptRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    //TODO exception-ök feltöltése adattal

    public UserInfo saveUser(UserCreateUpdateCommand command) {
        User toSave = modelMapper.map(command, User.class);
        toSave.setId(null);
        toSave.setReceipts(new ArrayList<>());
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
            throw new UserNotFoundException();
        }
        return convertUserToUserInfo(user.get());
    }

    public UserInfo updateUser(Integer toUpdateId, Integer loggedInUserId, UserCreateUpdateCommand command) {
        Optional<User> oldUser = userRepository.findById(toUpdateId);
        User toUpdate = modelMapper.map(command, User.class);
        toUpdate.setId(toUpdateId);
        if (oldUser.isEmpty()) {
            throw new UserNotFoundException();
        } else if(!toUpdate.getId().equals(loggedInUserId)) {
            throw new NoAuthorityForActionException();
        }
        toUpdate.setReceipts(oldUser.get().getReceipts());

        User saved = userRepository.update(toUpdate).get();
        return convertUserToUserInfo(saved);
    }

    public UserInfo deleteUser(Integer toDeleteId, Integer loggedInUserId) {
        Optional<User> toDelete = userRepository.findById(toDeleteId);
        if (toDelete.isEmpty()) {
            throw new UserNotFoundException();
        } else if(!toDelete.get().getId().equals(loggedInUserId)) {
            throw new NoAuthorityForActionException();
        }
        User deleted = userRepository.delete(toDelete.get()).get();
        return convertUserToUserInfo(deleted);
    }

    //Receipt methods

    public ReceiptInfo saveReceipt(Integer userId, LocalDate creationDate, ReceiptCreateUpdateCommand command) {
        // Mapping to Receipt
        Receipt toSave = modelMapper.map(command, Receipt.class);
        toSave.setId(null);
        Optional<User> userToSet = userRepository.findById(userId);
        if (userToSet.isEmpty()) {
            throw new UserNotFoundException();
        }
        toSave.setCreator(userToSet.get());
        toSave.setCreationDate(creationDate);
        toSave.setLastEditDate(creationDate);
        //Save and mapping to Info
        Receipt saved = receiptRepository.save(toSave).get();
        return convertReceiptToReceiptInfo(saved);
    }

    public List<ReceiptInfo> findAllReceipts() {
        List<Receipt> receipts = receiptRepository.findAll();
        return receipts.stream()
                .map(this::convertReceiptToReceiptInfo).collect(Collectors.toList());
    }

    public ReceiptInfo findReceiptById(Integer receiptId) {
        Optional<Receipt> receipt = receiptRepository.findById(receiptId);
        if (receipt.isEmpty()) {
            throw new ReceiptNotFoundException();
        }
        return convertReceiptToReceiptInfo(receipt.get());
    }

    public List<ReceiptInfo> findReceiptsByUser(Integer userId) {
        List<Receipt> receipts = receiptRepository.findByUser(userId);
        return receipts.stream()
                .map(this::convertReceiptToReceiptInfo).collect(Collectors.toList());
    }

    public ReceiptInfo updateReceipt(Integer receiptId, Integer userId,
                                     LocalDate editDate, ReceiptCreateUpdateCommand command) {

        Optional<Receipt> toUpdate = receiptRepository.findById(receiptId);
        if (toUpdate.isEmpty()) {
          throw new ReceiptNotFoundException();
        } else if (!userId.equals(toUpdate.get().getCreator().getId())) {
            throw new NoAuthorityForActionException();
        }
        toUpdate.get().setPreparation(command.getPreparation());
        toUpdate.get().setNote(command.getNote());
        toUpdate.get().setLastEditDate(editDate);
        Receipt updated = receiptRepository.update(toUpdate.get()).get();
        return convertReceiptToReceiptInfo(updated);
    }

    public ReceiptInfo deleteReceipt(Integer receiptId, Integer userId) {
        Optional<Receipt> toDelete = receiptRepository.findById(receiptId);
        if (toDelete.isEmpty()) {
            throw new ReceiptNotFoundException();
        } else if (!userId.equals(toDelete.get().getCreator().getId())) {
            throw new NoAuthorityForActionException();
        }
        Receipt deleted = receiptRepository.delete(toDelete.get()).get();
        return convertReceiptToReceiptInfo(deleted);
    }

    //Rating methods

    public RatingInfo saveOrUpdateRating(Integer userId, Integer receiptId, RatingCreateUpdateCommand command) {
        RatingInfo toReturn;
        if (ratingRepository.findByUserAndReceipt(userId, receiptId).isEmpty()) {
            toReturn = saveRating(userId, receiptId, command);
        } else {
            toReturn = updateRating(userId, receiptId, command);
        }
        return toReturn;
    }

    public RatingInfo saveRating(Integer userId, Integer receiptId, RatingCreateUpdateCommand command) {
        Optional<Receipt> receipt = receiptRepository.findById(receiptId);
        Optional<User> user = userRepository.findById(userId);
        if (receipt.isEmpty()) {
            throw new ReceiptNotFoundException();
        } else if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        Rating toSave = modelMapper.map(command, Rating.class);
        toSave.setUser(user.get());
        toSave.setReceipt(receipt.get());
        Rating saved = ratingRepository.saveRating(toSave).get();
        return convertRatingToRatingInfo(saved);
    }

    public RatingInfo findRatingByUserAndReceipt(Integer userId, Integer receiptId) {
        Optional<Rating> rating = ratingRepository.findByUserAndReceipt(userId, receiptId);
        if (rating.isEmpty()) {
            throw new RatingNotFoundException();
        }
        return convertRatingToRatingInfo(rating.get());
    }

    public List<RatingInfo> findAllRatingsByUser(Integer userId) {
        List<Rating> ratings = ratingRepository.findAllByUser(userId);
        return ratings.stream().map(this::convertRatingToRatingInfo).collect(Collectors.toList());
    }

    public RatingInfo updateRating(Integer userId, Integer receiptId, RatingCreateUpdateCommand command) {
        //itt nincs szükség jogosultság ellenőrzésre, mert a userId @PathVariable, nem @RequestParam
        Optional<Rating> toUpdate = ratingRepository.findByUserAndReceipt(userId, receiptId);
        if (toUpdate.isEmpty()) {
            throw new RatingNotFoundException();
        }
        toUpdate.get().setFingers(command.getFingers());
        Rating updated = ratingRepository.updateRating(toUpdate.get()).get();
        return convertRatingToRatingInfo(updated);
    }

    public RatingInfo deleteRating(Integer userId, Integer receiptId) {
        //itt nincs szükség jogosultság ellenőrzésre, mert a userId @PathVariable, nem @RequestParam
        Optional<Rating> toDelete = ratingRepository.findByUserAndReceipt(userId, receiptId);
        if (toDelete.isEmpty()) {
            throw new RatingNotFoundException();
        }
        Rating deleted = ratingRepository.deleteRating(toDelete.get()).get();
        return convertRatingToRatingInfo(deleted);
    }

    //Converting methods

    private UserInfo convertUserToUserInfo(User user) {
        UserInfo userInfo = modelMapper.map(user, UserInfo.class);
        List<ReceiptInfo> receiptInfos = user.getReceipts().stream()
                .map(entity -> modelMapper.map(entity, ReceiptInfo.class)).collect(Collectors.toList());
        userInfo.setReceipts(receiptInfos);
        return userInfo;
    }

    private ReceiptInfo convertReceiptToReceiptInfo(Receipt receipt) {
        ReceiptInfo receiptInfo = modelMapper.map(receipt, ReceiptInfo.class);
        receiptInfo.setCreatorId(receipt.getCreator().getId());
        return receiptInfo;
    }

    private RatingInfo convertRatingToRatingInfo(Rating rating) {
        RatingInfo ratingInfo = modelMapper.map(rating, RatingInfo.class);
        ratingInfo.setUserId(rating.getUser().getId());
        ratingInfo.setReceiptId(rating.getReceipt().getId());
        return ratingInfo;
    }
}
