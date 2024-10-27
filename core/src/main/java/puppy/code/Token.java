package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Un objeto que el jugador puede tomar y obtener una bonificación
public class Token extends Objeto {
	
	public Token(int x, int y, int score, int effect, float time, Sprite spr) {
		super(x, y, score, effect, time, spr);
	}
	
	// Le otorga puntaje al jugador
	@Override
	public int updateScore(int Score)
	{
		int updatedScore = Score + 10; // Suma 10 al nuevo puntaje
        return updatedScore;
	}
    
    // Aumenta el puntaje obtenido por 1.5
	@Override
    public double updateEffect(int score, Nave4 nave)
    {
		return getScore() * 1.5;
	}


}
