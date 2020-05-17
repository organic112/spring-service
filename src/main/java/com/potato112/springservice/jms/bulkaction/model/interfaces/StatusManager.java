package com.potato112.springservice.jms.bulkaction.model.interfaces;

import com.potato112.springservice.jms.bulkaction.model.init.ChangeStatusParams;
import com.potato112.springservice.jms.doclock.AlreadyLockedException;


public interface StatusManager<DOCTYPE extends SysDocument, STATUSTYPE extends SysStatus>  {

    boolean canChangeStatus(DOCTYPE document, STATUSTYPE newStatus);

    boolean canChangeStatus(DOCTYPE document, STATUSTYPE newStatus, String cancelationMessage);

    void changeStatus(ChangeStatusParams<DOCTYPE, STATUSTYPE> params) throws AlreadyLockedException;



}
