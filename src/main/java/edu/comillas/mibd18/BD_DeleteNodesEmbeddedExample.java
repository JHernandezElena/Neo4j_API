package edu.comillas.mibd18;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;

public class BD_DeleteNodesEmbeddedExample {

    public static void main(String[] args) {
        //TODO Crear la conexión con la base de datos embebida "/home/icai/tmp/dataNeo/data/databases/dbPrueba.db"
        //Se define el path del fichero de la base de datos
        String dbPath = "/home/icai/tmp/dataNeo/data/databases/dbPrueba.db";
        //Se crea el objeto File sobre el path
        File storDir = new File(dbPath);

        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( storDir );

        try ( Transaction tx = graphDb.beginTx() )
        {

            ResourceIterator<Node> ri =graphDb.getAllNodes().iterator();
            while (ri.hasNext()){
                Node n = ri.next();
                for (Relationship r : n.getRelationships()) {
                    r.delete();
                }
                n.delete();
            }

            tx.success();
        }

        //TODO Cerra conexion
        graphDb.shutdown();

    }

}
