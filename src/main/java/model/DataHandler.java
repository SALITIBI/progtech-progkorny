package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import IO.FavoritesStorage;
import IO.InOutException;
import IO.MovieStorage;

/**
 * This class contains the software's current state, and has methods for
 * manipulating it. Basically it has the functions of a primitive controller,
 * so the GUI and the business logic are separated from each other.
 * It also requires input/output sources to work with, such
 * as a database connection, or an XML file-handler. Data manipulation should
 * only be made through this Class.
 * 
 * @author Tibor Salagvárdi
 */
public class DataHandler {
	/**
	 * A logger for logging.
	 */
	private static Logger logger = LoggerFactory.getLogger(DataHandler.class);
	/**
	 * An input/output source, used for storing the user's favorite movies.
	 */
	private FavoritesStorage favStorage;
	/**
	 * An input/output source used for storing information of
	 * movies and their cast.
	 */
	private MovieStorage movieSource;
	/**
	 * A list of movies to be listed and modified later on.
	 * Basically it contains the search results, and is used by the view.
	 */
	private List<Movie> movies;
	/**
	 * Contains the last title filter for the favorites.
	 */
	private String previousTitle="";
	/**
	 * A list of the favorite movies, which is used by the view.
	 */
	private List<Movie> filteredFavorites;
	/**
	 * A list of movies which were previously saved as favorites.
	 */
	private List<Movie> allFavorites;
	/**
	 * A list of actors and actresses, which is used by the view.
	 */
	private List<Actor> actors;
	/**
	 * A Set for storing the movies which the user saved as their favorites in one session.
	 */
	private HashSet<Movie> ratedMovies;
	/**
	 * A Map for enforcing the consistency of the search results, and the favorites list.
	 */
	private HashMap<Integer,Movie> consistencyEnforcer=new HashMap<Integer,Movie>();
	/**
	 * Constructs a DataHandler object, with the specified input/output sources.
	 * 
	 * @param movieSource Represents the public movie database
	 * @param favStorage Represents the storage for the user's favorites
	 */
	public DataHandler(MovieStorage movieSource, FavoritesStorage favStorage) {
		this.movieSource=movieSource;
		this.favStorage=favStorage;
		movies = new ArrayList<Movie>();
		actors = new ArrayList<Actor>();
		ratedMovies = new HashSet<>();
		allFavorites = new ArrayList<>();
		filteredFavorites = new ArrayList<>();
	}
	/**
	 * Loads those movies, whose title contain the specified {@code String}.
	 * Reloads the movie list which is used by the view.
	 * @param title the title of the movies to be loaded
	 * @throws InOutException if an error occurs during reading from the IO source
	 */
	public void loadMoviesByTitle(String title) throws InOutException
	{
		logger.info("Loading movies by title: {}",title);
		consistencyEnforcer.clear();
		movies.clear();
		for (Movie mov : movieSource.findMoviesByTitle(title)) {
			movies.add(mov);
			consistencyEnforcer.put(mov.getId(), mov);
			mov.setActors(movieSource.findActorsByMovie(mov));
		}
		loadAllFavorites();
	}

	/**
	 * Loads those movies, which featured the specified actor.
	 * Reloads the movie list which is used by the view.
	 * @param actor the cast member of the returning movies
	 * @throws InOutException if an error occurs during reading from the IO source
	 */
	public void loadMoviesByActor(Actor actor) throws InOutException
	{
		logger.info("Loading movies by cast member: {}",actor.getName());
		consistencyEnforcer.clear();
		movies.clear();
		for (Movie mov : actor.getMovies()) {
			movies.add(mov);
			consistencyEnforcer.put(mov.getId(), mov);
			mov.setActors(movieSource.findActorsByMovie(mov));
		}
		loadAllFavorites();
	}

	/**
	 * Loads those actors, whose name contains the specified {@code String}.
	 * Reloads the actor list which is used by the view.
	 * @param name 
	 * @throws InOutException if an error occurs during reading from the IO source
	 */
	public void loadActorsByName(String name) throws InOutException
	{
		logger.info("Loading actors by name: {}",name);
		actors.clear();
		for (Actor actor : movieSource.findActorsByName(name)) {
			actors.add(actor);
			actor.setMovies(movieSource.findMoviesByActor(actor));
		}
	}

