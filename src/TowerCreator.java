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
    private static final int AIRPLANE_SPEED = 3;
    private static final int AIRPLANE_COOLDOWN = 2000;

    private boolean prevWasHorizontal = false;

    /**
     * Creator of towers depending on the towertype input
     * @param towerType a string - "tank", "supertank", "airplane"
     * @return a reference to the fresh tower
     */
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
            newTower.setCost(AIRPLANE_COST);
            newTower.setImage(new Image("res/images/airsupport.png"));
            newTower.setHitBox(newTower.getImage().getBoundingBox());
            newTower.setSpeed(AIRPLANE_SPEED);
            newTower.setAbility(new DropExplosive(AIRPLANE_COOLDOWN));
            if (prevWasHorizontal) {
                ((Airplane) newTower).setHorizontal(false);
                prevWasHorizontal = false;
            }
            else {
                ((Airplane) newTower).setHorizontal(true);
                prevWasHorizontal = true;
            }
        }
        else {
            newTower = null;
        }
        return newTower;
    }
}
