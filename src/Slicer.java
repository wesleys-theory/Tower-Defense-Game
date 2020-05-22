import bagel.DrawOptions;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;

public class Slicer extends Sprite {
    public static final double baseSpeed = ((double)(144)) / ((double)(Clock.refreshRate));

    private int pointIndex;
    private List<Point> polyLine;
    private int health;
    private int reward;
    private int penalty;
    private Point nextPoint;
    private boolean isAlive;

    public int getPointIndex() {
        return pointIndex;
    }

    public void setPointIndex(int pointIndex) {
        this.pointIndex = pointIndex;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public List<Point> getPolyLine() {
        return polyLine;
    }

    public void setPolyLine(List<Point> polyLine) {
        this.polyLine = polyLine;
    }

    public Point getNextPoint() {
        return nextPoint;
    }

    public void setNextPoint(Point nextPoint) {
        this.nextPoint = nextPoint;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void move() {
        // Moves the enemy in the direction of the current velocity a distance of speed pixels. timeMultiplier
        // specifies the number of repetitions to perform.

        for (int i=0; i<Clock.timeMultiplier; i++) {

            Vector2 directionVector = this.nextPoint.asVector().sub(this.getLocation().asVector());
            // If the distance between the current location and the next location is less than the distance moved
            // in one frame (baseSpeed), the current location and next location are basically the same location. Has an
            // inaccuracy of +- baseSpeed pixels.
            if (directionVector.length() < this.getSpeed() && this.pointIndex + 1 < this.polyLine.size()) {
                this.setLocation(this.nextPoint);
                this.pointIndex++;
                this.nextPoint = this.polyLine.get(this.pointIndex);
                directionVector = this.nextPoint.asVector().sub(this.getLocation().asVector());
            }

            this.setDirection(directionVector.normalised());
            this.setAngle(Math.atan2(this.getDirection().y, this.getDirection().x));
            this.setLocation(new Point(this.getLocation().x + this.getSpeed()*this.getDirection().x,
                                        this.getLocation().y + this.getSpeed()*this.getDirection().y));
        }
    }

    public void render() {
        this.getImage().draw(this.getLocation().x, this.getLocation().y, new DrawOptions().setRotation(getAngle()));
    }

    public boolean atEnd() {
        // Checks if the enemy is at the last polyline

        int endIndex = this.polyLine.size() - 1;
        return (this.getLocation().asVector().sub(this.polyLine.get(endIndex).asVector()).length() < baseSpeed);
    }

}
