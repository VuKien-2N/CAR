import java.awt.Image;
import java.util.LinkedList;
import java.util.Iterator;

class TileMap {  // Day la lop doi tuong Ban Do

    private Image[][] tiles; // Cac anh
    private LinkedList transports; // Cac phuong tien giao thong
    private Transport player; // Nguoi choi


    public TileMap(int width, int height) {  // Quy dinh kich co cho ban do
        tiles = new Image[width][height];
        transports = new LinkedList();
    }

  // Cac phuong thuc lay chieu Rong, do Cao ./
    public int getWidth() {
        return tiles.length;
    }

    public int getHeight() {
        return tiles[0].length;
    }

    public Image getTile(int x, int y) {
        if (x < 0 || x >= getWidth() ||
            y < 0 || y >= getHeight())
        {
            return null;
        }
        else {
            return tiles[x][y];
        }
    }

// Cac phuong thuc dat gia tri cho cac thuoc tinh
    public void setTile(int x, int y, Image tile) {
        tiles[x][y] = tile;
    }


    public Transport getPlayer() {
        return player;
    }


    public void setPlayer(Transport player) {
        this.player = player;
    }

// Them vao cac doi tuong cua lop xe
    public void addTransport(Transport transport) {
        transports.add(transport);
    }


    public void removeTransport(Transport transport) {
        transports.remove(transport);
    }


    public Iterator getTransport() {
        return transports.iterator();
    }

}
