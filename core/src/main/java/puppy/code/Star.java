package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Star extends Objeto{
	
	public Star(int x, int y, int score, int effect, float time, Sprite spr) {
		super(x, y, score, effect, time, spr);
	}
	
	public int updateScore(int Score)
	{
		int updatedScore = Score + 100; // Suma 100 al nuevo puntaje
        return updatedScore;
	}
    
    public double updateEffect(int score, Nave4 nave)
    {
		return 1.0;
	}
}
