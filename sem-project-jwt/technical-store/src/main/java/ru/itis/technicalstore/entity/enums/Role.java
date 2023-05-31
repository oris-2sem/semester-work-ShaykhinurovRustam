package ru.itis.technicalstore.entity.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    USER("user", "Пользователь"), SELLER("seller", "Продавец"), ADMIN("admin", "Администратор");

    private final String serviceName;
    private final String displayName;

    public String getServiceName() {
        return serviceName;
    }
    public String getDisplayName(){
        return displayName;
    }
}
