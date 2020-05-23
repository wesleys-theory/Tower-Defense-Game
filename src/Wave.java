import bagel.Input;
import bagel.Keys;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Wave implements Subject {
    private boolean started;
    private boolean finished;
    private boolean slicersOnScreen;
    private Queue<WaveEvent> waveEvents;
    private WaveEvent currentEvent;
    private ArrayList<Slicer> slicers;
    private Queue<Slicer> slicersToSpawn;
    private SlicerCreator slicerFactory;
    private ArrayList<Observer> observers;
    private int waveNum;

    public ArrayList<Slicer> getSlicers() {
        return slicers;
    }

    public boolean isFinished() {
        return this.finished;
    }
    public boolean hasStarted() {
        return this.started;
    }

    public Wave(int waveNum) {
        this.started = false;
        this.finished = false;
        this.slicerFactory = new SlicerCreator();
        this.waveEvents = new LinkedList<>();
        this.slicersToSpawn = new LinkedList<>();
        this.slicers = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.waveNum = waveNum;
    }


    public void update(Input input) {
        if (input.wasPressed(Keys.S) && !this.started) {
            this.started = true;
            startNextEvent();
        }
        else if (this.started) {
            slicersOnScreen = updateEvent();
            currentEvent.getClock().updateTimer();
        }
        notifyObservers();
    }

    public void addEvent(WaveEvent event) {
        this.waveEvents.add(event);
    }

    public void startNextEvent() {
        if (waveEvents.isEmpty() && !slicersOnScreen) {
            this.finished = true;
            return;
        }
        else if (waveEvents.isEmpty()) {
            return;
        }
        else {
            this.currentEvent = waveEvents.remove();
        }
        if (this.currentEvent instanceof SpawnEvent) {
            int numToSpawn = ((SpawnEvent) this.currentEvent).getNumToSpawn();
            int delay = ((SpawnEvent) this.currentEvent).getSpawnDelay();
            String slicerType = ((SpawnEvent) this.currentEvent).getSlicerType();

            for (int i = 0; i < numToSpawn; i++) {
                slicersToSpawn.add(slicerFactory.createSlicer(slicerType));
            }
            currentEvent.getClock().setDelay(delay);
            currentEvent.getClock().beginCountdown();
        }
        else if (this.currentEvent instanceof DelayEvent) {
            int delay = ((DelayEvent) this.currentEvent).getDelay();
            currentEvent.getClock().setDelay(delay);
        }
    }

    public boolean updateEvent() {
        if (currentEvent instanceof SpawnEvent) {
            if (currentEvent.getClock().timerFinished()) {
                if (slicersToSpawn.isEmpty()) {
                    startNextEvent();
                }
                else {
                    slicers.add(slicersToSpawn.remove());
                    currentEvent.getClock().beginCountdown();
                }
            }
        }
        else if (currentEvent instanceof DelayEvent && currentEvent.getClock().timerFinished()) {
            startNextEvent();
        }
        slicersOnScreen = false;
        ArrayList<Slicer> toBeDespawned = new ArrayList<>();
        for (Slicer slicer : slicers) {
            if (!slicer.atEnd()) {
                slicersOnScreen = true;
            }
            if (slicer.getHealth() <= 0) {
                toBeDespawned.add(slicer);
            }
            slicer.move();
            slicer.notifyObservers();
            slicer.render();
        }

        for (Slicer slicer : toBeDespawned) {
            slicers.remove(slicer);
        }
        return slicersOnScreen;
    }

    public int getWaveIndex() {
        return waveNum;
    }

    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
