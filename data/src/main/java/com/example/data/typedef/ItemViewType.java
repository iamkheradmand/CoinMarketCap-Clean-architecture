package com.example.data.typedef;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by marshall-mathers on 4/7/19.
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({ItemViewType.DEFAULT, ItemViewType.LOADING})
public @interface ItemViewType {
    int DEFAULT = 0;
    int LOADING = 2;
}
