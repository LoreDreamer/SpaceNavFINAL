package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Nave4 {
	
	//Instancia de nave unica
	private static Nave4 navePlayer;
	
	private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private Texture tx; // textura base
    private Texture txBuffed; // textura con modificador
    
    private boolean inmune = false;
    private float tiempoInmune = 0;
    private boolean paralizado = false;
    private float tiempoParalizado = 0;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;

    
    private Nave4(int x, int y, Texture tx, Texture txBuffed, Sound soundChoque, Texture txBala, Sound soundBala) {
    	
    	sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	this.tx = tx;
    	this.txBuffed = txBuffed;
    	
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	spr.setBounds(x, y, 45, 45);

    }
    
    // Metodo para obtener el objeto unico nave
    public static Nave4 getNavePlayer(int x, int y, Texture tx, Texture txBuffed, Sound soundChoque, Texture txBala, Sound soundBala)
    {
    	if (navePlayer == null)
    		navePlayer = new Nave4(x, y, tx, txBuffed, soundChoque, txBala, soundBala);
    	
    	return navePlayer;
    }
    
    public void draw(SpriteBatch batch, PantallaJuego juego){
    	
        float x =  spr.getX();
        float y =  spr.getY();
        
        if (paralizado)
        {
        	tiempoParalizado -= Gdx.graphics.getDeltaTime();
        	
        	if (tiempoParalizado <= 0)
        	{
        		paralizado = false;
        		tiempoParalizado = 0;
        	}
        }
        
        if (inmune)
        {
        	spr.setRegion(txBuffed);
        	tiempoInmune -= Gdx.graphics.getDeltaTime();
        	
        	if (tiempoInmune <= 0)
        	{
        		inmune = false;
        		tiempoInmune = 0;
        		spr.setRegion(tx);
        	}
        }
        
        if (!herido && !paralizado) { // que se mueva con teclado
        	
	        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) xVel--;
	        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) xVel++;
        	if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) yVel--;     
	        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) yVel++;
	        

	        if (x+xVel < 0 || x+xVel+spr.getWidth() > Gdx.graphics.getWidth()) // que se mantenga dentro de los bordes de la ventana
	        	xVel*=-1;
	        if (y+yVel < 0 || y+yVel+spr.getHeight() > Gdx.graphics.getHeight())
	        	yVel*=-1;
	        
	        spr.setPosition(x+xVel, y+yVel);   
 		    spr.draw(batch);
 		    
        } else {
        	
           spr.setX(spr.getX()+MathUtils.random(-2,2));
 		   spr.draw(batch); 
 		   spr.setX(x);
 		   
 		   tiempoHerido--;
 		   
 		   if (tiempoHerido<=0) herido = false;
 		 }   
    }
    
    public Bullet disparo(SpriteBatch batch) {
    	
    	if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { 
    		
          Bullet  bala = new Bullet(spr.getX()+spr.getWidth()/2-5,spr.getY()+ spr.getHeight()-5,0,3,txBala);
	      soundBala.play(); 
	      return bala;
        }
    	
    	return null;
    }
      
    public boolean checkCollision(Obstacle b) {
    	
        if (!inmune && !herido && b.getArea().overlaps(spr.getBoundingRectangle())){
        	
        	// rebote
            if (xVel ==0) xVel += b.getXSpeed()/2;
            if (b.getXSpeed() ==0) b.setXSpeed(b.getXSpeed() + (int)xVel/2);
            
            xVel = - xVel;
            b.setXSpeed(-b.getXSpeed());
            
            if (yVel ==0) yVel += b.getYSpeed()/2;
            if (b.getYSpeed() ==0) b.setYSpeed(b.getYSpeed() + (int)yVel/2);
            
            yVel = - yVel;
            b.setYSpeed(- b.getYSpeed());
            
            vidas--;
            herido = true;
  		    tiempoHerido=tiempoHeridoMax;
  		    
  		    sonidoHerido.play();
  		    
            if (vidas<=0)
          	    destruida = true; 
            
            return true;
        }
        return false;
    }
    
public boolean checkCollision(Item i) {
    	
        if (!inmune && !herido && i.getArea().overlaps(spr.getBoundingRectangle())){
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
    public void setInmune(boolean estado) {
    	inmune = estado;
    }
    public boolean esInmune() {
  	   return inmune;
     }
    public void setParalizado(boolean estado) {
    	paralizado = estado;
    }
    public boolean estaParalizado() {
  	   return paralizado;
     }
    
    public void setTiempoInmune(float time)
    {
    	this.tiempoInmune = time;
    }
    
    public void setTiempoParalizado(float time)
    {
    	this.tiempoParalizado = time;
    }
    
    public void setPosition(int x, int y)
    {
    	spr.setPosition(x, y);
    }
    
    public int getVidas() {return vidas;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}
}
