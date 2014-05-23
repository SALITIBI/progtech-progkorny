package model;

import java.util.List;

/**
 * Class representing a movie.
 * 
 * @author Tibor Salagvárdi
 * 
 */
public class Movie {
	/**
	 * The average of all ratings on the movie.
	 */
	private double ratings;
	/**
	 * The number representing how many times the movie has been rated.
	 */
	private int rateCount;
	/**
	 * The identifier of the movie.
	 */
	private int id;
	/**
	 * The title of the movie.
	 */
	private String title;
	/**
	 * The genre of the movie.
	 */
	private String genre;
	/**
	 * The year of release of the movie.
	 */
	private int releaseYear;
	/**
	 * 
	 */
	private List<Actor> actors;

	/**
	 * Constructs a new Movie object with the specified parameters.
	 * 
	 * @param id the identifier of the movie
	 * @param title the title of the movie
	 * @param genre the genre of the movie
	 * @param ratings the rating of the movie, which is a number between 1.0 and 5.0
	 * @param rateCount the number representing how many times the movie has been rated
	 * @param releaseDate the release date of the movie
	 */
	public Movie(int id, String title, String genre, double ratings,
			int rateCount, int releaseDate) {
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.releaseYear = releaseDate;
		this.ratings = ratings;
		this.rateCount = rateCount;
	}

	/**
	 * Rates the movie. This method takes a new rating value as a parameter and
	 * calculates the new average rating for the {@code Movie} object.
	 * 
	 * @param userRating the new rating
	 * @throws RatingOutOfBound if the specified rating's value isn't between 1.0 and 5.0
	 */
	public void rate(double userRating) throws RatingOutOfBound {
		if (userRating > 5) {
			throw new RatingOutOfBound("Your rating cannot be higher than 5!");
		}
		if (userRating < 1) {
			throw new RatingOutOfBound("Your rating cannot be lower than 1!");
		}
		ratings = (ratings * rateCount + userRating) / (++rateCount);
	}

	/**
	 * Returns the average rating of the movie.
	 * 
	 * @return the average rating of the movie
	 */
	public double getRatings() {
		return ratings;
	}

	/**
	 * Returns the identifier of the movie.
	 * 
	 * @return the identifier of the movie
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the title of the movie.
	 * 
	 * @return the title of the movie
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the genre of the movie.
	 * 
	 * @return the genre of the movie
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * Returns the year when the movie was released.
	 * 
	 * @return the year when the movie was released
	 */
	public int getReleaseYear() {
		return releaseYear;
	}

	/**
	 * Returns the number representing how many times the movie has been rated.
	 * 
	 * @return the number representing how many times the movie has been rated
	 * 
	 */
	public int getRateCount() {
		return rateCount;
	}

	/**
	 * Returns the cast of the movie.
	 * 
	 * @return the cast of the movie
	 */
	public List<Actor> getActors() {
		return actors;
	}

	/**
	 * Sets the movie's cast to the specified List of actors.
	 * 
	 * @param actors the new cast of the movie
	 */
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	/**
	 * Returns the string representation of this Class.
	 * 
	 * @return the string representation of this Class
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Title: ").append(title).append("(").append(releaseYear)
				.append(")").append(", Genre: ").append(genre)
				.append(",Rating:").append(ratings).append("(")
				.append(rateCount).append(")");
		return sb.toString();
	}

	/**
	 * Returns a unique integer for each different {@code Movie} object, which
	 * is their {@code id}.
	 * 
	 * @return the identifier of the movie
	 */
	@Override
	public int hashCode() {
		return getId();
	}

	/**
	 * Compares this {@code Movie} with the specified Object for
	 * equality. Two {@code Movie} objects are considered equal if and only if
	 * their identifiers are equal.
	 * 
	 * @param obj the object to compare to
	 * @return {@code true} if the objects are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Movie)) {
			return false;
		}
		if (this.getId() == ((Movie) obj).getId()) {
			return true;
		}
		return false;

	}

}
