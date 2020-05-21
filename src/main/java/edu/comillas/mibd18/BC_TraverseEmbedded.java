package edu.comillas.mibd18;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.graphdb.traversal.Uniqueness;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class BC_TraverseEmbedded {
    public static void main(String[] args) {
        //TODO Crear la conexión con la base de datos embebida "/home/icai/tmp/dataNeo/data/databases/dbPrueba.db"
        //Se define el path del fichero de la base de datos
        String dbPath = "/home/icai/tmp/dataNeo/data/databases/dbPrueba.db";
        //Se crea el objeto File sobre el path
        File storDir = new File(dbPath);

        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( storDir );

        try ( Transaction tx = graphDb.beginTx() ) {

            Node n = graphDb.findNode(A_Definitions.MyLabels.TEST,"message","Hello, ");
            System.out.println("Labels: " +n.getLabels().toString()+" ID: "+ n.getId()+" Property: "+n.getProperty("message"));
/*
            n = graphDb.findNode(MyLabels.TEST,"message","World!");
            System.out.println("Labels: " +n.getLabels().toString()+" ID: "+ n.getId()+" Property: "+n.getProperty("message"));
*/
            Iterable<Relationship> allRelationships = n.getRelationships(A_Definitions.RelTypes.KNOWS, Direction.OUTGOING);
            Set<Node> nodesAux = new HashSet<Node>();

            for (Relationship r : allRelationships) {
                Node na = r.getEndNode();
                nodesAux.add(na);
            }

            for(Node ni : nodesAux){
                System.out.println(ni.toString());
            }




            TraversalDescription traversalNodesSearch = graphDb.traversalDescription()
                    .relationships(A_Definitions.RelTypes.KNOWS,Direction.OUTGOING)
                    .relationships(A_Definitions.RelTypes.IS_FRIEND_OF, Direction.OUTGOING)
                    .uniqueness(Uniqueness.NODE_GLOBAL)
                    .evaluator(Evaluators.toDepth(2));

            Traverser tr = traversalNodesSearch.traverse(n);

            System.out.println();
            System.out.println();
            String output="";
            for (Path p: tr){
                output += p + "\n";
            }

            System.out.println(output);

        }

        //TODO Cerrar la conexion con la base de datos
        graphDb.shutdown();

    }
}
