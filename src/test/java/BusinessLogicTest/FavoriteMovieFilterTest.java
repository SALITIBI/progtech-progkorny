package BusinessLogicTest;

import static org.junit.Assert.*;
import io.FavoritesStorage;
import io.MovieStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import model.Movie;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import controller.Controller;
import IOMock.FavoriteStorageMocker;
import IOMock.MovieStorageMocker;

@RunWith(Parameterized.class)
public class FavoriteMovieFilterTest {

	private static Controller state;
	private static FavoritesStorage favStorage;
	private static MovieStorage movieStorage;
	@Parameterized.Parameters
	public static Collection data(){
		return Arrays.asList(new String[][]{{"a"},{"b"},{"c"},{"d"},{"e"},{"f"}});
	}
	String title;
	public FavoriteMovieFilterTest(String title){
		this.title=title;
	}
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		movieStorage=new MovieStorageMocker();
		favStorage=new FavoriteStorageMocker();
		state=new Controller(movieStorage, favStorage);
	}
	@Test
	public void test() {
		
		state.loadAllFavorites();
		state.filterFavorites(title);
		List<Movie> temp=state.getFavorites();
		List<Movie> actual=new ArrayList<>();
		for (Movie movie : temp) {
			actual.add(movie);
		}
		state.loadAllFavorites();
		
		List<Movie> expected=new ArrayList<>();
		for (Movie movie : temp) {
			if(movie.getTitle().contains(title))
			{
				expected.add(movie);
			}
		}
		if(!Helper.containsTheSameMovies(expected, actual))
		{
			fail("The search results aren't what they should be.");
		}
	}

}
