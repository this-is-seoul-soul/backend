package com.seouldata.fest.domain.fest.entity;

public enum SortOption {

    EMPTY("없음", 0),
    RECOMMEND("추천순", 1),
    NEW("최신순", 2),
    HIGH_POINT("평점 높은순", 3),
    LOW_POINT("평점 낮은순", 4);

    private final String sortName;
    private final int sortNum;

    SortOption(String sortName, int sortNum) {
        this.sortName = sortName;
        this.sortNum = sortNum;
    }

    public static SortOption getSortOption(int sortNum) {
        for (SortOption sort : SortOption.values()) {
            if (sort.sortNum == sortNum) {
                return sort;
            }
        }
        return EMPTY;
    }

}
