import bagel.Image;
import bagel.map.TiledMap;

public class Slicer extends Enemy {

    public Slicer(int timeMultiplier) {
        this.setImage(new Image("res/images/slicer.png"));
        this.setPolyLine((new TiledMap("res/levels/1.tmx")).getAllPolylines().get(0));
        this.setLocation(this.getPolyLine().get(0));
        this.setNextLocation(this.getPolyLine().get(0));
        this.setPointIndex(0);
        this.setVelocity(this.getNextLocation().asVector().sub(
                this.getLocation().asVector().normalised().mul(timeMultiplier)));
    }
}
