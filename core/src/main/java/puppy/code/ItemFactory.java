package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class ItemFactory
{
	private static ArrayList<Item> listItems = new ArrayList<>();
	
	public static Item createItem(int id, int x, int y, int size)
	{
		int score;
		Effect effect;
		float time;
		Texture tx;
		
		switch(id)
		{
			case 0:
				score = 500;
				time = 3;
				effect = new Inmune();
				tx = new Texture(Gdx.files.internal("aGreyMedium4.png"));
				
				return new Star(x, y, size, score, effect, time, tx);
			case 1:
				score = 10;
				time = 2;
				effect = new Paralizado();
				tx = new Texture(Gdx.files.internal("aGreyMedium4.png"));
				
				return new BlackHole(x, y, size, score, effect, time, tx);
			default:
				score = 100;
				time = 2;
				effect = new Inmune();
				tx = new Texture(Gdx.files.internal("aGreyMedium4.png"));
				
				return new Star(x, y, score, size, effect, time, tx);
		}
	}
	
	public static void inicializarItems()
	{
		Random rdm = new Random();
		Item mod;
		
		for (int i = 0; i < 2; i++)
		{
			mod = createItem(rdm.nextInt(2), rdm.nextInt((int) Gdx.graphics.getWidth()),
                    50 + rdm.nextInt((int) Gdx.graphics.getHeight() - 50),
                    20 + rdm.nextInt(10));
			
			listItems.add(mod);
		}
	}
	
	public static void drawItems(SpriteBatch batch) {
        for (Item mod : listItems) {
            mod.draw(batch);
        }
    }
	
	public static int handleCollisions(Player jugador) {
    	
    	int score = 0;
		
    	for (int i = 0; i < listItems.size(); i++) 
        {
            if (jugador.getNave().checkCollision(listItems.get(i))) 
            {
            	score = listItems.get(i).updateScore(score);
            	listItems.get(i).updateEffect(jugador.getNave());
            	listItems.remove(i);
                i--;
            }
        }
    	
    	return score;
    }
}
