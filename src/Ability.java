import java.util.ArrayList;

public abstract class Ability {
    private int cooldown;
    private CooldownBehaviour cooldownBehaviour;

    public CooldownBehaviour getCooldownBehaviour() {
        return cooldownBehaviour;
    }

    public void setCooldownBehaviour(CooldownBehaviour cooldownBehaviour) {
        this.cooldownBehaviour = cooldownBehaviour;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }


    public void performAbility(ArrayList<Slicer> slicers, Tower user) {}
    public void updateCooldown() {
    }
}
