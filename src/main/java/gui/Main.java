package gui;


import io.FavoritesStorage;
import io.FavoritesStorageImpl;
import io.MovieStorage;
import io.MovieStorageImpl;
import java.awt.EventQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.GuiEventListener;
import controller.Controller;
/**
 * 
 * Class containing the main-program.
 * 
 * @author Tibor Salagvárdi
 *
 */
public class Main {
	/**
	 * Logger for logging.
	 */
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	/**
	 * Method for initializing a {@code Controller} object and the IO sources for it. 
	 * @return the initialized {@code Controller} object
	 */
	public static Controller initializeModel()
	{
		Controller state;
		MovieStorage movs= new MovieStorageImpl();
		FavoritesStorage favs = new FavoritesStorageImpl();
		state = new Controller(movs,favs);
		return state;
	}
	/**
	 * The main-program itself. Used for initializing the software.
	 * @param args command-line arguments, which has no effect on the software at all
	 */
	public static void main(String[] args) {
		final Controller state=initializeModel();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainWindow view = new MainWindow(state, "DMDb 1.0");
				new GuiEventListener(view, state);
			}
		});
		logger.info("Initialization complete.");
	}

}
