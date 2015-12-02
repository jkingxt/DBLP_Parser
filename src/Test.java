import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(DB_PATH));
//
//		Label authorLabel = DynamicLabel.label("Author");
//		Label paperLabel = DynamicLabel.label("Paper");
//		
//		try (Transaction tx = graphDb.beginTx()) {
//			Node firstNode;
//			Node secondNode;
//			Relationship relationship;
//			
//			firstNode = graphDb.createNode(authorLabel);
//			firstNode.setProperty( "message", "Hello, " );
//			secondNode = graphDb.createNode(paperLabel);
//			secondNode.setProperty( "message", "World!" );
//
//			relationship = firstNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
//			relationship.setProperty( "message", "brave Neo4j " );
//			
//			System.out.println(graphDb.getNodeById(3).getProperty("message"));
//			
//			tx.success();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		graphDb.shutdown();
		

		GraphDB.open();
		
		SAXParserFactory saxParserFac = SAXParserFactory.newInstance();
        try {
			SAXParser parser = saxParserFac.newSAXParser();
			File file = new File("test.xml");
			InputStream inputStream= new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream,"UTF-8");
			    	      
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			parser.parse(is, new Parser());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GraphDB.close();
	}

}
