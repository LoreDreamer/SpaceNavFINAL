package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Item
{

	private int score;
	private Effect effect;
	private Sprite spr;
	
	public Item(int x, int y, int size, int score, Effect effect, Texture tx) {
		
		if (x - size < 0) x = x + size;
        if (x + size > Gdx.graphics.getWidth()) x = x - size;
        
        if (y - size < 0) y = y + size;
        if (y + size > Gdx.graphics.getHeight()) y = y - size;
		
		this.score = score;
		this.effect = effect;
		this.spr = new Sprite(tx);
		
    	spr.setPosition(x, y);
    	spr.setBounds(x, y, 45, 45);
	}
	
	
	public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
    
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }
    
    public  void setEffect(Effect effect)
    {
    	this.effect = effect;
    }
    
	public abstract int updateScore(int score);
	public abstract void updateEffect(Nave4 nave);


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public Sprite getSpr() {
		return spr;
	}


	public void setSpr(Sprite spr) {
		this.spr = spr;
	}


	public Effect getEffect() {
		return effect;
	}

}
