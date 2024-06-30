import java.lang.reflect.Constructor;
import java.awt.Image;


abstract class PowerUp extends Transport  // Lớp này hoạt đông khi ng chơi hoàn thành trò chơi Goal
{
  	
  	
    public PowerUp(Image image) { 
        super(image);
    }


	public Object clone() {
        Constructor constructor = getClass().getConstructors()[0];
        try 
        {
            return constructor.newInstance(new Object[] {image});
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static class Goal extends PowerUp  // Kế thừa từ lớp trên vừa xây dựng
    {
        public Goal(Image image) {  // Ảnh biểu tượng về đích
            super(image);
        }
    }

}
