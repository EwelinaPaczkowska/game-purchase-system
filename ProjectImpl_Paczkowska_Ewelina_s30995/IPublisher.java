public interface IPublisher {
    default void publishGame(Game game) {
        System.out.println("Publishing game: " + game.getName());
    }
}
