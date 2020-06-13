public class WaveEventCreator {
    private static final int INDEX_OF_EVENT_TYPE = 1;
    private static final int INDEX_OF_SPAWN_NUM = 2;
    private static final int INDEX_OF_SLICER_TYPE = 3;
    private static final int INDEX_OF_SPAWN_DELAY = 4;
    private static final int INDEX_OF_WAVENUM = 0;
    private static final int INDEX_OF_DELAY = 2;

    /**
     * Creates the wave events as specified by the input
     * @param waveEvent either "spawn" or "delay"
     * @return a reference to the new wave event
     */
    public WaveEvent parseEvent(String waveEvent) {
        String[] arguments = waveEvent.split(",");
        if (arguments[INDEX_OF_EVENT_TYPE].equals("spawn")) {
            SpawnEvent event = new SpawnEvent();

            event.setWaveNumber(Integer.parseInt(arguments[INDEX_OF_WAVENUM]));
            event.setNumToSpawn(Integer.parseInt(arguments[INDEX_OF_SPAWN_NUM]));
            event.setSlicerType(arguments[INDEX_OF_SLICER_TYPE]);
            event.setSpawnDelay(Integer.parseInt(arguments[INDEX_OF_SPAWN_DELAY]));

            return event;
        }

        else if (arguments[INDEX_OF_EVENT_TYPE].equals("delay")) {
            DelayEvent event = new DelayEvent();

            event.setWaveNumber(Integer.parseInt(arguments[INDEX_OF_WAVENUM]));
            event.setDelay(Integer.parseInt(arguments[INDEX_OF_DELAY]));

            return event;
        }

        else {
            System.out.println("ERROR PARSING EVENT");
            return null;
        }
    }
}
