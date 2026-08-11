public class Review extends ObjectPlus{
    private Game game;
    private String content;

    public Review(Game game, String content){
        if(game!=null){
            this.game = game;
        } else {
            throw new IllegalArgumentException("Game cannot be null");
        }
        setContent(content);
        game.addReview(this);
        addToExtent();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if(content==null || content.trim().isEmpty()){
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        this.content = content;
    }

    public Game getGame(){
        return game;
    }

    public void destruct(){
        game = null;
        removeFromExtent();
    }

    @Override
    public String toString() {
        return "Game: " + game.getName() + "{"+content+"}";
    }
}
