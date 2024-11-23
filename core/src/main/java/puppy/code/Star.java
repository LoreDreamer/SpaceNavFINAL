package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class Star extends Item{ // Buffs
	
	public Star(int x, int y, int size, int score, Effect effect, Texture tx) {
		super(x, y, size, score, effect, tx);
	}
	
	@Override
	public int updateScore(int scoreActual)
	{
        return scoreActual + score;
	}
}