package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

// Clase abstracta que implementa la interfaz Movement
public abstract class Obstacle implements Movement {
    
    // Atributos privados para encapsular los datos de posición y velocidad
    private int x;          // Coordenada x de la posición del Obstacle
    private int y;          // Coordenada y de la posición del Obstacle
    private int xSpeed;     // Velocidad en el eje x
    private int ySpeed;     // Velocidad en el eje y
    private Sprite spr;     // Sprite que representa visualmente al Obstacle

    // Constructor de Obstacle: inicializa posición, tamaño, velocidad y textura (sprite)
    public Obstacle(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        spr = new Sprite(tx); // Crea un nuevo sprite utilizando la textura proporcionada
        this.x = x;           // Asigna el valor de x a la posición inicial en el eje x

        // Validar que el borde de la esfera no quede fuera de la pantalla
        if (x - size < 0) this.x = x + size; // Si la esfera se sale por la izquierda, la posiciona a la derecha
        if (x + size > Gdx.graphics.getWidth()) this.x = x - size; // Si se sale por la derecha, la posiciona a la izquierda
        
        this.y = y;           // Asigna el valor de y a la posición inicial en el eje y
        // Validar que el borde de la esfera no quede fuera de la pantalla
        if (y - size < 0) this.y = y + size; // Si la esfera se sale por la parte superior, la posiciona por debajo
        if (y + size > Gdx.graphics.getHeight()) this.y = y - size; // Si se sale por la parte inferior, la posiciona por encima

        spr.setPosition(x, y); // Establece la posición inicial del sprite
        this.xSpeed = xSpeed;  // Establece la velocidad en el eje x
        this.ySpeed = ySpeed;  // Establece la velocidad en el eje y
    }

    // Método implementado de la interfaz Movement para mover el Obstacle
    public void move() {
        // Obtiene las coordenadas actuales
        int x = getX();
        int y = getY();
        Sprite spr = getSpr(); // Obtiene el sprite actual

        // Actualiza las posiciones basadas en la velocidad
        setX(x + getXSpeed());
        setY(y + getYSpeed());

        // Verifica si el Obstacle ha tocado los bordes de la pantalla
        // Si se sale del borde izquierdo o derecho, invierte la dirección en x
        if (x + getXSpeed() < 0 || x + getXSpeed() + spr.getWidth() > Gdx.graphics.getWidth())
            setXSpeed(getXSpeed() * -1);
        // Si se sale del borde superior o inferior, invierte la dirección en y
        if (y + getYSpeed() < 0 || y + getYSpeed() + spr.getHeight() > Gdx.graphics.getHeight())
            setYSpeed(getYSpeed() * -1);

        // Actualiza la posición del sprite después de cada movimiento
        spr.setPosition(x, y);
    }
    
    public void checkCollision(Obstacle b2) {
        
        Sprite spr = getSpr();

        if (spr.getBoundingRectangle().overlaps(b2.getSpr().getBoundingRectangle())) {
            // Rebote
            if (getXSpeed() == 0) setXSpeed(getXSpeed() + b2.getXSpeed() / 2);
            if (b2.getXSpeed() == 0) b2.setXSpeed(b2.getXSpeed() + getXSpeed() / 2);

            setXSpeed(-getXSpeed());
            b2.setXSpeed(-b2.getXSpeed());

            if (getYSpeed() == 0) setYSpeed(getYSpeed() + b2.getYSpeed() / 2);
            if (b2.getYSpeed() == 0) b2.setYSpeed(b2.getYSpeed() + getYSpeed() / 2);
            setYSpeed(-getYSpeed());
            b2.setYSpeed(-b2.getYSpeed());
        }
    }

    // Métodos abstractos que deben implementarse en las subclases
    public abstract Rectangle getArea(); // Devuelve el área de colisión del Obstacle
    public abstract void draw(SpriteBatch batch); // Dibuja el Obstacle en la pantalla
    public abstract boolean hitByBullet(); // Retorna si se debe destruir el obstaculo o no

    // Getters y setters para el encapsulamiento de datos: controlan el acceso y modificación de x, y, xSpeed, ySpeed y spr
    public int getX() {
        return x; // Devuelve la posición en el eje x
    }

    public void setX(int x) {
        this.x = x; // Asigna el valor de x a la posición en el eje x
    }

    public int getY() {
        return y; // Devuelve la posición en el eje y
    }

    public void setY(int y) {
        this.y = y; // Asigna el valor de y a la posición en el eje y
    }

    public int getXSpeed() {
        return xSpeed; // Devuelve la velocidad en el eje x
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed; // Asigna la velocidad en el eje x
    }

    public int getYSpeed() {
        return ySpeed; // Devuelve la velocidad en el eje y
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed; // Asigna la velocidad en el eje y
    }

    public Sprite getSpr() {
        return spr; // Devuelve el sprite actual
    }

    public void setSpr(Sprite spr) {
        this.spr = spr; // Asigna un nuevo sprite al Obstacle
    }
}
