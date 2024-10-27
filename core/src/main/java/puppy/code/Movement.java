package puppy.code;

public interface Movement 
{
	public void move();
	public void checkCollision(Obstacle b2); // Verifica colisión con otro Obstacle
	
}
