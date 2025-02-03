package org.anonymous.card.constants;


import lombok.Getter;

@Getter
public enum Category {
    SHOPPING(1,"SHOPPING"), // 온라인, 할인점, 백화점, 아울렛, 면세점 등등..
    LIFE(2, "LIFE"), // 병원약국, 커피제과, 교통, 영화관 주유, 통신 등등..
    TRAVEL(3, "TRAVEL"), // 여행, 항공, 해외 등등..
    LIVING(4, "LIVING"); // 편의점, 음식점, 교육, 배달앱, 보험, 생활비 등등..


    private final int target;
    private final String title;
    Category(int target, String title) {
        this.target = target;
        this.title = title;
    }

    public String toString() {
        return title;
    }
}
