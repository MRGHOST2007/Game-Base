package ir.mrghost.gamebase

import android.app.Application

class GameBaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppModule.init(this)
    }
}