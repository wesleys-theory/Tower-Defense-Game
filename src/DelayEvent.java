public class DelayEvent extends WaveEvent {
    private int delay;

    /**
     * DelayEvent constructor
     */
    public DelayEvent() {
        super();
    }

    /**
     * Getter for the delay
     * @return the delay in ms
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Setter for the delay
     * @param delay delay in ms
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }
}
