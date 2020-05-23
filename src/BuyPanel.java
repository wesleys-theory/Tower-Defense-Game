import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;

public class BuyPanel implements Subject, Observer {

    private final static String FONT_LOCATION = "res/fonts/DejaVuSans-Bold.ttf";
    private final static int START_MONEY = 10000;
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
    private static final int REWARD_MULTIPLIER = 100;
    private static final int BASE_REWARD = 150;

    private int money;
    private ArrayList<Tower> purchaseItems;
    private ArrayList<Tower> activeTowers;
    private Tower selectedTower;
    private boolean towerSelected;
    private Image background;
    private Font costFont;
    private Font moneyFont;
    private Font keyBindFont;
    private TowerCreator towerCreator;
    private ArrayList<Observer> observers;
    private ArrayList<Slicer> slicers;

    public ArrayList<Tower> getActiveTowers() {
        return activeTowers;
    }

    public boolean placing() {
        return towerSelected;
    }

    public BuyPanel() {
        this.costFont = new Font(FONT_LOCATION, COST_SIZE);
        this.moneyFont = new Font(FONT_LOCATION, MONEY_SIZE);
        this.keyBindFont = new Font(FONT_LOCATION, KEYBIND_SIZE);
        this.activeTowers = new ArrayList<>();
        this.selectedTower = null;
        this.towerSelected = false;
        this.towerCreator = new TowerCreator();
        this.observers = new ArrayList<>();
        this.slicers = new ArrayList<>();

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
        double y_location = background.getHeight()/2;
        double x_location = background.getWidth()/2;
        background.draw(x_location, y_location);
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
        moneyFont.drawString(output, ShadowDefend.WIDTH - OFFSET_FROM_RIGHT, OFFSET_FROM_TOP);
    }

    public void drawKeyBinds() {
        String output = "Key binds:\n\nS - Start Wave\nL - Increase Timescale\nK - Decrease Timescale";
        keyBindFont.drawString(output, background.getWidth()/2 - KEYBIND_OFFSET, KEYBIND_HEIGHT);
    }

    public void checkPurchase(Input input) {

        for (Tower tower:purchaseItems) {
            Rectangle hitBox = tower.getHitBox();
            if (hitBox.intersects(input.getMousePosition()) && input.wasPressed(MouseButtons.LEFT) && !towerSelected
                && tower.getCost() <= money) {
                towerSelected = true;
                selectedTower = towerCreator.makeTower(tower.getType());
                return;
            }
        }
        if (towerSelected && input.wasPressed(MouseButtons.RIGHT)) {
            towerSelected = false;
            selectedTower = null;
        }
        else if (towerSelected && input.wasPressed(MouseButtons.LEFT) && !selectedTower.blockedLocation()) {
            activeTowers.add(selectedTower);
            money -= selectedTower.getCost();
            towerSelected = false;
            selectedTower = null;
        }
        else if (towerSelected) {
            selectedTower.setLocation(input.getMousePosition());
        }
    }

    public void drawActiveTowers() {
        for (Tower tower : activeTowers) {
            tower.getAbility().performAbility(slicers, tower);
            tower.getAbility().getCooldownBehaviour().updateCooldown();
            tower.render();
        }
    }


    public void drawSelected() {
        if (!towerSelected ) {
            return;
        }
        if (!selectedTower.blockedLocation()) {
            selectedTower.render();
        }
    }

    public void update(Input input) {
        drawMoney();
        drawKeyBinds();
        drawPurchaseItems();
        checkPurchase(input);
        drawSelected();
        drawActiveTowers();
        notifyObservers();
    }

    public void reset() {
        getActiveTowers().clear();
        money = START_MONEY;
    }

    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof Wave) {
            Wave wave = (Wave) subject;
            if (wave.isFinished()) {
                money += BASE_REWARD + (wave.getWaveIndex() + 1)*REWARD_MULTIPLIER;
            }
            this.slicers = wave.getSlicers();
        }
    }
}
