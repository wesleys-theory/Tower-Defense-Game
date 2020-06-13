import bagel.Image;

public class ProjectileCreator {
    private static final int TANK_DAMAGE = 1;
    private static final int SUPERTANK_DAMAGE = 3*TANK_DAMAGE;


    /**
     * Projectile creation logic - either creates a regular tank projectile or a super tank projectile, assigning
     * relevant values such as damage and images
     * @param projectileType tank projectile or supertank projectile
     * @return the fresh projectile
     */
    public Projectile makeProjectile(String projectileType) {
        Projectile newProjectile = new Projectile();
        if (projectileType.equals("tank")) {
            newProjectile.setImage(new Image("res/images/tank_projectile.png"));
            newProjectile.setDamage(TANK_DAMAGE);
        }
        else if (projectileType.equals("supertank")) {
            newProjectile.setImage(new Image("res/images/supertank_projectile.png"));
            newProjectile.setDamage(SUPERTANK_DAMAGE);
        }
        return newProjectile;
    }
}
