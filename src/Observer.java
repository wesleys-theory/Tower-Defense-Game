public interface Observer {
    /**
     * Updates the state of an observer
     * @param subject a reference to the subject
     */
    public void update(Subject subject);
}
