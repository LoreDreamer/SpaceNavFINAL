package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Item
{
	private int x;
	private int y;
	private int size;
	private int score;
	private Effect effect;
	private Sprite spr;
	
	/*public Item(int x, int y, int size, int score, Effect effect, Texture tx) {
		this.x = x;
		
		if (x - size < 0) this.x = x + size;
        if (x + size > Gdx.graphics.getWidth()) this.x = x - size;
        
        this.y = y;
        
        if (y - size < 0) this.y = y + size;
        if (y + size > Gdx.graphics.getHeight()) this.y = y - size;
		
		this.size = size;
		this.score = score;
		this.effect = effect;
		this.spr = new Sprite(tx);
		
    	spr.setPosition(x, y);
    	spr.setBounds(x, y, 45, 45);
	}*/
	
	
	public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
    
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }
    
    public abstract void setEffect(Effect effect);
	public abstract int updateScore(int score);
	public abstract void updateEffect(Nave4 nave);
}
