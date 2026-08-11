public class IndependentDeveloper extends Developer implements IPublisher{
    Publisher publisher;
    public IndependentDeveloper(String name) {
        super(name);
        publisher = new Publisher(this, name);
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void releaseOwnGame(Game game) {
        addGame(game);
        publishGame(game);
    }
}
