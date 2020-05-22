import bagel.Image;
import bagel.util.Point;

import java.util.List;

public class SlicerCreator {
    private static final double SLICER_SPEED = Slicer.baseSpeed;
    private static final int SLICER_HEALTH = 1;
    private static final int SLICER_REWARD = 2;
    private static final int SLICER_PENALTY = 1;

    private static final double SUPERSLICER_SPEED = 3.0/4 * SLICER_SPEED;
    private static final int SUPERSLICER_HEALTH = SLICER_HEALTH;
    private static final int SUPERSLICER_REWARD = 15;
    private static final int SUPERSLICER_PENALTY = 2 * SLICER_PENALTY;

    private static final double MEGASLICER_SPEED = SUPERSLICER_SPEED;
    private static final int MEGASLICER_HEALTH = 2 * SLICER_HEALTH;
    private static final int MEGASLICER_REWARD = 10;
    private static final int MEGASLICER_PENALTY = 2 * SUPERSLICER_PENALTY;

    private static final double APEXSLICER_SPEED = 1.0/2 * MEGASLICER_SPEED;
    private static final int APEXSLICER_HEALTH = 25 * SLICER_HEALTH;
    private static final int APEXSLICER_REWARD = 150;
    private static final int APEXSLICER_PENALTY = 4 * MEGASLICER_PENALTY;

    public Slicer createSlicer(String slicerType) {
        Slicer slicer = new Slicer();
        slicer.setImage(new Image("res/images/" + slicerType + ".png"));
        if (slicerType.equals("slicer")) {
            slicer.setSpeed(SLICER_SPEED);
            slicer.setHealth(SLICER_HEALTH);
            slicer.setReward(SLICER_REWARD);
            slicer.setPenalty(SLICER_PENALTY);
        }
        else if (slicerType.equals("superslicer")) {
            slicer.setSpeed(SUPERSLICER_SPEED);
            slicer.setHealth(SUPERSLICER_HEALTH);
            slicer.setReward(SUPERSLICER_REWARD);
            slicer.setPenalty(SUPERSLICER_PENALTY);
        }
        else if (slicerType.equals("megaslicer")) {
            slicer.setSpeed(MEGASLICER_SPEED);
            slicer.setHealth(MEGASLICER_HEALTH);
            slicer.setReward(MEGASLICER_REWARD);
            slicer.setPenalty(MEGASLICER_PENALTY);
        }
        else if (slicerType.equals("apexslicer")) {
            slicer.setSpeed(APEXSLICER_SPEED);
            slicer.setHealth(APEXSLICER_HEALTH);
            slicer.setReward(APEXSLICER_REWARD);
            slicer.setPenalty(APEXSLICER_PENALTY);
        }
        List<Point> polyline = ShadowDefend.currentMap.getAllPolylines().get(0);
        slicer.setPolyLine(polyline);
        slicer.setLocation(polyline.get(0));
        slicer.setNextPoint(polyline.get(0));
        slicer.setPointIndex(0);
        return slicer;
    }
}