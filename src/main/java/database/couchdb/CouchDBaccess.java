package database.couchdb;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbException;
import org.lightcouch.CouchDbProperties;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 22 June Saturday 2024 - 14:39
 */
public class CouchDBaccess {
    private CouchDbClient client;
    private String databaseName;
    private String mainUser;
    private String mainUserPassword;
    private final static String PROTOCOL = "http";
    private final static int PORT = 5984;  //Set the port CouchDB is running on (5984)
    private final static String HOST_SERVER = "localhost";

    public CouchDBaccess(String databaseName, String mainUser, String mainUserPassword) {
        this.databaseName = databaseName;
        this.mainUser = mainUser;
        this.mainUserPassword = mainUserPassword;
    }

    private CouchDbProperties createProperties() {
        CouchDbProperties properties = new CouchDbProperties();
        properties.setDbName(databaseName);
        //Create the database if it didn't already e xist
        properties.setCreateDbIfNotExist(true);
        properties.setHost(HOST_SERVER);
        properties.setPort(PORT);
        properties.setProtocol(PROTOCOL);
        properties.setUsername(mainUser);
        properties.setPassword(mainUserPassword);
        return properties;
    }

    private void openConnection() {
        //Create the database client and open the connection with given properties
        try {
            client = new CouchDbClient(createProperties());
        } catch (CouchDbException couchDbException) {
            System.out.println(couchDbException.getMessage());
        }
    }

    public CouchDbClient getClient() {
        if (client == null) {
            openConnection();
        }
        return  client;
    }
}
