package hu.progmasters.vizsgaremek.repository;

import hu.progmasters.vizsgaremek.domain.Receipt;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReceiptRepositoryJPA implements ReceiptRepository{
    @Override
    public Receipt save(Receipt toSave) {
        return null;
    }

    @Override
    public List<Receipt> findAll() {
        return null;
    }

    @Override
    public Receipt findById(Integer id) {
        return null;
    }

    @Override
    public Receipt update(Receipt oldReceipt, Receipt toUpdate) {
        return null;
    }

    @Override
    public Receipt delete(Receipt toDelete) {
        return null;
    }
}
