package com.supremology.carandroidautoapp

import android.content.Intent
import androidx.car.app.CarAppService
import androidx.car.app.Screen
import androidx.car.app.Session
import androidx.car.app.validation.HostValidator
import androidx.car.app.SessionInfo


class MyCarAppService : CarAppService() {
    override fun onCreateSession(sessionInfo:SessionInfo): Session {
        return object : Session() {
            override fun onCreateScreen(intent: Intent): Screen {
                return MyCarScreen(carContext)
            }
        }
    }

    override fun createHostValidator(): HostValidator {
        return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR // Relax for dev
    }
}