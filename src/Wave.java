import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

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
    private ArrayList<Slicer> despawnedSlicers;
    private int despawnIndex;
    private ArrayList<Slicer> slicersAtEnd;

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
        this.despawnedSlicers = new ArrayList<>();
        this.despawnIndex = 0;
        this.waveNum = waveNum;
        this.slicersAtEnd = new ArrayList<>();
    }

    /**
     * Getter for the list of slicers at the end of the map
     * @return the list of slicers at the end of their polyline
     */
    public ArrayList<Slicer> getSlicersAtEnd() {
        return slicersAtEnd;
    }

    /**
     * removes a given slicer from the list of slicersAtEnd
     * @param slicer slicer to be removed
     */
    public void removeSlicerAtEnd(Slicer slicer) {
        this.slicersAtEnd.remove(slicer);
    }

    /**
     * Getter for list of despawned slicers
     * @return the list of despawned slicers
     */
    public ArrayList<Slicer> getDespawnedSlicers() {
        return despawnedSlicers;
    }

    /**
     * Getter for despawn index
     * @return the index of the despawned slicer list
     */
    public int getDespawnIndex() {
        return despawnIndex;
    }

    /**
     * Increases the index for the list of despawned slicers
     */
    public void increaseDespawnIndex() {
        this.despawnIndex++;
    }

    /**
     * Updates the state of the wave, starts it if it hasn't been already started
     * @param input user input
     */
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

    /**
     * Adds an event to the Wave's agenda
     * @param event event
     */
    public void addEvent(WaveEvent event) {
        this.waveEvents.add(event);
    }

    /**
     * Starts the next event in the event queue if the previous event had finished
     */
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
                slicersToSpawn.add(slicerFactory.createSlicer(SlicerType.valueOf(slicerType.toUpperCase())));
            }
            currentEvent.getClock().setDelay(delay);
            currentEvent.getClock().beginCountdown();
        }
        else if (this.currentEvent instanceof DelayEvent) {
            int delay = ((DelayEvent) this.currentEvent).getDelay();
            currentEvent.getClock().setDelay(delay);
        }
    }

    /**
     * Updates all the slicers on the screen
     * @return whether there are still any slicers on screen or not
     */
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
            else if (slicer.atEnd() && slicer.getHealth() > 0) {
                slicersAtEnd.add(slicer);
                toBeDespawned.add(slicer);
            }
            if (slicer.getHealth() <= 0) {
                toBeDespawned.add(slicer);
            }
            slicer.move();
            slicer.notifyObservers();
            slicer.render();
        }

        for (Slicer slicer : toBeDespawned) {
            despawn(slicer);
        }
        return slicersOnScreen;
    }

    /**
     * Despawns a given slicer in the list of active slicers
     * @param slicer slicer to be despawned
     */
    public void despawn(Slicer slicer) {
        if (slicer.getChildType() != null) {
            // Spawn the child slicers
            for (int i = 0; i < slicer.getChildAmount(); i++) {
                Slicer childSlicer = slicerFactory.createSlicer(slicer.getChildType());
                childSlicer.setPolyLine(slicer.getPolyLine());
                childSlicer.setLocation(slicer.getLocation());
                childSlicer.setNextPoint(slicer.getNextPoint());
                childSlicer.setPointIndex(slicer.getPointIndex());

                // Assign any projectiles targeting dead slicer to first child
                if (i == 0) {
                    ArrayList<Observer> toBeUnregistered = new ArrayList<>();
                    for (Observer observer : slicer.getObservers()) {
                        toBeUnregistered.add(observer);
                        childSlicer.registerObserver(observer);
                        childSlicer.notifyObservers();
                    }
                    for (Observer observer : toBeUnregistered) {
                        slicer.unregisterObserver(observer);
                    }
                }
                slicers.add(childSlicer);
            }
        }
        // No children, lonely projectile is marked for deletion
        else {
            for (Observer observer : slicer.getObservers()) {
                if (observer instanceof Projectile) {
                    ((Projectile) observer).makeLonely();
                }
            }
        }
        despawnedSlicers.add(slicer);
        slicers.remove(slicer);
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
