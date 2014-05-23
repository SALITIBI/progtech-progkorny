package io;

import java.util.List;

import model.Movie;
/**
 * Interface for handling a favorite movie storage.
 * @author Tibor Salagvárdi
 */
public interface FavoritesStorage {
	/**
	 * Loads all favorites from the IO source, and returns it as a {@link List}.
	 * @return the list of the user's favorites
	 */
	public List<Integer> loadAllFavorites();
	/**
	 * Saves the specified movie to the IO source.
	 * @param movie the movie to be saved
	 */
	public void saveToFavorites(Movie movie);
	/**
	 * Removes the specified movie from the IO source.
	 * @param movie the movie to be removed
	 */
	public void removeFromFavorites(Movie movie);
}
