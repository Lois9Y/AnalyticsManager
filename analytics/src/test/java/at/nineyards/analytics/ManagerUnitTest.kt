package at.nineyards.analytics

import org.junit.Test
import java.io.File

/**
 * Created by Lois-9Y on 19/09/2017.
 */
class ManagerUnitTest{

    @Test
    fun sendEventTest(){
        val manager = AnalyticsManager(this.javaClass.classLoader.getResourceAsStream("test.json"))
        manager.sendEvent("someevent","test",10.0)
    }
}
