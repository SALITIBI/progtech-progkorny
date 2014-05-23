package IO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Movie;
/**
 * Class for handling a favorite storage. This implementation uses DOM.
 * @author Tibor Salagvárdi
 */
public class FavoritesStorageImpl implements FavoritesStorage
{
	/**
	 * The DOM tree for XML document handling.
	 */
	private Document doc;
	/**
	 * A logger for logging.
	 */
	private static Logger logger = LoggerFactory.getLogger(FavoritesStorageImpl.class);
	/**
	 * Constructs a {@code FavoritesStorageImpl} object, initializes the DOM-tree, and -if possible-
	 * stores the XML document's content in it. If the file does not exist then it creates a new empty one.
	 */
	public FavoritesStorageImpl()
	{
		try(InputStream in = new FileInputStream("favorites.xml")) {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(in);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			createNewFavStorage();
		} catch (ParserConfigurationException e) {
			logger.error(e.getMessage());
		} catch (SAXException e) {
			logger.error(e.getMessage());
			createNewFavStorage();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	/**
	 * {@inheritDoc}
	 */
	public List<Integer> loadAllFavorites() {
		
		List<Integer> favIndeces = new ArrayList<Integer>();
		NodeList movies = doc.getElementsByTagName("movie");
		for (int i = 0; i < movies.getLength(); i++) {
			Element mov = (Element) movies.item(i);
			favIndeces.add(Integer.parseInt(mov.getElementsByTagName("id").item(0).getTextContent()));
		}
		return favIndeces;
	}
	/**
	 * {@inheritDoc}
	 */
	public void saveToFavorites(Movie movie)
	{
		NodeList nl = doc.getElementsByTagName("movies");
		Element mov = doc.createElement("movie");
		Element id = doc.createElement("id");
		id.setTextContent(String.valueOf(movie.getId()));
		mov.appendChild(id);
		nl.item(0).appendChild(mov);
		writeDOMTreeToFile();
	}
	/**
	 * {@inheritDoc}
	 */
	public void removeFromFavorites(Movie movie)
	{
		NodeList movies = doc.getElementsByTagName("movie");
		for (int i = 0; i < movies.getLength(); i++)
		{
			Element mov = (Element) movies.item(i);
			int id = Integer.parseInt(mov.getElementsByTagName("id").item(0).getTextContent());
			if (id == movie.getId())
			{
				mov.getParentNode().removeChild(mov);
			}
		}
		writeDOMTreeToFile();
	}
	/**
	 * Creates a new empty DOM tree for storing favorites.
	 */
	private void createNewFavStorage()
	{
		logger.info("Creating new empty favorites storage XML from scratch.");
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.newDocument();
			Element favorites = doc.createElement("favorites");
			doc.appendChild(favorites);
			Element movies = doc.createElement("movies");
			favorites.appendChild(movies);
			writeDOMTreeToFile();

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Writes the DOM tree to the XML document.
	 */
	private void writeDOMTreeToFile() {
		try(FileOutputStream out = new FileOutputStream("favorites.xml")) {
			TransformerFactory tff = TransformerFactory.newInstance();
			Transformer tf = tff.newTransformer();
			tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			tf.transform(new DOMSource(doc), new StreamResult(out));
		} catch (FileNotFoundException e){
			//TODO
		} catch (TransformerException e){
		} catch (IOException e1) {
		}
	}
}