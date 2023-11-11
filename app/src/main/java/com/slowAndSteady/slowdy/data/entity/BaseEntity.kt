package com.slowAndSteady.slowdy.data.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class BaseEntity(
    var id: String? = null
): Parcelable