import java.time.LocalDate;
import java.util.*;

public class Game extends ObjectPlus{
    private String name;
    private LocalDate released_date;
    private List<String> tags;
    private String description;
    private static int maximum_ram = 32;
    private int required_ram;
    private Set<Developer> developers = new HashSet<>();
    private Set<Review> reviews = new HashSet<>();
    private Set<Purchase> playersSet = new HashSet<>();
    private Singleplayer singleplayer;
    private Multiplayer multiplayer;
    private EnumSet<GameType> type;

    public Game(String name, LocalDate released_date, List<String> tags, String description, int required_ram,
                EnumSet<GameType> type) {
        try {
            setName(name);
            setReleased_date(released_date);
            setTags(tags);
            setDescription(description);
            setRequired_ram(required_ram);
            setType(type);
        }catch(Exception e){
                System.out.println(e.getMessage());
        }
    }

    public int getTagCount() {
        return tags.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.name = name;
    }

    public LocalDate getReleased_date() {
        return released_date;
    }

    public void setReleased_date(LocalDate released_date) {
        if(released_date == null){
            throw new IllegalArgumentException("Date cannot be blank");
        }
        if(released_date.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Date cannot be after now");
        }
        this.released_date = released_date;
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    public void setTags(List<String> tags) {
        if(tags == null || tags.isEmpty()){
            throw new IllegalArgumentException("Tags cannot be blank");
        }
        for(String tag : tags){
            if(tag == null || tag.trim().isEmpty()){
                throw new IllegalArgumentException("Tag cannot be blank");
            }
        }
        this.tags = tags;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(String description) {
        if(description != null && description.trim().isEmpty()){
            this.description = null;
        } else {
            this.description = description;
        }

    }

    public static int getMaximum_ram() {
        return maximum_ram;
    }

    public static void setMaximum_ram(int maximum_ram) {
        if(maximum_ram <= 0){
            throw new IllegalArgumentException("Maximum ram must be greater than 0");
        }
        Game.maximum_ram = maximum_ram;
    }

    public int getRequired_ram() {
        return required_ram;
    }

    public void setRequired_ram(int required_ram) {
        if(required_ram <= 0) {
            throw new IllegalArgumentException("Required ram must be greater than 0");
        }
        if(required_ram > maximum_ram){
            throw new IllegalArgumentException("Required ram cannot be greater than maximum ram");
        }
        this.required_ram = required_ram;
    }

    public int CalculateTotal_ram() {
        return required_ram;
    }

    public int CalculateTotal_ram(int extra_ram) {
        if(extra_ram <= 0){
            throw new IllegalArgumentException("Extra ram must be greater than 0");
        }
        return CalculateTotal_ram() + extra_ram;
    }

    public static Optional<Game> findNewestGame(){
        return ObjectPlus.getFromExtent(Game.class).stream().max(Comparator.comparing(Game::getReleased_date));
    }

    public void addDeveloper(Developer developer){
        if(developer == null){
            throw new IllegalArgumentException("Developer cannot be null");
        }
        if(developers.contains(developer)){
            return;
        }
        developers.add(developer);
        developer.addGame(this);
    }

    public Set<Developer> getDevelopers(){
        return Collections.unmodifiableSet(developers);
    }

    public void removeDeveloper(Developer developer){
        if(developer == null){
            throw new IllegalArgumentException("Developer cannot be null");
        }
        if(developers.remove(developer)){
            developer.removeGame(this);
        }
    }

    public void addReview(Review review){
        if(review == null){
            throw new IllegalArgumentException("Review cannot be null");
        }
        reviews.add(review);
    }

    public Set<Review> getReviews() {
        return Collections.unmodifiableSet(reviews);
    }

    public Review createReview(String content){
        return new Review(this, content);
    }

    public void removeReview(Review review){
        if(review == null){
            throw new IllegalArgumentException("Review cannot be null");
        }
        if(reviews.remove(review)){
            review.destruct();
        }
    }

    public void deleteGame() {
        while (!reviews.isEmpty()) {
            Review review = reviews.iterator().next();
            removeReview(review);
        }

        while (!developers.isEmpty()) {
            Developer developer = developers.iterator().next();
            removeDeveloper(developer);
        }

        while (!playersSet.isEmpty()) {
            Purchase purchase = playersSet.iterator().next();
            purchase.destruct();
        }

        removeFromExtent();
    }

    public void addPlayer(Player player, LocalDate purchaseDate){
        if(player == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
        new Purchase(purchaseDate, player, this);
    }

    protected void addPurchase(Purchase purchase){
        if (purchase == null) {
            throw new IllegalArgumentException("Purchase cannot be null");
        }
        playersSet.add(purchase);
    }

    public Set<Purchase> getPlayers(){return Collections.unmodifiableSet(playersSet);}

    public void removePlayer(Player player) {
        Iterator<Purchase> iterator = playersSet.iterator();

        while (iterator.hasNext()) {
            Purchase purchase = iterator.next();

            if (purchase.getPlayer() == player) {
                iterator.remove();
                player.removePurchase(purchase);
                purchase.removeFromExtent();
            }
        }
    }

    protected void removePurchase(Purchase purchase){ playersSet.remove(purchase);}

    public void setType(EnumSet<GameType> type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Game type is required");
        }
        this.type = type;
    }

    public EnumSet<GameType> getType() {
        return type;
    }

    public void create() {
        if (type.contains(GameType.SINGLEPLAYER)) {
            if (singleplayer == null) {
                throw new IllegalArgumentException("Singleplayer data is required");
            }
        }

        if (type.contains(GameType.MULTIPLAYER)) {
            if (multiplayer == null) {
                throw new IllegalArgumentException("Multiplayer data is required");
            }
        }

        addToExtent();
    }

    public Singleplayer getSingleplayer() {
        if(!type.contains(GameType.SINGLEPLAYER)){
            throw new IllegalArgumentException("Singleplayer data is required");
        }
        return singleplayer;
    }

    public Multiplayer getMultiplayer() {
        if(!type.contains(GameType.MULTIPLAYER)){
            throw new IllegalArgumentException("Multiplayer data is required");
        }
        return multiplayer;
    }

    public void setSingleplayer(Singleplayer singleplayer) {
        if(!type.contains(GameType.SINGLEPLAYER) || singleplayer == null){
            throw new IllegalArgumentException("Singleplayer data is required");
        }
        this.singleplayer = singleplayer;
    }

    public void setMultiplayer(Multiplayer multiplayer) {
        if(!type.contains(GameType.MULTIPLAYER) || multiplayer == null){
            throw new IllegalArgumentException("Multiplayer data is required");
        }
        this.multiplayer = multiplayer;
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", released_date=" + released_date +
                ", required_ram=" + required_ram +
                ", tags=" + tags +'}';
    }
}
