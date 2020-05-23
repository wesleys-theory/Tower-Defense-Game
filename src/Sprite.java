import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

public abstract class Sprite {
    private Point location;
    private Vector2 direction;
    private double speed;
    private Image image;
    private Rectangle hitBox;
    private double angle;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Rectangle getHitBox() {
        return this.image.getBoundingBoxAt(this.location);
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void render() {
        this.image.draw(this.location.x, this.location.y, new DrawOptions().setRotation(getAngle()));
    }

    public boolean blockedLocation() {
        if (this.getLocation().x >= ShadowDefend.WIDTH | this.getLocation().y >= ShadowDefend.HEIGHT) {
            return true;
        }
        return ShadowDefend.currentMap.getPropertyBoolean((int) this.getLocation().x,
                (int) this.getLocation().y,"blocked", false);
    }

}
