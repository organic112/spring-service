package com.potato112.springservice.crud.jpa.services;


import com.potato112.springservice.crud.model.RentalCar;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class RentalCarCRUDService implements JpaRepository<RentalCar, String> {
        // RevisionRepository<RentalCar, String, Integer>

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<RentalCar> findById(String id) {

        Optional<RentalCar> rentalCar = Optional
                .ofNullable(em.find(RentalCar.class, id));
        return rentalCar;
    }

    @Override
    public <S extends RentalCar> S save(S s) {
        em.persist(s);
        //em.flush();
        return null;
    }

    public void update(RentalCar transactionEntity) {

        em.merge(transactionEntity);
        //em.flush();
    }

    @Override
    public List<RentalCar> findAll() {

        List<RentalCar> rentalCars = em.createNamedQuery("getAllRentalCars", RentalCar.class)
                .getResultList();
        return rentalCars;
    }

    @Override
    public List<RentalCar> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<RentalCar> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public <S extends RentalCar> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends RentalCar> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<RentalCar> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public RentalCar getOne(String aString) {
        return null;
    }

    @Override
    public <S extends RentalCar> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends RentalCar> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<RentalCar> findAll(Pageable pageable) {
        return null;
    }


    @Override
    public boolean existsById(String aLong) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String aLong) {

    }

    @Override
    public void delete(RentalCar rentalCar) {

        Optional<RentalCar> car = findById(rentalCar.getCarId());

        if (car.isPresent()) {
            String carId = car.get().getCarId();

            em.createNamedQuery("deleteRentalCarsById")
                    .setParameter("carId", carId)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteAll(Iterable<? extends RentalCar> iterable) {

    }

    @Override
    public void deleteAll() {
        em.createNamedQuery("deleteAllRentalCars")
                .executeUpdate();
    }

    @Override
    public <S extends RentalCar> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends RentalCar> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends RentalCar> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends RentalCar> boolean exists(Example<S> example) {
        return false;
    }
}
