import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

public class Airplane extends Tower {
    private boolean horizontal;

    public Airplane(Point point) {
        super(point);
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

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

    @Override
    public void move() {
        setLocation(new Point(getLocation().x + getDirection().x * getSpeed() * Clock.timeMultiplier,
                getLocation().y + getDirection().y * getSpeed() * Clock.timeMultiplier));
        setAngle(Math.atan2(this.getDirection().x, -1*this.getDirection().y));
    }

    public boolean atEnd() {
        return (this.getLocation().x > ShadowDefend.WIDTH || this.getLocation().y > ShadowDefend.HEIGHT);
    }
    public boolean bombsDetonated() {
        return ((DropExplosive) getAbility()).explosiveListEmpty();
    }
}
