package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Item // Parte del Item Factory/Abstract Factory
{
	protected int score;
	protected Effect effect;
	protected Sprite spr;
	
	public Item(int x, int y, int size, int score, Effect effect, Texture tx) {
		
		this.spr = new Sprite(tx);
		
		if (x - spr.getWidth() < 0) x = x + (int)spr.getWidth();
        if (x + spr.getWidth() > Gdx.graphics.getWidth()) x = x - (int)spr.getWidth();
        
        if (y - spr.getHeight() < 0) y = y + (int)spr.getHeight();
        if (y + spr.getHeight() > Gdx.graphics.getHeight()) y = y - (int)spr.getHeight();
		
		this.score = score;
		this.effect = effect;
		
    	spr.setPosition(x, y);
    	spr.setBounds(x, y, size, size);
	}
	
	
	public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
    
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }
    
    public void setEffect(Effect effect)
    {
    	this.effect = effect;
    }
    
    public void updateEffect(Nave4 nave)
    {
    	this.effect.aplicarEffect(nave);
    }
    
	public abstract int updateScore(int score);
}
