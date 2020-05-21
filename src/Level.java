import bagel.map.TiledMap;

public class Level {
    private TiledMap map;
    private boolean levelComplete;

    public Level(String mapPath) {
        this.map = new TiledMap(mapPath);
    }

    public void drawMap(int width, int height) {
        map.draw(0,0,0,0, width, height);
    }
}
