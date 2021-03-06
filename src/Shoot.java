import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

public class Shoot extends Ability {
    private ArrayList<Projectile> projectiles;
    private ProjectileCreator projectileCreator;

    /**
     * "Shoot" ability constructor
     * @param delay the delay for cooldown
     */
    public Shoot(int delay) {
        this.projectileCreator = new ProjectileCreator();
        this.projectiles = new ArrayList<>();
        this.setCooldownBehaviour(new RegularCooldown());
        this.setCooldown(delay);
        // Makes the first shot instantaneous
        this.getCooldownBehaviour().generateCooldown(0);
    }

    /**
     * If the ability is off cooldown and a slicer is in range, then a "shoot" action is performed
     * @param slicers the list of slicers in the game
     * @param user the tower using the ability
     */
    @Override
    public void performAbility(ArrayList<Slicer> slicers, Tower user) {
        Tank client = (Tank) user;
        Slicer target = chooseTarget(slicers, client);
        if (target != null) {
            Vector2 directionVector = target.getLocation().asVector().sub(user.getLocation().asVector());
            double angle = Math.atan2(directionVector.x, -1 * directionVector.y);
            user.setAngle(angle);
        }

        if (getCooldownBehaviour().offCooldown() && target != null) {
            Projectile newProjectile = projectileCreator.makeProjectile(client.getType());
            newProjectile.setLocation(client.getLocation());
            projectiles.add(newProjectile);
            target.registerObserver(newProjectile);
            target.notifyObservers();
            getCooldownBehaviour().generateCooldown(getCooldown());
        }

        ArrayList<Projectile> toBeRemoved = new ArrayList<>();
        for (Projectile projectile : projectiles) {
            // Delete the lonely projectiles
            if (projectile.isLonely()) {
                toBeRemoved.add(projectile);
            }

            projectile.render();
            projectile.move();

            if (projectile.getLocation().distanceTo(projectile.getTargetLocation()) < projectile.getSpeed()) {
               toBeRemoved.add(projectile);
               projectile.getSlicer().reduceHealth(projectile.getDamage());
            }
        }
        for (Projectile projectile : toBeRemoved) {
            projectiles.remove(projectile);
        }
    }

    /**
     * Selects the best slicer to target for the given tank
     * @param slicers the slicers on the screen
     * @param user the tank that is shooting
     * @return the target slicer
     */
    public Slicer chooseTarget(ArrayList<Slicer> slicers, Tank user) {
        // Target selected will be the slicer that is furthest along the path, provided it is within the tank radius
        double maxDistance = user.getEffectRadius();
        int maxIndex = 0;
        Slicer target = null;
        for (Slicer slicer : slicers) {
            if (slicer.getLocation().distanceTo(user.getLocation()) < maxDistance &&
                    slicer.getPointIndex() > maxIndex) {
                target = slicer;
                maxIndex = slicer.getPointIndex();
            }
        }
        return target;
    }

}
