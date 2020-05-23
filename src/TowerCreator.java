import bagel.Image;
import bagel.util.Point;

public class TowerCreator {
    private static final int TANK_RADIUS = 100;
    private static final int SUPERTANK_RADIUS = 150;
    private static final int TANK_COST = 250;
    private static final int SUPERTANK_COST = 600;
    private static final int AIRPLANE_COST = 500;
    private static final int TANK_COOLDOWN = 1000;
    private static final int SUPERTANK_COOLDOWN = 500;

    public Tower makeTower(String towerType) {
        Tower newTower;
        if (towerType.equals("tank")) {
            newTower = new Tank(TANK_RADIUS, new Point(0,0));
            newTower.setType("tank");
            newTower.setCost(TANK_COST);
            newTower.setImage(new Image("res/images/tank.png"));
            newTower.setHitBox(newTower.getImage().getBoundingBox());
            newTower.setSpeed(0);
            newTower.setAbility(new Shoot(TANK_COOLDOWN));
        }
        else if (towerType.equals("supertank")) {
            newTower = new Tank(SUPERTANK_RADIUS, new Point(0, 0));
            newTower.setType("supertank");
            newTower.setCost(SUPERTANK_COST);
            newTower.setImage(new Image("res/images/supertank.png"));
            newTower.setHitBox(newTower.getImage().getBoundingBox());
            newTower.setSpeed(0);
            newTower.setAbility(new Shoot(SUPERTANK_COOLDOWN));
        }
        else if (towerType.equals("airplane")) {
            newTower = new Airplane(new Point(0, 0));
            newTower.setType("airplane");
            newTower.setHitBox(newTower.getImage().getBoundingBox());
            newTower.setCost(AIRPLANE_COST);
        }
        else {
            newTower = null;
        }
        return newTower;
    }
}
