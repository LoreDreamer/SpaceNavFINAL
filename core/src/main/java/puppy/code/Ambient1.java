package puppy.code;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;

public class Ambient1 extends AmbientTemplate{

	private Obstacles obstaculos;
	private ItemFactory items;
	private int vidas; 

	public Ambient1(int ronda, int vidas, int score, Sound explosionSound) 
	{
        super(ronda, vidas, score, explosionSound); // Llama al constructor de la clase base
		obstaculos = new Obstacles(1, 1, 10);
		items = new ItemFactory();
    }
	
	@Override
	protected void crearObstaculos() {
		obstaculos.createObstacles();
	}
	
	@Override
	protected void crearItems() {
		items.inicializarItems();
	}
	
	@Override	
	protected void dibujarObstaculos(SpriteBatch batch) {
		obstaculos.drawObstaculos(batch);
	}
	
	@Override
	protected void dibujarItems(SpriteBatch batch) {
		items.drawItems(batch);
	}
	
	public void updateObstaculos() {
		obstaculos.updateObstaculos();
	}

	public boolean hayObstaculos() {
		return obstaculos.hayObstaculos();
	}

	public void handleObstacleCollisions() {
		obstaculos.handleCollisions(getJugador(), getSonidoExplocion());
	}
	
	public int handleItemCollisions() {
		return items.handleCollisions(getJugador());
	}

	// Método para manejar las colisiones de las balas
	public int handleCollisions() {
		return obstaculos.handleBulletCollisions(getJugador(), getSonidoExplocion());
	}

	public void proceedToNextLevel(Music gameMusic, int ronda, SpaceNavigation game, int score) {
		gameMusic.stop(); // Detener la música
		ronda++; // Incrementa la ronda

		// Crear una nueva instancia de PantallaJuego con los valores actualizados
		PantallaJuego nuevaPantalla = new PantallaJuego(game, ronda, vidasNave(), score);
		
		// Cambia a la nueva pantalla
		game.setScreen(nuevaPantalla);
	}
	
	public void ambienteModificar() {
		obstaculos.increaseDifficulty();
	}
	
	public Obstacles getObstacles() {
		return obstaculos;
	}

}