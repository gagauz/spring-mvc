package org.gagauz.shop.web.pages;

import org.apache.tapestry5.services.ExceptionReporter;

public class Error500 implements ExceptionReporter {

    @Override
    public void reportException(Throwable exception) {
        exception.printStackTrace();
    }

}
