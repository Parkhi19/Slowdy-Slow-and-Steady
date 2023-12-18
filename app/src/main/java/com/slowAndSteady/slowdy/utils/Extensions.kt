package com.slowAndSteady.slowdy.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.CoroutineContext

 fun CoroutineScope.launchAwaitingVerification(coroutineContext: CoroutineContext, block: suspend () -> Unit) {
    launch {
        val emailVerified = checkEmailVerification(FirebaseAuth.getInstance().currentUser)
        if (emailVerified) {
            block()
        } else {
            delay(200)
            launchAwaitingVerification(coroutineContext, block)
        }
    }

}
suspend fun checkEmailVerification(user: FirebaseUser?): Boolean{
    user?.reload()?.await()
    return user?.isEmailVerified==true
}