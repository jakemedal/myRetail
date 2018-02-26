package util;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.apache.log4j.Logger;

public class MongoDBUtility {
    private static final Logger LOG = Logger.getLogger(MongoDBUtility.class);

    public static final MongoDBUtility INSTANCE = new MongoDBUtility();

    private static final String MONGO_HOST = "localhost";
    private static final int MONGO_PORT = 27017;
    private static final String DB_NAME = "myRetail";
    private static final String COLLECTION_NAME = "products";

    private static DB productDatabase;

    private MongoDBUtility() {
        MongoClient mongo = null;
        try {
            mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
        } catch (UnknownHostException e) {
            LOG.error("Unable to initialize MongoDB database.");
            e.printStackTrace();
        }
        productDatabase = mongo.getDB(DB_NAME);
    }

    public void cleanUp() throws UnknownHostException {
        LOG.info("Cleaning up MongoDB database...");
        productDatabase.getCollection(COLLECTION_NAME).remove(new BasicDBObject());
    }
}
