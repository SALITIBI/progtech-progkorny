package BusinessLogicTest;
import static org.junit.Assert.*;
import io.FavoritesStorage;
import io.MovieStorage;

import java.util.List;

import model.Actor;
import model.Movie;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.Controller;
import IOMock.FavoriteStorageMocker;
import IOMock.MovieStorageMocker;


public class ActorFilterListByMovieTest {
	private static Controller state;
	private static FavoritesStorage favStorage;
	private static MovieStorage movieStorage;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		movieStorage=new MovieStorageMocker();
		favStorage=new FavoriteStorageMocker();
		state=new Controller(movieStorage, favStorage);
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
