package puppy.code;

public class Paralizado implements Effect { // Clase que hace que el jugador no se mueve (o mejor que le da el tiempo y despues se hace en otra parte mediante otras cosas)

	private float time;
	
	public Paralizado(float time)
	{
		this.time = time;
	}
	
	public void aplicarEffect(Nave4 nave)
	{
		nave.setParalizado(true);
		nave.setTiempoParalizado(time);
	}
}
