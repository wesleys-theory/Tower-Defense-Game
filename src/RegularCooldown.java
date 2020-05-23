public class RegularCooldown implements CooldownBehaviour {
    private Clock clock;

    public RegularCooldown() {
        this.clock = new Clock();
    }

    @Override
    public void generateCooldown(int range) {
        clock.setDelay(range);
        clock.beginCountdown();
    }

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

    @Override
    public void updateCooldown() {
        this.clock.updateTimer();
    }
}
