package BusinessLogicTest;
import static org.junit.Assert.*;

import java.util.List;

import model.Actor;
import model.DataHandler;
import model.Movie;

import org.codehaus.groovy.runtime.metaclass.MethodMetaProperty.GetMethodMetaProperty;
import org.junit.BeforeClass;
import org.junit.Test;

import IO.FavoritesStorage;
import IO.MovieStorage;
import IOMock.FavoriteStorageMocker;
import IOMock.MovieStorageMocker;


public class ActorFilterListByMovieTest {
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
	public void testActorMovieFilter() {
		List<Actor> expected;
		List<Actor> actual;
		List<Movie> movies;
		state.loadMoviesByTitle("");
		movies=state.getMovies();
		for (Movie movie : movies) {
			state.loadActorsByMovie(movie);
			actual=state.getActors();
			expected=((MovieStorageMocker)movieStorage).getActorsOfMovie().get(movie);
			if(!Helper.containsTheSameActors(expected, actual))
			{
				fail("The search results aren't what they should be.");
			}
		}
	}
}
