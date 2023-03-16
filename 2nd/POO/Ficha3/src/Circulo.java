public class Circulo {
    private int x;
    private int y;

    public Circulo(){
        this.x = 0;
        this.y = 0;
    }

    public Circulo(int cx, int cy){
        this.x = cx;
        this.y = cy;
    }

    public Circulo(Circulo umCirculo){
        this.x = umCirculo.getX();
        this.y = umCirculo.getY();
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setX (int novoX ) {
        this.x = novoX;
    }

    public void setY(int novoY){
        this.y = novoY;
    }

    public void deslocamento(int deltaX, int deltaY){
        this.x += deltaX;
        this.y += deltaY;
    }



}
