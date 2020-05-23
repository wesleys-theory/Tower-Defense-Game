public interface CooldownBehaviour {
    public void generateCooldown(int range);
    public boolean offCooldown();
    public void updateCooldown();
}
