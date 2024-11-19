package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Random;

public class ItemFactory
{
	private ArrayList<Item> listItems = new ArrayList<>();
	
	public static Item createItem(int id, int x, int y, int size)
	{
		Item item;
		
		switch(id)
		{
			case 0:
				
				item = new Star(x, y, size, 200, new Inmune(3), new Texture(Gdx.files.internal("Star2.png")));
				item.setEffect(new Inmune(3));
				
				return item;
 			case 1:
				
				item = new BlackHole(x, y, size, 10, new Paralizado(2), new Texture(Gdx.files.internal("BlackHole2.png")));
				item.setEffect(new Paralizado(2));
				
				return item;
			default:
				
				item = new Star(x, y, size, 100, new Inmune(2), new Texture(Gdx.files.internal("Star2.png")));
				item.setEffect(new Inmune(2));
				
				return item;
		}
	}
	
	public void inicializarItems()
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
