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


public class MovieListFilterByActorTest {
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
