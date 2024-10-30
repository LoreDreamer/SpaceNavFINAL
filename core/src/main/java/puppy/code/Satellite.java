package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;

public class Satellite extends Obstacle implements Movement 
{ 
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
        // Update position based on speed
        setX(getX() + getXSpeed());
        setY(getY() + getYSpeed());

        // Screen dimensions
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // Check if the satellite is out of bounds and reflect its movement if necessary
        if (getX() < 0 || getX() + getSpr().getWidth() > screenWidth) {
            setXSpeed(-getXSpeed()); // Reverse direction on X-axis
        }
        if (getY() < 0 || getY() + getSpr().getHeight() > screenHeight) {
            setYSpeed(-getYSpeed()); // Reverse direction on Y-axis
        }

        // Update the position of the sprite
        getSpr().setPosition(getX(), getY());
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
    public void checkCollision(Obstacle b2) {
        if (this.getSpr().getBoundingRectangle().overlaps(b2.getSpr().getBoundingRectangle())) {
            // Satellite specific collision response: reduce speed significantly upon collision
            int tempXSpeed = this.getXSpeed();
            int tempYSpeed = this.getYSpeed();

            // Set new speeds with more reduction for satellites, ensuring they don't go to zero
            int newXSpeed = Math.max(1, Math.abs(b2.getXSpeed() / 3)) * (b2.getXSpeed() < 0 ? -1 : 1);
            int newYSpeed = Math.max(1, Math.abs(b2.getYSpeed() / 3)) * (b2.getYSpeed() < 0 ? -1 : 1);

            this.setXSpeed(newXSpeed);
            this.setYSpeed(newYSpeed);

            // Ensure the other obstacle's speed doesn't become zero
            b2.setXSpeed(Math.max(1, Math.abs(tempXSpeed / 3)) * (tempXSpeed < 0 ? -1 : 1));
            b2.setYSpeed(Math.max(1, Math.abs(tempYSpeed / 3)) * (tempYSpeed < 0 ? -1 : 1));

            // Slightly adjust positions to avoid overlap
            this.setX((int) (this.getX() + this.getXSpeed() * 0.05f));
            this.setY((int) (this.getY() + this.getYSpeed() * 0.05f));
            b2.setX((int) (b2.getX() + b2.getXSpeed() * 0.05f));
            b2.setY((int) (b2.getY() + b2.getYSpeed() * 0.05f));
        }
    }

    
    // Método para registrar un impacto de bala
    @Override
    public boolean hitByBullet() {
        bulletHitCount++; // Aumenta el contador cuando es golpeado por una bala
        return bulletHitCount >= 2; // Si ha sido golpeado dos veces o más, debe ser destruido
    }
}