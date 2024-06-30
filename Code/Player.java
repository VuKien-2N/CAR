import java.awt.Image;


public class Player extends Car //  Lớp người chơi được kế thừa từ lớp Car
{

	private Image image;  // thuộc tính ảnh


    public Player(Image image)  // Contructor gắn ảnh cho đối tượng
    {
        super(image);
        this.image = image;
    }

   // Thiet dat va tinh dung do
    public void collideVertical() {   
        setVelocityY(0);
    }
    
    public void collideHorizontal() {
        setVelocityX(0);
    }
    public float getMaxSpeed() {  //speed cao nhất của người chơi 
        return 0.2f;
    }

}
