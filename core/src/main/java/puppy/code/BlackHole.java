package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class BlackHole extends Item {
	
	private int x;
	private int y;
	private int size;
	private int score;
	private Effect effect;
	private float time;
	private Sprite spr;
	
	public BlackHole(int x, int y, int size, int score, Effect effect, float time, Texture tx) {
		super(x, y, size, score, effect, time, tx);
	}
	
	@Override
	public int updateScore(int scoreActual)
	{
        return scoreActual - this.score;
	}
	
	@Override
    public void updateEffect(Nave4 nave)
    {
		effect.aplicarEffect(nave);
	}
}