package puppy.code;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Obstaculos {
	
	private int velXAsteroides; // Velocidad en el eje X de los asteroides
    private int velYAsteroides; // Velocidad en el eje Y de los asteroides
    private int cantSatelites; // Cantidad de asteroides en juego
    private int velXSatelites;
    private int velYSatelites;
    private int cantAsteroides; 
    
    private ArrayList<Obstacle> obstaculos;
   
    public Obstaculos(int velXAsteroides, int velYAsteroides, int cantAsteroides, int cantSatelites) {
		
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		
		this.velXAsteroides = velXAsteroides/2;
		this.velYSatelites = velYAsteroides/2;
		
		this.cantAsteroides = cantAsteroides;
		this.cantSatelites = cantSatelites/2;
		
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
		
		for (int i = 0; i < cantSatelites; i++) {
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
	
	private void updateObstaculos() {
        for(Obstacle obj : obstaculos) {
            obj.move(); // Llama al método update de cada satélite
        }
    }
    
    private void drawObstaculos(SpriteBatch batch) {
        for (Obstacle obj : obstaculos) {
            obj.draw(batch); // Dibuja cada asteroide
        }
    }
    
       private void handleObstacleCollisions(Nave nave) 
    {
        for (int i = 0; i < obstaculos.size(); i++) 
        {
            if (nave.checkCollision(obstaculos.get(i))) 
            {
                explosionSound.play(); // Reproduce sonido de explosión
                nave.setVidas(nave.getVidas()); // Reduce las vidas de la nave
                obstaculos.remove(i); // Remueve el asteroide
                i--; // Ajusta el índice
            }
        }
    } 
   
}
