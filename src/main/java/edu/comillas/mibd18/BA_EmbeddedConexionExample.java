package edu.comillas.mibd18;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

import java.io.File;

public class BA_EmbeddedConexionExample {

    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }


    public static void main(String[] args){
        //Se define el path del fichero de la base de datos
        String dbPath = "/home/icai/tmp/dataNeo/data/databases/dbPrueba.db";
        //Se crea el objeto File sobre el path
        File storDir = new File(dbPath);

        //Se crea la base de datos en el directorio si NO existe

        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( storDir );
        registerShutdownHook(graphDb);

        //GraphDatabaseService graphDb2 = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder( storDir )
                //.setConfig(GraphDatabaseSettings.read_only, "true" )
                //.newGraphDatabase();

        CreateItems(graphDb);

        //Se para la base de datos
        graphDb.shutdown();

    }

    private static void CreateItems(GraphDatabaseService graphDb) {

        try ( Transaction tx = graphDb.beginTx() )
        {
            Node firstNode = graphDb.createNode();
            firstNode.addLabel(A_Definitions.MyLabels.TEST);
            firstNode.setProperty( "message", "Hello, " );
            Node secondNode = graphDb.createNode();
            secondNode.addLabel(A_Definitions.MyLabels.TEST);
            secondNode.setProperty( "message", "World!" );

            Node thirdNode = graphDb.createNode();
            thirdNode.addLabel(A_Definitions.MyLabels.TEST);
            thirdNode.setProperty( "message", "World!" );

            Relationship relationship = firstNode.createRelationshipTo( secondNode, A_Definitions.RelTypes.KNOWS);
            relationship.setProperty( "message", "brave Neo4j " );

            relationship = secondNode.createRelationshipTo( thirdNode, A_Definitions.RelTypes.IS_FRIEND_OF);
            relationship.setProperty( "message", "Thanks" );

            tx.success();
        }
    }
}
