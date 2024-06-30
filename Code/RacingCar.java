	import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.awt.color.*;
import java.io.*;

public class RacingCar extends GameCore {  // Lớp giao diện chính của trò chơi

    public static void main(String[] args) {
                new RacingCar().run();
              
    }
	private String name = ""; // Tên người chơi
	private static  int Timermark=0; // Thông tin về thời gian của người chơi 
	private  static  int TimerGoal=12000;
    private Point pointCache = new Point(); // Lưu điểm 
    private TileMap map; // Bản đồ được gọi
    private ResourceManager resourceManager; // Khai báo lớp resourceManager
    private InputManager inputManager; // Khai báo lớp InputManager
    private TileMapRenderer renderer; // Khai báo lớp renderer

    private GameAction moveLeft; // Các kí tự người dùng thao tác
    private GameAction moveRight;
    private GameAction moveForward;
    private GameAction moveBack;
    private GameAction exit;
    
    private ReadWriteFile rwf = new ReadWriteFile(); // Gọi đến lớp đọc và ghi và ghi file txt
    
   	private final Image carImage = Toolkit.getDefaultToolkit().getImage("images/Car2.png");  // Ảnh của xe
    
	
	private long timeStart = System.currentTimeMillis(); // Khai báo thời gian bắt đầu
	
	public RacingCar() // hàm Contructor không tham số
	{ 
		this.name = rwf.readNamePlayer(); // Gán tên người chơi bằng tên được khởi tạo bằng StartGame
	}
	
    public void init() { // Khởi tạo các control , giao dien ...
    	
        super.init();
		
        this.setIconImage(carImage); // Ảnh của xe
    	this.setTitle("Racing Car"); // Tên 
    	
    	
        initInput();

        resourceManager = new ResourceManager(
        screen.getFullScreenWindow().getGraphicsConfiguration());

        renderer = new TileMapRenderer();
        renderer.setBackground(
            resourceManager.loadImage("background.png")); // Ảnh nền Background

        map = resourceManager.loadNextMap(); //Tải khung bản đồ từ các file map1.txt,map2.txt..  

    }

    public void stop() {
        super.stop(); // được lấy từ lớp GameCore
    }


    private void initInput() {  // các phím khi được người chơi thao tác .
        moveLeft = new GameAction("moveLeft");
        moveRight = new GameAction("moveRight");
        
        moveForward = new GameAction("moveForward");
        moveBack = new GameAction("moveBack");
        
        exit = new GameAction("exit",
            GameAction.DETECT_INITAL_PRESS_ONLY);

        inputManager = new InputManager(
            screen.getFullScreenWindow());
        inputManager.setCursor(InputManager.INVISIBLE_CURSOR);

        inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
        inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
        
        inputManager.mapToKey(moveBack, KeyEvent.VK_DOWN);
        inputManager.mapToKey(moveForward, KeyEvent.VK_UP);
        
        inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
    }


    private void checkInput(long elapsedTime) {  // Hàm này có tác dụng kiểm tra đầu vào (người dùng nhập)..

        if (exit.isPressed()) {
            stop();
        }

        Player player = (Player)map.getPlayer();
        	{
            float velocityX = 0;
            float velocityY = 0;
            if (moveLeft.isPressed()) {
                velocityX-=player.getMaxSpeed();
            }
            if (moveRight.isPressed()) {
                velocityX+=player.getMaxSpeed();
            }
            if (moveForward.isPressed()) {
                velocityY-=player.getMaxSpeed();
            }
            if (moveBack.isPressed()) {
                velocityY+=player.getMaxSpeed();
            }
            player.setVelocityX(velocityX);
            player.setVelocityY(velocityY);
        }

    }


    public void draw(Graphics2D g) // vẽ các chướng ngoại vật theo map1.txt,map2.txt ...
    {
    
        renderer.draw(g, map,
            screen.getWidth(), screen.getHeight());	
        drawControl(g);
    }

	
	private void drawControl(Graphics2D g) // Hiện thị các thông số và người chơi ( Time,Level,NamPlayer ....)
	{
		
    	g.drawImage(resourceManager.loadImage("10.png"), 0 , 0, null);
    	g.setColor(Color.BLUE);
    	 if(!resourceManager.IsResuft()) // Nếu hoàn thành level 3 thì trò chơi dừng lại
    	 {
             Timermark=Integer.valueOf(String.valueOf(System.currentTimeMillis() - timeStart));
         	g.drawString("Time : " + String.valueOf(Timermark)+" < " +TimerGoal,10,30);
     	g.drawString("Level : " + String.valueOf(resourceManager.getCurrentMap()), 10, 80);	
    		 }
    		 else 
    		 	
    		 {
    		 		g.drawString("Time : " + Timermark,10,30);
    		 		            g.drawString("Level : 3", 10, 80);	
    		 }
    	
    		g.setColor(Color.RED);
    		g.drawString("NamePlayer : " +  this.name, 15, screen.getHeight()-20);	
    			g.setColor(Color.yellow);
		if(resourceManager.IsResuft())
	        g.drawString(" Không bất ngờ cho lắm, bạn là người chiến thắng  _(^.^)_", 10, (screen.getHeight()/2)+20); // Khi hoàn thành level 3 thì sẽ hiện thông báo

	}
	
    public TileMap getMap() { // Lấy bản đồ
        return map;
    }


