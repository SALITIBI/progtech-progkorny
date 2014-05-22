package IOMock;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Actor;
import model.Movie;
import IO.InOutException;
import IO.MovieStorage;


public class MovieStorageMocker implements MovieStorage{
	private Movie[] movies=new Movie[]{
			new Movie(1, "Abcd", "Bbcd", 0, 0, 2005),
			new Movie(2, "Bbcd", "Cbcd", 0, 0, 2004),
			new Movie(3, "Cbcd", "Dbcd", 0, 0, 2003),
			new Movie(4, "Dbcd", "Abcd", 0, 0, 2003),
			new Movie(5, "Ebcd", "Ebcd", 0, 0, 2002),
	};
	private Actor[] actors=new Actor[]{
		new Actor(1, "Abcd", 0, "E", new Date()),
		new Actor(1, "Bbcd", 1, "B", new Date()),
		new Actor(1, "Cbcd", 3, "D", new Date()),
		new Actor(1, "Dbcd", 2, "C", new Date()),
		new Actor(1, "Ebcd", 0, "A", new Date()),
	};
	private Map<Movie,List<Actor>> actorsOfMovie=new HashMap<Movie,List<Actor>>();
	private Map<Actor,List<Movie>> moviesOfActor=new HashMap<Actor,List<Movie>>();
	public MovieStorageMocker(){
		actorsOfMovie.put(movies[0], new ArrayList<Actor>());
		actorsOfMovie.get(movies[0]).add(actors[0]);
		actorsOfMovie.get(movies[0]).add(actors[1]);
		actorsOfMovie.get(movies[0]).add(actors[2]);
		
		actorsOfMovie.put(movies[1], new ArrayList<Actor>());
		actorsOfMovie.get(movies[1]).add(actors[2]);
		actorsOfMovie.get(movies[1]).add(actors[3]);
		actorsOfMovie.get(movies[1]).add(actors[4]);
		
		actorsOfMovie.put(movies[2], new ArrayList<Actor>());
		actorsOfMovie.get(movies[2]).add(actors[2]);
		actorsOfMovie.get(movies[2]).add(actors[3]);
		
		actorsOfMovie.put(movies[3], new ArrayList<Actor>());
		actorsOfMovie.get(movies[3]).add(actors[1]);
		actorsOfMovie.get(movies[3]).add(actors[2]);
		
		actorsOfMovie.put(movies[4], new ArrayList<Actor>());
		actorsOfMovie.get(movies[4]).add(actors[0]);
		
		moviesOfActor.put(actors[0], new ArrayList<Movie>());
		moviesOfActor.get(actors[0]).add(movies[0]);
		moviesOfActor.get(actors[0]).add(movies[4]);
		
		moviesOfActor.put(actors[1], new ArrayList<Movie>());
		moviesOfActor.get(actors[1]).add(movies[3]);
		moviesOfActor.get(actors[1]).add(movies[0]);
		
		moviesOfActor.put(actors[2], new ArrayList<Movie>());
		moviesOfActor.get(actors[2]).add(movies[1]);
		moviesOfActor.get(actors[2]).add(movies[2]);
		moviesOfActor.get(actors[2]).add(movies[3]);
		moviesOfActor.get(actors[2]).add(movies[0]);
		
		moviesOfActor.put(actors[3], new ArrayList<Movie>());
		moviesOfActor.get(actors[3]).add(movies[1]);
		moviesOfActor.get(actors[3]).add(movies[2]);
		
		moviesOfActor.put(actors[4], new ArrayList<Movie>());
		moviesOfActor.get(actors[4]).add(movies[1]);
		
	}
	@Override
	public List<Actor> findActorsByName(String name) throws InOutException {
		List<Actor> result=new ArrayList<Actor>();
		for (Actor actor : actors) {
			if(actor.getName().contains(name))
			{
				result.add(actor);
			}
		}
		return result;
	}

	@Override
	public List<Movie> findMoviesByTitle(String title) throws InOutException {
		List<Movie> result = new ArrayList<Movie>();
		for (Movie mov : movies) {
			if(mov.getTitle().contains(title))
			{
				result.add(mov);
			}
		}
		return result;
	}

	@Override
	public void updateMovieRating(Movie movie) throws InOutException{
		
	}

	@Override
	public List<Actor> findActorsByMovie(Movie movie) throws InOutException {
		return actorsOfMovie.get(movie);
	}

	@Override
	public List<Movie> findMoviesByActor(Actor actor) throws InOutException {
		return moviesOfActor.get(actor);
	}

	@Override
	public Movie GetMovieById(int id) throws InOutException {
		for (Movie mov : movies) {
			if(mov.getId()==id)
			{
				return mov;
			}
		}
		return null;
	}
	public Movie[] getMovies() {
		return movies;
	}
	public Actor[] getActors() {
		return actors;
	}
	public Map<Movie, List<Actor>> getActorsOfMovie() {
		return actorsOfMovie;
	}
	public Map<Actor, List<Movie>> getMoviesOfActor() {
		return moviesOfActor;
	}	
}
