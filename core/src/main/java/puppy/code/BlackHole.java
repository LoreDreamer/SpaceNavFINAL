package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class BlackHole extends Item {
	
	private int score;
	private Effect effect;
	
	public BlackHole(int x, int y, int size, int score, Effect effect, Texture tx) {
		
		super(x, y, size, score, effect, tx);
	}
	
	@Override
	public void setEffect(Effect effect)
    {
    	this.effect = effect;
    }
	
	@Override
	public int updateScore(int scoreActual)
	{
        return scoreActual - this.score;
	}
	
	@Override
    public void updateEffect(Nave4 nave)
    {
		this.effect.aplicarEffect(nave);
	}
	
	
}