package org.repetitor.database.model.enums;

public enum TotalLessonCount {
    UNKNOWN(0, 0),
    TOTAL_1(1, 1),
    TOTAL_FROM_2_TO_3(2, 3),
    TOTAL_FROM_4_TO_10(4, 10),
    TOTAL_FROM_10(10, Integer.MAX_VALUE);

    private final int min;
    private final int max;

    TotalLessonCount(int min, int max) {
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
