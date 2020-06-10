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


@Service
@Transactional
public class BaseDAO {

    @PersistenceContext
    private EntityManager entityManager;


    public List<String> getPropertyValuesByQueryMeta(QueryMeta queryMeta, Class<?> clazz, String propertyName){

        Projection idProjection = Projections.property(propertyName);
        return (List<String>) getByQueryMeta(queryMeta, clazz, idProjection);
    }

    public List<?> getByQueryMeta(QueryMeta queryMeta, Class<?> clazz) {
        List<?> result = getByQueryMeta(queryMeta, clazz, null);
        return result;
    }

    public int count(QueryMeta queryMeta, Class<?> clazz) {
        Session session = (Session) entityManager.getDelegate();

        Long count = 0L;
        DetachedCriteria criteria = getCriteria(queryMeta, session, clazz);
        Criteria c = criteria.getExecutableCriteria(session);
        c.setProjection(Projections.countDistinct("id"));
        count = (Long) c.uniqueResult();

        return count.intValue();
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
        SysDetachedCriteria c = SysDetachedCriteria.forClazz(clazz);
        customizeCriteria(c, queryMeta);

/*        if (null != queryMeta.getSavedSearch()) {
            fillAdvancedSearchCriteria(queryMeta, c);
        } else {*/
        List<Filter> filters = queryMeta.getFilters();
        if (null == filters) {
            return c;
        }
        filters.forEach(filter -> {
            String propertyName = filter.getPropertyId();
            propertyName = createAliasesForSearchField(c, propertyName);
            addRestrictionsFromFilter(c, filter.getFilterType(), filter.getValue(), filter.getValues(), propertyName);
        });
        return c;
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

    private void addRestrictionsFromFilter(SysDetachedCriteria criteria, FilterType filterType, Object filterValue, List<? extends Serializable> filterValues, String propertyName) {

        boolean wasDateFilterType = addDateRestrictionsFromFilter(criteria, filterType, filterValue, filterValues, propertyName);
        if (!wasDateFilterType) {
            addBasicRestrictionsFromFilter(criteria, filterType, filterValue, filterValues, propertyName);
        }
    }

    private boolean addDateRestrictionsFromFilter(SysDetachedCriteria criteria, FilterType filterType, Object filterValue, List<? extends Serializable> filterValues, String propertyName) {

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
                    addBasicRestrictionsFromFilter(criteria, FilterType.EQUALS, filterValue, filterValues, propertyName);
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
                    addBasicRestrictionsFromFilter(criteria, FilterType.GREATER, filterType, filterValues, propertyName);
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
                    addBasicRestrictionsFromFilter(criteria, FilterType.LESS, filterValue, filterValues, propertyName);
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

    private void addBasicRestrictionsFromFilter(SysDetachedCriteria criteria, FilterType filterType, Object filterValue,
                                                List<? extends Serializable> filterValues, String propertyName) {


        System.out.println("ECHO add basic restrictions");

        if (FilterType.INTEGER_LIKE.equals(filterType) && filterValue != null && filterValue instanceof Integer) {
            //c.add(new) FIXME
        } else if (FilterType.LIKE.equals(filterType) && filterValue != null) {
            criteria.add(Restrictions.like(propertyName, "%" + filterValue + "%"));
        } else if (FilterType.LIKE_CASE_INSENSITIVE.equals(filterType) && filterType != null) {
            criteria.add(Restrictions.ilike(propertyName, "%" + filterValue + "%"));
        } else if (FilterType.EQUALS.equals(filterType)) {
            criteria.add(Restrictions.eq(propertyName, filterValue));

            System.out.println("filterType" + filterType);
            System.out.println("propertyName" + propertyName);
            System.out.println("filterValue" + filterValue);
            System.out.println("Echo added equals restriction...");

        } else if (FilterType.NOT_EQUALS.equals(filterType)) {
            criteria.add(Restrictions.not(Restrictions.eqOrIsNull(propertyName, filterValue)));
        } else if (FilterType.GREATER.equals(filterType)) {
            criteria.add(Restrictions.gt(propertyName, filterValue));
        } else if (FilterType.LESS.equals(filterType)) {
            criteria.add(Restrictions.lt(propertyName, filterValue));
        } else if (FilterType.GREATER_EQUALS.equals(filterType)) {
            criteria.add(Restrictions.le(propertyName, filterValue));
        } else if (FilterType.IN.equals(filterType)) {
            criteria.add(Restrictions.in(propertyName, filterValues));
        } else if (FilterType.NOT_IN.equals(filterType)) {
            criteria.add(Restrictions.not(Restrictions.in(propertyName, filterValues)));
        }
    }
}
