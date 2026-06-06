package com.megalab.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongo {

    private static final String URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "megalab";

    private static MongoDatabase database;

    public static MongoDatabase conectar() {
        if (database == null) {
            MongoClient client = MongoClients.create(URI);
            database = client.getDatabase(DB_NAME);
        }
        return database;
    }
}