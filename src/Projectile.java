import bagel.util.Point;
import bagel.util.Vector2;

public class Projectile extends Sprite implements Observer {
    private static final double PROJECTILE_SPEED = 10;
    private int damage;
    private Point targetLocation;
    private Slicer slicer;
    private boolean lonely = false;

    /**
     * Marks the projectile as having no target, needs to be deleted
     */
    public void makeLonely() {
        lonely = true;
    }

    /**
     * Returns relationship status of a projectile
     * @return boolean value
     */
    public boolean isLonely() {
        return lonely;
    }

    /**
     * getter for the projectile's target
     * @return a slicer
     */
    public Slicer getSlicer() {
        return slicer;
    }

    /**
     * Getter for the projectile's target location
     * @return a point
     */
    public Point getTargetLocation() {
        return targetLocation;
    }

    /**
     * Projectile constructor
     */
    public Projectile() {
        setSpeed(PROJECTILE_SPEED);
        setAngle(0);
    }

    /**
     * Getter for the damage of a projectile
     * @return the damage (int)
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Setter for the damage
     * @param damage the amount of damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Changes the location of the projectile according to the timeMultiplier and location of it's target
     */
    @Override
    public void move() {
        Vector2 directionVector;
        directionVector = this.targetLocation.asVector().sub(this.getLocation().asVector()).normalised();
        this.setLocation(new Point(this.getLocation().x + getSpeed()*Clock.timeMultiplier*directionVector.x,
                            this.getLocation().y + getSpeed()*Clock.timeMultiplier*directionVector.y));
    }

    /**
     * Informs the projectile of the location of it's target
     * @param subject a reference to the subject
     */
    @Override
    public void update(Subject subject) {
        if (subject instanceof Slicer) {
            Slicer slicer = (Slicer) subject;
            this.targetLocation = slicer.getLocation();
            this.slicer = slicer;
        }
    }
}
