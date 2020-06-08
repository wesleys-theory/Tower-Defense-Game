import java.lang.reflect.Array;
import java.util.ArrayList;

public class DropExplosive extends Ability {

    private ArrayList<Explosive> explosives;

    public DropExplosive(int range) {
        this.setCooldownBehaviour(new RandomCooldown(range));
        this.setCooldown(range);
        this.getCooldownBehaviour().generateCooldown(getCooldown());
        this.explosives = new ArrayList<>();
    }

    @Override
    public void performAbility(ArrayList<Slicer> slicers, Tower user) {
        ArrayList<Explosive> toBeRemoved = new ArrayList<>();

        Airplane client = (Airplane) user;
        if (getCooldownBehaviour().offCooldown() && !client.atEnd()) {
            getCooldownBehaviour().generateCooldown(getCooldown());
            explosives.add(new Explosive(client.getLocation()));
        }
        for (Explosive explosive : explosives) {
            explosive.updateCooldown();
            explosive.render();
            if (explosive.timerDone()) {
                explosive.explode(slicers);
                toBeRemoved.add(explosive);
            }
        }
        for (Explosive explosive : toBeRemoved) {
            explosives.remove(explosive);
        }
    }

    public boolean explosiveListEmpty() {
        return explosives.isEmpty();
    }
}
