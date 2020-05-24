import bagel.Drawing;
import bagel.util.Colour;
import bagel.util.Point;


public class Tank extends Tower {
    private int effectRadius;

    public int getEffectRadius() {
        return effectRadius;
    }

    public Tank(int radius, Point point) {
        super(point);
        this.effectRadius = radius;
    }

    public void drawEffectRadius() {
        int stepSize = 1;
        int numSteps = 2*effectRadius/stepSize;
        double startX = this.getLocation().x - effectRadius;
        double startY = this.getLocation().y;

        // Draw top half of arc
        for (int i = 0; i < numSteps; i++) {
            double endX = startX + stepSize;
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
            double endX = startX + stepSize;
            double endY = Math.sqrt(Math.pow(effectRadius,2) - Math.pow(endX - this.getLocation().x, 2)) +
                    this.getLocation().y;
            Drawing.drawLine(new Point(startX, startY), new Point(endX, endY), 1, Colour.RED);
            startX = endX;
            startY = endY;
        }
    }
}
