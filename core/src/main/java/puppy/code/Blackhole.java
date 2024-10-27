package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Blackhole extends Objeto {
	
	public Blackhole(int x, int y, int score, int effect, float time, Sprite spr) {
		super(x, y, score, effect, time, spr);
	}
	
	@Override
	public int updateScore(int newScore) //Retorna el nuevo puntaje luego de tocar 
	{		
        int updatedScore = newScore - 10; // Resta 10 al nuevo puntaje
        return updatedScore; // Retorna el puntaje actualizado
    }
    
    // 
	@Override
    public double updateEffect(int score, Nave4 nave)
    {
		return 2.0;
	}

}

