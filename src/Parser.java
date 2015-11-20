import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Parser extends DefaultHandler {
	String content = null;
	boolean bpaper = false;
	boolean bauthor = false;
	boolean btitle = false;
	boolean byear = false;
	List<String> authors = new LinkedList<String>();
	Map<String, Long> authorsId = new HashMap<String, Long>();
	String title = null;
	
	@Override
	public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
		if (qName.equals("paper") || qName.equals("incollection")) {
			bpaper = true;
		}
		if (qName.equals("author")) {
			bauthor = true;
		}
		if (qName.equals("title")) {
			btitle = true;
		}
		if (qName.equals("year")) {
			byear = true;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("paper") || qName.equals("incollection")) {
			bpaper = false;
			long titleId = GraphDB.createPaper(title);
			for (String author : authors) {
				Long authorId = authorsId.get(author);
				if (authorId == null) {
					authorId = GraphDB.createAuthor(author);
				}
				GraphDB.createRelationship(authorId, titleId);
			}
		}
		if (qName.equals("author")) {
			bauthor = false;
		}
		if (qName.equals("title")) {
			btitle = false;
		}
		if (qName.equals("year")) {
			byear = false;
		}
    }
	
	@Override
	public void characters(char ch[], int start, int length) {
		if (bauthor) {
			//System.out.println("author: " + new String(ch, start, length));
			//bauthor = false;
			authors.add(new String(ch, start, length));
		}
		if (btitle) {
			//System.out.println("title: " + new String(ch, start, length));
			//btitle = false;
			title = new String(ch, start, length);
		}
		if (byear) {
			//System.out.println("year: " + new String(ch, start, length));
			//byear = false;
		}
	}
}