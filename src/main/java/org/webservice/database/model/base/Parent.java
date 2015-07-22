package org.webservice.database.model.base;

import java.util.Collection;

public interface Parent {
    Collection<? extends Parent> getChildren();
}
