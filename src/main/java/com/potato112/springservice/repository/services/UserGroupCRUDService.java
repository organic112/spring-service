package com.potato112.springservice.repository.services;

import com.potato112.springservice.repository.entities.auth.UserGroup;
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
public class UserGroupCRUDService implements JpaRepository<UserGroup, String> {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Optional<UserGroup> findById(String id) {

        Optional<UserGroup> userGroup = Optional
                .ofNullable(em.find(UserGroup.class, id));
        return userGroup;
    }

    @Override
    public <S extends UserGroup> S save(S s) {
        em.persist(s);
        //em.flush();
        return null;
    }

    public void update(UserGroup transactionEntity) {

        em.merge(transactionEntity);
        //em.flush();
    }

    @Override
    public List<UserGroup> findAll() {

        List<UserGroup> userGroups = em.createNamedQuery("getAllUserGroups", UserGroup.class)
                .getResultList();
        return userGroups;
    }

    @Override
    public List<UserGroup> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<UserGroup> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public <S extends UserGroup> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends UserGroup> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<UserGroup> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public UserGroup getOne(String aLong) {
        return null;
    }

    @Override
    public <S extends UserGroup> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends UserGroup> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<UserGroup> findAll(Pageable pageable) {
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

        Optional<UserGroup> userGroup = findById(id);

        if (userGroup.isPresent()) {
            String userGroupId = userGroup.get().getId();

            em.createNamedQuery("deleteUserGroupById")
                    .setParameter("userGroupId", userGroupId)
                    .executeUpdate();
        }
    }

    @Override
    public void delete(UserGroup user) {

        Optional<UserGroup> userGroupOp = findById(user.getId());
        if (userGroupOp.isPresent()) {
            String userGroupId = userGroupOp.get().getId();
            em.createNamedQuery("deleteUserGroupById")
                    .setParameter("userId", userGroupId)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteAll(Iterable<? extends UserGroup> iterable) {

    }

    @Override
    public void deleteAll() {
        em.createNamedQuery("deleteAllUserGroups")
                .executeUpdate();
    }

    @Override
    public <S extends UserGroup> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends UserGroup> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UserGroup> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends UserGroup> boolean exists(Example<S> example) {
        return false;
    }



}