	/**
	 * Loads those actors, who were featured by the specified movie.
	 * Reloads the actor list which is used by the view.
	 * @param movie the movie which featured the resulting actors
	 * @throws InOutException if an error occurs during reading from the IO source
	 */
	public void loadActorsByMovie(Movie movie) throws InOutException
	{
		logger.info("Loading actors by movie: {}",movie.getTitle());
		actors.clear();
		for (Actor actor : movie.getActors()) {
			actors.add(actor);
			actor.setMovies(movieSource.findMoviesByActor(actor));
		}
	}

	/**
	 * Rates the specified movie with the specified user rating.
	 * @param movie the movie to rate
	 * @param userRating the user's rating
	 * @throws RatingOutOfBound if the {@code userRating} isn't between 1.0 and 5.0
	 * @throws InOutException if an error occurs during writing to the IO source
	 * @throws AlreadyRated if the specified movie has been already rated in the ongoing session
	 */
	public void rateMovie(Movie movie, double userRating) throws RatingOutOfBound, InOutException, AlreadyRated
	{
		logger.info("Rating movie: {}",movie.getTitle());
		if (ratedMovies.contains(movie))
		{
			throw new AlreadyRated("The selected movie has been already rated.");
		}
		else
		{
			logger.info("User's rating is {}",userRating);
			movie.rate(userRating);
			movieSource.updateMovieRating(movie);
			ratedMovies.add(movie);
		}
	}

	/**
	 * Saves the specified movie as favorite.
	 * @param movie the movie to be saved as favorite
	 * @throws InOutException if an error occurs during writing to the IO source
	 * @throws AlreadyFavorite if the specified movie is already in the user's favorite list
	 */
	public void addMovieToFavorites(Movie movie) throws InOutException, AlreadyFavorite
	{
		logger.info("Saving {} as favorite.",movie.getTitle());
		if (allFavorites.contains(movie)) {
			throw new AlreadyFavorite("The selected movie is already in your favorites list.");
		} else {
			allFavorites.add(movie);
		}
		favStorage.saveToFavorites(movie);
		filterFavorites(previousTitle);
	}

	/**
	 * Removes the specified movie from the user's favorites.
	 * @param movie the movie to be removed
	 * @throws InOutException if an error occurs during changing the state of the IO source
	 */
	public void removeFromFavorites(Movie movie) throws InOutException
	{
		logger.info("Removing {} from favorites.",movie.getTitle());
		allFavorites.remove(movie);
		favStorage.removeFromFavorites(movie);
		filterFavorites(previousTitle);
	}

	/**
	 * Returns the list of movies, which is usually a search result.
	 * It is usually used by the view, so it can list the search results.
	 * @return the movie list
	 */
	public List<Movie> getMovies() {
		return movies;
	}

	/**
	 * Returns the list of the favorites, which might have been filtered earlier.
	 * @return the filtered favorites list
	 */
	public List<Movie> getFavorites() {
		return filteredFavorites;
	}

	/**
	 * Returns the list of actors, which is usually a search result. 
	 * @return the actor list
	 */
	public List<Actor> getActors()
	{
		return actors;
	}
	/**
	 * Filters the user's favorite list by the movies' titles.
	 * Reloads the favorites list used by the view.
	 * @param title the {@code String} to search for in the movies' titles.
	 */
	public void filterFavorites(String title){
		this.previousTitle=title;
		logger.info("Searching in favorites list.");
		filteredFavorites.clear();
		for (Movie movie : allFavorites) {
			if(movie.getTitle().contains(title))
			{
				filteredFavorites.add(movie);
			}
		}
	}
	/**
	 * Loads all the user's favorites into the favorites list.
	 * It also causes the list used by the view to be reloaded.
	 */
	public void loadAllFavorites()
	{
		logger.info("(Re)loading favorites list.");
		allFavorites.clear();
		try {
			for (Integer i : favStorage.loadAllFavorites())
			{
				if(consistencyEnforcer.containsKey(i)){
					allFavorites.add(consistencyEnforcer.get(i));
				}
				else{
					allFavorites.add(movieSource.GetMovieById(i));
				}
			}
			// favorites=movieSource.loadAllFavorites();
			for (Movie mov : allFavorites) {
				mov.setActors(movieSource.findActorsByMovie(mov));
			}
			filterFavorites("");
		} catch (InOutException e){
			e.printStackTrace();
		}
	}
}
