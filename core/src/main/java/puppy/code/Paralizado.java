package puppy.code;

public class Paralizado implements Effect{

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
