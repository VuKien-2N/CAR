import javax.swing.RepaintManager;
import javax.swing.JComponent;

public class NullRepaintManager extends RepaintManager
{

  // Cắt bỏ các mẫu dư thừa không cần thiết khi người dùng di chuyển các Button
  //Thay vào đó nó sẽ thay đổi bằng hình nền của nó
 
    public static void install() {
        RepaintManager repaintManager = new NullRepaintManager();
        repaintManager.setDoubleBufferingEnabled(false);
        RepaintManager.setCurrentManager(repaintManager);
    }
    public void addInvalidComponent(JComponent c) {
       
    }
    public void addDirtyRegion(JComponent c, int x, int y,
        int w, int h)
    {
      
    }
    public void markCompletelyDirty(JComponent c) {
       
    }
    public void paintDirtyRegions() {
   
    }
   

}
