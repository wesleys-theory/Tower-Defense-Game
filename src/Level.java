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

    public void drawMap(int width, int height) {
        map.draw(0,0,0,0, width, height);
    }

    public TiledMap getMap() {
        return map;
    }

    public void update(Input input) {
        if (waves.get(waveIndex).isFinished() && waveIndex + 1 == waves.size() - 1) {
            levelComplete = true;
            return;
        }
        else if (waves.get(waveIndex).isFinished()) {
            waveIndex++;
        }
        waves.get(waveIndex).update(input);
    }

    public Wave getWave() {
        return this.waves.get(waveIndex);
    }
}
