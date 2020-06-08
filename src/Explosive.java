import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

public class Explosive extends Sprite {
    private static final double EXPLOSIVE_SPEED = 0;
    private static final int DAMAGE = 500;
    private static final int EFFECT_RADIUS = 200;
    private static final int DETONATION_TIME = 2000;

    private CooldownBehaviour cooldownBehaviour;

    public Explosive(Point location) {
        this.cooldownBehaviour = new RegularCooldown();
        this.cooldownBehaviour.generateCooldown(DETONATION_TIME);
        this.setImage(new Image("res/images/explosive.png"));
        this.setLocation(location);
    }

    public void updateCooldown() {
        this.cooldownBehaviour.updateCooldown();
    }

    public boolean timerDone() {
        return cooldownBehaviour.offCooldown();
    }

    public void explode(ArrayList<Slicer> slicers) {
        for (Slicer slicer : slicers) {
            if (getLocation().distanceTo(slicer.getLocation()) <= EFFECT_RADIUS) {
                slicer.reduceHealth(DAMAGE);
            }
        }
    }
}
