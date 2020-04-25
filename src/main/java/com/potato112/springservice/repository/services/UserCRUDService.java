package com.potato112.springservice.repository.services;

import com.potato112.springservice.repository.entities.auth.User;
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
public class UserCRUDService implements JpaRepository<User, String> {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Optional<User> findById(String id) {

        Optional<User> user = Optional
                .ofNullable(em.find(User.class, id));
        return user;
    }

    @Override
    public <S extends User> S save(S s) {
        em.persist(s);
        //em.flush();
        return null;
    }

    public void update(User transactionEntity) {

        em.merge(transactionEntity);
        //em.flush();
    }

    @Override
    public List<User> findAll() {

        List<User> users = em.createNamedQuery("getAllUsers", User.class)
                .getResultList();
        return users;
    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<User> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<User> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(String aLong) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
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

        Optional<User> user = findById(id);

        if (user.isPresent()) {
            String userId = user.get().getId();

            em.createNamedQuery("deleteUserGroupById")
                    .setParameter("userId", userId)
                    .executeUpdate();
        }
    }

    @Override
    public void delete(User user) {

        Optional<User> userOp = findById(user.getId());
        if (userOp.isPresent()) {
            String userId = userOp.get().getId();
            em.createNamedQuery("deleteUserGroupById")
                    .setParameter("userId", userId)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {

    }

    @Override
    public void deleteAll() {
        em.createNamedQuery("deleteAllUserGroups")
                .executeUpdate();
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }



}
