package edu.comillas.mibd18;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;

public class A_Definitions {
    //Se definen los tipos de relaciones que se van a utilizar en el grafo
    public static enum RelTypes implements RelationshipType
    {
        KNOWS, IS_FRIEND_OF
    }

    //Se definen los tipos de nodos que se van a tener en el grafo
    public enum MyLabels implements Label {
        TEST
    }
}
