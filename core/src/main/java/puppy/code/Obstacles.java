package puppy.code;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Obstacles {
	
	private int velXAsteroides; // Velocidad en el eje X de los asteroides
    private int velYAsteroides; // Velocidad en el eje Y de los asteroides
    private int cantSatelites; // Cantidad de asteroides en juego
    private int velXSatelites;
    private int velYSatelites;
    private int cantAsteroides; 
    
    private ArrayList<Obstacle> obstaculos;
   
    public Obstacles(int velXAsteroides, int velYAsteroides, int cantAsteroides) {
		
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		
		this.velXSatelites = velXAsteroides/3;
		this.velYSatelites = velYAsteroides/3;
		
		this.cantAsteroides = cantAsteroides;
		this.cantSatelites = cantAsteroides/3;
		
		obstaculos = new ArrayList<>(); // Lista de obstaculos
		
	}
	
	public void createObstacles() {
		
		Random r = new Random();
		
		for (int i = 0; i < (cantAsteroides - cantSatelites); i++) {
			
			Obstacle asteroide = new Ball2(
                    r.nextInt((int) Gdx.graphics.getWidth()), // Posición X aleatoria
                    50 + r.nextInt((int) Gdx.graphics.getHeight() - 50), // Posición Y aleatoria
                    20 + r.nextInt(10), // Tamaño aleatorio
                    velXAsteroides + r.nextInt(4), // Velocidad X aleatoria
                    velYAsteroides + r.nextInt(4), // Velocidad Y aleatoria
                    new Texture(Gdx.files.internal("aGreyMedium4.png"))); // Textura del asteroide

            obstaculos.add(asteroide); // Agrega el asteroide a la lista
			
		}
		
		Random ra = new Random();
		
		for (int k = 0; k < cantSatelites; k++) {
            Obstacle satelite = new Satellite(
                    ra.nextInt((int) Gdx.graphics.getWidth()), // Posición X aleatoria
                    50 + ra.nextInt((int) Gdx.graphics.getHeight() - 50), // Posición Y aleatoria
                    20 + ra.nextInt(10), // Tamaño aleatorio
                    velXSatelites + ra.nextInt(4), // Velocidad X aleatoria
                    velYSatelites + ra.nextInt(4), // Velocidad Y aleatoria
                    new Texture(Gdx.files.internal("sat.png"))); // Textura del satélite

            obstaculos.add(satelite); // Agrega el satélite a la lista
        }
	}
	
	public void updateObstaculos() {
        for(Obstacle obj : obstaculos) {
            obj.move(); // Llama al método update de cada satélite
        }
    }
    
    public void drawObstaculos(SpriteBatch batch) {
        for (Obstacle obs : obstaculos) {
            obs.draw(batch); // Dibuja cada obstaculo
        }
    }
       
    public boolean hayObstaculos() {
    	if(obstaculos.isEmpty()) return false;
    	return true;
    }
       
    public ArrayList<Obstacle> getObstaculos(){
    	return obstaculos;
    }
    
    public void increaseDifficulty() {
    	 	
        increaseVelocity();
        increaseQuantity();
    }
    
    private void increaseVelocity() {
    	
    	this.velXAsteroides += 0.5;
    	this.velYAsteroides += 0.5;
    	
    	this.velXAsteroides += 0.3;
    	this.velYSatelites += 0.3;
    }
    
    private void increaseQuantity() {
    	
    	Random random = new Random();
    	int randomNumber = random.nextInt(3) + 1;
    	
    	if (randomNumber == 1)
    		this.cantSatelites++;

    	this.cantAsteroides += 2;
    
    }
      
}
