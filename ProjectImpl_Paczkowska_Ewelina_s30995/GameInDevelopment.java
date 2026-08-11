import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;

public class GameInDevelopment extends Game{
    private LocalDate plannedDate;

    public GameInDevelopment(String name, LocalDate released_date, List<String> tags, String description, int required_ram,
                             LocalDate plannedDate, EnumSet<GameType> type) {
        super(name, released_date, tags, description, required_ram, type);
        setPlannedDate(plannedDate);
    }

    public GameReleased changeToReleased(LocalDate releaseDate){
        GameReleased gameReleased = new GameReleased(getName(), getReleased_date(), getTags(),
                 getDescription(), getRequired_ram(), releaseDate, getType());
        if (getType().contains(GameType.SINGLEPLAYER)) {
            gameReleased.setSingleplayer(getSingleplayer());
        }

        if (getType().contains(GameType.MULTIPLAYER)) {
            gameReleased.setMultiplayer(getMultiplayer());
        }

        for (Developer developer : new HashSet<>(getDevelopers())) {
            gameReleased.addDeveloper(developer);
        }

        gameReleased.create();
        removeFromExtent();
        return gameReleased;
    }

    public LocalDate getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(LocalDate plannedDate) {
        if (plannedDate == null || plannedDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Planned date cannot be before current date");
        }
        this.plannedDate = plannedDate;
    }
}
