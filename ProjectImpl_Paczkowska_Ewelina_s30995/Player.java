import java.time.LocalDate;
import java.util.*;

public class Player extends ObjectPlus{
    private String name;
    private Set<Purchase> gamesSet = new HashSet<>();

    public void addGame(Game game, LocalDate purchaseDate){
        new Purchase(purchaseDate, this, game);
    }

    public Player(String name){
        setName(name);
        addToExtent();
    }

    public void setName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Player name cannot be empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected void addPurchase(Purchase purchase){
        if (purchase == null) {
            throw new IllegalArgumentException("Purchase cannot be null");
        }
        gamesSet.add(purchase);
    }

    public Set<Purchase> getGameSet(){return Collections.unmodifiableSet(gamesSet);}

    public void removeGame(Game game){
        for(Purchase purchase : new HashSet<>(gamesSet)){
            if(purchase.getGame() == game){
                purchase.destruct();
            }
        }
    }

    public void removePurchase(Purchase purchase){
        gamesSet.remove(purchase);
    }

    public String toString(){
        return "Player name: " + getName() + ", games: " + gamesSet;
    }
}
