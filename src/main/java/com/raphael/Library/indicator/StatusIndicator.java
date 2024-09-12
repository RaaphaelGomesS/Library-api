package com.raphael.Library.indicator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusIndicator {

    CRIADO("criar"),
    POSTERGADO("renovar"),
    FINALIZADO("finalizar");

    private final String action;

    public static StatusIndicator getValueByAction(String action) {

        for (StatusIndicator indicator : values()) {
            if (indicator.getAction().contains(action)) {
                return indicator;
            }
        }
        return null;
    }
}
