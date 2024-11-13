package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input; // Para Input.Keys
import com.badlogic.gdx.graphics.Mesh;

/**
 * Clase PantallaJuego implementa la pantalla principal del juego,
 * donde se desarrollan las mecánicas del juego como la nave,
 * los asteroides, las balas y las colisiones.
 */
 
public class PantallaJuego implements Screen 
{
    private SpaceNavigation game; // Referencia al juego principal
    private OrthographicCamera camera; // Cámara para la visualización
    private SpriteBatch batch; // Batch para dibujar sprites
    private boolean isPaused; // Variable para controlar el estado de pausa

    private Sound explosionSound; // Sonido de explosión
    private Music gameMusic; // Música de fondo del juego

    private int score; // Puntuación del jugador
    private int ronda; // Ronda actual

    private SpriteBatch spriteBatch;
    private Mesh mesh;
    private Texture fondo;
    private Ambient ambiente;

    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score) {
    	
        this.game = game; // Inicializa la referencia al juego
        this.ronda = ronda; // Establece la ronda actual
        this.score = score; // Establece la puntuación actual
        
        isPaused = false;
        ambiente = new Ambient(game, ronda, vidas, score, explosionSound, batch);
        
        initialize(); // Inicializa la configuración del juego
        ambiente.inicializar();
    }
    
    //Encapsulación de sonido
    private void loadAudioResources() {
    	
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1, 0.5f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Smile -  Butterfly.mp3"));
        
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.5f);
        gameMusic.play();
    }

    // Método para inicializar variables y recursos 
    private void initialize() {
    	
        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);
        
        loadAudioResources(); // Cargar recursos de audio
        fondo = new Texture(Gdx.files.internal("gal7.png"));
        
        for (int i = 0; i < ronda; i++) {
        	modificarAmbiente();
        }
    }
    
    
    //
    
    public void handleItemCollisions()
    {
    	this.score += ambiente.handleItemCollisions();
    }
    
    //
    

    @Override
    public void pause() {
    	
        isPaused = true; // Cambiar el estado a pausado
        gameMusic.pause(); // Pausar la música
        drawPauseScreen(); // Mostrar la pantalla de pausa
        
    }

    @Override
    public void resume() {
    	
        isPaused = false; // Cambiar el estado a reanudado
        gameMusic.play(); // Reanudar la música
        
    }

    private void drawPauseScreen() { // Lógica para dibujar la pantalla de pausa

        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "Juego en Pausa. Presiona P para reanudar.", 400, 400);
        game.getBatch().end();
        
    }  

    @Override
    public void resize(int width, int height) {
        camera.update(); // Actualiza la cámara
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch(); // Inicializar spriteBatch
    }

    @Override
    public void hide() {
        dispose(); // Libera recursos cuando la pantalla se oculta 
    }
    
    @Override
    public void dispose() {
    	
        if (spriteBatch != null) {
            spriteBatch.dispose();
        }
        
        if (mesh != null) {
            mesh.dispose();
        }
        
    }
    
    // Método para verificar si el juego ha terminado
    private void checkGameOver() 
    {
        if (ambiente.naveDestruida()) 
        {
            if (score > game.getHighScore()) game.setHighScore(score);
            Screen ss = new PantallaGameOver(game);
            gameMusic.stop(); 
            ss.resize(1200, 800);
            game.setScreen(ss);
        }
    }   

    
    // Método para dibujar el encabezado de la interfaz de juego
    private void drawHeader() {

        CharSequence str = "Vidas: " + ambiente.vidasNave() + " Ronda: " + ronda; // cambiar el nave.getVidas() por el vidasNave de ambiente
        game.getFont().getData().setScale(2f); // Establece el tamaño de la fuente
        game.getFont().draw(batch, str, 10, 30); // Dibuja el texto de vidas y ronda
        
        game.getFont().draw(batch, "Score:" + this.score, Gdx.graphics.getWidth() - 150, 30); // Dibuja la puntuación
        game.getFont().draw(batch, "High Score:" + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30); // Dibuja el puntaje más alto
    }

    
    @Override
    public void render(float delta) {
    	
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) { // Detectar cuando se presiona la tecla P
            if (isPaused) {
                resume();  // Si está en pausa, reanuda
            } else {
                pause();   // Si no está en pausa, pausa
            }
        }

        if (isPaused) {

            drawPauseScreen(); // Mostrar pantalla de pausa
            return; // No actualiza el juego
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Limpia la pantalla
        batch.begin(); // Inicia el batch para dibujar

        // Dibujar el fondo
        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Dibuja el fondo

        drawHeader(); // Dibuja el encabezado del juego

       if (!ambiente.naveHerida()) { //reemplazar por "naveHerida" de ambiente y lo de abajo tambein esta en ambiente
    	   
            score += ambiente.handleCollisions();    // Maneja las colisiones
            ambiente.updateObstaculos();     // Actualiza los satélites
            ambiente.handleObstacleCollisions(); // Maneja las colisiones entre asteroides
            
        }
       
        ambiente.disparo(batch);
        ambiente.drawNave(batch, this);
        
        ambiente.drawBullets(batch);  // Llama a drawBullets aquí       
        ambiente.drawObstaculos(batch); //reemplazar por el 
        ambiente.drawItems(batch);
        
        checkGameOver(); // Verifica si el juego ha terminado 
        batch.end(); // Termina el batch de dibujo
        
        if (ambiente.hayObstaculos() == false) { //reemplazar por hayObstaculos de ambiente
            ambiente.proceedToNextLevel(gameMusic, ronda, game, score); // Cambia de nivel si no hay asteroides ni satélites
        }
    }
    
    private void modificarAmbiente() {
    	ambiente.ambienteModificar();
    }
}
