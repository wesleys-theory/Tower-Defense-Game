import bagel.util.Point;

public abstract class Tower extends Sprite {
    private int cost;
    private String type;
    private Ability ability;

    /**
     * Getter for the tower's ability
     * @return Ability
     */
    public Ability getAbility() {
        return ability;
    }

    /**
     * Setter for the tower's ability
     * @param ability the desired ability
     */
    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    /**
     * Getter for the type of tank
     * @return a string representing the type - "supertank" or "tank"
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for the type of tank
     * @param type a string
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Constructor for a tower
     * @param point tower location
     */
    public Tower(Point point) {
        this.setLocation(point);
    }

    /**
     * Getter for the cost of a tank
     * @return cost of tank
     */
    public int getCost() {
        return cost;
    }

    /**
     * Setter for the cost of a tank
     * @param cost cost of tank
     */
    public void setCost(int cost) {
        this.cost = cost;
    }
}
