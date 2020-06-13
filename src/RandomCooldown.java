public class RandomCooldown implements CooldownBehaviour {
    private static final int MIN_COOLDOWN = 1000;
    private Clock clock;
    private int range;

    /**
     * RandomCooldown constructor - creates a new clock and assigns the relevant range
     * @param range the cooldown time
     */
    public RandomCooldown(int range) {
        this.clock = new Clock();
        this.range = range;
    }

    /**
     * Logic for creating a random cooldown to govern some event
     * @param range some numerical parameter for determining the cooldown
     */
    @Override
    public void generateCooldown(int range) {
        double delay = MIN_COOLDOWN + (range/2.0) * Math.random();
        clock.setDelay(delay);
        clock.beginCountdown();
    }

    /**
     * Determines whether the cooldown has finished or not
     * @return true or false
     */
    @Override
    public boolean offCooldown() {
        if (this.clock.timerFinished()) {
            generateCooldown(range);
            clock.beginCountdown();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Updates the amount of time left for the cooldown
     */
    @Override
    public void updateCooldown() {
        this.clock.updateTimer();
    }
}
