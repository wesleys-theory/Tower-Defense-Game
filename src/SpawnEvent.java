import java.util.ArrayList;

public class SpawnEvent extends WaveEvent {
    private int numToSpawn;
    private int spawnDelay;
    private String slicerType;

    /**
     * SpawnEvent constructor
     */
    public SpawnEvent() {
        super();
    }

    /**
     * Getter for the number of slicers to spawn
     * @return a number
     */
    public int getNumToSpawn() {
        return numToSpawn;
    }

    /**
     * Setter for the num of slicers to spawn
     * @param numToSpawn number of slicers to spawn
     */
    public void setNumToSpawn(int numToSpawn) {
        this.numToSpawn = numToSpawn;
    }

    /**
     * Getter
     * @return the spawn delay
     */
    public int getSpawnDelay() {
        return spawnDelay;
    }

    /**
     * Setter
     * @param spawnDelay the spawn delay
     */
    public void setSpawnDelay(int spawnDelay) {
        this.spawnDelay = spawnDelay;
    }

    /**
     * Getter
     * @return the slicer type
     */
    public String getSlicerType() {
        return slicerType;
    }

    /**
     * Setter
     * @param slicerType string specifying the type of slicer
     */
    public void setSlicerType(String slicerType) {
        this.slicerType = slicerType;
    }
}
