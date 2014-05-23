package gui;

import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Actor;

public class ActorViewHelper extends AbstractTableModel
{
	String[] columnNames = new String[] { "Name", "No. of Oscars",
			"Place of birth", "Date of birth" };
	private List<Actor> actors;

	public ActorViewHelper(List<Actor> actors)
	{
		this.actors = actors;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int col)
	{
		return columnNames[col];
	}

	public int getRowCount()
	{
		return actors.size();
	}

	public Class<?> getColumnClass(int c)
	{
		switch (c) {
		case 0:
			return String.class;
		case 1:
			return Integer.class;
		case 2:
			return String.class;
		case 3:
			return Date.class;

		default:
			return null;
		}
	}

	public Object getValueAt(int arg0, int arg1)
	{
		switch (arg1) {
		case 0:
			return actors.get(arg0).getName();
		case 1:
			return actors.get(arg0).getNumberOfOscars();
		case 2:
			return actors.get(arg0).getPlaceOfBirth();
		case 3:
			return actors.get(arg0).getDateOfBirth();
		default:
			return null;
		}
	}

	public Actor getValueAt(int arg0)
	{
		return actors.get(arg0);
	}

	public boolean isCellEditable(int row, int col)
	{
		return false;
	}

}
