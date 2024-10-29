package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;

public class Satellite extends Obstacle implements Movement 
{
	private Sprite spr;  
    private float scale; // Tamaño del satélite
    private int bulletHitCount; // Contador de impactos de bala
    private static final int TARGET_SIZE_PX = 40; // Tamaño deseado en píxeles (1 cm)

    // Constructor de la clase Satellite
    public Satellite(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        super(x, y, size, xSpeed, ySpeed, tx);
        
        // Escala para ajustar al tamaño deseado
        this.scale = (float) TARGET_SIZE_PX / size; 
        getSpr().setSize(getSpr().getWidth() * scale, getSpr().getHeight() * scale);
        this.bulletHitCount = 0; 
    }

    // Getters y setters para el encapsulamiento de datos
    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        // Aplica el cambio de escala al sprite
        getSpr().setSize(TARGET_SIZE_PX * scale, TARGET_SIZE_PX * scale);
    }

    public int getBulletHitCount() {
        return bulletHitCount;
    }

    public void setBulletHitCount(int bulletHitCount) {
        this.bulletHitCount = bulletHitCount;
    }

    // Método de movimiento que implementa el comportamiento de la interfaz Movement
    @Override
    public void move() {
        Random random = new Random();
        int randomNumber = random.nextInt(100); // Genera un número aleatorio entre 0 y 99
        
        int x = getX();
        int y = getY();
        Sprite spr = getSpr();

        // Determinar si el número es par o impar
        if (randomNumber % 2 == 0) { // Par: mover de arriba a abajo
            setY(y + getYSpeed()); // Mover verticalmente
        } else { // Impar: mover de lado a lado
            setX(x + getXSpeed()); // Mover horizontalmente
        }

        // Rebotar en los bordes de la pantalla
        if (x + getXSpeed() < 0 || x + getXSpeed() + spr.getWidth() > Gdx.graphics.getWidth()) {
            setXSpeed(getXSpeed() * -1);
        }
        if (y + getYSpeed() < 0 || y + getYSpeed() + spr.getHeight() > Gdx.graphics.getHeight()) {
            setYSpeed(getYSpeed() * -1);
        }

        spr.setPosition(x, y);
    }

    // Método para obtener el área del satélite
    @Override
    public Rectangle getArea() {
        return getSpr().getBoundingRectangle();
    }

    // Método para dibujar el satélite
    @Override
    public void draw(SpriteBatch batch) {
        getSpr().setSize(TARGET_SIZE_PX * scale, TARGET_SIZE_PX * scale); 
        getSpr().setPosition(getX(), getY()); // Establece la posición donde se debe dibujar
        getSpr().draw(batch); // Dibuja el sprite
    }

    // Método para verificar la colisión con otro obstáculo
    @Override
    public void checkCollision(Obstacle s2) {
        Sprite spr = getSpr();

        if (spr.getBoundingRectangle().overlaps(s2.getSpr().getBoundingRectangle())) {
            // Rebote
            if (getXSpeed() == 0) setXSpeed(getXSpeed() + s2.getXSpeed() / 2);
            if (s2.getXSpeed() == 0) s2.setXSpeed(s2.getXSpeed() + getXSpeed() / 2);

            setXSpeed(-getXSpeed());
            s2.setXSpeed(-s2.getXSpeed());

            if (getYSpeed() == 0) setYSpeed(getYSpeed() + s2.getYSpeed() / 2);
            if (s2.getYSpeed() == 0) s2.setYSpeed(s2.getYSpeed() + getYSpeed() / 2);
            setYSpeed(-getYSpeed());
            s2.setYSpeed(-s2.getYSpeed());
        }
    }
    
    // Método para registrar un impacto de bala
    @Override
    public boolean hitByBullet() {
        bulletHitCount++; // Aumenta el contador cuando es golpeado por una bala
        return bulletHitCount >= 2; // Si ha sido golpeado dos veces o más, debe ser destruido
    }
}