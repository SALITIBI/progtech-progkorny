package IO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class for maintaining a JDBC database connection. It connects to the remote
 * database when required, and prepares the necessary {@link PreparedStatement}
 * s. It also creates a "shutdown hook" to close all these statements and the
 * connection.
 */
public class JDBCHelper {
	/**
	 * A logger for logging.
	 */
	private static Logger logger = LoggerFactory.getLogger(JDBCHelper.class);
	/**
	 * Used for maintaining a connection throughout the runtime of the software.
	 */
	private static Connection connection;

	/**
	 * {@link PreparedStatement} for selecting actors and actresses by their
	 * name.
	 */
	private static PreparedStatement selectActorsByName;
	/**
	 * {@link PreparedStatement} for selecting actors and actresses by the movie
	 * which featured them.
	 */
	private static PreparedStatement selectActorsByMovie;
	/**
	 * {@link PreparedStatement} for selecting movies by their title.
	 */
	private static PreparedStatement selectMoviesByTitle;
	/**
	 * {@link PreparedStatement} for selecting movies by their cast member.
	 */
	private static PreparedStatement selectMoviesByActor;
	/**
	 * {@link PreparedStatement} for selecting movies by their identifier.
	 */
	private static PreparedStatement selectMoviesById;
	/**
	 * {@link PreparedStatement} for updating movies' ratings.
	 */
	private static PreparedStatement updateMovieRating;
	/**
	 * SQL Expression for selecting all actors and actresses.
	 */
	private static final String SELECT_ACTORS = 
			"SELECT actor_id,actor_name,number_of_oscars,place_of_birth,date_of_birth FROM prt_ACTORS";
	/**
	 * SQL Expression for selecting actors and actresses by name.
	 */
	private static final String SELECT_ACTORS_BY_NAME = SELECT_ACTORS
			+ " WHERE actor_name LIKE ?";
	/**
	 * SQL Expression for selecting actors and actresses by the movie which
	 * featured them.
	 */
	private static final String SELECT_ACTORS_BY_MOVIE = SELECT_ACTORS
			+ " WHERE actor_id IN(SELECT actor_id FROM prt_actorsofmovies WHERE movie_id= ?)";
	/**
	 * SQL Expression for selecting all movies.
	 */
	private static final String SELECT_MOVIES = 
			"SELECT movie_id,title,genre,ratings,rate_count,release_date FROM prt_movies";
	/**
	 * SQL Expression for selecting movies by their title.
	 */
	private static final String SELECT_MOVIES_BY_TITLE = SELECT_MOVIES+
			" WHERE title LIKE ?";
	/**
	 * SQL Expression for selecting movies by their cast member.
	 */
	private static final String SELECT_MOVIES_BY_ACTOR = SELECT_MOVIES+
			" WHERE movie_id IN(SELECT movie_id FROM prt_actorsofmovies WHERE actor_id=?)";
	/**
	 * SQL Expression for selecting movies by their identifier.
	 */
	private static final String SELECT_MOVIES_BY_ID = SELECT_MOVIES+
			" WHERE movie_id=?";
	/**
	 * SQL Expression for updating movies' ratings.
	 */
	private static final String UPDATE_MOVIE_RATING = "UPDATE prt_movies SET ratings=?,rate_count=? WHERE movie_id=?";

