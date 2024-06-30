import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;


class ResourceManager {

    private ArrayList tiles; // Tạo mảng
    private int currentMap; // Số hiệu của bản đồ hiện tại 1.2.3
    private GraphicsConfiguration gc;

    private Transport player; // Đối tượng người chơi
    private Transport goal; // Đối tượng Goal
    private Transport obstacle; // Đối tượng chướng ngoại vật 
    private Transport rival; // Đối tượng đối thủ
    public boolean resuft = false;



    public ResourceManager(GraphicsConfiguration gc) {
        this.gc = gc;
        loadTileImages();
        loadTransport();
        loadPowerUpTransport();
    }


    public Image loadImage(String name)  // Load ảnh
    {
    	
        String filename = "images/" + name;
        return new ImageIcon(filename).getImage();
    }



    public TileMap loadNextMap() { // Lấy bản đồ tiếp theo
        TileMap map = null;
        while (map == null) {
            currentMap++;
            try {
                map = loadMap(
                    "maps/map" + currentMap + ".txt");
            }
            catch (IOException ex) {
                resuft = true;
                currentMap = 0;
                map = null;
            }
        }

        return map;
    }

	
	public boolean IsResuft()
	{
		return resuft;
	}	
	public int getCurrentMap() // Lấy bản đồ hiện tại
	{
		return currentMap;
	}
	public void setCurrentMap(int _Level) // Gắn bản đồ theo Level
	{
		this.currentMap=_Level;
	}
    public TileMap reloadMap() {  // Load lại bản đồ
        try {
            return loadMap(
                "maps/map" + currentMap + ".txt");
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    private TileMap loadMap(String filename) //Trả về một TileMap. Khi được truyền vào một đối số, 
      //tham số này chính là tên của tập tin là bản đồ đường đi khi điều khiển xe. Dựa trên một số được truyền vào một chuỗi
        throws IOException
    {
        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;
        int xstart = 0;
        int ystart = 0;

        BufferedReader reader = new BufferedReader(
            new FileReader(filename));
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                reader.close();
                break;
            }

            if (!line.startsWith("#")) {
                lines.add(line);
                height = Math.max(height, line.length());
            }
        }

        width = lines.size();
        TileMap newMap = new TileMap(width, height);
        
        for (int x=0; x<width; x++) {
            String line = (String)lines.get(x);
            for (int y=0; y<line.length(); y++) {
                char ch = line.charAt(y);

                int tile = ch - 'A';
                if (tile >= 0 && tile < tiles.size()) {
                    newMap.setTile(x, y, (Image)tiles.get(tile));
                }

                else if (ch == 'o') {
                    addTransport(newMap, obstacle, x, y);
                }

                else if (ch == '*') {
                    addTransport(newMap, goal, x, y);
                }
                else if (ch == '1') {
                    addTransport(newMap, rival, x, y);
                }
                else if (ch == '2') {
                    addTransport(newMap, rival, x, y);
                }
                else if (ch == 's'){
                	xstart = x;
                	ystart = y;
                	System.out.println (x + " : " + y);
                }
                	
                	
            }
        }

        Transport playerTransport = (Transport)player.clone();
        playerTransport.setX(TileMapRenderer.tilesToPixels(xstart) +
                (TileMapRenderer.tilesToPixels(1) -
                playerTransport.getWidth()) / 2);
        playerTransport.setY(TileMapRenderer.tilesToPixels(ystart + 1) -
                playerTransport.getHeight());
        newMap.setPlayer(playerTransport);

        return newMap;
    }


    private void addTransport(TileMap map, Transport hostTransports, int tileX, int tileY)  // Thêm một xe trên bản đồ
    {
        if (hostTransports != null) {
            Transport transport = (Transport)hostTransports.clone();

            transport.setX(
                TileMapRenderer.tilesToPixels(tileX) +
                (TileMapRenderer.tilesToPixels(1) -
                transport.getWidth()) / 2);

            transport.setY(
                TileMapRenderer.tilesToPixels(tileY + 1) -
                transport.getHeight());

            map.addTransport(transport);
        }
    }


    public void loadTileImages() // Load ảnh của bản đồ
    {
        tiles = new ArrayList();
        char ch = 'A';
        while (true) {
            String name = "tile_" + ch + ".png";
            File file = new File("images/" + name);
            if (!file.exists()) {
                break;
            }
            tiles.add(loadImage(name));
            ch++;
        }
    }


    public void loadTransport() { // Load các loại phương tiện trên đường


        player = new Player(loadImage("player.png"));
        obstacle = new Obstacle(loadImage("obstacle.png"));
        rival = new Rival(loadImage("rival.png"));
    }

    // Tạo người, chướng ngoại vật, đối thủ
    private Image createPlayer(Image player)
    {
        
        return player;
    }


    private Image createObstacle(Image img)
    {
        return img;
    }


    private Image createGrubAnim(Image img) 
    {
        return img;
    }


    private void loadPowerUpTransport() {
        
        goal = new PowerUp.Goal(loadImage("goal.png"));
    }

}
