package com.example.domain.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

data class SortModel(
    val sort : String ?= null,
    val sort_dir : String ?= null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sort ?: "")
        parcel.writeString(sort_dir ?: "")
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SortModel> {
        override fun createFromParcel(parcel: Parcel): SortModel {
            return SortModel(parcel)
        }

        override fun newArray(size: Int): Array<SortModel?> {
            return arrayOfNulls(size)
        }
    }
}
