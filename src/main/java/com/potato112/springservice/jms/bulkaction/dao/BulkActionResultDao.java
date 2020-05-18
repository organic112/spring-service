package com.potato112.springservice.jms.bulkaction.dao;


import com.potato112.springservice.jms.bulkaction.model.entity.BulkActionResult;
import com.potato112.springservice.repository.interfaces.crud.CRUDServiceBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class BulkActionResultDao extends CRUDServiceBean<BulkActionResult> {





}
