import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.util.Colour;

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

    private boolean statuses[];
    private int waveNumber;
    private int lives;
    private Image background;
    private Clock clock;

    public StatusPanel() {
        waveNumber = 0;
        lives = START_LIVES;
        background = new Image("res/images/statuspanel.png");
        statuses = new boolean[]{false,false,false,true};
        clock = new Clock();
    }

    public void update(Input input) {
        clock.updateMultiplier(input);
    }

    public void render() {
        double y_location = ShadowDefend.HEIGHT - background.getHeight()/2;
        double x_location = background.getWidth()/2;
        background.draw(x_location, y_location);
        drawWave();
        drawTimeScale();
        drawStatus();
        drawLives();
    }

    public void drawWave() {
        font.drawString(String.format("Wave: %d", waveNumber), OFFSET_FROM_LEFT,
                HEIGHT);
    }

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

    public void drawLives() {
        String string = String.format("Lives: %d", lives);
        font.drawString(string, LIVES_X, HEIGHT);
    }

    @Override
    public void update(Subject subject) {
        Wave wave = (Wave) subject;
        statuses[WAVE_INDEX] = wave.hasStarted();
        this.waveNumber = wave.getWaveNum() + 1;
    }
}
