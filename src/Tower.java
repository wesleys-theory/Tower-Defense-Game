import bagel.util.Point;

public abstract class Tower extends Sprite {
    private int cost;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
