package BusinessLogicTest;
import static org.junit.Assert.*;
import io.FavoritesStorage;
import io.InOutException;
import io.MovieStorage;

import java.util.Arrays;
import java.util.Collection;

import model.AlreadyRated;
import model.Movie;
import model.RatingOutOfBound;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import controller.Controller;
import IOMock.FavoriteStorageMocker;
import IOMock.MovieStorageMocker;

@RunWith(Parameterized.class)
public class RateMoviesOutOfBoundTest {
	
	@Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Double[][]{
        		{5+Math.pow(2,-15)},{7.0},{5.0001},{5.1},{6.0},{100.0},
        		{0.9},{0.999999},{-100.0},{1-Math.pow(2,-15)}
        		});
    }
	double userRating;
	public RateMoviesOutOfBoundTest(double userRating){
		this.userRating=userRating;
	}
	
	private static Controller state;
	private static FavoritesStorage favStorage;
	private static MovieStorage movieStorage;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		movieStorage=new MovieStorageMocker();
		favStorage=new FavoriteStorageMocker();
	}
	@Test(expected = RatingOutOfBound.class)
	public void testRatingBounds() throws AlreadyRated, RatingOutOfBound{
		state=new Controller(movieStorage, favStorage);
		Movie movie = new Movie(1, "Apacuka", "Fundaluka", 0,0, 2014);
		state.rateMovie(movie, userRating);
	}
}
