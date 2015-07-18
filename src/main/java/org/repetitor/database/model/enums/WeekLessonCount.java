package org.repetitor.database.model.enums;

public enum WeekLessonCount {
    UNKNOWN(0, 0),
    WEEK_TO_1(0, 1),
    WEEK_1(1, 1),
    WEEK_FROM_1_TO_2(1, 2),
    WEEK_2(2, 2),
    WEEK_FROM_2_TO_3(2, 3),
    WEEK_3(3, 3),
    WEEK_FROM_3(3, Integer.MAX_VALUE);

    private final int min;
    private final int max;

    WeekLessonCount(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

}
