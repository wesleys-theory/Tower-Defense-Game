import bagel.util.Point;

public class Tank extends Tower {
    int effectRadius;

    public Tank(int radius, Point point) {
        super(point);
        this.effectRadius = radius;
    }
}
