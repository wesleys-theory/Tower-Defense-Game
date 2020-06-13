import bagel.Drawing;
import bagel.util.Colour;
import bagel.util.Point;


public class Tank extends Tower {
    // Stepsize used in circle drawing algorithm.
    private static final double STEPSIZE = 1;

    private int effectRadius;

    /**
     * Getter for the effect radius of the tank
     * @return the effectRadius
     */
    public int getEffectRadius() {
        return effectRadius;
    }

    /**
     * Tank constructor
     * @param radius how far away it can shoot
     * @param point location of the tank
     */
    public Tank(int radius, Point point) {
        super(point);
        this.effectRadius = radius;
    }

    /**
     * Function for drawing the range around a tank - basically just draws a tonne of really small straight lines
     * to make a circle
     */
    public void drawEffectRadius() {
        double numSteps = 2*effectRadius/STEPSIZE;
        double startX = this.getLocation().x - effectRadius;
        double startY = this.getLocation().y;

        // Draw top half of arc
        for (int i = 0; i < numSteps; i++) {
            double endX = startX + STEPSIZE;
            double endY = -1*Math.sqrt(Math.pow(effectRadius,2) - Math.pow(endX - this.getLocation().x, 2)) +
                    this.getLocation().y;
            Drawing.drawLine(new Point(startX, startY), new Point(endX, endY), 1, Colour.RED);
            startX = endX;
            startY = endY;
        }

        startX = this.getLocation().x - effectRadius;
        startY = this.getLocation().y;
        // Draw bottom half of arc
        for (int i = 0; i < numSteps; i++) {
            double endX = startX + STEPSIZE;
            double endY = Math.sqrt(Math.pow(effectRadius,2) - Math.pow(endX - this.getLocation().x, 2)) +
                    this.getLocation().y;
            Drawing.drawLine(new Point(startX, startY), new Point(endX, endY), 1, Colour.RED);
            startX = endX;
            startY = endY;
        }
    }
}
