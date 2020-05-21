package edu.comillas.mibd18;

import org.neo4j.driver.*;



public class AA_ConexionExample {
    public static void main(String[] args){
        //Se define el usuario que se conectará a ne4j
        String user = "neo4j";
        //Se define la password de usuario anterior
        String password = "master";
        //Se define la uri de conexión
        String uri = "bolt://localhost:7687";

        //Se crea el driver con la base de datos
        Driver driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ));
/*


*/
        //Se cierra la conexión con la base de datos
        driver.close();

    }

/*


*/
}
