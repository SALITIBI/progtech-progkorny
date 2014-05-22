package IO;

import java.util.List;

import model.Actor;
import model.Movie;

/**
 * Interface for defining an Input/Output source for the public movie database.
 * 
 * @author Tibor Salagvárdi
 * 
 */
public interface MovieStorage {
	/**
	 * Gets those actors from the IO source, whose name contain the specified String.
	 * 
	 * @param name the name of the actors to be found
	 * @return the list of the actors found with the name containing the specified name
	 */
	public List<Actor> findActorsByName(String name);

	/**
	 * Gets those movies from the IO source, whose title contain the specified String.
	 * 
	 * @param title the title of the movies to be found
	 * @return the list of the movies found with the specified title
	 */
	public List<Movie> findMoviesByTitle(String title);

	/**
	 * Updates the specified movie's rating in the IO source.
	 * 
	 * @param movie the movie to be updated
	 */
	public void updateMovieRating(Movie movie);

	/**
	 * Finds those actors who were featured by the specific movie.
	 * 
	 * @param movie the movie which featured the resulting actors
	 * @return a list of actors who were featured by the specific movie
	 */
	public List<Actor> findActorsByMovie(Movie movie);

	/**
	 * Finds those movies in the IO source, which featured the specified actor.
	 * 
	 * @param actor the actor who was featured by the returning movies
	 * @return the list of movies, which featured the specified actor
	 */
	public List<Movie> findMoviesByActor(Actor actor);
	/**
	 * Finds that movie in the IO source which has the specified identifier.
	 * @param id the identifier of the movie
	 * @return a movie which has the same id as the specified one
	 */
	public Movie GetMovieById(int id);

}
