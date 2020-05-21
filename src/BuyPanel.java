import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;

import java.util.ArrayList;

public class BuyPanel {
    private final static int HEIGHT = 768;
    private final static int WIDTH = 1024;

    private final static int START_MONEY = 500;
    private final static int OFFSET_FROM_LEFT = 64;
    private final static int OFFSET_FROM_CENTRE = 10;
    private final static int OFFSET_FROM_RIGHT = 200;
    private final static int OFFSET_FROM_TOP = 65;
    private final static int COST_GAP = 20;
    private final static int TOWER_GAP = 120;
    private final static int COST_SIZE = 25;
    private final static int MONEY_SIZE = 40;
    private final static int KEYBIND_SIZE = 15;
    private final static int KEYBIND_HEIGHT = 20;
    private final static int KEYBIND_OFFSET = 30;

    private int money;
    private ArrayList<Tower> purchaseItems;
    private Image background;
    private Font costFont;
    private Font moneyFont;
    private Font keyBindFont;

    public BuyPanel() {
        this.costFont = new Font("res/fonts/DejaVuSans-Bold.ttf", COST_SIZE);
        this.moneyFont = new Font("res/fonts/DejaVuSans-Bold.ttf", MONEY_SIZE);
        this.keyBindFont = new Font("res/fonts/DejaVuSans-Bold.ttf", KEYBIND_SIZE);

        money = START_MONEY;
        background = new Image("res/images/buypanel.png");

        double height = background.getHeight()/2 - OFFSET_FROM_CENTRE;

        TowerCreator towerFactory = new TowerCreator();
        Tower towerPrototype;
        purchaseItems = new ArrayList<>();

        towerPrototype = towerFactory.makeTower("tank");
        towerPrototype.setLocation((new Point(OFFSET_FROM_LEFT, height)));
        purchaseItems.add(towerPrototype);

        towerPrototype = towerFactory.makeTower("supertank");
        towerPrototype.setLocation((new Point(OFFSET_FROM_LEFT + TOWER_GAP, height)));
        purchaseItems.add(towerPrototype);

        towerPrototype = towerFactory.makeTower("airplane");
        towerPrototype.setLocation((new Point(OFFSET_FROM_LEFT +2* TOWER_GAP, height)));
        purchaseItems.add(towerPrototype);
    }

    public void render() {
        double height = background.getHeight()/2;
        double width = background.getWidth()/2;
        background.draw(width, height);
        drawPurchaseItems();
        drawMoney();
        drawKeyBinds();
    }

    public void drawPurchaseItems() {
        Colour colour;
        for (Tower tower : purchaseItems) {
            if (money >= tower.getCost()) {
                colour = Colour.GREEN;
            }
            else {
                colour = Colour.RED;
            }

            tower.render();
            int cost = tower.getCost();
            double x_coord = tower.getLocation().x - tower.getImage().getWidth()/2;
            double y_coord = tower.getLocation().y + tower.getImage().getHeight()/2 + COST_GAP;
            costFont.drawString(String.format("$%d", cost), x_coord, y_coord, new DrawOptions().setBlendColour(colour));
        }
    }

    public void drawMoney() {
        String output = String.format("$%d", money);
        moneyFont.drawString(output, WIDTH - OFFSET_FROM_RIGHT, OFFSET_FROM_TOP);
    }

    public void drawKeyBinds() {
        String output = "Key binds:\n\nS - Start Wave\nL - Increase Timescale\nK - Decrease Timescale";
        keyBindFont.drawString(output, background.getWidth()/2 - KEYBIND_OFFSET, KEYBIND_HEIGHT);
    }
}
