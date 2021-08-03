package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Receipt;

import java.util.List;

public interface ReceiptRepository {

    Receipt save(Receipt toSave);
    List<Receipt> findAll();
    Receipt findById(Integer id);
    Receipt update(Receipt oldReceipt, Receipt toUpdate);
    Receipt delete(Receipt toDelete);

}
