import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

public class Airplane extends Tower {
    private boolean horizontal;

    /**
     * Airplane constructor
     * @param point the location of the tower
     */
    public Airplane(Point point) {
        super(point);
    }

    /**
     * Setter for flight direction
     * @param horizontal Boolean value, true = horizontal, false = vertical
     */
    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    /**
     * Sets the start location and velocity of the plane according to whether it should fly horizontally or vertically
     */
    public void placeAtStart() {
        if (horizontal) {
            setLocation(new Point(0, getLocation().y));
            setDirection(new Vector2(1, 0));
        }
        else {
            setLocation(new Point(getLocation().x, 0));
            setDirection(new Vector2(0, 1));
        }
    }

    /**
     * Moves the plane in a straight line
     */
    @Override
    public void move() {
        setLocation(new Point(getLocation().x + getDirection().x * getSpeed() * Clock.timeMultiplier,
                getLocation().y + getDirection().y * getSpeed() * Clock.timeMultiplier));
        setAngle(Math.atan2(this.getDirection().x, -1*this.getDirection().y));
    }

    /**
     * Determines whether a plane's flight has finished or not
     * @return boolean value
     */
    public boolean atEnd() {
        return (this.getLocation().x > ShadowDefend.WIDTH || this.getLocation().y > ShadowDefend.HEIGHT);
    }

    /**
     * Determines whether all the bombs associated with a plane have detonated or not
     * @return boolean value
     */
    public boolean bombsDetonated() {
        return ((DropExplosive) getAbility()).explosiveListEmpty();
    }
}
