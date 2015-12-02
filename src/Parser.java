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
		if (qName.equals("article")) {
			barticle = true;
		}
		if (qName.equals("inproceedings")) {
			binproceedings = true;
		}
		if (qName.equals("proceedings")) {
			bproceedings = true;
		}
		if (qName.equals("book")) {
			bbook = true;
		}
		if (qName.equals("incollection")) {
			bincollection = true;
		}
		if (qName.equals("incollection")) {
			bincollection = true;
		}
		if (qName.equals("phdthesis")) {
			bphdthesis = true;
		}
		if (qName.equals("phdthesis")) {
			bphdthesis = true;
		}
		if (qName.equals("mastersthesis")) {
			bmastersthesis = true;
		}
		if (qName.equals("www")) {
			bwww = true;
		}
		if (qName.equals("title")) {
			btitle = true;
		}
		if (qName.equals("author")) {
			bauthor = true;
		}
		if (qName.equals("year")) {
			byear = true;
		}
		if (qName.equals("cite")) {
			bcite = true;
		}
		if (qName.equals("volumn")) {
			bvolumn = true;
		}
		if (qName.equals("booktitle")) {
			bbooktitle = true;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("paper") || qName.equals("incollection")) {
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
				GraphDB.createProperty("channel", titleId, "paper");
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
			
			authors.clear();
			title = "";
			year = "";
			cite = "";
			journal = "";
			volumn = "";
			booktitle = "";
		}
		if (qName.equals("author")) {
			bauthor = false;
			authors.add(author);
			author = "";
		}
		if (qName.equals("title")) {
			btitle = false;
		}
		if (qName.equals("year")) {
			byear = false;
		}
		if (qName.equals("cite")) {
			bcite = false;
		}
		if (qName.equals("journal")) {
			bjournal = false;
		}
		if (qName.equals("volumn")) {
			bvolumn = false;
		}
		if (qName.equals("booktitle")) {
			bbooktitle = false;
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
