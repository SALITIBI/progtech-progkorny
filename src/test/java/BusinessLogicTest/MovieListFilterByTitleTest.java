package BusinessLogicTest;
import static org.junit.Assert.*;
import io.FavoritesStorage;
import io.MovieStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import controller.Controller;
import model.Movie;
import IOMock.FavoriteStorageMocker;
import IOMock.MovieStorageMocker;

@RunWith(Parameterized.class)
public class MovieListFilterByTitleTest {
	@Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new String[][]{{"a"},{"b"},{"Ab"},{"c"},{"d"},{"h"},{"z"}});
    }
	String title;
	public MovieListFilterByTitleTest(String title){
		this.title=title;
	}
	private static Controller state;
	private static FavoritesStorage favStorage;
	private static MovieStorage movieStorage;
	@BeforeClass
	public static void setUpBeforeClass(){
		movieStorage=new MovieStorageMocker();
		favStorage=new FavoriteStorageMocker();
		state=new Controller(movieStorage, favStorage);
	}
	
	@Test
	public void testMovieTitleFilter(){
		List<Movie> expected=new ArrayList<Movie>();
		for (Movie movie : ((MovieStorageMocker)movieStorage).getMovies()) {
			if(movie.getTitle().contains(title))
			{
				expected.add(movie);
			}
		}
		state.loadMoviesByTitle(title);
		List<Movie> actual=state.getMovies();
		if(!Helper.containsTheSameMovies(expected, actual))
		{
			fail("The search results aren't what they should be.");
		}
	}
}
