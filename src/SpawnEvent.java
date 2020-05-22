import java.util.ArrayList;

public class SpawnEvent extends WaveEvent{
    private int numToSpawn;
    private int spawnDelay;
    private String slicerType;

    public SpawnEvent() {
        super();
    }

    public int getNumToSpawn() {
        return numToSpawn;
    }

    public void setNumToSpawn(int numToSpawn) {
        this.numToSpawn = numToSpawn;
    }

    public int getSpawnDelay() {
        return spawnDelay;
    }

    public void setSpawnDelay(int spawnDelay) {
        this.spawnDelay = spawnDelay;
    }

    public String getSlicerType() {
        return slicerType;
    }

    public void setSlicerType(String slicerType) {
        this.slicerType = slicerType;
    }
}
