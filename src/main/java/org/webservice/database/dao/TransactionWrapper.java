package org.webservice.database.dao;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.Callable;

@Service("TransactionWrapper")
public class TransactionWrapper {

    private static final Logger logger = LoggerFactory.getLogger(TransactionWrapper.class);

    private final SessionFactory sessionFactory;
    private final TransactionTemplate transactionTemplate;
    private final ThreadLocal<TransactionTemplate> threadTransactionTemplate;

    @Autowired
    TransactionWrapper(PlatformTransactionManager transactionManager, SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.threadTransactionTemplate = new ThreadLocal<TransactionTemplate>();
        threadTransactionTemplate.set(transactionTemplate);
    }

    public void wrap(final Runnable call) {
        wrap(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                call.run();
                return null;
            }
        });
    }

    public <T> T wrap(final Callable<T> call) {
        return wrap(call, false);
    }

    public <T> T wrap(final Callable<T> call, final boolean readonly) {
        if (TransactionSynchronizationManager.getResource(sessionFactory) != null) {
            logger.info("Wrap runnable method in transaction, run in separated thread. Use session factory");
            return readonly ? transactionalReadOnly(call) : transactional(call);
        }
        transactionTemplate.setReadOnly(readonly);
        return transactionTemplate.execute(new TransactionCallback<T>() {
            @Override
            public T doInTransaction(TransactionStatus status) {
                logger.info("Wrap runnable method in transaction, run in separated thread. Use transactionTemplate");
                try {
                    return call.call();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Transactional
    @Async
    private <T> T transactional(final Callable<T> call) {
        try {
            return call.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    @Async
    private <T> T transactionalReadOnly(final Callable<T> call) {
        try {
            return call.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
