import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

public class Explosive extends Sprite {
    private static final double EXPLOSIVE_SPEED = 0;
    private static final int DAMAGE = 500;
    private static final int EFFECT_RADIUS = 200;
    private static final int DETONATION_TIME = 2000;

    private CooldownBehaviour cooldownBehaviour;

    /**
     * Explosive constructor
     * @param location the point where it lives
     */
    public Explosive(Point location) {
        this.cooldownBehaviour = new RegularCooldown();
        this.cooldownBehaviour.generateCooldown(DETONATION_TIME);
        this.setImage(new Image("res/images/explosive.png"));
        this.setLocation(location);
    }

    /**
     * updates the "fuse" on the explosive
     */
    public void updateCooldown() {
        this.cooldownBehaviour.updateCooldown();
    }

    /**
     * Determines if the explosive timer has finished or not
     * @return a true or false
     */
    public boolean timerDone() {
        return cooldownBehaviour.offCooldown();
    }

    /**
     * "Explosion" logic; damages nearby slicers
     * @param slicers slicers on the screen
     */
    public void explode(ArrayList<Slicer> slicers) {
        for (Slicer slicer : slicers) {
            if (getLocation().distanceTo(slicer.getLocation()) <= EFFECT_RADIUS) {
                slicer.reduceHealth(DAMAGE);
            }
        }
    }
}
