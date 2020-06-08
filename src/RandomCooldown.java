public class RandomCooldown implements CooldownBehaviour {
    private static final int MIN_COOLDOWN = 1000;
    private Clock clock;
    private int range;

    public RandomCooldown(int range) {
        this.clock = new Clock();
        this.range = range;
    }

    @Override
    public void generateCooldown(int range) {
        double delay = MIN_COOLDOWN + (range/2.0) * Math.random();
        clock.setDelay(delay);
        clock.beginCountdown();
    }

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

    @Override
    public void updateCooldown() {
        this.clock.updateTimer();
    }
}
