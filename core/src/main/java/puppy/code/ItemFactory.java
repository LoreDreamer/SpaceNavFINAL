package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Random;

public class ItemFactory
{
	
	private Random rdm = new Random();
	private ArrayList<Item> listItems = new ArrayList<>();
	
	public Item createItem(int id, int x, int y)
	{
		Item item;
		
		if (id < 2)
		{
			if(rdm.nextInt(2) == 0)
			{
				item = new BlackHole(x, y, 45 + rdm.nextInt(11),100, new Proteccion(8), new Texture(Gdx.files.internal("SolarWind.png")));
			}
			
			else
			{
				item = new BlackHole(x, y, 45 + rdm.nextInt(11),50, new Paralizado(2), new Texture(Gdx.files.internal("BlackHole2.png")));					
			}
		}
		
		else
		{
			if(rdm.nextInt(2) == 0)
			{
				item = new Star(x, y, 45 + rdm.nextInt(11), 250, new Confusion(4), new Texture(Gdx.files.internal("DarkStar.png")));
			}
			
			else
			{
				item = new Star(x, y, 45 + rdm.nextInt(11),150, new Inmune(3), new Texture(Gdx.files.internal("Star2.png")));
			}
			
		}
		
		return item;
	}
	
	public void inicializarItems()
	{
		Item mod;
		
		int cantItems = 1 + rdm.nextInt(3);
		
		for (int i = 0; i < cantItems; i++)
		{
			mod = createItem(rdm.nextInt(3), rdm.nextInt((int) Gdx.graphics.getWidth()),
                    50 + rdm.nextInt((int) Gdx.graphics.getHeight() - 50));
			
			listItems.add(mod);
		}
	}
	
	public void drawItems(SpriteBatch batch) {
        for (Item mod : listItems) {
            mod.draw(batch);
        }
    }
	
	public int handleCollisions(Player jugador) {
    	
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
