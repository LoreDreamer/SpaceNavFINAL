package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Elements {
	
	private int x;          
    private int y;          
    private int xSpeed;     
    private int ySpeed;     
    private Sprite spr;   

    private Elements(ElemBuilder builder){
    	
    	this.spr = builder.spr;
    	this.x = builder.x;

        // Validar que el borde de la esfera no quede fuera de la pantalla
        if (x - builder.size < 0) this.x = x + builder.size;
        if (x + builder.size > Gdx.graphics.getWidth()) this.x = x - builder.size;
        
        this.y = builder.y;
        
        // Validar que el borde de la esfera no quede fuera de la pantalla
        if (y - builder.size < 0) this.y = y + builder.size;
        if (y + builder.size > Gdx.graphics.getHeight()) this.y = y - builder.size;
        	
        spr.setPosition(x, y);
        this.xSpeed = builder.xSpeed;
        this.ySpeed = builder.ySpeed;
    }
    
    //Clase interna builder
    
    public static class ElemBuilder
    {
    	//Instancias obligatorias
    	private int x;
        private int y;
        private Sprite spr;
        private int size;
        
        //Instancias opcionales
        private int xSpeed;
        private int ySpeed;
    	private int score;
    	private int effect;
    	private float time;
    	private Texture tx;
        
        public ElemBuilder(int x, int y, Sprite spr)
        {
        	this.x = x;
        	this.y = y;
        	this.spr = spr;
        }
        
        public ElemBuilder addSize(int size)
        {
        	this.size = size;
        	
        	return this;
        }
        
        public ElemBuilder addSpeed(int xSpeed, int ySpeed)
        {
        	this.xSpeed = xSpeed;
        	this.ySpeed = ySpeed;
        	
        	return this;
        }
        
        public ElemBuilder addEffect(int score, int effect, int time, Texture tx)
        {
        	this.score = score;
        	this.effect = effect;
        	this.time = time;
        	this.tx = tx;
        	
        	return this;
        }
        
        public Elements build()
        {
        	return new Elements(this);
        }
        
    }

    public void move() { // Método implementado de la interfaz Movement para mover el Obstacle
    	
        int x = getX();
        int y = getY();
        Sprite spr = getSpr();

        setX(x + getXSpeed());
        setY(y + getYSpeed());

        // Verifica si el Obstacle ha tocado los bordes de la pantalla
        if (x + getXSpeed() < 0 || x + getXSpeed() + spr.getWidth() > Gdx.graphics.getWidth())
            setXSpeed(getXSpeed() * -1);
        if (y + getYSpeed() < 0 || y + getYSpeed() + spr.getHeight() > Gdx.graphics.getHeight())
            setYSpeed(getYSpeed() * -1);

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
