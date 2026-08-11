public class Publisher extends ObjectPlus implements IPublisher {
    private IndependentDeveloper independentDeveloper;
    private String publisherName;

    public Publisher(IndependentDeveloper independentDeveloper, String publisherName) {
        this.independentDeveloper = independentDeveloper;
        setName(publisherName);
        addToExtent();
    }

    public String getName() {
        return publisherName;
    }

    public void setName(String publisherName) {
        if (publisherName == null || publisherName.trim().isEmpty()) {
            throw new IllegalArgumentException("Publisher name cannot be empty");
        }
        this.publisherName = publisherName;
    }

    @Override
    public String toString() {
        return "Publisher{name='" + publisherName + "'}";
    }
}