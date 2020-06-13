import java.util.ArrayList;

public abstract class Ability {
    private int cooldown;
    private CooldownBehaviour cooldownBehaviour;

    /**
     * cooldownBehaviour getter
     * @return the cooldown behaviour of the ability
     */
    public CooldownBehaviour getCooldownBehaviour() {
        return cooldownBehaviour;
    }

    /**
     * cooldownBehaviour setter
     * @param cooldownBehaviour the cooldown behaviour specified for this ability
     */
    public void setCooldownBehaviour(CooldownBehaviour cooldownBehaviour) {
        this.cooldownBehaviour = cooldownBehaviour;
    }

    /**
     * Gets the integer cooldown of an ability (generally a range or a fixed number)
     * @return the cooldown
     */
    public int getCooldown() {
        return cooldown;
    }

    /**
     * Cooldown setter
     * @param cooldown the cooldown
     */
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }


    /**
     * Takes a list of slicers and a reference to the object performing the ability and performs the ability
     * @param slicers the list of slicers in the game
     * @param user the tower using the ability
     */
    public void performAbility(ArrayList<Slicer> slicers, Tower user) {}
}
