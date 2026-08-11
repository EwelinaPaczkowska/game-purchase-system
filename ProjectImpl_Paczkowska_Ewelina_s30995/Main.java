import javax.swing.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumSet;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                ObjectPlus.loadExtent();
            } catch (RuntimeException e) {
                System.out.println("Extent file not loaded, sample data will be created.");

                Game snake = new Game("Snake", LocalDate.of(1976, 2, 2), Arrays.asList("arcade", "retro"),
                        "very funny snake game", 4, EnumSet.of(GameType.SINGLEPLAYER));
                snake.setSingleplayer(new Singleplayer("SnakePlayer"));
                snake.create();

                Game mafia = new Game("Mafia", LocalDate.of(2002, 8, 28),
                        Arrays.asList("action", "classic", "open-world", "crime", "third-person"), "",
                        4, EnumSet.of(GameType.SINGLEPLAYER, GameType.MULTIPLAYER));
                mafia.setSingleplayer(new Singleplayer("MafiaStoryPlayer"));
                mafia.setMultiplayer(new Multiplayer(10));
                mafia.create();

                Game maxPayne = new Game("Max Payne", LocalDate.of(2001, 7, 25),
                        Arrays.asList("action", "noir", "third-person", "bullet-time"), "dark action game",
                        4, EnumSet.of(GameType.SINGLEPLAYER));
                maxPayne.setSingleplayer(new Singleplayer("MaxPayneStoryPlayer"));
                maxPayne.create();

                Game stardewValley = new Game("Stardew Valley", LocalDate.of(2016, 2, 26),
                        Arrays.asList("farming", "relaxing", "pixel-art", "simulation"), "cozy farming game",
                        4, EnumSet.of(GameType.SINGLEPLAYER, GameType.MULTIPLAYER));
                stardewValley.setSingleplayer(new Singleplayer("FarmStoryPlayer"));
                stardewValley.setMultiplayer(new Multiplayer(4));
                stardewValley.create();

                Game rocketLeague = new Game("Rocket League", LocalDate.of(2015, 7, 7),
                        Arrays.asList("cars", "football", "online", "competitive"), "cars playing football",
                        8, EnumSet.of(GameType.MULTIPLAYER));
                rocketLeague.setMultiplayer(new Multiplayer(8));
                rocketLeague.create();

                Game portal = new Game("Portal", LocalDate.of(2007, 10, 10),
                        Arrays.asList("puzzle", "sci-fi", "first-person", "classic"), "puzzle game with portals",
                        4, EnumSet.of(GameType.SINGLEPLAYER));
                portal.setSingleplayer(new Singleplayer("PortalPlayer"));
                portal.create();

                Game hades = new Game("Hades", LocalDate.of(2020, 9, 17),
                        Arrays.asList("roguelike", "action", "mythology", "indie"),
                        "Action roguelike about escaping the underworld.", 8,
                        EnumSet.of(GameType.SINGLEPLAYER, GameType.MULTIPLAYER));
                hades.setSingleplayer(new Singleplayer("HadesPlayer"));
                hades.setMultiplayer(new Multiplayer(4));
                hades.create();

                Game celeste = new Game("Celeste", LocalDate.of(2018, 1, 25),
                        Arrays.asList("platformer", "indie", "precision", "story"),
                        "Precision platform game about climbing a mountain.", 4, EnumSet.of(GameType.SINGLEPLAYER));
                celeste.setSingleplayer(new Singleplayer("CelestePlayer"));
                celeste.create();

                Game left4Dead2 = new Game("Left 4 Dead 2", LocalDate.of(2009, 11, 17),
                        Arrays.asList("co-op", "zombie", "action", "shooter"),
                        "Cooperative multiplayer action game focused on survival.", 8, EnumSet.of(GameType.MULTIPLAYER));
                left4Dead2.setMultiplayer(new Multiplayer(4));
                left4Dead2.create();

                Player lama = new Player("Lama");
                Player kot = new Player("Kot");
                Player anna = new Player("Anna");
                Player marek = new Player("Marek");
                Player ola = new Player("Ola");

                lama.addGame(snake, LocalDate.of(2024, 1, 20));
                lama.addGame(maxPayne, LocalDate.of(2024, 2, 10));
                lama.addGame(stardewValley, LocalDate.of(2024, 3, 15));
                lama.addGame(portal, LocalDate.of(2024, 4, 5));
                kot.addGame(stardewValley, LocalDate.of(2024, 5, 1));
                kot.addGame(rocketLeague, LocalDate.of(2024, 6, 12));
                anna.addGame(hades, LocalDate.of(2024, 8, 2));
                anna.addGame(celeste, LocalDate.of(2024, 9, 10));
                marek.addGame(left4Dead2, LocalDate.of(2024, 10, 14));
                marek.addGame(rocketLeague, LocalDate.of(2024, 11, 5));
                ola.addGame(portal, LocalDate.of(2025, 1, 18));
                ola.addGame(mafia, LocalDate.of(2025, 2, 9));

                Developer indieStudio = new Developer("Indie Studio");
                Developer classicWorks = new Developer("Classic Works");
                Developer onlineArena = new Developer("Online Arena");

                indieStudio.addGame(hades);
                indieStudio.addGame(celeste);
                classicWorks.addGame(portal);
                classicWorks.addGame(mafia);
                classicWorks.addGame(stardewValley);
                onlineArena.addGame(rocketLeague);
                onlineArena.addGame(left4Dead2);

                hades.createReview("Fast combat and a strong progression loop.");
                hades.createReview("Great replay value.");
                hades.createReview("The soundtrack makes every run feel intense.");
                hades.createReview("Characters are memorable and well written.");
                hades.createReview("Combat stays readable even during chaotic fights.");

                celeste.createReview("Challenging, but very rewarding.");
                celeste.createReview("Precise controls make difficult sections feel fair.");
                celeste.createReview("A strong story hidden inside a platform game.");
                celeste.createReview("Great level design for players who enjoy mastery.");

                portal.createReview("Smart puzzles and memorable atmosphere.");
                portal.createReview("Short, focused, and very clever.");
                portal.createReview("The portal mechanic is easy to understand and hard to master.");
                portal.createReview("Excellent pacing from start to finish.");

                stardewValley.createReview("Relaxing and easy to return to.");
                stardewValley.createReview("There is always one more thing to do.");
                stardewValley.createReview("Good balance between farming, mining, and social systems.");
                stardewValley.createReview("Co-op makes the farm feel alive.");

                rocketLeague.createReview("Best with friends.");
                rocketLeague.createReview("Simple idea, very high skill ceiling.");
                rocketLeague.createReview("Matches are short and energetic.");
                rocketLeague.createReview("Competitive mode is easy to watch and understand.");

                left4Dead2.createReview("Very good cooperative gameplay.");
                left4Dead2.createReview("Teamwork matters in every campaign.");
                left4Dead2.createReview("Still fun because every run can play differently.");
                left4Dead2.createReview("Great game for demonstrating multiplayer-oriented design.");

                ObjectPlus.saveExtent();
            }

            new PurchaseAdminFrame().setVisible(true);
        });
    }
}
