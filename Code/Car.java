import java.lang.reflect.Constructor;
import java.awt.Image;

//Thuộc tính xe
abstract class Car extends Transport // Lớp Car được kế thừa từ lớp Transport (Xe)
{

    private Image image; // Thuộc tính Ảnh
    public Car(Image image)
    {
        super(image);
        this.image = image; // hàm Construter khi gọi tên đối tượng Car thì đưa ảnh vào làm tham số
    }
	
	
	public Object clone() 
	{
        Constructor constructor = getClass().getConstructors()[0];
        try {
            return constructor.newInstance(new Object[] {image});
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
   public float getMaxSpeed()  // Lấy tốc độ cao nhất
    {
       return 0;
   }

    public void wakeUp() // Tăng tốc
    {
       setVelocityY(-getMaxSpeed()); //Tốc độ ban đầu - tốc độ Max
    }


    public void collideHorizontal() 
    {
       setVelocityX(-getVelocityX());
    }


    public void collideVertical()  
    {
        setVelocityY(-getVelocityY());
		
    }


}
