package puppy.code;

public class Proteccion implements Effect
{
	private float time;
	
	public Proteccion(float time)
	{
		this.time = time;
	}
	
	public void aplicarEffect(Nave4 nave)
	{
		nave.setProtegido(true);
		nave.setTiempoProtegido(time);
	}
}
