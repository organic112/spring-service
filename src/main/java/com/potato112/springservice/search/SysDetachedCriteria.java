package com.potato112.springservice.search;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.sql.JoinType;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom criteria that extends DetachedCriteria to store list of Aliases for created custom criteria
 */
public class SysDetachedCriteria extends DetachedCriteria {

    private List<String> createdAliases = new ArrayList<>();

    public SysDetachedCriteria(String entityName) {
        super(entityName);
    }

    public static SysDetachedCriteria forClazz(Class<?> clazz) {

        return new SysDetachedCriteria(clazz.getName());
    }

    @Override
    public DetachedCriteria createAlias(String associationPath, String alias, JoinType joinType) {
        createdAliases.add(alias);
        return super.createAlias(associationPath, alias, joinType);
    }

    @Override
    public DetachedCriteria createAlias(String associationPath, String alias) {

        createdAliases.add(alias);
        return super.createAlias(associationPath, alias);
    }

    public List<String> getCreatedAliases() {

        return createdAliases;
    }
}
