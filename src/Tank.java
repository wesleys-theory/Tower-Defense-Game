import bagel.util.Point;

public class Tank extends Tower {
    private int effectRadius;

    public int getEffectRadius() {
        return effectRadius;
    }

    public Tank(int radius, Point point) {
        super(point);
        this.effectRadius = radius;
    }
}
