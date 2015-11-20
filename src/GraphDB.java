import java.io.File;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class GraphDB {
	private static enum RelTypes implements RelationshipType
	{
	    PUBLISH
	}
	public static String DB_PATH = "C:/Users/Xiatao/Documents/Neo4j/default.graphdb";
	public static GraphDatabaseService graphDb = null;
	static Label authorLabel = DynamicLabel.label("Author");
	static Label paperLabel = DynamicLabel.label("Paper");
	public static void open() {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(DB_PATH));
	}
	public static void close() {
		graphDb.shutdown();
	}
	
	public static long createAuthor(String name) {
		Node curAuthor = null;
		try (Transaction tx = graphDb.beginTx()) {
			
			curAuthor = graphDb.createNode(authorLabel);
			curAuthor.setProperty("name", name);
			tx.success();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return curAuthor.getId();
	}
	
	public static long createPaper(String title) {
		Node curPaper = null;
		try (Transaction tx = graphDb.beginTx()) {
			
			curPaper = graphDb.createNode(paperLabel);
			curPaper.setProperty("title", title);
			tx.success();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return curPaper.getId();
	}
	
	public static void createRelationship(long authorId, long paperId) {
		try (Transaction tx = graphDb.beginTx()) {
			graphDb.getNodeById(authorId).createRelationshipTo(graphDb.getNodeById(paperId), RelTypes.PUBLISH);
			tx.success();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
