package IOMock;
import io.InOutException;

import java.util.ArrayList;
import java.util.List;

import model.Movie;


public class FavoriteStorageMocker implements io.FavoritesStorage {
	List<Integer> favIds=new ArrayList<Integer>();
	public FavoriteStorageMocker(){
		favIds.add(1);
		favIds.add(2);
	}
	@Override
	public List<Integer> loadAllFavorites() {
		return favIds;
	}
	@Override
	public void saveToFavorites(Movie movie){
		favIds.add(movie.getId());
		
	}
	@Override
	public void removeFromFavorites(Movie movie) {
		favIds.remove(new Integer(movie.getId()));
	}
}
