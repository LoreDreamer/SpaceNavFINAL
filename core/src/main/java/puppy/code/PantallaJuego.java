package puppy.code;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input; // Para Input.Keys
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Mesh;

/**
 * Clase PantallaJuego implementa la pantalla principal del juego,
 * donde se desarrollan las mecánicas del juego como la nave,
 * los asteroides, las balas y las colisiones.
 */
public class PantallaJuego implements Screen {
    
    private SpaceNavigation game; // Referencia al juego principal
    private OrthographicCamera camera; // Cámara para la visualización
    private SpriteBatch batch; // Batch para dibujar sprites
    private boolean isPaused; // Variable para controlar el estado de pausa


    private Sound explosionSound; // Sonido de explosión
    private Music gameMusic; // Música de fondo del juego

    private int score; // Puntuación del jugador
    private int ronda; // Ronda actual
    private int velXAsteroides; // Velocidad en el eje X de los asteroides
    private int velYAsteroides; // Velocidad en el eje Y de los asteroides
    private int cantSatelites; // Cantidad de asteroides en juego
    private int velXSatelites;
    private int velYSatelites;
    private int cantAsteroides; 
    private Objeto token;
    private SpriteBatch spriteBatch;
    private Mesh mesh;
    private Texture fondo;

    private Nave4 nave; // Objeto nave
    private ArrayList<Obstacle> obstaculos = new ArrayList<>(); // Lista de obstaculos
    private ArrayList<Bullet> balas = new ArrayList<>(); // Lista de balas

    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,  
                         int velXAsteroides, int velYAsteroides, int cantidad) {
        this.game = game; // Inicializa la referencia al juego
        this.ronda = ronda; // Establece la ronda actual
        this.score = score; // Establece la puntuación actual
        this.velXAsteroides = velXAsteroides; // Establece la velocidad X de los asteroides
        this.velYAsteroides = velYAsteroides; // Establece la velocidad Y de los asteroides
        this.cantAsteroides = cantidad; // Establece la cantidad de asteroides
        this.velXSatelites = (velXAsteroides/2);
        this.velYSatelites = (velYAsteroides/2);
        this.cantSatelites = (cantidad / 2);
        isPaused = false;

        initialize(); // Inicializa la configuración del juego
        createNave(vidas); // Crea la nave del jugador
        createAsteroids(); // Crea los asteroides
        createSatellites(); // Crea los satélites
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
    }


    // Método para crear los asteroides 
    private void createAsteroids() {
        Random r = new Random(); // Crea un objeto Random para generar posiciones aleatorias
        for (int i = 0; i < (cantAsteroides - cantSatelites); i++) {
            Obstacle asteroide = new Ball2(
                    r.nextInt((int) Gdx.graphics.getWidth()), // Posición X aleatoria
                    50 + r.nextInt((int) Gdx.graphics.getHeight() - 50), // Posición Y aleatoria
                    20 + r.nextInt(10), // Tamaño aleatorio
                    velXAsteroides + r.nextInt(4), // Velocidad X aleatoria
                    velYAsteroides + r.nextInt(4), // Velocidad Y aleatoria
                    new Texture(Gdx.files.internal("aGreyMedium4.png"))); // Textura del asteroide

            obstaculos.add(asteroide); // Agrega el asteroide a la lista
        }
    }    

    // Método para crear los satélites
    private void createSatellites() {
        Random ra = new Random(); // Crea un objeto Random para generar posiciones aleatorias
        for (int i = 0; i < cantSatelites; i++) {
            Obstacle satelite = new Satellite(
                    ra.nextInt((int) Gdx.graphics.getWidth()), // Posición X aleatoria
                    50 + ra.nextInt((int) Gdx.graphics.getHeight() - 50), // Posición Y aleatoria
                    20 + ra.nextInt(10), // Tamaño aleatorio
                    velXSatelites + ra.nextInt(4), // Velocidad X aleatoria
                    velYSatelites + ra.nextInt(4), // Velocidad Y aleatoria
                    new Texture(Gdx.files.internal("sat.png"))); // Textura del satélite

            obstaculos.add(satelite); // Agrega el satélite a la lista
        }
    }
    
    private void crearToken() {
        Random rand = new Random();
        int x = rand.nextInt(Gdx.graphics.getWidth() - 50); // Posición X aleatoria
        int y = rand.nextInt(Gdx.graphics.getHeight() - 50); // Posición Y aleatoria
        Texture textura = new Texture(Gdx.files.internal("moneda.png")); // Cargar la textura de la moneda
        Sprite sprite = new Sprite(textura); // Crear un sprite para la moneda
        token = new Token(x, y, 10, 0, 0f, sprite); // Crear un nuevo token (moneda)
    }

    // Método para actualizar el estado de los satélites
    private void updateObstaculos() {
        for(Obstacle obj : obstaculos) {
            obj.move(); // Llama al método update de cada satélite
        }
    }


    // Método para dibujar el encabezado de la interfaz de juego
    private void drawHeader() {
        // Crea la cadena de texto con la información de vida y puntuación
        CharSequence str = "Vidas: " + nave.getVidas() + " Ronda: " + ronda;
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
            // Si el juego está en pausa, solo dibuja la pantalla de pausa
            drawPauseScreen(); // Mostrar pantalla de pausa
            return; // No actualiza el juego
        }

        // Continuar con la lógica del juego solo si no está en pausa
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Limpia la pantalla
        batch.begin(); // Inicia el batch para dibujar

        // Dibujar el fondo
        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Dibuja el fondo

        drawHeader(); // Dibuja el encabezado del juego

        if (!nave.estaHerido()) {
            handleCollisions();    // Maneja las colisiones
            updateObstaculos();     // Actualiza los satélites
            handleObstacleCollisions(); // Maneja las colisiones entre asteroides
        }

        drawBullets(); // Dibuja las balas
        nave.draw(batch, this); // Dibuja la nave
        drawObstaculos(); 

        checkGameOver(); // Verifica si el juego ha terminado
        batch.end(); // Termina el batch de dibujo

        if (obstaculos.isEmpty()) {
            proceedToNextLevel(); // Cambia de nivel si no hay asteroides ni satélites
        }
    }

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

    private void drawPauseScreen() {
        // Lógica para dibujar la pantalla de pausa
        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "Juego en Pausa. Presiona P para reanudar.", 400, 400);
        game.getBatch().end();
    }


    // Método para dibujar los asteroides en pantalla
    private void drawObstaculos() {
        for (Obstacle obj : obstaculos) {
            obj.draw(batch); // Dibuja cada asteroide
        }
    }

    // Método para manejar las colisiones entre asteroides
    private void handleObstacleCollisions() 
    {
        for (int i = 0; i < obstaculos.size(); i++) 
        {
            if (nave.checkCollision(obstaculos.get(i))) 
            {
                explosionSound.play(); // Reproduce sonido de explosión
                nave.setVidas(nave.getVidas()); // Reduce las vidas de la nave
                obstaculos.remove(i); // Remueve el asteroide
                i--; // Ajusta el índice
            }
        }
    }

    
    // Método para verificar si el juego ha terminado
    private void checkGameOver() {
        if (nave.estaDestruido()) {
            if (score > game.getHighScore()) game.setHighScore(score);
            Screen ss = new PantallaGameOver(game);
            gameMusic.stop(); 
            ss.resize(1200, 800);
            game.setScreen(ss);
            // No llames a dispose() aquí.
        }
    }
    

    // Método para avanzar al siguiente nivel
    private void proceedToNextLevel() {
        gameMusic.stop(); // Detiene la música
        ronda++; // Incrementa la ronda
        game.setScreen(new PantallaJuego(game, ronda, nave.getVidas(), score, velXAsteroides, velYAsteroides, cantAsteroides)); // Cambia a la siguiente pantalla
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
        // Libera cualquier otro recurso
    }
    
   
    
    
    
    
    
    
    
    
    
    // Método para crear la nave del jugador
    private void createNave(int vidas) {
        nave = new Nave4(Gdx.graphics.getWidth() / 2 - 50, 30,
                new Texture(Gdx.files.internal("MainShip3.png")),
                Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
                new Texture(Gdx.files.internal("Rocket2.png")),
                Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));
        nave.setVidas(vidas); // Establece las vidas de la nave
    }
    
	// Método para verificar las colisiones de las balas con asteroides y satélites
    private void checkBulletCollisions(Bullet b, int bulletIndex) {
        // Verifica colisiones con asteroides
        for (int j = 0; j < obstaculos.size(); j++)  //recorro el array
        {
        	Obstacle obs = obstaculos.get(j);
            if (b.checkCollision(obs)) 
            {
                explosionSound.play(); // Reproduce sonido de explosión
                if(obs.hitByBullet())
                {
                	score += 10; // Incrementa la puntuación
                    obstaculos.remove(j); // Remueve el asteroide
                }
                break; // Sale del bucle tras una colisión
            }
        }
    }
    
 // Método para manejar las colisiones de las balas
    private void handleCollisions() {
        for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            b.update(); // Actualiza la posición de la bala
            checkBulletCollisions(b, i); // Verifica colisiones de la bala
            removeDestroyedBullet(i); // Remueve balas destruidas
        }
    }
    

    // Método para remover balas destruidas
    private void removeDestroyedBullet(int bulletIndex) {
        if (balas.get(bulletIndex).isDestroyed()) {
            balas.remove(bulletIndex); // Remueve la bala de la lista
        }
    }

    // Método para dibujar las balas en pantalla
    private void drawBullets() {
        for (Bullet b : balas) {
            b.draw(batch); // Dibuja cada bala
        }
    }  
    
    public boolean agregarBala(Bullet bb) {
        return balas.add(bb);
    
    } 
}
