package edu.comillas.mibd18;

import org.neo4j.driver.*;

import static org.neo4j.driver.Values.parameters;

public class AC_ExecuteStatementExample {
    public static void main(String[] args) {
        //TODO conectarse a el servidor neo4j del docker local
        //Se define el usuario que se conectará a ne4j
        String user = "neo4j";
        //Se define la password de usuario anterior
        String password = "master";
        //Se define la uri de conexión
        String uri = "bolt://localhost:7687";

        //Se crea el driver con la base de datos
        Driver driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ));

        String catId = "";
        String catName = "";
        String description = "";

        try (Session session = driver.session()) {
            try (Transaction tx = session.beginTransaction()) {
                String statement = "CREATE (c:Category) SET c = {categoryID: {catId}, categoryName: {catName}, description: {description}}";
                Value param = parameters("catId", catId, "catName", catName, "description", description);
                StatementResult result = tx.run(statement, param);
                tx.success();
                //tx.failure();
            }

            try (Transaction tx = session.beginTransaction()) {
                addCategory(tx,"22", "Prueba 2", "Desc Prueba 2");
                addCategory(tx,"23", "Prueba 3", "Desc Prueba 3");

                String statement = "MATCH (co:Category {categoryID: $catIdO})\n" +
                        "                MATCH (cd:Category {categoryID:$catIdD})\n" +
                        "                MERGE (co)-[r:PJEL]->(cd)\n" +
                        "                        ON CREATE SET r.random = $valRel";
                Value param = parameters("catIdO", "22", "catIdD", "23", "valRel", 25);
                StatementResult result = tx.run(statement,param);
                tx.success();
                //tx.failure();
            }

        }
        //TODO cerrar la conexión
        driver.close();
    }


    static public void addCategory(Transaction tx, String catId, String catName, String description) {
        String statement = "CREATE (c:Category) SET c = {categoryID: {catId}, categoryName: {catName}, description: {description}}";
        Value param = parameters("catId", catId, "catName", catName, "description", description);
        StatementResult result = tx.run(statement, param);

    }

}
