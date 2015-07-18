package org.repetitor.utils;

import org.gagauz.utils.Filter;
import org.repetitor.database.model.SubjectExtra;
import org.repetitor.database.model.SubjectSection;

public class Filters {

    public static <X> Filter<X> trueFilter() {
        return new Filter<X>() {
            @Override
            public boolean apply(X arg0) {
                return true;
            };
        };
    }

    public static final Filter<SubjectExtra> VISIBLE_EXTRA = new Filter<SubjectExtra>() {

        @Override
        public boolean apply(SubjectExtra arg0) {
            return arg0.isVisible();
        }
    };

    public static final Filter<SubjectSection> VISIBLE_SECTION = new Filter<SubjectSection>() {

        @Override
        public boolean apply(SubjectSection arg0) {
            return arg0.isVisible();
        }
    };
}