    public Point getTileCollision(Transport transport,   // Hàm xét các đụng độ, va chạm của người chơi với chướng ngoại vật
        float newX, float newY)
    {
        float fromX = Math.min(transport.getX(), newX);
        float fromY = Math.min(transport.getY(), newY);
        float toX = Math.max(transport.getX(), newX);
        float toY = Math.max(transport.getY(), newY);


        int fromTileX = TileMapRenderer.pixelsToTiles(fromX);
        int fromTileY = TileMapRenderer.pixelsToTiles(fromY);
        int toTileX = TileMapRenderer.pixelsToTiles(
            toX + transport.getWidth() - 1);
        int toTileY = TileMapRenderer.pixelsToTiles(
            toY + transport.getHeight() - 1);


        for (int y = fromTileY; y <= toTileY; y++){
            for (int x = fromTileX; x <= toTileX; x++)  {
                if (y < 0 || y >= map.getHeight() ||
                	x < 0 || x >= map.getWidth() ||
                	map.getTile(x, y) != null)
                {
                    pointCache.setLocation(x, y);
                    return pointCache;
                }
            }
        }

        return null;
    }


    public boolean isCollision(Transport t1, Transport t2) { // Xét có xảy ra đụng độ hay không
        if (t1 == t2) {
            return false;
        }


        int t1x = Math.round(t1.getX());
        int t1y = Math.round(t1.getY());
        int t2x = Math.round(t2.getX());
        int t2y = Math.round(t2.getY());

        return (t1x < t2x + t2.getWidth() &&
            t2x < t1x + t1.getWidth() &&
            t1y < t2y + t2.getHeight() &&
            t2y < t1y + t1.getHeight());
    }


    public Transport getTransportCollision(Transport transport) 
    {

        Iterator i = map.getTransport();
        while (i.hasNext()) {
            Transport otherTransport = (Transport)i.next();
            if (isCollision(transport, otherTransport)) {
                return otherTransport;
            }
        }

        return null;
    }


    public void update(long elapsedTime) { // Cập nhật thời gian mọi lúc ở level khác
        Car player = (Car)map.getPlayer();


        
        checkInput(elapsedTime);

        updateTransport(player, elapsedTime);
        player.update(elapsedTime);

        Iterator i = map.getTransport();
        while (i.hasNext()) {
            Transport transport = (Transport)i.next();
            if (transport instanceof Car) {
                Car car = (Car)transport;
                updateTransport(car, elapsedTime);
            }
            transport.update(elapsedTime);
        }
    }


    private void updateTransport(Car car, // Cập nhật vị trí của xe
        long elapsedTime)
    {

        // change x
        float dx = car.getVelocityX();
        float oldX = car.getX();
        float newX = oldX + dx * elapsedTime;
        Point tile =
            getTileCollision(car, newX, car.getY());
        if (tile == null) {
            car.setX(newX);
        }
        else {
            if (dx > 0) {
                car.setX(
                    TileMapRenderer.tilesToPixels(tile.x) -
                    car.getWidth());
            }
            else if (dx < 0) {
                car.setX(
                    TileMapRenderer.tilesToPixels(tile.x + 1));
            }
            car.collideHorizontal();
        }
        if (car instanceof Player) {
            checkPlayerCollision((Player)car);
        }

        // change y
        float dy = car.getVelocityY();
        float oldY = car.getY();
        float newY = oldY + dy * elapsedTime;
        tile = getTileCollision(car, car.getX(), newY);
        if (tile == null) {
            car.setY(newY);
        }
        else {
            if (dy > 0) {
                car.setY(
                    TileMapRenderer.tilesToPixels(tile.y) -
                    car.getHeight());
            }
            else if (dy < 0) {
                car.setY(
                    TileMapRenderer.tilesToPixels(tile.y + 1));
            }
            car.collideVertical();
        }
        if (car instanceof Player) {
            checkPlayerCollision((Player)car);
        }

    }

    public void checkPlayerCollision(Player player) // Kiểm tra đụng độ người chơi
    {

        Transport collisionTransport = getTransportCollision(player);
        
        if (collisionTransport instanceof PowerUp) {
            acquirePowerUp((PowerUp)collisionTransport);
        }
        else if (collisionTransport instanceof Car) {
            Transport transport = (Transport)collisionTransport;

			if(transport.getY() < player.getY())
			{
				player.setY(transport.getY() + player.getHeight());
			}
			else if(transport.getY() > player.getY())
			{
				transport.setY(player.getY() + transport.getHeight());
			}			
			else if(transport.getX() > player.getX())
			{
				player.setX(transport.getX() - player.getWidth());
			}
			else if(transport.getX() < player.getX())
			{
				player.setX(transport.getX() + player.getWidth());
			}
                
        }
            
    }

    public void acquirePowerUp(PowerUp powerUp) {  // Kiểm tra xem người chơi có tăng level hoặc ghi điểm hay không?
       
        map.removeTransport(powerUp);

        if (powerUp instanceof PowerUp.Goal) {
        	
        	if(resourceManager.getCurrentMap()==1 && Timermark< TimerGoal)
        	{  map = resourceManager.loadNextMap(); TimerGoal=22000; } // Tăng lên level 2
        	else if(resourceManager.getCurrentMap()==2 && Timermark < TimerGoal)
        	{ map = resourceManager.loadNextMap();TimerGoal=32000;} // Tăng lên level 3
        	else if(resourceManager.getCurrentMap()==3 && Timermark < TimerGoal)
        	{ map = resourceManager.loadNextMap();timeStart = System.currentTimeMillis();}	
            else
             {
             	resourceManager.setCurrentMap(1);  // Nếu không qua được level thì cho về level đầu tiên
             	 map = resourceManager.reloadMap();
                        TimerGoal=12000;   timeStart = System.currentTimeMillis();   
             }
            
            if(resourceManager.IsResuft() && Timermark<30000) // Điểm cao thì 30000 trở xuống
            {
            	rwf.writeFileScore(name+" : "+Timermark); // Nếu thắng ghi lại thông tin của người chơi ( điểm + thời gian hoàn thành )
            }
            	
        }
    }

}