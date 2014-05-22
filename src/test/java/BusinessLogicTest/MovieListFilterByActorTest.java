package BusinessLogicTest;
import static org.junit.Assert.*;

import java.util.List;

import model.Actor;
import model.DataHandler;
import model.Movie;

import org.junit.BeforeClass;
import org.junit.Test;

import IO.FavoritesStorage;
import IO.MovieStorage;
import IOMock.FavoriteStorageMocker;
import IOMock.MovieStorageMocker;


public class MovieListFilterByActorTest {
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
	public void testMovieActorFilter(){
		List<Movie> expected;
		List<Movie> actual;
		List<Actor> actors;
		state.loadActorsByName("");
		actors=state.getActors();
		for (Actor actor : actors) {
			state.loadMoviesByActor(actor);
			actual=state.getMovies();
			expected=((MovieStorageMocker)movieStorage).getMoviesOfActor().get(actor);
			if(!Helper.containsTheSameMovies(expected, actual))
			{
				fail("The search results aren't what they should be.");
			}
		}
	}
}
