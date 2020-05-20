import bagel.AbstractGame;
import bagel.Image;
import bagel.Input;
import bagel.Window;
import bagel.map.TiledMap;

// ShadowDefend game for SWEN20003, implemented by Nelson Walker

// PLEASE read comments in Enemy.java and Clock.java about enemy movement speed and timings. Thank you :)

public class ShadowDefend extends AbstractGame {

    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;

    private Wave wave;
    private TiledMap map;

    public ShadowDefend() {
        super(WIDTH, HEIGHT, "ShadowDefend");
        this.wave = new Wave();
        this.map = new TiledMap("res/levels/1.tmx");
        // Fixes rendering glitch
        new Image("res/images/slicer.png");
    }

    public static void main(String[] args) throws Exception {
        new ShadowDefend().run();
    }

    @Override
    protected void update(Input input) {
        this.map.draw(0,0,0,0,WIDTH, HEIGHT);
        this.wave.checkInput(input);
        if (this.wave.waveFinished()) {
            Window.close();
        }
    }
}
