package edu.comillas.mibd18;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

import java.io.File;
import java.util.Map;

public class BB_ReadEmbeddedExample {

    public static void main(String[] args) {
        //TODO Crear la conexión con la base de datos embebida "/home/icai/tmp/dataNeo/data/databases/dbPrueba.db"
        //Se define el path del fichero de la base de datos
        String dbPath = "/home/icai/tmp/dataNeo/data/databases/dbPrueba.db";
        //Se crea el objeto File sobre el path
        File storDir = new File(dbPath);
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( storDir );

        //TODO escribir la consulta en CYPHER que devuelve todos los nodos
        String query = "MATCH (e) RETURN e as nodo";

        try ( Transaction tx = graphDb.beginTx() ) {
            Result res = graphDb.execute(query);

            while (res.hasNext()) {
                Map<String, Object> row = res.next();
                for (Map.Entry<String,Object> entry: row.entrySet()) {
                    Node n = (Node) entry.getValue();
                    System.out.printf("%s = %s%n", entry.getKey(), n.getProperty("message"));
                }
            }



            //OTRA FORMA DE HACER LO MISMO
            System.out.println();
            ResourceIterator<Node> ri =graphDb.getAllNodes().iterator();
            while (ri.hasNext()){
                Node n = ri.next();
                System.out.println("Labels: " +n.getLabels().toString()+" ID: "+ n.getId()+" Property: "+n.getProperty("message"));
            }

            System.out.println();
            Node n = graphDb.findNode(A_Definitions.MyLabels.TEST,"message","Hello");
            System.out.println("Labels: " +n.getLabels().toString()+" ID: "+ n.getId()+" Property: "+n.getProperty("message"));

            n = graphDb.findNode(A_Definitions.MyLabels.TEST,"message","World!");
            System.out.println("Labels: " +n.getLabels().toString()+" ID: "+ n.getId()+" Property: "+n.getProperty("message"));


        }

        //TODO cerrar la conexion con la base de datos
        graphDb.shutdown();
    }
}