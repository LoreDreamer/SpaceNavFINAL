package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;

public class Satellite extends Obstacle 
{ 
    private float scale; // Tamaño del satélite
    private int movimiento;
    private int bulletHitCount; // Contador de impactos de bala

    // Constructor de la clase Satellite
    public Satellite(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
    	
        super(x, y, size, xSpeed, ySpeed, tx);
        
        Random rdm = new Random();
        
        if (rdm.nextInt(2) % 2 == 0)
        	movimiento = 0;
        else
        	movimiento = 1;
        
        this.bulletHitCount = 0; 
    }

    // Getters y setters para el encapsulamiento de datos
    public float getScale() {
        return scale;
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
    	
    	if (movimiento == 0)
    		setXSpeed(0);
    	else
    		setYSpeed(0);
    	
        // Update position based on speed
        setX(getX() + getXSpeed());
        setY(getY() + getYSpeed());

        // Screen dimensions
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        if (getX() < 0 || getX() + getSpr().getWidth() > screenWidth) {
            setXSpeed(-getXSpeed());
        }
        if (getY() < 0 || getY() + getSpr().getHeight() > screenHeight) {
            setYSpeed(-getYSpeed());
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
    	
        getSpr().draw(batch);
        
    }

    // Método para verificar la colisión con otro obstáculo
    @Override
    public void checkCollision(Obstacle b2) {
    	
        if (this.getSpr().getBoundingRectangle().overlaps(b2.getSpr().getBoundingRectangle())) {

            int tempXSpeed = this.getXSpeed();
            int tempYSpeed = this.getYSpeed();

            int newXSpeed = Math.max(1, Math.abs(b2.getXSpeed() / 4)) * (b2.getXSpeed() < 0 ? -1 : 1);
            int newYSpeed = Math.max(1, Math.abs(b2.getYSpeed() / 4)) * (b2.getYSpeed() < 0 ? -1 : 1);

            this.setXSpeed(newXSpeed);
            this.setYSpeed(newYSpeed);

            b2.setXSpeed(Math.max(1, Math.abs(tempXSpeed / 4)) * (tempXSpeed < 0 ? -1 : 1));
            b2.setYSpeed(Math.max(1, Math.abs(tempYSpeed / 4)) * (tempYSpeed < 0 ? -1 : 1));

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