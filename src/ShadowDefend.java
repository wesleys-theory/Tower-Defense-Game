import bagel.*;
import bagel.map.TiledMap;

import java.util.ArrayList;

// ShadowDefend game for SWEN20003, implemented by Nelson Walker


public class ShadowDefend extends AbstractGame {

    // Made public for drawing purposes
    public static final int HEIGHT = 768;
    public static final int WIDTH = 1024;

    private final ArrayList<Level> levels;
    private int levelIndex;
    private BuyPanel buyPanel;
    private StatusPanel statusPanel;
    public static TiledMap currentMap;


    public ShadowDefend() {
        super(WIDTH, HEIGHT, "ShadowDefend");
        levels = new ArrayList<>();
        levels.add(new Level("res/levels/1.tmx", "res/levels/waves.txt"));
        levels.add(new Level("res/levels/2.tmx", "res/levels/waves.txt"));
        levelIndex = 0;
        // Fixes rendering glitch
        new Image("res/images/slicer.png");
        buyPanel = new BuyPanel();
        statusPanel = new StatusPanel();
        buyPanel.registerObserver(statusPanel);

    }

    public static void main(String[] args) throws Exception {
        new ShadowDefend().run();
    }

    @Override
    protected void update(Input input) {
        if (getCurrentLevel().levelFinished() && levelIndex + 1 == levels.size()) {
            statusPanel.winnerAlert();
        }
        else if (getCurrentLevel().levelFinished()) {
            levelIndex++;
            buyPanel.reset();
            statusPanel.reset();
        }

        if (statusPanel.getLives() < 0) {
            Window.close();
        }

        getCurrentLevel().drawMap(WIDTH, HEIGHT);
        getCurrentLevel().update(input);
        getCurrentLevel().getWave().registerObserver(statusPanel);
        getCurrentLevel().getWave().registerObserver(buyPanel);
        currentMap = levels.get(levelIndex).getMap();
        statusPanel.update(input);
        buyPanel.update(input);
        render();
    }

    public Level getCurrentLevel() {
        return levels.get(levelIndex);
    }

    public void render() {
        buyPanel.render();
        statusPanel.render();
    }
}
