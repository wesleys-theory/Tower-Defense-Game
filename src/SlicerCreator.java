import bagel.Image;
import bagel.util.Point;

import java.util.List;

public class SlicerCreator {
    private static final double SLICER_SPEED = 2;
    private static final int SLICER_HEALTH = 1;
    private static final int SLICER_REWARD = 2;
    private static final int SLICER_PENALTY = 1;

    private static final double SUPERSLICER_SPEED = 3.0/4 * SLICER_SPEED;
    private static final int SUPERSLICER_HEALTH = SLICER_HEALTH;
    private static final int SUPERSLICER_REWARD = 15;
    // All units are broken down into slicers before calculating penalties.
    private static final int SUPERSLICER_PENALTY = 0;

    private static final double MEGASLICER_SPEED = SUPERSLICER_SPEED;
    private static final int MEGASLICER_HEALTH = 2 * SLICER_HEALTH;
    private static final int MEGASLICER_REWARD = 10;
    private static final int MEGASLICER_PENALTY = 0;

    private static final double APEXSLICER_SPEED = 1.0/2 * MEGASLICER_SPEED;
    private static final int APEXSLICER_HEALTH = 25 * SLICER_HEALTH;
    private static final int APEXSLICER_REWARD = 150;
    private static final int APEXSLICER_PENALTY = 0;

    private static final int NUM_SUPERSLICER_CHILDREN = 2;
    private static final int NUM_MEGASLICER_CHILDREN = 2;
    private static final int NUM_APEXSLICER_CHILDREN = 4;

    public Slicer createSlicer(SlicerType slicerType) {
        Slicer slicer = new Slicer();
        slicer.setImage(new Image("res/images/" + slicerType.toString().toLowerCase() + ".png"));
        if (slicerType.equals(SlicerType.SLICER)) {
            slicer.setSpeed(SLICER_SPEED);
            slicer.setHealth(SLICER_HEALTH);
            slicer.setReward(SLICER_REWARD);
            slicer.setPenalty(SLICER_PENALTY);
            slicer.setChildType(null);
        }
        else if (slicerType.equals(SlicerType.SUPERSLICER)) {
            slicer.setSpeed(SUPERSLICER_SPEED);
            slicer.setHealth(SUPERSLICER_HEALTH);
            slicer.setReward(SUPERSLICER_REWARD);
            slicer.setPenalty(SUPERSLICER_PENALTY);
            slicer.setChildType(SlicerType.SLICER);
            slicer.setChildAmount(NUM_SUPERSLICER_CHILDREN);
        }
        else if (slicerType.equals(SlicerType.MEGASLICER)) {
            slicer.setSpeed(MEGASLICER_SPEED);
            slicer.setHealth(MEGASLICER_HEALTH);
            slicer.setReward(MEGASLICER_REWARD);
            slicer.setPenalty(MEGASLICER_PENALTY);
            slicer.setChildType(SlicerType.SUPERSLICER);
            slicer.setChildAmount(NUM_MEGASLICER_CHILDREN);
        }
        else if (slicerType.equals(SlicerType.APEXSLICER)) {
            slicer.setSpeed(APEXSLICER_SPEED);
            slicer.setHealth(APEXSLICER_HEALTH);
            slicer.setReward(APEXSLICER_REWARD);
            slicer.setPenalty(APEXSLICER_PENALTY);
            slicer.setChildType(SlicerType.MEGASLICER);
            slicer.setChildAmount(NUM_APEXSLICER_CHILDREN);
        }
        List<Point> polyline = ShadowDefend.currentMap.getAllPolylines().get(0);
        slicer.setPolyLine(polyline);
        slicer.setLocation(polyline.get(0));
        slicer.setNextPoint(polyline.get(0));
        slicer.setPointIndex(0);
        return slicer;
    }
}
