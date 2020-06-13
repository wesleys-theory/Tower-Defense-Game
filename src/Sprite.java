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


    /**
     * Move function, overriden in child classes/left blank in sprites that are meant to be stationary
     */
    public void move() {}

    /**
     * Determines if the proposed location of a sprite is blocked or not
     * @return true or false
     */
    public boolean blockedLocation() {
        if (this.getLocation().x >= ShadowDefend.WIDTH | this.getLocation().y >= ShadowDefend.HEIGHT) {
            return true;
        }
        return ShadowDefend.currentMap.getPropertyBoolean((int) this.getLocation().x,
                (int) this.getLocation().y,"blocked", false);
    }

    /**
     *
     * @return Point
     */
    public Point getLocation() {
        return location;
    }

    /**
     *
     * @param location location of sprite
     */
    public void setLocation(Point location) {
        this.location = location;
    }

    /**
     *
     * @return direction of movement
     */
    public Vector2 getDirection() {
        return direction;
    }

    /**
     *
     * @param direction direction of movement
     */
    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    /**
     *
     * @return speed of movement
     */
    public double getSpeed() {
        return speed;
    }

    /**
     *
     * @param speed speed of movement
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     *
     * @return sprite's image
     */
    public Image getImage() {
        return image;
    }

    /**
     *
     * @param image sprite's image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     *
     * @return a rectangle
     */
    public Rectangle getHitBox() {
        return this.image.getBoundingBoxAt(this.location);
    }

    /**
     *
     * @param hitBox the sprite's hitbox
     */
    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    /**
     *
     * @return the angle the sprite is facing
     */
    public double getAngle() {
        return angle;
    }

    /**
     *
     * @param angle the angle the sprite is facing
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Renders the sprite to the screen
     */
    public void render() {
        this.image.draw(this.location.x, this.location.y, new DrawOptions().setRotation(getAngle()));
    }
}
