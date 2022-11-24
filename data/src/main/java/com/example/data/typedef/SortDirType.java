package com.example.data.typedef;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by marshall-mathers on 11/24/2022.
 */

@Retention(RetentionPolicy.SOURCE)
@StringDef({SortDirType.ASC, SortDirType.DESC})
public @interface SortDirType {
    String ASC = "asc";
    String DESC = "desc";
}
