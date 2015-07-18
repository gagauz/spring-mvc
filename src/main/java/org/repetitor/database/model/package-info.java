@TypeDefs({

        @TypeDef(name = "setOf.String", typeClass = HashSetType.class, parameters = {
                @Parameter(name = CollectionType.CLASS, value = "java.lang.String"),
                @Parameter(name = CollectionType.SERIALIZER, value = "org.repetitor.database.model.base.StringSerializer") }),
        @TypeDef(name = "setOf.Integer", typeClass = HashSetType.class, parameters = {
                @Parameter(name = CollectionType.CLASS, value = "java.lang.integer"),
                @Parameter(name = CollectionType.SERIALIZER, value = "org.repetitor.database.model.base.IntegerSerializer") }) })
package org.repetitor.database.model;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.repetitor.database.model.base.HashSetType;
import org.repetitor.database.model.types.CollectionType;

