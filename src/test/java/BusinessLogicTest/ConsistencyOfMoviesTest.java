package BusinessLogicTest;
import static org.junit.Assert.*;

import java.util.List;

import model.DataHandler;
import model.Movie;

import org.junit.BeforeClass;
import org.junit.Test;

import IO.FavoritesStorage;
import IO.MovieStorage;
import IOMock.FavoriteStorageMocker;
import IOMock.MovieStorageMocker;


public class ConsistencyOfMoviesTest {

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
	public void searchThenLoadFavorites() {
		state.loadMoviesByTitle("");
		state.loadAllFavorites();
		List<Movie> favorites=state.getFavorites();
		List<Movie> searchRes=state.getMovies();
		for (Movie movie : favorites) {
			for (Movie m : searchRes) {
				if(m.equals(movie))
				{
					if(movie!=m)
					{
						fail("The the movies are equal but aren't contained in the same object.");
					}
				}
			}
		}
	}

}
