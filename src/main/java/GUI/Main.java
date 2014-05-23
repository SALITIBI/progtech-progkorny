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

public class Main {
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	public static Controller initializeModel()
	{
		Controller state;
		MovieStorage movs= new MovieStorageImpl();
		FavoritesStorage favs = new FavoritesStorageImpl();
		state = new Controller(movs,favs);
		return state;
	}
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
