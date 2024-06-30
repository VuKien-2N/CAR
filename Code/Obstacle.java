import java.awt.Image;

class Obstacle extends Car { //Đây là chướng ngoại vật được kế thừa từ lớp Car

    public Obstacle(Image image) //  Đặt ảnh cho chướng ngoại vật
    {
        super(image);
    }


    public float getMaxSpeed() { // Lấy tốc độ max cho chướng ngoại vật
        return 0.05f;
    }

}
