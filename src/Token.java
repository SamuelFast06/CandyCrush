public class Token {
    private int id;
    private String text;

    public Token(int id, String text){
        this.id = id;
        this.text = text;
    }
    public Token(){

    }

    public int getId(){
        return id;
    }
    public String getText(){
        return text;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }
}
