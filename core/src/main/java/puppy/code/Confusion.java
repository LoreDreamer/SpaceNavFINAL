package puppy.code;

public class Confusion implements Effect // cambia los controles
{
	
	private float time;
	
	public Confusion(float time)
	{
		this.time = time;
	}
	
	public void aplicarEffect(Nave4 nave)
	{
		nave.setConfuso(true);
		nave.setTiempoConfuso(time);
	}
}
