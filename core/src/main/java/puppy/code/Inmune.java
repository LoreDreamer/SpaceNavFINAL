package puppy.code;

public class Inmune implements Effect { // Para que el jugador no se haga daño durante un cierto tiempo.

	private float time;
	
	public Inmune(float time)
	{
		this.time = time;
	}
	
	public void aplicarEffect(Nave4 nave)
	{
		nave.setInmune(true);
		nave.setTiempoInmune(time);
	}
}
