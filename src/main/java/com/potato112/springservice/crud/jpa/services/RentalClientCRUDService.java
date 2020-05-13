package com.potato112.springservice.crud.jpa.services;

import com.potato112.springservice.crud.model.RentalClient;
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
public class RentalClientCRUDService implements JpaRepository<RentalClient, String> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<RentalClient> findById(String id) {

        Optional<RentalClient> rentalCar = Optional
                .ofNullable(em.find(RentalClient.class, id));
        return rentalCar;
    }

    @Override
    public <S extends RentalClient> S save(S s) {
        em.persist(s);
        //em.flush();
        return null;
    }

    public void update(RentalClient rentalClient) {

        em.merge(rentalClient);
        em.flush();
    }

    @Override
    public List<RentalClient> findAll() {

        List<RentalClient> rentalCars = em.createNamedQuery("getAllRentalClient", RentalClient.class)
                .getResultList();

        return rentalCars;
    }

    @Override
    public List<RentalClient> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<RentalClient> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public <S extends RentalClient> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends RentalClient> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<RentalClient> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public RentalClient getOne(String id) {
        return null;
    }

    @Override
    public <S extends RentalClient> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends RentalClient> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<RentalClient> findAll(Pageable pageable) {
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
    public void deleteById(String id) {

        Optional<RentalClient> rentalClient = findById(id);

        rentalClient.ifPresent(c -> em.createNamedQuery("deleteRentalClientById", RentalClient.class)
                .setParameter("clientId", c.getClientId())
                .executeUpdate());
    }

    @Override
    public void delete(RentalClient client) {

        Optional<RentalClient> rentalClient = findById(client.getClientId());

        rentalClient.ifPresent(c -> em.createNamedQuery("deleteRentalClientById", RentalClient.class)
                .setParameter("clientId", c.getClientId())
                .executeUpdate());
    }


    @Override
    public void deleteAll(Iterable<? extends RentalClient> iterable) {

    }

    @Override
    public void deleteAll() {

        em.createNamedQuery("deleteAllRentalClient")
                .executeUpdate();
    }

    @Override
    public <S extends RentalClient> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends RentalClient> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends RentalClient> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends RentalClient> boolean exists(Example<S> example) {
        return false;
    }
}
