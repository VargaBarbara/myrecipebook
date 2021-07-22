package hu.progmasters.vizsgaremek.service;

import hu.progmasters.vizsgaremek.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;
    private ModelMapper modelMapper;

    public UserService(UserRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }
}
