import bagel.util.Point;

public abstract class Tower extends Sprite {
    private int cost;

    public Tower(Point point) {
        this.setLocation(point);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
