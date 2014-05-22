package GUI;


import java.awt.EventQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import IO.FavoritesStorage;
import IO.FavoritesStorageImpl;
import IO.MovieStorage;
import IO.MovieStorageImpl;
import model.DataHandler;

public class Main {
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	public static DataHandler initializeModel()
	{
		DataHandler state;
		MovieStorage movs= new MovieStorageImpl();
		FavoritesStorage favs = new FavoritesStorageImpl();
		state = new DataHandler(movs,favs);
		return state;
	}
	public static void main(String[] args) {
		final DataHandler state=initializeModel();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainWindow view = new MainWindow(state, "DMDb 1.0");
				new Controller(view, state);
			}
		});
		logger.info("Initialization complete.");
	}

}
