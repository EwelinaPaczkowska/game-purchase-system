import java.io.Serializable;

public class Multiplayer implements Serializable {
    private int maxPlayers;

    public Multiplayer(int maxPlayers) {
        setMaxPlayers(maxPlayers);
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        if (maxPlayers <= 0) {
            throw new IllegalArgumentException("Max players must be greater than 0");
        }
        this.maxPlayers = maxPlayers;
    }

    @Override
    public String toString() {
        return "Multiplayer{" +
                "maxPlayers=" + maxPlayers +
                '}';
    }
}