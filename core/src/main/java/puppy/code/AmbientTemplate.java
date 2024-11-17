package puppy.code;

import java.util.ArrayList;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*dejé aqui lo de jugador porque la justificacion del template es que si a futuro se quieren implementar mas y diferentes
 ambientes con otro tipo de items y obstaculos se pueda crear otra subclase que los implemente, de esa manera esta clase gestiona
 los elementos que siempre se usan, es decir la nave y las balas, y por el contrario los diferentes tipos de elementos de los
  ambientes que se puedan craer (como si fueran diferentes niveles) se crean en las subclases
  
  RESUMEN: si a futuro queremos crear otros tipos de ambientes (con diferentes obstaculos e items de los que tenemos), esto es super util porque
  solo creamos clases que extiendan de esta y listo (obviamente sobreescribiendo los métodos necesarios)
 
*/ 

public abstract class AmbientTemplate 
{
	private Player jugador;
	private Sound sonidoExplocion;
	private int vidas;
	
	public AmbientTemplate(int ronda, int vidas, int score, Sound sonidoExplocion) {
		this.vidas = vidas;
		this.sonidoExplocion = sonidoExplocion;
		this.jugador = new Player();
	}
	
	public final void inicializarJuego() //secuencia de pasos para crear los elementos necesarios del juego
	{
        crearNave(); //método concreto
        crearObstaculos(); //metodo abstracto a implementar en las subclases
        crearItems(); //metodo abstracto a implementar en las subclases
    }
	
	public final void dibujarElementos(SpriteBatch batch, PantallaJuego game) //secuencia de pasos para dibujar los elementos del juego
	{
        drawNave(batch, game);   //método concreto
        drawBullets(batch);       //método concreto
        dibujarObstaculos(batch);  //metodo abstracto a implementar en las subclases
        dibujarItems(batch);  //metodo abstracto a implementar en las subclases
    }
	
	// Métodos concretos
	
	public void drawNave(SpriteBatch batch, PantallaJuego game) {
		jugador.drawNave(batch, game);
	}
	
	public void crearNave() {
		jugador.createNave(vidas);
	}
	
	public boolean naveHerida() {
		return jugador.naveHerida();
	}
	
	public int vidasNave() {
		return jugador.vidasNave();
	}

	public boolean naveDestruida() {
		return jugador.naveDestruida();
	}

	public void drawBullets(SpriteBatch batch) {
		jugador.drawBullets(batch);
	}

	public void actualizaBalas(ArrayList<Bullet> balas) {
		jugador.setBalas(balas);
	}

	public void disparo(SpriteBatch batch) {
		jugador.disparo(batch);
	}
	
	
	public Player getJugador() {
		return jugador;
	}
	
	public Sound getSonidoExplocion()
	{
		return sonidoExplocion;
	}
	
	// Métodos abstractos a implemengtar en las subclases
    protected abstract void crearObstaculos();
    protected abstract void crearItems();
    protected abstract void dibujarObstaculos(SpriteBatch batch);
    protected abstract void dibujarItems(SpriteBatch batch);
}