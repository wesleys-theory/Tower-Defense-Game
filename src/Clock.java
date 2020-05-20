import bagel.Input;
import bagel.Keys;

public class Clock {
    private static final int secondsBetweenSpawns = 5;
    // Enter your monitor's refresh rate here. Made public so Enemy class can access it for movement speed purposes.
    // Leaving it unchanged will produce a speed of 1 pixel/frame but may cause it to move slower/faster depending on
    // system specific things.
    public static final int refreshRate = 144;
    private static final int ticksBetweenSpawns = secondsBetweenSpawns*refreshRate;
    private static final int baseTimeMultiplier = 1;


    private int timeMultiplier;
    private int ticksSinceLastSpawn;

    public int getTimeMultiplier() {
        return timeMultiplier;
    }

    public Clock() {
        this.timeMultiplier = baseTimeMultiplier;
        // Want to start spawning straight away
        this.ticksSinceLastSpawn = ticksBetweenSpawns;
    }

    public void checkInput(Input input) {
        if (input.wasPressed(Keys.L)) {
            this.timeMultiplier += baseTimeMultiplier;
        }
        if (input.wasPressed(Keys.K) && this.timeMultiplier > baseTimeMultiplier) {
            this.timeMultiplier -= baseTimeMultiplier;
        }
    }

    // Checks if it is time to spawn a new enemy
    public boolean spawnDue() {
        // Need greater than since the ticks may be updated each frame by a number that doesn't divide
        // ticksBetweenSpawns
        if (this.ticksSinceLastSpawn >= ticksBetweenSpawns) {
            this.ticksSinceLastSpawn = 0;
            return true;
        }
        else {
            this.ticksSinceLastSpawn += this.timeMultiplier;
            return false;
        }
    }
}
