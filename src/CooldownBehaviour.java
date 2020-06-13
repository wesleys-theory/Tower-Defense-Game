public interface CooldownBehaviour {
    /**
     * Generates a cooldown from a specified range
     * @param range some numerical parameter for determining the cooldown
     */
    public void generateCooldown(int range);

    /**
     * Determines whether the cooldown is over or not
     * @return a boolean value
     */
    public boolean offCooldown();

    /**
     * Updates the timer on the cooldown
     */
    public void updateCooldown();
}
