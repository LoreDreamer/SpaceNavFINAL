package puppy.code;


import com.badlogic.gdx.graphics.Texture;

public class BlackHole extends Item {
	
	public BlackHole(int x, int y, int size, int score, Effect effect, Texture tx) {
		super(x, y, size, score, effect, tx);
	}
	
	@Override
	public int updateScore(int scoreActual) // Aplica el efecto del agujero negro, el cual debe reducir el puntaje del jugador + paralizado que se hace en otra sección del código.
	{
        return scoreActual - score;
	}
}