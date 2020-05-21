import bagel.Image;
import bagel.util.Point;

public class Airplane extends Tower {

    public Airplane(Point point) {
        super(point);
        this.setImage(new Image("res/images/airsupport.png"));
    }
}
