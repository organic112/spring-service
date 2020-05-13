package com.potato112.springservice.jms.bulkaction;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * this class represents, e.g. UI component that
 * - collects checked items on grid
 * - prepares params
 * - execute bulk action
 */
@Component
@Scope("prototype")
public class BulkActionExecutor {

    private final ExecutorService executorService;
    private final BulkActionRunner bulkActionRunner;

    public BulkActionExecutor(ExecutorService executorService, BulkActionRunner bulkActionRunner) {
        this.executorService = executorService;
        this.bulkActionRunner = bulkActionRunner;
    }


    public void executeBulkAction(){

        //final ExecutorService executorService = Executors.newSingleThreadExecutor();

       // BulkActionRunner runner = applicationContext.getBean(BulkActionRunner.class);
        executorService.execute(bulkActionRunner);
    }
}
