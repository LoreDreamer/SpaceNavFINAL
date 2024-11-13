package puppy.code;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player 
{

	private Nave4 nave; //objeto nave
	private ArrayList<Bullet> balas; // Lista de balas 
	
	public Player()
	{
		balas = new ArrayList<Bullet>(); 
	}
	
    // Método para crear la nave del jugador
    public void createNave(int vidas)
    {
        nave = Nave4.getNavePlayer(Gdx.graphics.getWidth() / 2 - 50, 30,
                new Texture(Gdx.files.internal("MainShip3.png")),
                Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
                new Texture(Gdx.files.internal("Rocket2.png")),
                Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));
        
        nave.setVidas(vidas); // Establece las vidas de la nave   
    }
    

    // Método para remover balas destruidas
    public void removeDestroyedBullet(int bulletIndex) {
        if (balas.get(bulletIndex).isDestroyed()) {
            balas.remove(bulletIndex); // Remueve la bala de la lista
        }
    }

    // Método para dibujar las balas en pantalla
    public void drawBullets(SpriteBatch batch) 
    {
        for (Bullet b : balas) {
            b.draw(batch); // Dibuja cada bala
        }
    }  
    
    public boolean agregarBala(Bullet bb) {
        return balas.add(bb);
        
    }

    public Nave4 getNave() {
    	return nave;
    }
    
    public int vidasNave() {
    	return nave.getVidas();
    }
    
    public boolean naveDestruida()
    {
    	return nave.estaDestruido();
    }
    
    public boolean naveHerida() {
    	return nave.estaHerido();
    }
    
    public ArrayList<Bullet> getBalas() {
    	return balas;
    }
    
    public void setBalas( ArrayList<Bullet> balas) {
    	this.balas = balas;
    	return;
    }
    
    public void drawNave(SpriteBatch batch, PantallaJuego juego)
    {
    	nave.draw(batch, juego);
    }
    
    public void disparo(SpriteBatch batch) {
    	
    	if (nave.disparo(batch) != null) {
    		agregarBala(nave.disparo(batch));
    	}
    }
}
