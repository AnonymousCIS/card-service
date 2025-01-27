package org.anonymous.card.constants;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BankName {
    HANKUK("00", "한국은행"), // 한국은행 0
    KB("01", "국민은행"), // 국민은행 1
    SC("02", "제일은행"), // SC제일은행 2
    CITY("03", "한국시티은행"), // 한국시티은행 3
    HANA("04", "하나은행"), // 하나은행 4
    SHINHAN("05", "신한은행"), // 신한 5
    KBANK("06", "K-뱅크"), // K뱅크 6
    KAKAO("07", "카카오"), // 카카오은행 7
    TOSS("08", "토스"), // 토스은행 8
    SUHYUP("09", "수협은행"), // 수협은행 9
    BUSAN("10", "부산은행"), // 부산은행 10
    KYUNGNAM("11", "경남은행"), // 경남은행 11
    KYANGJOO("12", "광주은행"), // 광주은행 12
    JUNBOK("13", "전북은행"), // 전북은행 13
    JEJOO("14", "제주은행"), // 제주은행 14
    LOTTE("15", "롯데카드"), // 롯데카드 15
    NONGHYUP("16", "농협은행"), // 농협은행 16
    SAMSUNG("17", "삼성카드"), // 삼성카드 17
    HYUNDAI("18", "현대카드"), // 현대카드 18
    WOORI("19", "우리은행"), // 우리은행 19
    SINHYUP("20", "신협은행"), // 신협은행 20
    SAEMAEULGEUMGO("21", "새마을금고"), // 새마을금고 21
    WOOCAEKUK("22", "우체국"); // 우체국 22

    private final String target;
    private final String title;
    BankName(String target, String title) {
        this.target = target;
        this.title = title;
    }

    public static BankName getTarget(String target) {
        return Arrays.stream(BankName.values())
                .filter(bank -> bank.getTarget().equals(target))
                .findFirst().orElse(null);
    }
}
