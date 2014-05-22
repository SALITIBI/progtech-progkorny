package model;

import java.util.Date;
import java.util.List;

/**
 * Class representing an actor or actress.
 * 
 * @author Tibor Salagvárdi
 */
public class Actor {
	/**
	 * The name of the actor/actress.
	 */
	private String name;
	/**
	 * The number representing how many Oscars the actor/actress currently has.
	 */
	private int numberOfOscars;
	/**
	 * The place where the actor/actress was born.
	 */
	private String placeOfBirth;
	/**
	 * The date when the actor/actress was born.
	 */
	private Date dateOfBirth;
	/**
	 * The identifier of the actor.
	 */
	private int id;
	/**
	 * The list of movies which featured the actor/actress.
	 */
	private List<Movie> movies;

	/**
	 * Constructs a new Actor object with the specified attributes.
	 * 
	 * @param name The name of the actor/actress
	 * @param numberOfOscars The number representing how many Oscars the actor/actress has
	 * @param placeOfBirth The place where the actor/actress was born
	 * @param dateOfBirth The date when the actor/actress was born
	 * @param id the identifier of the actor
	 */
	public Actor(int id, String name, int numberOfOscars, String placeOfBirth,
			Date dateOfBirth) {
		this.id = id;
		this.name = name;
		this.numberOfOscars = numberOfOscars;
		this.placeOfBirth = placeOfBirth;
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Returns the name of the actor/actress.
	 * 
	 * @return the name of the actor/actress
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the number representing how many Oscars the actor/actress has.
	 * 
	 * @return the number representing how many Oscars the actor/actress has
	 */
	public int getNumberOfOscars() {
		return numberOfOscars;
	}

	/**
	 * Returns the place where the actor/actress was born.
	 * 
	 * @return the place where the actor/actress was born
	 */
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	/**
	 * Returns the date when the actor/actress was born.
	 * 
	 * @return the date when the actor/actress was born
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Returns the identifier of the actor/actress.
	 * 
	 * @return the identifier of the actor/actress
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the list of movies which featured the actor/actress.
	 * 
	 * @return the list of movies which featured the actor/actress
	 */
	public List<Movie> getMovies() {
		return movies;
	}

	/**
	 * Sets The list of movies which featured the actor/actress.
	 * 
	 * @param movies
	 *            the list of {@link Movie} objects representing those movies
	 *            which featured the actor/actress
	 */
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	/**
	 * Returns a unique integer for each different {@code Actor} object, which
	 * is their {@code id}.
	 * 
	 * @return the identifier of the actor
	 */
	@Override
	public int hashCode() {
		return this.getId();
	}

	/**
	 * Compares this {@code Actor} with the specified Object for
	 * equality. Two {@code Actor} objects are considered equal if and only if
	 * their identifiers are equal.
	 * 
	 * @param obj the object to compare to
	 * @return {@code true} if the objects are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Actor)) {
			return false;
		}
		if (this.getId() == ((Actor) obj).getId()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the string representation of this Class.
	 * 
	 * @return the string representation of this Class
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: ").append(name).append(", No. of oscars: ")
				.append(numberOfOscars).append(" Place of birth: ")
				.append(placeOfBirth).append(" Date of Birth: ")
				.append(dateOfBirth);
		return sb.toString();
	}

}
