package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Movie;

public class MovieViewHelper extends AbstractTableModel
{

	String[] columnNames = new String[] { "Title", "Genre", "Rating","No. of Ratings", "Year of release" };
	List<Movie> movies;

	public MovieViewHelper(List<Movie> movies) {
		this.movies = movies;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public int getRowCount() {
		return movies.size();
	}

	public Class<?> getColumnClass(int c) {
		switch (c) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return Double.class;
		case 3:
			return Integer.class;
		case 4:
			return Integer.class;
		default:
			return null;
		}
	}

	public Object getValueAt(int arg0, int arg1) {
		Movie mov = movies.get(arg0);
		switch (arg1) {
		case 0:
			return mov.getTitle();
		case 1:
			return mov.getGenre();
		case 2:
			return mov.getRatings();
		case 3:
			return mov.getRateCount();
		case 4:
			return mov.getReleaseYear();
		default:
			return null;
		}
	}

	public Movie getValueAt(int arg0) {
		return movies.get(arg0);
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

}
