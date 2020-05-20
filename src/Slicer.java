import bagel.DrawOptions;
import bagel.Image;
import bagel.map.TiledMap;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;

public abstract class Enemy implements Moveable {
    /*
        The baseSpeed is the number of pixels moved per frame. I implemented the game on a 144hz refresh rate monitor,
        so to get the same timings & speeds, change the Clock.refreshRate to your monitor's refresh rate. Note that the
        specification says to move enemies at a rate of 1 pixel per frame, so you can just set baseSpeed = 1 to meet
        that criteria, but this will mean the enemies may move faster/slower than on my machine. If we're assuming
        an update rate of 60 fps and a move speed of 1 pixel per frame, set Clock.refreshRate to 60 and set baseSpeed
        to 1. It should be possible to get the timings right by just adjusting baseSpeed, refreshRate, and
        secondsBetweenSpawns manually until it looks correct.
    */
    private static final double baseSpeed = ((double)(144)) / ((double)(Clock.refreshRate));

    private Vector2 velocity;
    private Point location;
    private Point nextLocation;
    private int pointIndex;
    private Image image;
    private List<Point> polyLine;

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Point getNextLocation() {
        return nextLocation;
    }

    public void setNextLocation(Point nextLocation) {
        this.nextLocation = nextLocation;
    }


    public void setPointIndex(int pointIndex) {
        this.pointIndex = pointIndex;
    }


    public void setImage(Image image) {
        this.image = image;
    }

    public List<Point> getPolyLine() {
        return polyLine;
    }

    public void setPolyLine(List<Point> polyLine) {
        this.polyLine = polyLine;
    }

    public Enemy(int timeMultiplier, String image, String map) {
        this.setImage(new Image(image));
        this.setPolyLine((new TiledMap(map)).getAllPolylines().get(0));
        this.setLocation(this.getPolyLine().get(0));
        this.setNextLocation(this.getPolyLine().get(0));
        this.setPointIndex(0);
        this.setVelocity(this.getNextLocation().asVector().sub(
                this.getLocation().asVector().normalised().mul(timeMultiplier)));
    }

    public void move(int timeMultiplier) {
        // Moves the enemy in the direction of the current velocity a distance of baseSpeed pixels. timeMultiplier
        // specifies the number of repetitions to perform.

        for (int i=0; i<timeMultiplier; i++) {

            Vector2 directionVector = this.nextLocation.asVector().sub(this.location.asVector());
            // If the distance between the current location and the next location is less than the distance moved
            // in one frame (baseSpeed), the current location and next location are basically the same location. Has an
            // inaccuracy of +- baseSpeed pixels.
            if (directionVector.length() < baseSpeed && this.pointIndex + 1 < this.polyLine.size()) {
                this.location = this.nextLocation;
                this.pointIndex++;
                this.nextLocation = this.polyLine.get(this.pointIndex);
                directionVector = this.nextLocation.asVector().sub(this.location.asVector());
            }

            // Changes the length of the direction vector to length baseSpeed
            this.velocity = directionVector.normalised().mul(baseSpeed);
            this.location = new Point(this.location.x + this.velocity.x, this.location.y + this.velocity.y);
        }
    }

    public void render() {
        // Draws the enemy to the screen, using the current velocity to rotate it accordingly

        double angle = Math.atan(this.velocity.y/this.velocity.x);
        // The ArcTan function only returns angles facing to the right, so if a slicer is moving left,
        // we must add pi to the angle to flip it to the opposite quadrant
        if (this.velocity.x < 0) {
            angle += Math.PI;
        }
        DrawOptions rotation = new DrawOptions().setRotation(angle);
        this.image.draw(this.location.x, this.location.y, rotation);
    }

    public boolean atEnd() {
        // Checks if the enemy is at the last polyline

        int endIndex = this.polyLine.size() - 1;
        return (this.location.asVector().sub(this.polyLine.get(endIndex).asVector()).length() < baseSpeed);
    }
}
