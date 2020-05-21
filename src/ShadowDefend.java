import bagel.AbstractGame;
import bagel.Image;
import bagel.Input;
import bagel.Window;
import bagel.map.TiledMap;

import java.util.ArrayList;

// ShadowDefend game for SWEN20003, implemented by Nelson Walker


public class ShadowDefend extends AbstractGame {

    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;

    private final ArrayList<Level> levels;
    private int levelIndex;
    private BuyPanel buyPanel;


    public ShadowDefend() {
        super(WIDTH, HEIGHT, "ShadowDefend");
        levels = new ArrayList<>();
        levels.add(new Level("res/levels/1.tmx"));
        levels.add(new Level("res/levels/2.tmx"));
        levelIndex = 0;
        // Fixes rendering glitch
        new Image("res/images/slicer.png");
        buyPanel = new BuyPanel();

    }

    public static void main(String[] args) throws Exception {
        new ShadowDefend().run();
    }

    @Override
    protected void update(Input input) {
        getCurrentLevel().drawMap(WIDTH, HEIGHT);
        render();
    }

    public Level getCurrentLevel() {
        return levels.get(levelIndex);
    }

    public void render() {
        buyPanel.render();
    }
}
