package ru.itis.technicalstore.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    INSTOCK("На складе"), DELIVERY("Доставка"), DELIEVERED("Доставлен");

    private final String displayStatusName;
}
