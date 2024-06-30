import java.awt.*;
import java.util.Iterator;


class TileMapRenderer {

  //TileMapRenderer se cap nhat lien tuc man hinh. 
        //Khi xe oto cua ngýoi choi di chuyen thi man hinh cung dich chuyen theo và cap nhat vi tri cua cac xe
        // khac co tren ðýong ðua.
    private static final int TILE_SIZE = 64;
    private static final int TILE_SIZE_BITS = 6;

    private Image background;


    public static int pixelsToTiles(float pixels) 
    {
        return pixelsToTiles(Math.round(pixels));
    }


    public static int pixelsToTiles(int pixels) 
    {
        return pixels >> TILE_SIZE_BITS;

    }


    public static int tilesToPixels(int numTiles) 
    {
        return numTiles << TILE_SIZE_BITS;

    }


    public void setBackground(Image background) 
    {
        this.background = background;
    }


   public void draw(Graphics2D g, TileMap map,
        int screenWidth, int screenHeight)
    {
        Transport player = map.getPlayer();
        int mapHeight = tilesToPixels(map.getHeight());
        
       

        int offsetY = screenHeight / 2 -
            Math.round(player.getY()) - TILE_SIZE;
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, screenHeight - mapHeight);
        int offsetX = screenWidth -
            tilesToPixels(map.getWidth());

        if (background == null ||
            screenHeight > background.getHeight(null))
        {
            g.setColor(Color.gray);
            g.fillRect(0, 0, screenWidth, screenHeight);
        }

        if (background != null) {
            int y = offsetY *
                (screenHeight - background.getHeight(null)) /
                (screenHeight - mapHeight);
            int x = screenWidth - background.getWidth(null);

            g.drawImage(background, x, y, null);
        }

        int firstTileY = pixelsToTiles(-offsetY);
        int lastTileY = firstTileY +
            pixelsToTiles(screenHeight) + 1;
		

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = firstTileY; y <= lastTileY; y++) {
            	
                Image image = map.getTile(x, y);
                if (image != null) {
                    g.drawImage(image,
                        tilesToPixels(x) + offsetX,
                        tilesToPixels(y) + offsetY,
                        null);
                }
            }
        }

        // draw player
        g.drawImage(player.getImage(),
            Math.round(player.getX()) + offsetX,
            Math.round(player.getY()) + offsetY,
            null);

        // draw Cars
        Iterator i = map.getTransport();
        while (i.hasNext()) {
            Transport transport = (Transport)i.next();
            int x = Math.round(transport.getX()) + offsetX;
            int y = Math.round(transport.getY()) + offsetY;
            g.drawImage(transport.getImage(), x, y, null);

            if (transport instanceof Car &&
                x >= 0 && x < screenWidth)
            {
                ((Car)transport).wakeUp();
            }
        }
    }

}
