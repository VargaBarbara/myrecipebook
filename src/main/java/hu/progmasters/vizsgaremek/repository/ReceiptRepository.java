package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Receipt;

import java.util.List;
import java.util.Optional;

public interface ReceiptRepository {

    Optional<Receipt> save(Receipt toSave);
    List<Receipt> findAll();
    Optional<Receipt> findById(Integer id);
    List<Receipt> findByUser(Integer userId);
    Optional<Receipt> update(Receipt toUpdate);
    Optional<Receipt> delete(Receipt toDelete);

}
