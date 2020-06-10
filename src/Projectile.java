import bagel.util.Point;
import bagel.util.Vector2;

public class Projectile extends Sprite implements Observer {
    private static final double PROJECTILE_SPEED = 10;
    private int damage;
    private Point targetLocation;
    private Slicer slicer;
    private boolean lonely = false;

    public void makeLonely() {
        lonely = true;
    }

    public boolean isLonely() {
        return lonely;
    }

    public Slicer getSlicer() {
        return slicer;
    }

    public Point getTargetLocation() {
        return targetLocation;
    }

    public Projectile() {
        setSpeed(PROJECTILE_SPEED);
        setAngle(0);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void move() {
        Vector2 directionVector;
        directionVector = this.targetLocation.asVector().sub(this.getLocation().asVector()).normalised();
        this.setLocation(new Point(this.getLocation().x + getSpeed()*Clock.timeMultiplier*directionVector.x,
                            this.getLocation().y + getSpeed()*Clock.timeMultiplier*directionVector.y));
    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof Slicer) {
            Slicer slicer = (Slicer) subject;
            this.targetLocation = slicer.getLocation();
            this.slicer = slicer;
        }
    }
}
