package puppy.code;

import com.badlogic.gdx.graphics.g2d.Sprite;

// Clase que define objetos que podran ser tomados por el jugador para recibir beneficios o perjuicios
public abstract class Objeto {
	private int x;
    private int y;
    private int score;
    private int effect;
    private float time;
    private Sprite spr;
    
    public Objeto (int x, int y, int score, int effect, float time, Sprite spr) {
    	this.x = x;
    	this.y = y;
    	this.score = score;
    	this.effect = effect;
    	this.time = time;
    	this.spr = spr;
    } 
    
    // Actualiza el puntaje del jugador según el objeto que tome
    public abstract int updateScore(int newScore); 
    
    // Le aplica un efecto al jugador según el objeto que tome
    public abstract double updateEffect(int score, Nave4 nave);

    

	public int getX() {
		return x;
	}



	public void setX(int x) {
		this.x = x;
	}



	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}



	public int getScore() {
		return score;
	}



	public void setScore(int score) {
		this.score = score;
	}



	public int getEffect() {
		return effect;
	}



	public void setEffect(int effect) {
		this.effect = effect;
	}

	public float getTime() {
		return time;
	}



	public void setTime(int time) {
		this.time = time;
	}

	public Sprite getSpr() {
		return spr;
	}



	public void setSpr(Sprite spr) {
		this.spr = spr;
	}
}