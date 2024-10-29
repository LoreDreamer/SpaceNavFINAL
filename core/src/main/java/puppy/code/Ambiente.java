package puppy.code;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;

public class Ambiente {

	private Obstaculos obstaculos;
	private Jugador jugador;
	private Sound sonidoExplocion;
	private int vidas;
	
	public Ambiente(SpaceNavigation game, int ronda, int vidas, int score,  
            int velXAsteroides, int velYAsteroides, int cantidad, Sound sonidoExplocion, SpriteBatch batch)
	{
		this.vidas = vidas;
		this.sonidoExplocion = sonidoExplocion;
		obstaculos = new Obstaculos(velXAsteroides, velXAsteroides, cantidad, sonidoExplocion);
		jugador = new Jugador(batch, vidas);	
	}
	
	public void inicializar()
	{
		obstaculos.createObstacles();
		jugador.createNave(vidas);
	}
	
	public void crearObstaculos() {
		obstaculos.createObstacles();
	}
	
	public void updateObstaculos()
	{
		obstaculos.updateObstaculos();
	}
	
	public void drawObstaculos(SpriteBatch batch)
	{
		obstaculos.drawObstaculos(batch);
	}
	
	public boolean hayObstaculos() {
		return obstaculos.hayObstaculos();
	}
	
    // Método para manejar las colisiones entre asteroides
    public void handleObstacleCollisions() 
    {
        for (int i = 0; i < obstaculos.getObstaculos().size(); i++) 
        {
            if (jugador.getNave().checkCollision(obstaculos.getObstaculos().get(i))) 
            {
            	sonidoExplocion.play(); // Reproduce sonido de explosión
            	jugador.getNave().setVidas(jugador.getNave().getVidas()); // Reduce las vidas de la nave
                obstaculos.getObstaculos().remove(i); // Remueve el asteroide
                i--; // Ajusta el índice
            }
        }
    }
    
    
	// Método para verificar las colisiones de las balas con asteroides y satélites
    public int checkBulletCollisions(Bullet b, int bulletIndex) {
        // Verifica colisiones
    	int score = 0;
        for (int j = 0; j < obstaculos.getObstaculos().size(); j++)  //recorro el array
        {
        	Obstacle obs = obstaculos.getObstaculos().get(j);
            if (b.checkCollision(obs)) 
            {
            	sonidoExplocion.play(); // Reproduce sonido de explosión
                if(obs.hitByBullet())
                {
                	score += 10; // Incrementa la puntuación
                    obstaculos.getObstaculos().remove(j); // Remueve el asteroide
                }
                break; // Sale del bucle tras una colisión
            }
        }
        return score;
    }
    
    
    // Método para manejar las colisiones de las balas
    public int handleCollisions() 
    {
    	int score = 0;
        for (int i = 0; i < jugador.getBalas().size(); i++) 
        {
            Bullet b = jugador.getBalas().get(i);
            b.update(); // Actualiza la posición de la bala
            score = checkBulletCollisions(b, i); // Verifica colisiones de la bala
            jugador.removeDestroyedBullet(i); // Remueve balas destruidas
        }
        return score;
    }
	
    public void proceedToNextLevel(Music gameMusic, int velXAsteroides, int velYAsteroides, int cantAsteroides, int ronda, SpaceNavigation game, int score) {
        gameMusic.stop(); // Detener la música
        ronda++; // Incrementa la ronda

        // Crear una nueva instancia de PantallaJuego con los valores actualizados
        PantallaJuego nuevaPantalla = new PantallaJuego(
            game,
            ronda,
            vidas,
            score,
            velXAsteroides + (ronda/5),  // Aumenta la velocidad según la ronda, por ejemplo
            velYAsteroides + (ronda/5),  // Aumenta la velocidad según la ronda
            cantAsteroides + ronda * 2  // Aumenta la cantidad de asteroides
        );
        
        // Cambia a la nueva pantalla
        game.setScreen(nuevaPantalla);
    }

	public int vidasNave()
	{
		return jugador.vidasNave();
	}
	
	public boolean naveDestruida()
	{
		return jugador.naveDestruida();
	}
	
    public void drawBullets(SpriteBatch batch) {
    	jugador.drawBullets(batch);
    }
    
    public void drawNave(SpriteBatch batch, PantallaJuego juego)
    {
    	jugador.drawNave(batch, juego);
    }
    
    public boolean naveHerida()
    {
    	return jugador.naveHerida();
    }
    
    public void actualizaBalas(ArrayList<Bullet> balas) {
    	jugador.setBalas(balas);
    }
    
    public void disparo(SpriteBatch batch) {
    	jugador.disparo(batch);
    }

}
