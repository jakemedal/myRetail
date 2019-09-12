package util;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

// TODO: Update deprecated method calls
public class MongoDBUtility {

    public static final MongoDBUtility INSTANCE = new MongoDBUtility();

    private static final String MONGO_HOST = "localhost";
    private static final int MONGO_PORT = 27017;
    private static final String DB_NAME = "myRetail";
    private static final String COLLECTION_NAME = "products";
    private static DB productDatabase;

    private MongoDBUtility() {
        MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
        productDatabase = mongo.getDB(DB_NAME);
    }

    public void cleanUp() {
        productDatabase.getCollection(COLLECTION_NAME).remove(new BasicDBObject());
    }

    public void loadSampleTestData(String data) {
        DBObject object = (DBObject) JSON.parse(data);
        productDatabase.getCollection(COLLECTION_NAME).save(object);
    }
}
