import java.awt.Image;

class Rival extends Car // Lớp đối tượng của xe đối thủ được kế thừa từ lớp Car
{

    public Rival(Image image) // Đặt ảnh
    {
        super(image);
    }

    public float getMaxSpeed() { // Tốc độ cao nhất của xe chạy được
        return 0.19f;
    }

}