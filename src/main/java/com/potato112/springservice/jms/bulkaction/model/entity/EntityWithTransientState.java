package com.potato112.springservice.jms.bulkaction.model.entity;


/**
 * For some entities transient state is sth. that should be maintained.
 * Maintaining transient state means to keep it constantly in entity not in DB.
 * Essential usecase is maintain the state between merge() methods of EntityManager,
 * as they tend to clear transient fields in returned objects.
 *
 * @param <T> Object representing transient state, that is 'maintainable state' of an entity.
 */
public interface EntityWithTransientState<T extends TransientEntityState> {

    /**
     * Returns object representing transient, maintainable state of this entity
     *
     * @return
     */
    T getTransientState();

    /**
     * Sets object representing transient, maintainable state of this entity
     *
     * @param state
     */
    void setTransientState(T state);

    /**
     * Transfer transient state to non-transient, persistent fields of an entity
     * Use stateProvider if existing state is missing something.
     *
     * @param stateProvider in current version UserManager as transient state is restricted
     *                      to updateUser
     */
    void transferStateToPersistentFields(Object stateProvider);
}
