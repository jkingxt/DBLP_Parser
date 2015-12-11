import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Parser extends DefaultHandler {
	boolean barticle = false;
	boolean binproceedings = false;
	boolean bproceedings = false;
	boolean bbook = false;
	boolean bincollection = false;
	boolean bphdthesis = false;
	boolean bmastersthesis = false;
	boolean bwww = false;
	boolean bauthor = false;
	boolean btitle = false;
	boolean byear = false;
	boolean bcite = false;
	boolean bjournal = false;
	boolean bvolumn = false;
	boolean bbooktitle = false;
	boolean yearExisted = false;
	boolean citeExisted = false;
	boolean journalExisted = false;
	boolean volumnExisted = false;
	boolean booktitleExisted = false;
	List<String> authors = new LinkedList<String>();
	Map<String, Long> authorsId = new HashMap<String, Long>();
	String title = "";
	String author = "";
	String year = "";
	String cite = "";
	String journal = "";
	String volumn = "";
	String booktitle = "";
	
	@Override
	public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
		switch (qName) {
		case "article": {
			barticle = true;
			break;
		}
		case "inproceedings": {
			binproceedings = true;
			break;
		}
		case "proceedings": {
			bproceedings = true;
			break;
		}
		case "book": {
			bbook = true;
			break;
		}
		case "incollection": {
			bincollection = true;
			break;
		}
		case "phdthesis": {
			bphdthesis = true;
			break;
		}
		case "mastersthesis": {
			bmastersthesis = true;
			break;
		}
		case "www": {
			bwww = true;
			break;
		}
		case "title": {
			btitle = true;
			break;
		}
		case "author": {
			bauthor = true;
			break;
		}
		case "year": {
			byear = true;
			break;
		}
		case "cite": {
			bcite = true;
			break;
		}
		case "journal": {
			bjournal = true;
			break;
		}
		case "volumn": {
			bvolumn = true;
			break;
		}
		case "booktitle": {
			bbooktitle = true;
			break;
		}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName) {
		case "article":
		case "inproceedings":
		case "proceedings":
		case "book":
		case "incollection":
		case "phdthesis":
		case "mastersthesis":
		case "www": {
			long titleId = GraphDB.createPaper(title);
			for (String author : authors) {
				Long authorId = authorsId.get(author);
				if (authorId == null) {
					authorId = GraphDB.createAuthor(author);
					authorsId.put(author, authorId);
				}
				GraphDB.createRelationship(authorId, titleId);
			}
			if (barticle) {
				GraphDB.createProperty("channel", titleId, "article");
				barticle = false;
			}
			if (binproceedings) {
				GraphDB.createProperty("channel", titleId, "inproceedings");
				binproceedings = false;
			}
			if (bproceedings) {
				GraphDB.createProperty("channel", titleId, "proceedings");
				bproceedings = false;
			}
			if (bbook) {
				GraphDB.createProperty("channel", titleId, "book");
				bbook = false;
			}
			if (bincollection) {
				GraphDB.createProperty("channel", titleId, "incollection");
				bincollection = false;
			}
			if (bphdthesis) {
				GraphDB.createProperty("channel", titleId, "phdthesis");
				bphdthesis = false;
			}
			if (bmastersthesis) {
				GraphDB.createProperty("channel", titleId, "mastersthesis");
				bmastersthesis = false;
			}
			if (bwww) {
				GraphDB.createProperty("channel", titleId, "www");
				bwww = false;
			}
			if (yearExisted) {
				GraphDB.createProperty("year", titleId, year);
				yearExisted = false;
			}
			if (citeExisted) {
				GraphDB.createProperty("cite", titleId, cite);
				citeExisted = false;
			}
			if (journalExisted) {
				GraphDB.createProperty("journal", titleId, journal);
				journalExisted = false;
			}
			if (volumnExisted) {
				GraphDB.createProperty("volumn", titleId, volumn);
				volumnExisted = false;
			}
			if (booktitleExisted) {
				GraphDB.createProperty("booktitle", titleId, booktitle);
				booktitleExisted = false;
			}
			if (title.contains("Operating System")) {
				GraphDB.createProperty("category", titleId, "Operating System");
			}
			else if (title.contains("Database")) {
				GraphDB.createProperty("category", titleId, "Database");
			}
			else if (title.contains("Web")) {
				GraphDB.createProperty("category", titleId, "Web");
			}
			else if (title.contains("Software")) {
				GraphDB.createProperty("category", titleId, "Software");
			}
			else {
				GraphDB.createProperty("category", titleId, "Other");
			}
			
			authors.clear();
			title = "";
			year = "";
			cite = "";
			journal = "";
			volumn = "";
			booktitle = "";
			break;
		}
		case "author": {
			bauthor = false;
			authors.add(author);
			author = "";
			break;
		}
		case "title": {
			btitle = false;
			break;
		}
		case "year": {
			byear = false;
			break;
		}
		case "cite": {
			bcite = false;
			break;
		}
		case "journal": {
			bjournal = false;
			break;
		}
		case "volumn": {
			bvolumn = false;
			break;
		}
		case "booktitle": {
			bbooktitle = false;
			break;
		}
		}
    }
	
	@Override
	public void characters(char ch[], int start, int length) {
		if (bauthor) {
			//System.out.println("author: " + new String(ch, start, length));
			//bauthor = false;
			//authors.add(new String(ch, start, length));
			author = author + new String(ch, start, length);
		}
		if (btitle) {
			//System.out.println("title: " + new String(ch, start, length));
			//btitle = false;
			title = new String(ch, start, length);
		}
		if (byear) {
			//System.out.println("year: " + new String(ch, start, length));
			//byear = false;
			year = new String(ch, start, length);
			yearExisted = true;
		}
		if (bcite) {
			cite = new String(ch, start, length);
			citeExisted = true;
		}
		if (bjournal) {
			journal = new String(ch, start, length);
			journalExisted = true;
		}
		if (bvolumn) {
			volumn = new String(ch, start, length);
			volumnExisted = true;
		}
		if (bbooktitle) {
			booktitle = new String(ch, start, length);
			booktitleExisted = true;
		}
	}
}
