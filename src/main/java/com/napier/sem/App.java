package com.napier.sem;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class App {
    public static void main(String[] args) {
        // Use try-with-resources to automatically close MongoClient
        try (MongoClient mongoClient = new MongoClient("localhost", 27000)) {
            // Get a database - will create when we use it
            MongoDatabase database = mongoClient.getDatabase("mydb");
            // Get a collection from the database
            MongoCollection<Document> collection = database.getCollection("test");
            // Create a document to store
            Document doc = new Document("name", "Kevin Sim")
                    .append("class", "Software Engineering Methods")
                    .append("year", "2021")
                    .append("result", new Document("CW", 95).append("EX", 85));
            // Add document to collection
            collection.insertOne(doc);

            // Check document in collection
            Document myDoc = collection.find().first();
            System.out.println(myDoc.toJson());
        }
    }
}
