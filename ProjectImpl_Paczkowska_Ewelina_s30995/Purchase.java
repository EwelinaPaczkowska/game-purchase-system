import java.time.LocalDate;
import java.util.Objects;

public class Purchase extends ObjectPlus{
    private LocalDate purchaseDate;
    private Game game;
    private Player player;

    public Purchase(LocalDate purchaseDate, Player player, Game game) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }

        if(purchaseDate.isBefore(game.getReleased_date())){
            throw new IllegalArgumentException("Purchase date cannot be before the release date");
        }

        setPurchaseDate(purchaseDate);
        this.game = game;
        this.player = player;

        player.addPurchase(this);
        game.addPurchase(this);
        addToExtent();
    }

    public void destruct(){
        player.removePurchase(this);
        game.removePurchase(this);
        removeFromExtent();
    }

    public boolean equals(Object o){
        if(o==null || getClass()!=o.getClass()) return false;
        Purchase p = (Purchase)o;
        return Objects.equals(player, p.player) && Objects.equals(game, p.game);
    }

    public int hashCode(){return Objects.hash(player, game);}

    public Game getGame(){return game;}
    public Player getPlayer(){return player;}

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        if(purchaseDate == null){
            throw new IllegalArgumentException("Purchase date cannot be null");
        }

        if(purchaseDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Purchase date cannot be in the future");
        }

        this.purchaseDate = purchaseDate;
    }

    public String toString() {
        return "Purchase{" +
                "player=" + player.getName() +
                ", game=" + game.getName() +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
