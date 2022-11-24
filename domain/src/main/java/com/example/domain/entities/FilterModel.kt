package com.example.domain.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

data class FilterModel(
    val percent_change_24_min: Long? = null,
    val percent_change_24_max: Long? = null,
    val volume_24_min: Long? = null,
    val volume_24_max: Long? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(percent_change_24_min ?: 0)
        parcel.writeValue(percent_change_24_max ?: 0)
        parcel.writeValue(volume_24_min ?: 0)
        parcel.writeValue(volume_24_max) ?: 0
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FilterModel> {
        override fun createFromParcel(parcel: Parcel): FilterModel {
            return FilterModel(parcel)
        }

        override fun newArray(size: Int): Array<FilterModel?> {
            return arrayOfNulls(size)
        }
    }
}
