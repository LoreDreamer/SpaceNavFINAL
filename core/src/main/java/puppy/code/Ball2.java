package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ball2 extends Obstacle implements Movement {
    
    // Atributos privados para encapsular los datos de posición y velocidad
    private int x;        // Coordenada x de la posición de Ball2
    private int y;        // Coordenada y de la posición de Ball2
    private int xSpeed;   // Velocidad en el eje x
    private int ySpeed;   // Velocidad en el eje y
    private Sprite spr;   // Sprite que representa visualmente a Ball2

    // Constructor de Ball2: inicializa posición, tamaño, velocidad y textura (sprite)
    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        super(x, y, size, xSpeed, ySpeed, tx); // Llama al constructor de la superclase (Obstacle)
        this.x = x;           // Asigna el valor de x a la posición inicial en el eje x
        this.y = y;           // Asigna el valor de y a la posición inicial en el eje y
        this.xSpeed = xSpeed; // Establece la velocidad en el eje x
        this.ySpeed = ySpeed; // Establece la velocidad en el eje y
        this.spr = getSpr();  // Obtiene el sprite de la superclase para Ball2
    }
    
    // Getters y setters para encapsulamiento de datos: controlan el acceso y modificación de x, y, xSpeed y ySpeed
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;                // Asigna el valor de x a la posición en el eje x
        spr.setPosition(x, y);     // Actualiza la posición del sprite con la nueva posición x e y
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;                // Asigna el valor de y a la posición en el eje y
        spr.setPosition(x, y);     // Actualiza la posición del sprite con la nueva posición x e y
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;      // Asigna la velocidad en el eje x
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;      // Asigna la velocidad en el eje y
    }

    // Método move(): actualiza la posición de Ball2 según sus velocidades
    @Override
    public void move() {
        // Cambia las coordenadas x e y de Ball2 de acuerdo a sus velocidades actuales
        setX(getX() + getXSpeed() + 1);
        setY(getY() + getYSpeed() + 1);

        // Rebotar cuando el borde del sprite toca el borde de la pantalla
        if (x < 0) {
            setX(0);
            setXSpeed(getXSpeed() * -1);
        } else if (x + spr.getWidth() > Gdx.graphics.getWidth()) {
            setX(Gdx.graphics.getWidth() - (int) spr.getWidth());
            setXSpeed(getXSpeed() * -1);
        }

        if (y < 0) {
            setY(0);
            setYSpeed(getYSpeed() * -1);
        } else if (y + spr.getHeight() > Gdx.graphics.getHeight()) {
            setY(Gdx.graphics.getHeight() - (int) spr.getHeight());
            setYSpeed(getYSpeed() * -1);
        }

        // Actualiza la posición del sprite en pantalla después de cada movimiento
        spr.setPosition(x, y);
    }


    // Método getArea(): devuelve el área de colisión (bounding rectangle) de Ball2
    @Override
    public Rectangle getArea() {
        return spr.getBoundingRectangle(); // Devuelve el área rectangular del sprite
    }

    // Método draw(): dibuja el sprite de Ball2 en la pantalla utilizando un SpriteBatch
    @Override
    public void draw(SpriteBatch batch) {
        spr.draw(batch); // Dibuja el sprite en el lote de dibujo proporcionado
    }

    // Método checkCollision(): verifica la colisión con otro Obstacle y ajusta la velocidad si colisiona
    @Override
    public void checkCollision(Obstacle b2) {
        // Verifica si el área de colisión de Ball2 se superpone con el área de colisión de b2
        if (spr.getBoundingRectangle().overlaps(b2.getSpr().getBoundingRectangle())) { // Rebote
            // Si Ball2 o el otro Obstacle (b2) no tienen velocidad en x, establece una velocidad inicial
            if (getXSpeed() == 0) setXSpeed(getXSpeed() + b2.getXSpeed() / 2);
            if (b2.getXSpeed() == 0) b2.setXSpeed(b2.getXSpeed() + getXSpeed() / 2);

            // Invertir la velocidad en x para ambos objetos
            setXSpeed(-getXSpeed());
            b2.setXSpeed(-b2.getXSpeed());

            // Si Ball2 o el otro Obstacle (b2) no tienen velocidad en y, establece una velocidad inicial
            if (getYSpeed() == 0) setYSpeed(getYSpeed() + b2.getYSpeed() / 2);
            if (b2.getYSpeed() == 0) b2.setYSpeed(b2.getYSpeed() + getYSpeed() / 2);

            // Invertir la velocidad en y para ambos objetos
            setYSpeed(-getYSpeed());
            b2.setYSpeed(-b2.getYSpeed());
        }
    }
    
    @Override
    public boolean hitByBullet() {
    	return true;
    }
}
