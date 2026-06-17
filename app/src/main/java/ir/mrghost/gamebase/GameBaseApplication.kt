package ir.mrghost.gamebase

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameBaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppModule.init(this)

        CoroutineScope(Dispatchers.IO).launch {
            AppModule.initializeDatabase()
        }
    }
}