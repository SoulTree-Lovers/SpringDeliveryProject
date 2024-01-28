package org.delivery.db.userorder.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserOrderStatus {
    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ORDER("주문"),
    ACCEPT("확인"),
    COOKING("조리 중"),
    DELIVERY("배달 중"),
    RECEIVE("배달 완료"),
    ;

    private final String description;
}
