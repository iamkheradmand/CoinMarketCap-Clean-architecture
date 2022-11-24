package com.example.data.typedef;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by marshall-mathers on 11/24/2022.
 */

@Retention(RetentionPolicy.SOURCE)
@StringDef({SortType.NAME, SortType.PRICE, SortType.CIRCULATING_SUPPLY, SortType.NAMARKET_CAPE})
public @interface SortType {
    String NAME = "name";
    String PRICE = "price";
    String CIRCULATING_SUPPLY = "circulating_supply";
    String NAMARKET_CAPE = "market_cap";
}
