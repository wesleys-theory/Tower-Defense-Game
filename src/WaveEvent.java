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

    @Override
    public int compareTo(WaveEvent waveEvent) {
        return Integer.compare(this.waveNumber, waveEvent.waveNumber);
    }
}
