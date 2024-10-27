package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Jugador 
{
	private Nave4 nave;
	private Bullet balas;
	
	public Jugador(Nave4 nave)
	{
		
	}
	
/*	
    // Método para crear la nave del jugador
    private void createNave(int vidas) {
        nave = new Nave4(Gdx.graphics.getWidth() / 2 - 50, 30,
                new Texture(Gdx.files.internal("MainShip3.png")),
                Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
                new Texture(Gdx.files.internal("Rocket2.png")),
                Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));
        nave.setVidas(vidas); // Establece las vidas de la nave
    }
    
	// Método para verificar las colisiones de las balas con asteroides y satélites
    private void checkBulletCollisions(Bullet b, int bulletIndex) {
        // Verifica colisiones con asteroides
        for (int j = 0; j < obstaculos.size(); j++)  //recorro el array
        {
        	Obstacle obs = obstaculos.get(j);
            if (b.checkCollision(obs)) 
            {
                explosionSound.play(); // Reproduce sonido de explosión
                if(obs.hitByBullet())
                {
                	score += 10; // Incrementa la puntuación
                    obstaculos.remove(j); // Remueve el asteroide
                }
                break; // Sale del bucle tras una colisión
            }
        }
    }
    
 // Método para manejar las colisiones de las balas
    private void handleCollisions() {
        for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            b.update(); // Actualiza la posición de la bala
            checkBulletCollisions(b, i); // Verifica colisiones de la bala
            removeDestroyedBullet(i); // Remueve balas destruidas
        }
    }
    

    // Método para remover balas destruidas
    private void removeDestroyedBullet(int bulletIndex) {
        if (balas.get(bulletIndex).isDestroyed()) {
            balas.remove(bulletIndex); // Remueve la bala de la lista
        }
    }

    // Método para dibujar las balas en pantalla
    private void drawBullets() {
        for (Bullet b : balas) {
            b.draw(batch); // Dibuja cada bala
        }
    }  
    
    public boolean agregarBala(Bullet bb) {
        return balas.add(bb);
    }
    
    */
}
