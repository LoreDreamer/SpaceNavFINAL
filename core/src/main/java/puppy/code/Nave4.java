package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Nave4 {

    private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    private final float VELOCIDAD_MAXIMA = 3; // Velocidad máxima constante
    
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;

    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
        sonidoHerido = soundChoque;
        this.soundBala = soundBala;
        this.txBala = txBala;
        
        spr = new Sprite(tx);
        spr.setPosition(x, y);
        spr.setBounds(x, y, 45, 45);
    }
    
    public void draw(SpriteBatch batch, PantallaJuego juego) {
        float x = spr.getX();
        float y = spr.getY();
        
        if (!herido) {
            // Movimientos con teclas y velocidad constante
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) xVel = -VELOCIDAD_MAXIMA;
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) xVel = VELOCIDAD_MAXIMA;
            else xVel = 0; // No movimiento horizontal si no se presionan las teclas
            
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) yVel = -VELOCIDAD_MAXIMA;
            else if (Gdx.input.isKeyPressed(Input.Keys.UP)) yVel = VELOCIDAD_MAXIMA;
            else yVel = 0; // No movimiento vertical si no se presionan las teclas

            // Mantener la nave dentro de los bordes de la ventana
            if (x + xVel < 0 || x + xVel + spr.getWidth() > Gdx.graphics.getWidth())
                xVel = 0;
            if (y + yVel < 0 || y + yVel + spr.getHeight() > Gdx.graphics.getHeight())
                yVel = 0;

            // Actualizar la posición de la nave
            spr.setPosition(x + xVel, y + yVel);
            spr.draw(batch);
        } else {
            // Lógica para cuando la nave está herida (temblor)
            spr.setX(spr.getX() + MathUtils.random(-2, 2));
            spr.draw(batch);
            spr.setX(x);
            
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
    }
    
    public Bullet disparo(SpriteBatch batch) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { 
            Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5, spr.getY() + spr.getHeight() - 5, 0, 3, txBala);
            soundBala.play();
            return bala;
        }
        return null;
    }
    
    public boolean checkCollision(Obstacle b) {
        if (!herido && b.getArea().overlaps(spr.getBoundingRectangle())) {
            // Rebote
            if (xVel == 0) xVel += b.getXSpeed() / 2;
            if (b.getXSpeed() == 0) b.setXSpeed(b.getXSpeed() + (int) xVel / 2);
            
            xVel = -xVel;
            b.setXSpeed(-b.getXSpeed());
            
            if (yVel == 0) yVel += b.getYSpeed() / 2;
            if (b.getYSpeed() == 0) b.setYSpeed(b.getYSpeed() + (int) yVel / 2);
            
            yVel = -yVel;
            b.setYSpeed(-b.getYSpeed());
            
            vidas--;
            herido = true;
            tiempoHerido = tiempoHeridoMax;
            
            sonidoHerido.play();
            
            if (vidas <= 0)
                destruida = true;
            
            return true;
        }
        return false;
    }
    
    public boolean estaDestruido() {
        return !herido && destruida;
    }
    
    public boolean estaHerido() {
        return herido;
    }
    
    public int getVidas() {
        return vidas;
    }
    
    public int getX() {
        return (int) spr.getX();
    }
    
    public int getY() {
        return (int) spr.getY();
    }
    
    public void setVidas(int vidas2) {
        vidas = vidas2;
    }
}

