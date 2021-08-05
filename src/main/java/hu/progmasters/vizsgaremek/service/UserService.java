package hu.progmasters.vizsgaremek.service;

import hu.progmasters.vizsgaremek.domain.Receipt;
import hu.progmasters.vizsgaremek.domain.User;
import hu.progmasters.vizsgaremek.dto.ReceiptCreateUpdateCommand;
import hu.progmasters.vizsgaremek.dto.ReceiptInfo;
import hu.progmasters.vizsgaremek.dto.UserCreateUpdateCommand;
import hu.progmasters.vizsgaremek.dto.UserInfo;
import hu.progmasters.vizsgaremek.repository.RatingRepository;
import hu.progmasters.vizsgaremek.repository.ReceiptRepository;
import hu.progmasters.vizsgaremek.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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


    public UserInfo saveUser(UserCreateUpdateCommand command) {
        User toSave = modelMapper.map(command, User.class);
        toSave.setId(null);
        toSave.setReceipts(new ArrayList<>());
        User saved = userRepository.save(toSave);
        return modelMapper.map(saved, UserInfo.class);
    }

    public List<UserInfo> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertUserToUserInfo).collect(Collectors.toList());
    }

    public UserInfo findUserById(Integer id) {
        User user = userRepository.findById(id);
        return convertUserToUserInfo(user);

    }

    public UserInfo updateUser(Integer id, Integer userId, UserCreateUpdateCommand command) {
        User toUpdate = modelMapper.map(command, User.class); //TODO user jogosultság if
        toUpdate.setId(id);
        toUpdate.setReceipts(userRepository.findById(id).getReceipts());
        User saved = userRepository.update(toUpdate);
        return convertUserToUserInfo(saved);
    }

    public UserInfo deleteUser(Integer id, Integer userId) {
        User deleted = userRepository.delete(userRepository.findById(id));
        return convertUserToUserInfo(deleted);      //TODO user jogosultság if
    }

    //Receipt methods

    public ReceiptInfo saveReceipt(Integer userId, LocalDate creationDate, ReceiptCreateUpdateCommand command) {
        // Mapping to Receipt
        Receipt toSave = modelMapper.map(command, Receipt.class);
        toSave.setId(null);
        User userToSet = userRepository.findById(userId);
        toSave.setCreator(userToSet);
        toSave.setCreationDate(creationDate);
        toSave.setLastEditDate(creationDate);
        //Save and mapping to Info
        Receipt saved = receiptRepository.save(toSave);
        return convertReceiptToReceiptInfo(saved);
    }

    public List<ReceiptInfo> findAllReceipts() {
        List<Receipt> receipts = receiptRepository.findAll();
        return receipts.stream()
                .map(this::convertReceiptToReceiptInfo).collect(Collectors.toList());
    }

    public ReceiptInfo findReceiptById(Integer receiptId) {
        return convertReceiptToReceiptInfo(receiptRepository.findById(receiptId));
    }

    public List<ReceiptInfo> findReceiptsByUser(Integer userId) {
        List<Receipt> receipts = receiptRepository.findByUser(userId);
        return receipts.stream()
                .map(this::convertReceiptToReceiptInfo).collect(Collectors.toList());
    }

    public ReceiptInfo updateReceipt(Integer receiptId, Integer userId,
                                     LocalDate editDate, ReceiptCreateUpdateCommand command) {
        Receipt toUpdate = receiptRepository.findById(receiptId);       //TODO user jogosultság if
        toUpdate.setPreparation(command.getPreparation());
        toUpdate.setNote(command.getNote());
        toUpdate.setLastEditDate(editDate);
        Receipt updated = receiptRepository.update(toUpdate);
        return convertReceiptToReceiptInfo(updated);
    }

    public ReceiptInfo deleteReceipt(Integer receiptId, Integer userId) {
        Receipt deleted = receiptRepository.delete(receiptRepository.findById(receiptId));
        return convertReceiptToReceiptInfo(deleted);        //TODO user jogosultság if
    }

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
}
