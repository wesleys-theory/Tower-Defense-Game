import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.util.Colour;

import java.util.ArrayList;

public class StatusPanel implements Observer {
    private static final int START_LIVES = 25;
    private static final int FONT_SIZE = 17;
    private static final int OFFSET_FROM_LEFT = 10;
    private static final int OFFSET_FROM_BOTTOM = 7;
    private static final Font font = new Font("res/fonts/DejaVuSans-Bold.ttf", FONT_SIZE);
    private static final int HEIGHT = ShadowDefend.HEIGHT - OFFSET_FROM_BOTTOM;
    private static final int TIME_X = 200;
    private static final int STATUS_X = 450;
    private static final int LIVES_X = 925;
    private static final int WAVE_INDEX = 2;
    private static final int PLACING_INDEX = 1;
    private static final int WINNER_INDEX = 0;

    private boolean[] statuses;
    private int waveNumber;
    private int lives;
    private Image background;
    private Clock clock;

    /**
     * Status panel constructor
     */
    public StatusPanel() {
        waveNumber = 0;
        lives = START_LIVES;
        background = new Image("res/images/statuspanel.png");
        statuses = new boolean[]{false,false,false,true};
        clock = new Clock();
    }

    /**
     * Resets the status panel for the next level
     */
    public void reset() {
        this.lives = START_LIVES;
    }

    /**
     *
     * @return the lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets the "winner" status to true
     */
    public void winnerAlert() {
        statuses[WINNER_INDEX] = true;
    }

    /**
     * updates the time multiplier
     * @param input k for slower l for faster
     */
    public void update(Input input) {
        clock.updateMultiplier(input);
    }

    /**
     * Renders the status panel to the screen
     */
    public void render() {
        double y_location = ShadowDefend.HEIGHT - background.getHeight()/2;
        double x_location = background.getWidth()/2;
        background.draw(x_location, y_location);
        drawWave();
        drawTimeScale();
        drawStatus();
        drawLives();
    }

    /**
     * Writes the wave number to the screen
     */
    public void drawWave() {
        font.drawString(String.format("Wave: %d", waveNumber), OFFSET_FROM_LEFT,
                HEIGHT);
    }

    /**
     * Draws the time multiplier to the screen
     */
    public void drawTimeScale() {
        Colour colour;
        if (Clock.timeMultiplier > 1) {
            colour = Colour.GREEN;
        }
        else {
            colour = Colour.RED;
        }
        font.drawString(String.format("Time Scale: %d.0", Clock.timeMultiplier), TIME_X, HEIGHT,
                new DrawOptions().setBlendColour(colour));
    }

    /**
     * Draws the status to the screen by checking for important statuses first
     */
    public void drawStatus() {
        String string = "Status: ";
        int index = 0;
        for (boolean status : statuses) {
            if (status) {
                break;
            }
            index++;
        }
        if (index == WINNER_INDEX) {
            string += "Winner!";
        }
        else if (index == PLACING_INDEX) {
            string += "Placing";
        }
        else if (index == WAVE_INDEX) {
            string += "Wave In Progress";
        }
        else {
            string += "Awaiting Start";
        }
        font.drawString(string, STATUS_X, HEIGHT);
    }

    /**
     * Draws the number of lives left to the status panel
     */
    public void drawLives() {
        String string = String.format("Lives: %d", lives);
        font.drawString(string, LIVES_X, HEIGHT);
    }

    /**
     * Updates the status panel by looking at the current wave and buy panel
     * @param subject a reference to the subject
     */
    @Override
    public void update(Subject subject) {
        if (subject instanceof Wave) {
            Wave wave = (Wave) subject;
            statuses[WAVE_INDEX] = wave.hasStarted();
            this.waveNumber = wave.getWaveIndex() + 1;

            // Decrease number of lives for the slicers at the end
            ArrayList<Slicer> processedSlicers = new ArrayList<>();
            for (Slicer slicer : wave.getSlicersAtEnd()) {
                this.lives -= slicer.getPenalty();
                processedSlicers.add(slicer);
            }
            for (Slicer slicer : processedSlicers) {
                wave.removeSlicerAtEnd(slicer);
            }
        }
        else if (subject instanceof BuyPanel) {
            BuyPanel buyPanel = (BuyPanel) subject;
            statuses[PLACING_INDEX] = buyPanel.placing();
        }
    }
}
