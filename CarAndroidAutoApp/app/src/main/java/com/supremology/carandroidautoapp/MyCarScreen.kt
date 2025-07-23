package com.supremology.carandroidautoapp

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.*

class MyCarScreen(carContext: CarContext) : Screen(carContext) {

    override fun onGetTemplate(): Template {
        val row = Row.Builder()
            .setTitle("Welcome to MyCarApp 🚗")
            .addText("This is a simple Android Auto projection app.")
            .build()

        val pane = Pane.Builder()
            .addRow(row)
            .build()

        return PaneTemplate.Builder(pane)
            .setTitle("Home Screen")
            .setHeaderAction(Action.APP_ICON) // or Action.BACK if needed
            .build()
    }
}
