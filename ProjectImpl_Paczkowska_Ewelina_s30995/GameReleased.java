import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class GameReleased extends Game{
    private LocalDate releaseDate;

    public GameReleased(String name, LocalDate released_date, List<String> tags, Optional<String> description, int required_ram,
                        LocalDate releaseDate, EnumSet<GameType> type) {
        super(name, released_date, tags, description.orElse(null), required_ram, type);
        setReleaseDate(releaseDate);
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        if(releaseDate == null || releaseDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Invalid date passed");
        }
        this.releaseDate = releaseDate;
    }
}
