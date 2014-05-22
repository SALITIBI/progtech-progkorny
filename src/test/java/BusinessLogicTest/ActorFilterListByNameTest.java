package BusinessLogicTest;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import model.Actor;
import model.DataHandler;
import model.Movie;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import IO.FavoritesStorage;
import IO.MovieStorage;
import IOMock.FavoriteStorageMocker;
import IOMock.MovieStorageMocker;

@RunWith(Parameterized.class)
public class ActorFilterListByNameTest {
	@Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new String[][]{{"a"},{"b"},{"Ab"},{"c"},{"d"},{"h"},{"z"}});
    }
	private static DataHandler state;
	private static FavoritesStorage favStorage;
	private static MovieStorage movieStorage;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		movieStorage=new MovieStorageMocker();
		favStorage=new FavoriteStorageMocker();
		state=new DataHandler(movieStorage, favStorage);
	}
	String name;
	public ActorFilterListByNameTest(String name){
		this.name=name;
	}
	@Test
	public void testActorNameFilter() {
		List<Actor> expected=new ArrayList<Actor>();
		for (Actor actor : ((MovieStorageMocker)movieStorage).getActors()) {
			if(actor.getName().contains(name))
			{
				expected.add(actor);
			}
		}
		state.loadMoviesByTitle(name);
		List<Actor> actual=state.getActors();
		if(!Helper.containsTheSameActors(expected, actual))
		{
			fail("The search results aren't what they should be.");
		}
	}

}
