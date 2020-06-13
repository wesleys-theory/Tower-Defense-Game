public class RegularCooldown implements CooldownBehaviour {
    private Clock clock;

    /**
     * Regular cooldown constructor
     */
    public RegularCooldown() {
        this.clock = new Clock();
    }

    /**
     * Generates a regular old cooldown
     * @param range some numerical parameter for determining the cooldown
     */
    @Override
    public void generateCooldown(int range) {
        clock.setDelay(range);
        clock.beginCountdown();
    }

    /**
     * Determines whether the cooldown has finished or not
     * @return true or false
     */
    @Override
    public boolean offCooldown() {
       if (this.clock.timerFinished()) {
           clock.beginCountdown();
           return true;
       }
       else {
           return false;
       }
    }

    /**
     * Updates the time left on the cooldown
     */
    @Override
    public void updateCooldown() {
        this.clock.updateTimer();
    }
}
