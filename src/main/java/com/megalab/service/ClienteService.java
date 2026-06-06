package com.megalab.service;

import com.megalab.database.ConexionMongo;
import com.megalab.model.Cliente;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;




import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.model.IndexOptions;

import com.mongodb.MongoWriteException;

public class ClienteService {

    private MongoCollection<Document> collection;

    public ClienteService() {

        collection = ConexionMongo.conectar().getCollection("clientes");
        collection.createIndex(new Document("cedula", 1), new IndexOptions().unique(true));

    }

    public boolean agregarCliente(String nombre, String cedula, String telefono) {
        try {
            Document doc = new Document("nombre", nombre)
                    .append("cedula", cedula)
                    .append("telefono", telefono);

            collection.insertOne(doc);
            return true;

        } catch (MongoWriteException e) {
            return false; // duplicado detectado por Mongo
        }
    }

    public List<Cliente> obtenerClientes() {
        List<Cliente> lista = new ArrayList<>();

        for (Document doc : collection.find()) {
            lista.add(new Cliente(
                    doc.getObjectId("_id").toString(),
                    doc.getString("nombre"),
                    doc.getString("cedula"),
                    doc.getString("telefono")
            ));
        }

        return lista;
    }

    public void eliminarCliente(String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }

    public boolean actualizarCliente(String id, String nombre, String cedula, String telefono) {
        try {
            Document existente = collection.find(new Document("cedula", cedula)).first();

            if (existente != null && !existente.getObjectId("_id").toString().equals(id)) {
                return false;
            }

            Document update = new Document("$set",
                    new Document("nombre", nombre)
                            .append("cedula", cedula)
                            .append("telefono", telefono)
            );

            collection.updateOne(
                    new Document("_id", new ObjectId(id)),
                    update
            );

            return true;

        } catch (MongoWriteException e) {
            return false;
        }
    }


    // Validaciones
    public boolean existeCedula(String cedula) {
        Document query = new Document("cedula", cedula);
        return collection.find(query).first() != null;
    }

}