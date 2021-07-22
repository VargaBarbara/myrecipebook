package hu.progmasters.vizsgaremek.service;

import hu.progmasters.vizsgaremek.repository.ReceiptRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    private ReceiptRepository repository;
    private ModelMapper modelMapper;

    public ReceiptService(ReceiptRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }
}
