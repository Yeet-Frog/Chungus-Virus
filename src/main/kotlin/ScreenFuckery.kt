import com.sun.awt.AWTUtilities
import com.sun.jna.Native
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.platform.win32.WinUser
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.imageio.ImageIO
import javax.swing.JComponent
import javax.swing.Timer
import kotlin.random.Random


object ScreenFuckery {
    fun fuck() {
        for (i in GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices.indices) {
            fuckScreen(i)
        }
    }

    fun fuckScreen(screen: Int) {
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val gd = ge.screenDevices
        val frame = fuckerWindow(
            gd[screen].defaultConfiguration.bounds.x,
            gd[screen].defaultConfiguration.bounds.y,
            gd[screen].defaultConfiguration.bounds.width,
            gd[screen].defaultConfiguration.bounds.height
        )
    }

    fun fuckerWindow(x: Int, y: Int, width: Int, height: Int): Window {
        val w = Window(null)
        val c = object : JComponent(), ActionListener {
            val img = ImageIO.read(javaClass.getResourceAsStream("screenoverlay.png")).getScaledInstance(height, height, 0)

            /**
             * This will draw a black cross on screen.
             */
            override fun paintComponent(g: Graphics) {
               val image = Robot().createScreenCapture(Rectangle(Toolkit.getDefaultToolkit().screenSize)).getScaledInstance(
                    (width * 0.8).toInt(), (height * 0.8).toInt(), 0)
                g.drawImage(image, (width * 0.1).toInt(), (height * 0.1).toInt(), this)
                //g.drawImage(img, width - height, 0, this)
                val xm = width / 10
                val ym = height / 10
                /*for (x2 in 0..9) {
                    for (y2 in 0..9) {
                        g.color = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), 50 + Random.nextInt(150))
                        g.fillRect(x2 * xm, y2 * ym, xm, ym)
                    }
                }*/

            }

            override fun getPreferredSize(): Dimension {
                return Dimension(width, height)
            }

            override fun actionPerformed(e: ActionEvent?) {
                repaint()
                w.isAlwaysOnTop = true
                //w.isAlwaysOnTop = false
            }
        }
        w.add(c)
        w.setLocation(x, y)
        w.pack()
        //w.setLocationRelativeTo(null)
        w.setVisible(true)
        w.isAlwaysOnTop = false//changed
        /**
         * This sets the background of the window to be transparent.
         */
        /**
         * This sets the background of the window to be transparent.
         */
        AWTUtilities.setWindowOpaque(w, false)
        setTransparent(w)
        //frame.shape = Rectangle(200, 200, 100, 100)
        val timer = Timer(100, c)
        timer.start()
        return w
    }

    private fun setTransparent(w: Component) {
        val hwnd = getHWnd(w)
        var wl = User32.INSTANCE.GetWindowLong(hwnd, WinUser.GWL_EXSTYLE)
        wl = wl or WinUser.WS_EX_LAYERED or WinUser.WS_EX_TRANSPARENT
        User32.INSTANCE.SetWindowLong(hwnd, WinUser.GWL_EXSTYLE, wl)
    }

    /**
     * Get the window handle from the OS
     */
    private fun getHWnd(w: Component): HWND {
        val hwnd = HWND()
        hwnd.pointer = Native.getComponentPointer(w)
        return hwnd
    }
}