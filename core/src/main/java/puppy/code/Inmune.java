package puppy.code;

public class Inmune implements Effect{

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
