package io;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Actor;
import model.Movie;

/**
 * Class for handling a database connection, using the OJDBC driver. It has methods for reading from the database,
 * and manipulating it.
 * 
 * @author Tibor Salagvárdi
 */
public class MovieStorageImpl implements MovieStorage
{
	/**
	 * A logger for logging.
	 */
	private static Logger logger = LoggerFactory.getLogger(MovieStorageImpl.class);
	/**
	 * {@inheritDoc}
	 * @throws InOutException if an error occurs while trying to load the specified actors from the database
	 */
	public List<Actor> findActorsByName(String name) throws InOutException
	{
		List<Actor> actors = new ArrayList<Actor>();
		name = "%" + name + "%";
		try {
			PreparedStatement selectActorsByName = JDBCHelper.getSelectActorsByName();
			selectActorsByName.setString(1, name);
			try (ResultSet rs = selectActorsByName.executeQuery())
			{
				while (rs.next())
				{
					actors.add(new Actor(rs.getInt(1), rs.getString(2),
							rs.getInt(3), rs.getString(4), rs.getDate(5)));
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new InOutException("Error while loading from database!");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new InOutException("Error while loading from database!");
		}
		return actors;
	}

	/**
	 * {@inheritDoc}
	 * @throws InOutException if an error occurs while trying to load the specified movies from the database
	 */
	public List<Movie> findMoviesByTitle(String title) throws InOutException
	{
		List<Movie> movies = new ArrayList<Movie>();
		title = "%" + title + "%";
		try {
			PreparedStatement selectMoviesByTitle = JDBCHelper.getSelectMoviesByTitle();
			selectMoviesByTitle.setString(1, title);
			try (ResultSet rs = selectMoviesByTitle.executeQuery()) {
				while (rs.next()) {
					movies.add(new Movie(rs.getInt(1), rs.getString(2),
							rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6)));
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new InOutException("Error while loading from database!");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new InOutException("Error while loading from database!");
		}
		return movies;
	}

	/**
	 * {@inheritDoc}
	 * @throws InOutException if an error occurs while updating the database
	 */
	public void updateMovieRating(Movie movie) throws InOutException
	{
		try {
			PreparedStatement updateMovieRating = JDBCHelper.getUpdateMovieRating();
			updateMovieRating.setDouble(1, movie.getRatings());
			updateMovieRating.setInt(2, movie.getRateCount());
			updateMovieRating.setInt(3, movie.getId());
			updateMovieRating.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new InOutException("Error while writing to database!");
		}

	}

	/**
	 * {@inheritDoc}
	 * @throws InOutException if an error occurs while reading from the database
	 */
	public List<Actor> findActorsByMovie(Movie movie) throws InOutException
	{
		PreparedStatement selectActorsByMovie=null;
		List<Actor> actors = new ArrayList<Actor>();
		try {
			selectActorsByMovie = JDBCHelper.getSelectActorsByMovie();
			selectActorsByMovie.setInt(1, movie.getId());
			try (ResultSet rs = selectActorsByMovie.executeQuery()) {
				while (rs.next()) {
					actors.add(new Actor(rs.getInt(1), rs.getString(2),
							rs.getInt(3), rs.getString(4), rs.getDate(5)));
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new InOutException("Error while loading from database!");
		}
		return actors;
		
	}

	/**
	 * {@inheritDoc}
	 * @throws InOutException if an error occurs while reading from the database
	 */
	public List<Movie> findMoviesByActor(Actor actor) throws InOutException
	{
		List<Movie> movies = new ArrayList<Movie>();
		try {
			PreparedStatement selectMoviesByActor = JDBCHelper.getSelectMoviesByActor();
			selectMoviesByActor.setInt(1, actor.getId());
			try (ResultSet rs = selectMoviesByActor.executeQuery()) {
				while (rs.next())
				{
					movies.add(new Movie(rs.getInt(1), rs.getString(2),
							rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6)));
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new InOutException("Error while loading from database!");
		}
		return movies;
	}
	/**
	 * {@inheritDoc}
	 * @throws InOutException if an error occurs while reading from the database
	 */
	@Override
	public Movie GetMovieById(int id) throws InOutException
	{
		Movie movie = null;
		PreparedStatement selectMovieById;
		try {
			selectMovieById = JDBCHelper.getSelectMoviesById();
			selectMovieById.setInt(1, id);
			try (ResultSet rs = selectMovieById.executeQuery()) {
				while (rs.next())
				{
					movie = new Movie(rs.getInt(1), rs.getString(2),
							rs.getString(3), rs.getDouble(4), rs.getInt(5),rs.getInt(6));
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new InOutException("Error while loading from database!");
		}

		return movie;
	}
}
