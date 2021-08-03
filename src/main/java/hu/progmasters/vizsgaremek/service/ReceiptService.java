package hu.progmasters.vizsgaremek.service;

import hu.progmasters.vizsgaremek.repository.RatingRepository;
import hu.progmasters.vizsgaremek.repository.ReceiptRepository;
import hu.progmasters.vizsgaremek.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    private ReceiptRepository receiptRepository;
    private RatingRepository ratingRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public ReceiptService(ReceiptRepository receiptRepository,
                          RatingRepository ratingRepository,
                          UserRepository userRepository,
                          ModelMapper modelMapper) {
        this.receiptRepository = receiptRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }



}
