package BusinessLogicTest;
import static org.junit.Assert.*;

import java.util.List;

import model.AlreadyFavorite;
import model.DataHandler;
import model.Movie;

import org.junit.BeforeClass;
import org.junit.Test;

import IO.FavoritesStorage;
import IO.InOutException;
import IO.MovieStorage;
import IOMock.FavoriteStorageMocker;
import IOMock.MovieStorageMocker;


public class FavoriteTest {
	private static DataHandler state;
	private static FavoritesStorage favStorage;
	private static MovieStorage movieStorage;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		movieStorage=new MovieStorageMocker();
		favStorage=new FavoriteStorageMocker();
		state=new DataHandler(movieStorage, favStorage);
	}

	@Test
	public void addToFavouriteTest() {
		Movie movie = new Movie(1, "Apacuka", "Fundaluka", 5, 100, 2014);
		state.loadAllFavorites();
		try {
			state.addMovieToFavorites(movie);
		} catch (AlreadyFavorite e) {
		}
		
		List<Movie> favorites=state.getFavorites();
		if(!favorites.contains(movie))
		{
			fail("Movie hasn't been added to favorites list.");
		}
		state.removeFromFavorites(movie);

	}
	@Test
	public void removeFromFavouriteTest() {
		Movie movie = new Movie(1, "Apacuka", "Fundaluka", 5, 100, 2014);
		try {
			state.addMovieToFavorites(movie);
		} catch (AlreadyFavorite e) {
		}
		state.loadAllFavorites();
		List<Movie> favorites=state.getFavorites();
		
		state.removeFromFavorites(movie);
		
		if(favorites.contains(movie) || favStorage.loadAllFavorites().contains(movie.getId()))
		{
			fail("Movie hasn't been removed from favorites list.");
		}
	}
	@Test(expected = AlreadyFavorite.class)
	public void testAlreadyFavourite() throws  AlreadyFavorite{
		Movie movie = new Movie(1, "Apacuka", "Fundaluka", 5, 100, 2014);
		try {state.addMovieToFavorites(movie);
		} catch (AlreadyFavorite e) {
		}
		state.addMovieToFavorites(movie);
	}
}
