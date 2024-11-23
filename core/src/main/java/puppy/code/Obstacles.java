package puppy.code;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;


public class Obstacles { // Clase que contiene todos los obstaculos dentro del juego y los maneja
	
	private int velXAsteroides;
    private int velYAsteroides;
    private int cantSatelites;
    
    private int velXSatelites;
    private int velYSatelites;
    private int cantAsteroides; 
    
    private ArrayList<Obstacle> obstaculos;
   
    public Obstacles(int velXAsteroides, int velYAsteroides, int cantAsteroides) {
		
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		
		this.velXSatelites = velXAsteroides / 3;
		this.velYSatelites = velYAsteroides / 3;
		
		this.cantAsteroides = cantAsteroides;
		this.cantSatelites = cantAsteroides / 3;
		
		obstaculos = new ArrayList<>(); // Lista de obstaculos
		
	}
	
	public void createObstacles() {
		
		Random r = new Random();
		
		for (int i = 0; i < (cantAsteroides - cantSatelites); i++) {
			
			Obstacle asteroide = new Ball2(
                    r.nextInt((int) Gdx.graphics.getWidth()), // Posición X aleatoria
                    50 + r.nextInt((int) Gdx.graphics.getHeight() - 50), // Posición Y aleatoria
                    45 + r.nextInt(16),
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
                    45 + ra.nextInt(16),
                    velXSatelites + ra.nextInt(4), // Velocidad X aleatoria
                    velYSatelites + ra.nextInt(4), // Velocidad Y aleatoria
                    new Texture(Gdx.files.internal("Satelite5.png"))); // Textura del satélite

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
    	if (obstaculos.isEmpty()) return false;
    	return true;
    }
    
    public void increaseDifficulty() {
    	 	
        increaseVelocity();
        increaseQuantity();
        
    }
    
    private void increaseVelocity() {
    	
    	this.velXAsteroides += 1;
    	this.velYAsteroides += 1;
    	
    	this.velXAsteroides += 1;
    	this.velYSatelites += 1;
    }
    
    private void increaseQuantity() {
    	
    	Random random = new Random();
    	int randomNumber = random.nextInt(3) + 1;
    	
    	if (randomNumber == 1)
    		this.cantSatelites++;

    	this.cantAsteroides += 2;
    
    }
    
    public void handleCollisions(Player jugador, Sound sonidoExplocion) {
    	
    	
    	for (int i = 0; i < obstaculos.size(); i++) 
        {
            if (jugador.getNave().checkCollision(obstaculos.get(i))) 
            {
            	sonidoExplocion.play(); // Reproduce sonido de explosión
            	jugador.getNave().setVidas(jugador.getNave().getVidas()); // Reduce las vidas de la nave
                obstaculos.remove(i); // Remueve el asteroide
                i--; // Ajusta el índice
            }
        }
    	
    	for (int i = 0; i < obstaculos.size(); i++) {
    		Obstacle ball1 = obstaculos.get(i);
    		for (int j = 0; j < obstaculos.size(); j++) {
    			Obstacle ball2 = obstaculos.get(j);
    			if (i < j) {
    				ball1.checkCollision(ball2);
    			}
    		}
    	}
    	
    }
    	
    public int handleBulletCollisions(Player jugador, Sound sonidoExplocion) {
    	
    	int score = 0;
        for (int i = 0; i < jugador.getBalas().size(); i++) 
        {
            Bullet b = jugador.getBalas().get(i);
            b.update(); // Actualiza la posición de la bala
            score += checkBullet(b, i, sonidoExplocion); // Verifica colisiones de la bala
            jugador.removeDestroyedBullet(i); // Remueve balas destruidas
        }
        return score;
        
    }
    
    private int checkBullet(Bullet b, int bulletIndex, Sound sonidoExplocion) {
    	
    	int score = 0;
        for (int j = 0; j < obstaculos.size(); j++)  //recorro el array
        {
        	Obstacle obs = obstaculos.get(j);
            if (b.checkCollision(obs)) 
            {
            	sonidoExplocion.play(); // Reproduce sonido de explosión
                if(obs.hitByBullet())
                {
                	score += 10; // Incrementa la puntuación
                    obstaculos.remove(j); // Remueve el asteroide
                }
                break; // Sale del bucle tras una colisión
            }
        }
        return score;
        
    }  
}
