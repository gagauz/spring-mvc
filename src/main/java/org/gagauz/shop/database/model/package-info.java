@TypeDefs({

        @TypeDef(name = "setOf.String", typeClass = HashSetType.class, parameters = {
                @Parameter(name = CollectionType.CLASS, value = "java.lang.String"),
                @Parameter(name = CollectionType.SERIALIZER, value = "org.webservice.database.model.base.StringSerializer")}),
        @TypeDef(name = "setOf.Integer", typeClass = HashSetType.class, parameters = {
                @Parameter(name = CollectionType.CLASS, value = "java.lang.Integer"),
                @Parameter(name = CollectionType.SERIALIZER, value = "org.webservice.database.model.base.IntegerSerializer")})})
package org.gagauz.shop.database.model;

import org.gagauz.hibernate.types.CollectionType;
import org.gagauz.hibernate.types.HashSetType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
