package com.para_ti.chocoapp.data.firebase


import com.google.firebase.firestore.FirebaseFirestore

object FirebaseService {
    val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
}
