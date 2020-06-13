public abstract class WaveEvent implements Comparable<WaveEvent>{

    private int waveNumber;
    private boolean finished;
    private Clock clock;

    public WaveEvent() {
        this.clock = new Clock();
        this.finished = false;
    }

    public int getWaveNumber() {
        return waveNumber;
    }

    public void setWaveNumber(int waveNumber) {
        this.waveNumber = waveNumber;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    /**
     * used to sort the wave events, probably not needed since they are given in the correct order anyway
     * @param waveEvent the wave event to be compared to
     * @return greater than, equal, or less than
     */
    @Override
    public int compareTo(WaveEvent waveEvent) {
        return Integer.compare(this.waveNumber, waveEvent.waveNumber);
    }
}
