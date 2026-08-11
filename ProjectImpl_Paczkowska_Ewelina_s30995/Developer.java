import java.util.*;

public class Developer extends ObjectPlus{
    private String name;
    private Set<Game> games = new HashSet<Game>();

    public Developer(String name){
        setName(name);
        addToExtent();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Name cannot be empty");
        this.name = name;
    }

    public Set<Game> getGames() {
        return Collections.unmodifiableSet(games);
    }

    public void addGame(Game game) {
        if (game == null)
            throw new IllegalArgumentException("Game cannot be null");
        if (games.contains(game)) {
            return;
        }
        games.add(game);
        game.addDeveloper(this);
    }

    public void removeGame(Game game) {
        if(game == null)
            throw new IllegalArgumentException("Game cannot be null");
        if (games.remove(game))
            game.removeDeveloper(this);
    }

    @Override
    public String toString() {
        return "Developer{" +
                "name='" + name + '\'' +
                ", games=" + games +
                '}';
    }
}
