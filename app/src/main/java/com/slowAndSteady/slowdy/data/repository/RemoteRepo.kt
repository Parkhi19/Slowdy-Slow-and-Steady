package com.slowAndSteady.slowdy.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.slowAndSteady.slowdy.data.entity.BaseEntity

interface RemoteRepo {
    val collectionName : String
    val collectionReference
        get() = FirebaseFirestore.getInstance().collection(collectionName)
}