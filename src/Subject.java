public interface Subject {
    /**
     * Subscribes a new observer to the subject
     * @param observer object who wants a subscription
     */
    public void registerObserver(Observer observer);

    /**
     * Unsubscribes an existing observer
     * @param observer existing observer
     */
    public void unregisterObserver(Observer observer);

    /**
     * notifies the observers of what's going on
     */
    public void notifyObservers();
}
