package com.potato112.springservice.search;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides generic db queries based on:
 * - QueryMeta
 * - Entity class
 * - class property name (class field)
 */
@Service
@Transactional
public class BaseDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public int count(QueryMeta queryMeta, Class<?> clazz) {
        Session session = (Session) entityManager.getDelegate();
        Long count = 0L;
        DetachedCriteria criteria = getCriteria(queryMeta, session, clazz);
        Criteria c = criteria.getExecutableCriteria(session);
        c.setProjection(Projections.countDistinct("id"));
        count = (Long) c.uniqueResult();

        return count.intValue();
    }

    public List<?> getByQueryMeta(QueryMeta queryMeta, Class<?> clazz) {
        List<?> result = getByQueryMeta(queryMeta, clazz, null);
        return result;
    }

    public List<String> getPropertyValuesByQueryMeta(QueryMeta queryMeta, Class<?> clazz, String propertyName){

        Projection idProjection = Projections.property(propertyName);
        return (List<String>) getByQueryMeta(queryMeta, clazz, idProjection);
    }

    protected final List<?> getByQueryMeta(QueryMeta queryMeta, Class<?> clazz, Projection projection) {

        Session session = (Session) entityManager.getDelegate();
        ArrayList<Object> resultList = new ArrayList<>();

        SysDetachedCriteria criteria = getCriteria(queryMeta, session, clazz);

        criteria = getCriteriaWithOrder(queryMeta, criteria);
        Criteria c = getCriteriaPaged(criteria, queryMeta, session);

        Optional.ofNullable(projection).ifPresent(c::setProjection);
        List<?> result = c.list();

        if (projection == null) {
            initializeFields(result);
        }
        resultList.addAll(result);

        System.out.println("result list size "+ resultList.size());
        return resultList;
    }

    private void initializeFields(List<?> result) {
        //TODO
    }

    private Criteria getCriteriaPaged(SysDetachedCriteria detachedCriteria, QueryMeta queryMeta, Session session) {

        Criteria criteria = detachedCriteria.getExecutableCriteria(session);
        if (null == queryMeta) {
            return criteria;
        }
        criteria.setFirstResult(queryMeta.getStartIndex()).setMaxResults(queryMeta.getNumberOfIds());
        return criteria;
    }

    private SysDetachedCriteria getCriteriaWithOrder(QueryMeta queryMeta, SysDetachedCriteria criteria) {

        List<Object> fields = queryMeta.getSortField();
        boolean[] asc = queryMeta.getAscending();
        if (null != fields && null != asc) {
            AtomicInteger i = new AtomicInteger(0);
            fields.forEach(field -> {
                String propertyName = createAliasesForSearchField(criteria, field.toString());
                sortField(criteria, propertyName, asc[i.getAndIncrement()]);
            });
        }
        return criteria;
    }

    private void sortField(SysDetachedCriteria searchCriteria, Object propertyName, boolean ascendingOrder) {
        Order order;
        if (ascendingOrder) {
            order = Order.asc(propertyName.toString());
        } else {
            order = Order.desc(propertyName.toString());
        }
        searchCriteria.addOrder(order.ignoreCase());
    }

    private SysDetachedCriteria getCriteria(QueryMeta queryMeta, Session session, Class<?> clazz) {
        SysDetachedCriteria detachedCriteria = SysDetachedCriteria.forClazz(clazz);
        customizeCriteria(detachedCriteria, queryMeta);

/*        if (null != queryMeta.getSavedSearch()) {
            fillAdvancedSearchCriteria(queryMeta, c);
        } else {*/

        // simple search
        List<Filter> filters = queryMeta.getFilters();
        if (null == filters) {
            return detachedCriteria;
        }
        filters.forEach(filter -> {
            String propertyName = filter.getPropertyId();
            propertyName = createAliasesForSearchField(detachedCriteria, propertyName);
            addFilterRestrictions(detachedCriteria, filter.getFilterType(), filter.getValue(), filter.getValues(), propertyName);
        });
        return detachedCriteria;
        //}
    }

    private String createAliasesForSearchField(SysDetachedCriteria c, String searchField) {

        if (searchField.contains(".")) {
            String[] parts = searchField.split("\\.");
            String rootAlias = c.getAlias();

            for (int i = 0; i < parts.length - 1; i++) {
                String part = parts[i];
                if (c.getCreatedAliases().contains(part)) {
                    rootAlias = part;
                    continue;
                }
                c.createAlias(rootAlias + "." + part, part, JoinType.LEFT_OUTER_JOIN);
                rootAlias = part;
            }
            Matcher matcher = Pattern.compile("[\\w]+[\\.][\\w]+$").matcher(searchField);
            if (matcher.find()) {
                return matcher.group();
            }
        }
        return searchField;
    }

    private void customizeCriteria(SysDetachedCriteria criteria, QueryMeta queryMeta) {
    }

    private void addFilterRestrictions(SysDetachedCriteria criteria, FilterType filterType, Object filterValue, List<? extends Serializable> filterValues, String propertyName) {

        boolean wasDateFilterType = addDateFilterRestrictions(criteria, filterType, filterValue, filterValues, propertyName);
        if (!wasDateFilterType) {
            addBasicFilterRestriction(criteria, filterType, filterValue, filterValues, propertyName);
        }
    }

    /**
     * Handles comparison between dates
     */
    private boolean addDateFilterRestrictions(SysDetachedCriteria criteria, FilterType filterType, Object filterValue, List<? extends Serializable> filterValues, String propertyName) {

        Date filterDate;
        switch (filterType) {
            case DATE_EQUALS:
                try {
                    filterDate = (Date) filterValue;
                    Calendar cal1 = new GregorianCalendar();
                    cal1.setTime(filterDate);
                    removeTimeFromCalendar(cal1);
                    Calendar cal2 = new GregorianCalendar();
                    cal2.setTime(cal1.getTime());
                    cal2.add(Calendar.DAY_OF_YEAR, 1);
                    criteria.add(Restrictions.between(propertyName, cal1.getTime(), cal2.getTime()));
                } catch (ClassCastException e) {
                    addBasicFilterRestriction(criteria, FilterType.EQUALS, filterValue, filterValues, propertyName);
                }
                return true;
            case DATE_AFTER:
                try {
                    filterDate = (Date) filterValue;
                    Calendar cal = new GregorianCalendar();
                    cal.setTime(filterDate);
                    removeTimeFromCalendar(cal);
                    cal.add(Calendar.DAY_OF_YEAR, 1);
                    criteria.add(Restrictions.ge(propertyName, cal.getTime()));
                } catch (ClassCastException e) {
                    addBasicFilterRestriction(criteria, FilterType.GREATER, filterType, filterValues, propertyName);
                }
                return true;
            case DATE_BEFORE:
                try {
                    filterDate = (Date) filterValue;
                    Calendar cal = new GregorianCalendar();
                    cal.setTime(filterDate);
                    removeTimeFromCalendar(cal);
                    criteria.add(Restrictions.lt(propertyName, cal.getTime()));
                } catch (ClassCastException e) {
                    addBasicFilterRestriction(criteria, FilterType.LESS, filterValue, filterValues, propertyName);
                }
                return true;
            default:
                return false;
        }
    }

    private void removeTimeFromCalendar(Calendar cal) {
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Adds Hibernate criterions (Restrictions) for current simple search filter
     * Depending on criterion uses single value or list of values
     */
    private void addBasicFilterRestriction(SysDetachedCriteria criteria, FilterType filterType, Object singleFilterValue,
                                           List<? extends Serializable> filterValueList, String propertyName) {

        System.out.println("ECHO add basic restrictions");

        if (FilterType.INTEGER_LIKE.equals(filterType) && singleFilterValue != null && singleFilterValue instanceof Integer) {
            //c.add(new) FIXME - custom Restriction
        } else if (FilterType.LIKE.equals(filterType) && singleFilterValue != null) {
            criteria.add(Restrictions.like(propertyName, "%" + singleFilterValue + "%"));
        } else if (FilterType.LIKE_CASE_INSENSITIVE.equals(filterType) && filterType != null) {
            criteria.add(Restrictions.ilike(propertyName, "%" + singleFilterValue + "%"));
        } else if (FilterType.EQUALS.equals(filterType)) {
            criteria.add(Restrictions.eq(propertyName, singleFilterValue));

            System.out.println("filterType" + filterType);
            System.out.println("propertyName" + propertyName);
            System.out.println("filterValue" + singleFilterValue);
            System.out.println("Echo added equals restriction...");

        } else if (FilterType.NOT_EQUALS.equals(filterType)) {
            criteria.add(Restrictions.not(Restrictions.eqOrIsNull(propertyName, singleFilterValue)));
        } else if (FilterType.GREATER.equals(filterType)) {
            criteria.add(Restrictions.gt(propertyName, singleFilterValue));
        } else if (FilterType.LESS.equals(filterType)) {
            criteria.add(Restrictions.lt(propertyName, singleFilterValue));
        } else if (FilterType.GREATER_EQUALS.equals(filterType)) {
            criteria.add(Restrictions.le(propertyName, singleFilterValue));
        } else if (FilterType.IN.equals(filterType)) {
            criteria.add(Restrictions.in(propertyName, filterValueList));
        } else if (FilterType.NOT_IN.equals(filterType)) {
            criteria.add(Restrictions.not(Restrictions.in(propertyName, filterValueList)));
        }
    }
}
