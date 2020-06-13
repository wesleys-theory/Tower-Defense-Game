import bagel.Input;
import bagel.map.TiledMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Level {
    private TiledMap map;
    private boolean levelComplete;
    private ArrayList<Wave> waves;
    private int waveIndex;
    private WaveEventCreator WaveFactory;

    public boolean levelFinished() {
        return levelComplete;
    }

    /**
     * Level constructor
     * @param mapPath the path for the map.tmx file
     * @param wavePath the path for the wave.txt file
     */
    public Level(String mapPath, String wavePath) {
        map = new TiledMap(mapPath);
        this.levelComplete = false;
        this.WaveFactory = new WaveEventCreator();
        this.waves = new ArrayList<>();
        this.waveIndex = 0;

        try {
            File waveFile = new File(wavePath);
            Scanner scanner = new Scanner(waveFile);
            ArrayList<WaveEvent> events = new ArrayList<>();

            // Read events into an array list and sort them
            while (scanner.hasNextLine()) {
                events.add(WaveFactory.parseEvent(scanner.nextLine()));
            }
            Collections.sort(events);

            // Now add the events to their respective Waves
            int currentWaveIndex = 0;
            waves.add(new Wave(currentWaveIndex));
            for (WaveEvent event: events) {
                // First wave starts at index 1, need + 1 to index
                if (event.getWaveNumber() != currentWaveIndex + 1) {
                    currentWaveIndex++;
                    waves.add(new Wave(currentWaveIndex));
                }
                waves.get(currentWaveIndex).addEvent(event);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the map to the window
     * @param width width of the window
     * @param height height of the window
     */
    public void drawMap(int width, int height) {
        map.draw(0,0,0,0, width, height);
    }

    /**
     * Map object getter so the slicers know what's up
     * @return a map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Updates level tings like wave index and whether the level has finished or not
     * @param input user input
     */
    public void update(Input input) {
        if (waves.get(waveIndex).isFinished() && waveIndex + 1 == waves.size() - 1) {
            levelComplete = true;
            return;
        }
        else if (waves.get(waveIndex).isFinished()) {
            // Ensures that each slicer has been despawned properly before moving on to the next wave
            while (!waves.get(waveIndex).getSlicers().isEmpty()) {
                waves.get(waveIndex).update(input);
            }

            waveIndex++;
        }
        waves.get(waveIndex).update(input);
    }

    /**
     * Getter for the current wave object
     * @return current wave object
     */
    public Wave getWave() {
        return this.waves.get(waveIndex);
    }
}
