package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;

import controller.Controller;

public class MainWindow extends JFrame {
	private Controller state;

	private JButton 		searchInPublicDB	=new JButton("Search for:");
	private JTextField		nameInPublicDB		=new JTextField();
	
	private JButton			showAllFavorites	=new JButton("Show all");
	private JButton			searchInFavorites  =new JButton("Filter");
	private JTextField		nameInFavorites	=new JTextField();
	
	private JTextField		rating				=new JTextField();

	private JRadioButton 	selectActor			= new JRadioButton("Actors");
	private JRadioButton 	selectMovie			= new JRadioButton("Movies");

	private JPanel 			mainPanel;
	private JPanel 			ratePanel;

	private MovieTab 		resultsTab;
	private MovieTab 		favoriteTab;

	private JTabbedPane 	tabbedPane;

	public static final int LOOKING_FOR_ACTOR = 0;
	public static final int LOOKING_FOR_MOVIE = 1;

	public MainWindow(Controller state, String title) {
		super(title);
		this.state = state;

		mainPanel = new JPanel();
		tabbedPane = new JTabbedPane();

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		JPanel dBSearchBar = new JPanel();
		JPanel dBSearchPane= new JPanel();
		JPanel favSearchBar = new JPanel();
		JPanel favSearchPane = new JPanel();
		dBSearchPane.setLayout(new BoxLayout(dBSearchPane, BoxLayout.PAGE_AXIS));
		favSearchPane.setLayout(new BoxLayout(favSearchPane, BoxLayout.PAGE_AXIS));
		favSearchBar.setLayout(new GridLayout(1, 0));
		favSearchBar.setMaximumSize(new Dimension(100000, 50));
		dBSearchBar.setMaximumSize(new Dimension(100000, 50));
		dBSearchBar.setLayout(new GridLayout(1, 0));
		ButtonGroup group = new ButtonGroup();
		group.add(selectActor);
		group.add(selectMovie);

		resultsTab = new MovieTab(state.getActors(), state.getMovies(), "Save");
		favoriteTab = new MovieTab(state.getActors(), state.getFavorites(),
				"Remove");
		resultsTab.setFocusable(false);
		favoriteTab.setFocusable(false);

		dBSearchBar.add(searchInPublicDB);
		dBSearchBar.add(selectMovie);
		dBSearchBar.add(selectActor);
		dBSearchBar.add(new JLabel("by name/title:"));
		dBSearchBar.add(nameInPublicDB);
		dBSearchPane.add(dBSearchBar);
		dBSearchPane.add(resultsTab);
		selectMovie.setSelected(true);
		tabbedPane.addTab("Show search results", dBSearchPane);
		
		favSearchBar.add(showAllFavorites);
		favSearchBar.add(new JLabel(" "));
		favSearchBar.add(searchInFavorites);
		JLabel moviesbytitle = new JLabel("movies by title:");
		moviesbytitle.setHorizontalAlignment(SwingConstants.CENTER);
		favSearchBar.add(moviesbytitle);
		favSearchBar.add(nameInFavorites);
		favSearchPane.add(favSearchBar);
		favSearchPane.add(favoriteTab);
		
		tabbedPane.addTab("Show favorites", favSearchPane);
		
		mainPanel.add(tabbedPane);

		this.setContentPane(mainPanel);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setMinimumSize(this.getSize());

		JLabel lab = new JLabel("Type in your rating:");
		ratePanel = new JPanel();
		ratePanel.add(lab);

		rating.setPreferredSize(new Dimension(50, 20));
		ratePanel.add(rating);
		resultsTab.getMovieTable().getTableHeader().setToolTipText("Click to sort");
	}

	public void updateMovieTable(){
		resultsTab.updateMovieTable();
	}

	public void updateActorTable() {
		resultsTab.updateActorTable();
	}

	public void updateFavActorTable() {
		favoriteTab.updateActorTable();
	}

	public void updateFavMovieTable() {
		favoriteTab.updateMovieTable();
	}
	public void addShowAllFavoritesListener(ActionListener al){
		showAllFavorites.addActionListener(al);
	}
	public void addSearchListenerForPublicDB(ActionListener al) {
		searchInPublicDB.addActionListener(al);
	}
	public void addSearchListenerForFavorites(ActionListener al) {
		searchInFavorites.addActionListener(al);
	}

	public void addMovieSelectionListener(ListSelectionListener lsl) {
		resultsTab.addSelectMovieListener(lsl);
	}

	public void addActorSelectionListener(ListSelectionListener lsl) {
		resultsTab.addSelectActorListener(lsl);
	}

	public void addFavMovieSelectionListener(ListSelectionListener lsl) {
		favoriteTab.addSelectMovieListener(lsl);
	}

	public void addMovieRateListener(ActionListener al) {
		resultsTab.addRateListener(al);
	}

	public void addFavMovieRateListener(ActionListener al) {
		favoriteTab.addRateListener(al);
	}

	public void addSaveListener(ActionListener al) {
		resultsTab.addRemoveOrSaveListener(al);
	}

	public void addRemoveListener(ActionListener al) {
		favoriteTab.addRemoveOrSaveListener(al);
	}

	public void addTabListener(ChangeListener l) {
		tabbedPane.addChangeListener(l);
	}

	public void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public int rateDialog() {
		return JOptionPane.showConfirmDialog(this, ratePanel,
				"Rate the movie!", JOptionPane.OK_CANCEL_OPTION);
	}

	public String getRating() {
		return rating.getText();
	}

	public MovieTab getSearchTab() {
		return resultsTab;
	}

	public MovieTab getFavoriteTab() {
		return favoriteTab;
	}

	public String getNameInPublicDB() {
		return nameInPublicDB.getText();
	}
	public String getNameInFavorites(){
		return nameInFavorites.getText();
	}
	public int getDataToSearchFor() {
		if (selectActor.isSelected()) {
			return LOOKING_FOR_ACTOR;
		} else {
			return LOOKING_FOR_MOVIE;
		}
	}

	public JTable getMovieTable() {
		return resultsTab.getMovieTable();
	}

	public JTable getActorTable() {
		return resultsTab.getActorTable();
	}

	public JTable getFavMovieTable() {
		return favoriteTab.getMovieTable();
	}

	public JTable getFavActorTable() {
		return favoriteTab.getActorTable();
	}

}
