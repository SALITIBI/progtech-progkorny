package BusinessLogicTest;
import static org.junit.Assert.*;
import io.FavoritesStorage;
import io.MovieStorage;
import model.AlreadyRated;
import model.Movie;
import model.RatingOutOfBound;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.Controller;
import IOMock.FavoriteStorageMocker;
import IOMock.MovieStorageMocker;


public class RateMoviesTest {
	private static Controller state;
	private static FavoritesStorage favStorage;
	private static MovieStorage movieStorage;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		movieStorage=new MovieStorageMocker();
		favStorage=new FavoriteStorageMocker();
		state=new Controller(movieStorage, favStorage);
	}
	
	private double average(double[] vector){
		double sum=0;
		for (double d : vector) {
			sum+=d;
		}
		return sum/vector.length;
	}
	@Test
	public void testCalculationCorrectness() {
		double rating[]=new double[]{5,4.5,3.2,1,5,4,3,4,2,1,3,4,5,4,3,2};
		Movie movie = new Movie(1, "Apacuka", "Fundaluka", 0,0, 2014);
		for (double d : rating) {
			state=new Controller(movieStorage, favStorage);
			try {
				state.rateMovie(movie, d);
			} catch (RatingOutOfBound e) {
			} catch (AlreadyRated e) {
			}
		}
		assertEquals(average(rating), movie.getRatings(), Math.pow(2, -15));
	}
	@Test(expected = AlreadyRated.class)
	public void testRatingAvailabilityThroughoutSession() throws AlreadyRated{
		Movie movie = new Movie(1, "Apacuka", "Fundaluka", 0,0, 2014);
		try {
			state.rateMovie(movie, 5);
		} catch (RatingOutOfBound e) {
		} catch (AlreadyRated e) {
		}
		try{
			state.rateMovie(movie, 3);
		}catch(RatingOutOfBound e){
		}
	}

}
