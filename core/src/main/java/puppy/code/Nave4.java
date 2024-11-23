package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Nave4 { // Clase Singleton
	
	private static Nave4 navePlayer; //Instancia de nave unica
	
	private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    
    private Texture txBala; // textura bala
    private Texture tx; // textura base
    private Texture txBuffed; // textura con Invulnerabilidad
    private Texture txProtected;  // textura con proteccion
    
    private int cantBalas = 16;
    private float recarga = 4;
    private boolean inmune = false;
    
    private float tiempoInmune = 0;
    private boolean paralizado = false;
    private float tiempoParalizado = 0;
    
    private boolean protegido = false;
    private float tiempoProtegido = 0;
    private boolean confuso = false;
    
    private float tiempoConfuso = 0;
    private boolean herido = false;
    
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;

    
    private Nave4(int x, int y, Texture tx, Texture txBuffed, Texture txProtected, Sound soundChoque, Texture txBala, Sound soundBala) {
    	
    	sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	this.tx = tx;
    	this.txBuffed = txBuffed;
    	this.txProtected = txProtected;
    	
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	spr.setBounds(x, y, 45, 45);

    }
    
    // Metodo para obtener el objeto unico nave
    public static Nave4 getNavePlayer(int x, int y, Texture tx, Texture txBuffed, Texture txProtected, Sound soundChoque, Texture txBala, Sound soundBala) 
    {
    	if (navePlayer == null)
    		navePlayer = new Nave4(x, y, tx, txBuffed, txProtected, soundChoque, txBala, soundBala);
    	
    	return navePlayer;
    }
    
    public void draw(SpriteBatch batch, PantallaJuego juego){
    	
        float x =  spr.getX();
        float y =  spr.getY();
        
        if (confuso) // Si los controles se encuentran invertidos
        {
        	tiempoConfuso -= Gdx.graphics.getDeltaTime();
        	
        	if (tiempoConfuso <= 0)
        	{
        		confuso = false;
        		tiempoConfuso = 0;
        	}
        }
        
        if (protegido) // Si se encuentra con escudo
        {
        	spr.setRegion(txProtected);
        	tiempoProtegido -= Gdx.graphics.getDeltaTime();
        	
        	if (tiempoProtegido <= 0)
        	{
        		protegido = false;
        		tiempoProtegido = 0;
        		spr.setRegion(tx);
        	}
        }
        
        if (paralizado) // Si no se puede mover
        {
        	tiempoParalizado -= Gdx.graphics.getDeltaTime();
        	
        	if (tiempoParalizado <= 0)
        	{
        		paralizado = false;
        		tiempoParalizado = 0;
        	}
        }
        
        if (inmune) // Si no se le puede hacer daño
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
        
        
        if (!herido && !paralizado) { // que se mueva con teclado en cualquier otro caso
        	
        	if (confuso)
        	{
        		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) xVel++;
        		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) xVel--;
        		
        		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) yVel++;     
        		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) yVel--;
        	}
        	
        	else
        	{
        		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) xVel--;
        		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) xVel++;
        		
        		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) yVel--;     
        		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) yVel++;
        	}
	        
	        spr.setPosition(x + xVel * Gdx.graphics.getDeltaTime(), y + yVel * Gdx.graphics.getDeltaTime());
	        
	        if (cantBalas <= 0 || Gdx.input.isKeyJustPressed(Input.Keys.R))
	        {
	        	if (inmune)
	        	{
	        		cantBalas = 16;
	        		recarga = 4;
	        	}
	        	else
	        		recargar();
	        }

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
    	
    	if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !paralizado) { 
    		
    		if (cantBalas > 0 || inmune)
    		{
    			Bullet  bala = new Bullet(spr.getX()+spr.getWidth()/2-5,spr.getY()+ spr.getHeight()-5,0,3,txBala);
    			cantBalas--;
    			soundBala.play();
    			
    			return bala;
    		}
        }
    	
    	return null;
    }
    
    public void recargar()
    {
    	cantBalas = 0;
    		
    	if (protegido)
    		spr.setRegion(new Texture(Gdx.files.internal("ShipProtectedReloading.png")));
    		
    	else
    		spr.setRegion(new Texture(Gdx.files.internal("ShipReloading.png")));
    		
    	recarga -= Gdx.graphics.getDeltaTime();
    		
    	if (recarga <= 0)
    	{
    		spr.setRegion(tx);
    		cantBalas = 16;
    		recarga = 4;
    	}
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
            
            sonidoHerido.play();
            
            if (!protegido) {
            	
            	vidas--;
            	herido = true;
  		    	tiempoHerido=tiempoHeridoMax;
  		   
  		    	if (vidas<=0)
  		    		destruida = true; 
  		    	
            } else {
            	
            	protegido = false;
            	spr.setRegion(tx);
            	
            }
            
            return true;
        }
        
        return false;
    }
    
    public boolean checkCollision(Item i) { // Revisión de colisiones con items
    	
        if (!inmune && !herido && i.getArea().overlaps(spr.getBoundingRectangle())) {
            return true;
        }
        
        return false;
    }
    
    // Métodos simples
    
    public boolean estaDestruido() {
       return !herido && destruida;
    }
    public boolean estaHerido() {
 	   return herido;
    }
    public void setInmune(boolean estado) {
    	this.inmune = estado;
    }
    public boolean esInmune() {
  	   return inmune;
     }
    public void setParalizado(boolean estado) {
    	this.paralizado = estado;
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
    
    public void setProtegido (boolean estado)
    {
    	this.protegido = estado;
    }
    
    public void setConfuso (boolean estado)
    {
    	this.confuso = estado;
    }
    
    public void setTiempoProtegido(float time)
    {
    	this.tiempoProtegido = time;
    }
    
    public void setTiempoConfuso(float time)
    {
    	this.tiempoConfuso = time;
    }
    
    public void setPosition(int x, int y)
    {
    	spr.setPosition(x, y);
    	this.xVel = 0;
    	this.yVel = 0;
    }
    
    // En caso de que el jugador muera, se reinicia y revive.
    
    public void revivir() {
    	
    	destruida = false;
    	this.inmune = true;
    	this.tiempoInmune = 2;
    	
    	this.confuso = false;
    	this.tiempoConfuso = 0;
    	this.protegido = false;
    	
    	this.tiempoProtegido = 0;
    	this.paralizado = false;
    	
    	this.tiempoParalizado = 0;
    	this.cantBalas = 16;
    }
    
    // Métodos básicos (2)
    
    public int getVidas() {return vidas;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}
	
}
