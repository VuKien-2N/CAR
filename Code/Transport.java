import java.awt.Image;

class Transport { // Lớp này xây dựng các thuộc tính của đối tượng xe, 
                         //và các phương thức cài đặt trên lớp kế thừa
	protected Image image; // ảnh của xe 
    private float x; // Các tọa độ
    private float y; 
    private float dx; 
    private float dy; 
 
	public Transport(Image image)
	{
		this.image = image; // Hàm Contructor truyen tham so ảnh
	}

    public void update(long elapsedTime) // Phương thức update(long elapsedTime) sẽ được cập nhật tọa độ
                                                   // cuar phương tiện giao thông đó theo một khoảng thời gian
    {
        x += dx * elapsedTime;
        y += dy * elapsedTime;
    }

//  Lấy giá trị của các thuộc tính
    public float getX() 
    {
        return x;
    }

    public float getY() 
    {
        return y;
    }
    public float getVelocityX() 
    {
        return dx;
    }
    public float getVelocityY() 
    {
        return dy;
    }
    public int getWidth() 
    {
        return image.getWidth(null);
    }
    public int getHeight() 
    {
        return image.getHeight(null);
    }
     
    public Image getImage() {
        return image;
    }
    
   // Thiết đặt giá trị cho các thuộc tính
    public void setX(float x) 
    {
        this.x = x;
    }
    public void setY(float y) 
    {
        this.y = y;
    }
    public void setVelocityX(float dx) 
    {
        this.dx = dx;
    }


    public void setVelocityY(float dy) 
    {
        this.dy = dy;
    }
    
    public Object clone() // 
    {
        return new Transport(image);
    }

}
