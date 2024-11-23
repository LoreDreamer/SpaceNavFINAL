package puppy.code;

public interface Movement { // Interfaz de movimiento para las clases que se mueven que no sea el jugador ni la bala.
	
	public void move();
	public void checkCollision(Obstacle b2);
	
}
