package com.example.appmaroto.repository

import com.google.firebase.messaging.FirebaseMessaging

object SubscriptionRepository {

    const val MAROTAGENS = "marotagens"

    fun subscribeMarotagens(){
        FirebaseMessaging.getInstance().subscribeToTopic(MAROTAGENS)
    }

    fun unsubscribeMarotagens(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(MAROTAGENS)
    }
}