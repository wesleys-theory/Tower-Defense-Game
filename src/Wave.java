import bagel.Input;
import bagel.Keys;

import java.util.ArrayList;

public class Wave {
    private static final int maxEnemies = 5;

    private int numEnemiesSpawned;
    private ArrayList<Enemy> enemies;
    private Clock timer;
    private boolean inProgress;

    // Wave constructor
    public Wave() {
        this.numEnemiesSpawned = 0;
        this.enemies = new ArrayList<>(maxEnemies);
        this.timer = new Clock();
        this.inProgress = false;
    }

    public void checkInput(Input input) {
        this.timer.checkInput(input);
        if (input.wasPressed(Keys.S) && !this.inProgress) {
            this.inProgress = true;
            this.updateWave();
        }
        else if (this.inProgress) {
            this.updateWave();
        }
    }

    public void updateWave() {
        if (this.timer.spawnDue()) {
            spawnEnemy();
        }
        for (int i=0; i<numEnemiesSpawned; i++) {
            enemies.get(i).move(this.timer.getTimeMultiplier());
            enemies.get(i).render();
        }
    }

    public void spawnEnemy() {
        // Currently only spawns slicers
        if (this.numEnemiesSpawned < maxEnemies) {
            this.enemies.add(new Slicer(this.timer.getTimeMultiplier()));
            this.numEnemiesSpawned++;
        }
    }

    public boolean waveFinished() {
        return (this.numEnemiesSpawned == maxEnemies && this.enemies.get(maxEnemies - 1).atEnd());
    }
}
