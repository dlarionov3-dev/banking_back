package larionov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyCode {

    // Константы теперь принимают два аргумента: код и описание
    EUR("EUR", "Европейская валюта"),
    USD("USD", "Доллар США"),
    GBP("GBP", "Британский фунт стерлингов"),
    RUB("RUB", "Российский рубль");

    private final String code;
    private final String description;

}