	/**
	 * Initializer block for creating a shutdown hook to close all opened
	 * resources.
	 */
	static {
		logger.info("Creating shutdown hook for closing resources.");
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run() {
				
				try {
					closeResources();
				} catch (SQLException e) {
					logger.error(e.getMessage());
				}
			}
		});
	}

	/**
	 * Returns the connection to the database and establishes the connection
	 * if called for the first-time during runtime.
	 * @return the connection to the database
	 * @throws SQLException if an error occurs while connecting
	 */
	public static Connection getConnection() throws SQLException
	{
		if (connection == null)
		{
			logger.info("Connecting to database.");
			Properties props = new Properties();
			try {
				props.load(JDBCHelper.class.getResourceAsStream("/jdbc_credentials.properties"));
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			connection = DriverManager.getConnection(
					props.getProperty("url"),
					props.getProperty("username"),
					props.getProperty("password"));
		}

		return connection;
	}

	/**
	 * Returns a {@link PreparedStatement} for selecting actors by their name
	 * and creating it, if called for the first-time during runtime.
	 * @return the statement for querying the database
	 * @throws SQLException if an error occurs while reading from the database
	 */
	public static PreparedStatement getSelectActorsByName() throws SQLException
	{
		connection = getConnection();
		if (selectActorsByName == null)
		{
			logger.info("Preparing statement: selectActorsByName");
			selectActorsByName = connection.prepareStatement(SELECT_ACTORS_BY_NAME);
		}
		return selectActorsByName;
	}

	/**
	 * Returns a {@link PreparedStatement} for selecting actors by the movie which featured them
	 * and creating it, if called for the first-time during runtime.
	 * @return the statement for querying the database
	 * @throws SQLException if an error occurs while reading from the database
	 */
	public static PreparedStatement getSelectActorsByMovie() throws SQLException {
		connection = getConnection();
		if (selectActorsByMovie == null)
		{
			logger.info("Preparing statement: selectActorsByMovie");
			selectActorsByMovie = connection.prepareStatement(SELECT_ACTORS_BY_MOVIE);
		}
		return selectActorsByMovie;
	}
	/**
	 * Returns a {@link PreparedStatement} for selecting movies by their title
	 * and creating it, if called for the first-time during runtime.
	 * @return the statement for querying the database
	 * @throws SQLException if an error occurs while reading from the database
	 */
	public static PreparedStatement getSelectMoviesByTitle() throws SQLException 
	{
		connection = getConnection();
		if (selectMoviesByTitle == null)
		{
			logger.info("Preparing statement: selectMoviesByTitle");
			selectMoviesByTitle = connection.prepareStatement(SELECT_MOVIES_BY_TITLE);
		}
		return selectMoviesByTitle;
	}

	/**
	 * Returns a {@link PreparedStatement} for selecting movies by their cast member
	 * and creating it, if called for the first-time during runtime.
	 * @return the statement for querying the database
	 * @throws SQLException if an error occurs while reading from the database
	 */
	public static PreparedStatement getSelectMoviesByActor() throws SQLException
	{
		connection = getConnection();
		if (selectMoviesByActor == null)
		{
			logger.info("Preparing statement: selectMoviesByActor");
			selectMoviesByActor = connection.prepareStatement(SELECT_MOVIES_BY_ACTOR);
		}
		return selectMoviesByActor;
	}

	/**
	 * Returns a {@link PreparedStatement} required for updating a movie's rating
	 * and creating it, if called for the first-time during runtime.
	 * @return the statement for updating the database
	 * @throws SQLException if an error occurs while writing to the database
	 */
	public static PreparedStatement getUpdateMovieRating() throws SQLException
	{
		connection = getConnection();
		if (updateMovieRating == null)
		{
			logger.info("Preparing statement: updateMovieRating");
			updateMovieRating = connection.prepareStatement(UPDATE_MOVIE_RATING);
		}
		return updateMovieRating;
	}

	/**
	 * Returns a {@link PreparedStatement} required for finding a movie in the database by its id
	 * and creating it, if called for the first-time during runtime.
	 * @return the statement for querying the database
	 * @throws SQLException if an error occurs while writing to the database
	 */
	public static PreparedStatement getSelectMoviesById() throws SQLException
	{
		connection = getConnection();
		if (selectMoviesById == null)
		{
			logger.info("Preparing statement: selectMoviesById");
			selectMoviesById = connection.prepareStatement(SELECT_MOVIES_BY_ID);
		}
		return selectMoviesById;
	}

	/**
	 * Method for closing all resources used by the database connection, and the connection itself.
	 * @throws SQLException if an error occurs while trying to close any of these resources
	 */
	public static void closeResources() throws SQLException
	{
		if (selectActorsByName != null)
		{
			logger.info("Closing preparedStatement: selectActorsByName");
			selectActorsByName.close();
		}
		if (selectActorsByMovie != null)
		{
			selectActorsByMovie.close();
			logger.info("Closing preparedStatement: selectActorsByMovie");
		}
		if (selectMoviesByTitle != null)
		{
			selectMoviesByTitle.close();
			logger.info("Closing preparedStatement: selectMoviesByTitle");
		}
		if (selectMoviesByActor != null)
		{
			selectMoviesByActor.close();
			logger.info("Closing preparedStatement: selectMoviesByActor");
		}
		if (updateMovieRating != null)
		{
			updateMovieRating.close();
			logger.info("Closing preparedStatement: updateMovieRating");
		}
		if (connection != null)
		{
			connection.close();
			logger.info("Database connection closed.");
		}
	}

}
