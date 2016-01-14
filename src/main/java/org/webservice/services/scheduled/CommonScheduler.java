package org.webservice.services.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(CommonScheduler.class);

    // TODO May be not optimal
    @Scheduled(initialDelay = 60000, fixedDelay = 600000)
    @Transactional
    public void updateOveralRating() {

    }

}
