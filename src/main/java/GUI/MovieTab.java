package GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import model.Actor;
import model.Movie;

public class MovieTab extends JPanel {
	private JButton				rateMovie		= new JButton("Rate Movie");
	private JButton				removeOrSave	= new JButton();
	private JTable				actorTable;
	private JTable				movieTable;

	private MovieViewHelper		mvh;
	private ActorViewHelper		avh;

	public MovieTab(List<Actor> actorsToDisplay, List<Movie> moviesToDisplay,String arg0) {
		removeOrSave.setMaximumSize(new Dimension(200,30));
		rateMovie.setMaximumSize(new Dimension(200,30));
		removeOrSave.setText(arg0);
		avh = new ActorViewHelper(actorsToDisplay);
		actorTable = new JTable(avh);
		actorTable.setFillsViewportHeight(true);
		actorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		actorTable.setRowSorter(new TableRowSorter<AbstractTableModel>(avh));

		mvh = new MovieViewHelper(moviesToDisplay);
		movieTable = new JTable(mvh);
		movieTable.setFillsViewportHeight(true);
		movieTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		movieTable.setRowSorter(new TableRowSorter<AbstractTableModel>(mvh));

		JPanel movieButtons = new JPanel();
		JPanel movieContainer = new JPanel();
		movieContainer.setLayout(new BoxLayout(movieContainer,
				BoxLayout.PAGE_AXIS));
		JPanel actorContainer = new JPanel();
		actorContainer.setLayout(new BoxLayout(actorContainer,
				BoxLayout.PAGE_AXIS));
		JPanel Container = new JPanel(new GridLayout(2, 1));

		movieButtons.setLayout(new BoxLayout(movieButtons, BoxLayout.PAGE_AXIS));
		movieButtons.add(rateMovie);
		movieButtons.add(removeOrSave);
		JScrollPane movieTablePane = new JScrollPane(movieTable,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JScrollPane actorTablePane = new JScrollPane(actorTable,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		movieContainer.add(new JLabel("Movies:"));
		movieContainer.add(movieTablePane);
		actorContainer.add(new JLabel("Actors:"));
		actorContainer.add(actorTablePane);
		Container.add(movieContainer);
		Container.add(actorContainer);
		Container.setPreferredSize(new Dimension(500,300));
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(movieButtons);
		this.add(Container);
	}

	public void addSelectMovieListener(ListSelectionListener lsl) {
		movieTable.getSelectionModel().addListSelectionListener(lsl);
	}

	public void addSelectActorListener(ListSelectionListener lsl) {
		actorTable.getSelectionModel().addListSelectionListener(lsl);
	}

	public void addRateListener(ActionListener al) {
		rateMovie.addActionListener(al);
	}

	public void addRemoveOrSaveListener(ActionListener al) {
		removeOrSave.addActionListener(al);
	}

	public void updateActorTable() {

		avh.fireTableDataChanged();
	}

	public void updateMovieTable() {
		mvh.fireTableDataChanged();
	}

	/**
	 * @return the actorTable
	 */
	public JTable getActorTable() {
		return actorTable;
	}

	/**
	 * @return the movieTable
	 */
	public JTable getMovieTable() {
		return movieTable;
	}
}
