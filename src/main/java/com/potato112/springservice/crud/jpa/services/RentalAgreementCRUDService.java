package com.potato112.springservice.crud.jpa.services;

import com.potato112.springservice.crud.model.RentalAgreement;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class RentalAgreementCRUDService implements JpaRepository<RentalAgreement, String> {


    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<RentalAgreement> findById(String id) {

        Optional<RentalAgreement> rentalCar = Optional
                .ofNullable(em.find(RentalAgreement.class, id));
        return rentalCar;
    }

    @Override
    public <S extends RentalAgreement> S save(S s) {
        em.persist(s);
        //em.flush();
        return null;
    }

    public void update(RentalAgreement transactionEntity) {

        em.merge(transactionEntity);
        //em.flush();
    }

    @Override
    public List<RentalAgreement> findAll() {

        List<RentalAgreement> rentalCars = em.createNamedQuery("getAllRentalAgreements", RentalAgreement.class)
                .getResultList();
        return rentalCars;
    }

    @Override
    public List<RentalAgreement> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<RentalAgreement> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public <S extends RentalAgreement> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends RentalAgreement> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<RentalAgreement> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public RentalAgreement getOne(String aLong) {
        return null;
    }

    @Override
    public <S extends RentalAgreement> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends RentalAgreement> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<RentalAgreement> findAll(Pageable pageable) {
        return null;
    }


    @Override
    public boolean existsById(String aString) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String id) {

        Optional<RentalAgreement> agreement = findById(id);

        if (agreement.isPresent()) {
            String agreementId = agreement.get().getAgreementId();

            em.createNamedQuery("deleteRentalAgreementById")
                    .setParameter("agreementId", agreementId)
                    .executeUpdate();
        }
    }

    @Override
    public void delete(RentalAgreement rentalAgreement) {

        Optional<RentalAgreement> agreement = findById(rentalAgreement.getAgreementId());
        if (agreement.isPresent()) {
            String agreementId = agreement.get().getAgreementId();
            em.createNamedQuery("deleteRentalAgreementById")
                    .setParameter("agreementId", agreementId)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteAll(Iterable<? extends RentalAgreement> iterable) {

    }

    @Override
    public void deleteAll() {
        em.createNamedQuery("deleteAllRentalAgreements")
                .executeUpdate();
    }

    @Override
    public <S extends RentalAgreement> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends RentalAgreement> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends RentalAgreement> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends RentalAgreement> boolean exists(Example<S> example) {
        return false;
    }


}
