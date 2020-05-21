package edu.comillas.mibd18;

import org.neo4j.driver.*;

import static org.neo4j.driver.Values.parameters;

public class AB_ExecuteStatementExample {
    public static void main(String[] args){
        //TODO conectarse a el servidor neo4j del docker local
        //Se define el usuario que se conectará a ne4j
        String user = "neo4j";
        //Se define la password de usuario anterior
        String password = "master";
        //Se define la uri de conexión
        String uri = "bolt://localhost:7687";

        //Se crea el driver con la base de datos
        Driver driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ));

        try ( Session session = driver.session() )
        {
            String catId="";
            String catName="";
            String description="";

            String statement = "CREATE (c:Category {categoryID: $catId, categoryName: $catName, description: $description})";
            Value p = parameters( "catId", catId, "catName",catName, "description",description);

            session.run( statement,p);


            statement = "MATCH (c:Category {categoryID:$catId}) DELETE c";
            p = parameters( "catId", catId);
            //TODO ejecutar en la sesion
            session.run( statement,p);


            statement = "MERGE (c:Category {categoryID: $catId, categoryName: $catName, description: $description})";
            p = parameters( "catId", catId, "catName",catName, "description",description);
            //TODO ejecutar en la sesion
            session.run( statement,p);
        }

        //TODO cerrar la conexión
        driver.close();
    }
}
