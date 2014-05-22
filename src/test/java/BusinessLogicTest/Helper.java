package BusinessLogicTest;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.Actor;
import model.Movie;


public class Helper {
	public static boolean containsTheSameMovies(List<Movie> expected,List<Movie> actual){
		Collections.sort(expected,new Comparator<Movie>() {
			@Override
			public int compare(Movie o1, Movie o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}
		});
		Collections.sort(actual,new Comparator<Movie>() {
			@Override
			public int compare(Movie o1, Movie o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}
		});
		boolean isEquals=true;
		for (int i = 0; i < actual.size(); i++) {
			try{
				if(!expected.get(i).equals(actual.get(i)))
				{
					isEquals=false;
					break;
				}
			}catch(IndexOutOfBoundsException e)
			{
				isEquals=false;
				break;
			}
		}
		return isEquals;
	}
	public static boolean containsTheSameActors(List<Actor> expected,List<Actor> actual){
		Collections.sort(expected,new Comparator<Actor>() {
			@Override
			public int compare(Actor o1, Actor o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		Collections.sort(actual,new Comparator<Actor>() {
			@Override
			public int compare(Actor o1, Actor o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		boolean isEquals=true;
		for (int i = 0; i < actual.size(); i++) {
			try{
				if(!expected.get(i).equals(actual.get(i)))
				{
					isEquals=false;
					break;
				}
			}catch(IndexOutOfBoundsException e)
			{
				isEquals=false;
				break;
			}
		}
		return isEquals;
	}
}
