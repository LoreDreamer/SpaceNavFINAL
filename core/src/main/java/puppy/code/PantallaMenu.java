package puppy.code;

import com.badlogic.gdx.Gdx; // Importa las funcionalidades de GDX
import com.badlogic.gdx.Input; // Importa la clase para gestionar entradas del teclado y ratón
import com.badlogic.gdx.Screen; // Interfaz que representa una pantalla en el juego
import com.badlogic.gdx.graphics.OrthographicCamera; // Importa la clase de cámara ortográfica
import com.badlogic.gdx.utils.ScreenUtils; // Utilidades para manejar pantallas

public class PantallaMenu implements Screen { // Implementa la interfaz Screen

    private SpaceNavigation game; // Referencia al juego principal
    private OrthographicCamera camera; // Cámara para proyectar la vista

    public PantallaMenu(SpaceNavigation game) {
        this.game = game; // Inicializa la referencia al juego
        
        camera = new OrthographicCamera(); // Crea una nueva cámara ortográfica
        camera.setToOrtho(false, 1200, 800); // Establece el tamaño de la cámara
    }

    @Override
    public void render(float delta) { // Método para renderizar la pantalla
        ScreenUtils.clear(0, 0, 0.2f, 1); // Limpia la pantalla con un color de fondo

        camera.update(); // Actualiza la cámara
        game.getBatch().setProjectionMatrix(camera.combined); // Establece la matriz de proyección

        game.getBatch().begin(); // Comienza a dibujar
        // Dibuja el texto de bienvenida y las instrucciones
        game.getFont().draw(game.getBatch(), "¡Bienvenido a Space Navigation!", 140, 400);
        game.getFont().draw(game.getBatch(), "Aprieta o clickea para empezar.", 100, 300);
        game.getBatch().end(); // Finaliza el dibujo

        // Detecta si el usuario toca la pantalla o presiona una tecla
        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            // Crea una nueva pantalla de juego
            Screen ss = new PantallaJuego(game, 1, 3, 0, 1, 1, 6);
            ss.resize(1200, 800); // Ajusta el tamaño de la nueva pantalla
            game.setScreen(ss); // Cambia a la nueva pantalla
            dispose(); // Libera los recursos de la pantalla actual
        }
    }
    
    @Override
    public void show() {
        // Este método se llama cuando la pantalla se muestra
        // Puedes inicializar elementos de la pantalla aquí
    }

    @Override
    public void resize(int width, int height) {
        // Este método se llama cuando la ventana cambia de tamaño
        // Ajusta la cámara para que se adapte al nuevo tamaño de la ventana
        camera.setToOrtho(false, width, height); // Ajusta la cámara a las nuevas dimensiones
    }

    @Override
    public void pause() {
        // Este método se llama cuando la pantalla se pausa
        // Puedes guardar el estado del juego o liberar recursos aquí
    }

    @Override
    public void resume() {
        // Este método se llama cuando la pantalla se reanuda
        // Puedes restaurar el estado del juego o volver a cargar recursos aquí
    }

    @Override
    public void hide() {
        // Este método se llama cuando la pantalla se oculta
        // Puedes liberar recursos si es necesario
    }

    @Override
    public void dispose() {
        // Este método se llama cuando la pantalla se destruye
        // Libera cualquier recurso que necesite ser desalojado
    }
}
