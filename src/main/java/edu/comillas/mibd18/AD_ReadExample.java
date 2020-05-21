package edu.comillas.mibd18;

import org.neo4j.driver.*;

import java.util.ArrayList;
import java.util.List;

public class AD_ReadExample {
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

        //Se buscan todas las categorias de los nodos de la base de datos y se muestran
        for(String cat : getCategories(driver)){
            System.out.println(cat);
        }

        System.out.println();
        System.out.println("METODO 2");
        System.out.println();

        //Se buscan todas las categorias de los nodos de la base de datos y se muestran
        for(String cat : getCategories2(driver)){
            System.out.println(cat);
        }

        //TODO Cerrar la conexion
        driver.close();

    }


    private static List<String> matchCategoriesNodes(Transaction tx )
    {
        //Se crea una lsita donde almacenar las categorias
        List<String> categorias = new ArrayList<>();

        //TODO definir la consulta en CYPHER para obtener los nodos con etiqueta "Category" y devolver la "description" ordenada por orden alfabetico
        //Se define la consulta que se quiere ejecutar
        String query = "MATCH (c:Category) RETURN c.description ORDER BY c.description";

        //Sobre la transacción se ejecuta la consulta
        StatementResult result = tx.run(query);

        //Se itera sobre el resultado para obtener las categorias
        while ( result.hasNext() )
        {
            //Se obtiene el registro
            Record r = result.next();
            //Se obtiene el primer atributo del registro
            String cat = r.get( 0 ).asString();
            //Se añade la categoria a la lista
            categorias.add( cat );
        }

        //Se devuelve la lista de categorias
        return categorias;
    }



    static public List<String> getCategories(Driver driver)
    {
        try ( Session session = driver.session() )
        {
            return session.readTransaction( new TransactionWork<List<String>>()
            {
                @Override
                public List<String> execute( Transaction tx )
                {
                    return matchCategoriesNodes( tx );
                }
            } );
        }
    }



    static public List<String> getCategories2(Driver driver)
    {
        //Se crea una lista donde almacenar las categorias
        List<String> categories = new ArrayList<>();

        //Se obtiene una sesion asociada a la base de datos
        Session session = driver.session();

        //TODO definir la consulta en CYPHER para obtener los nodos con etiqueta "Category" y devolver la "description" ordenada por orden alfabetico
        //Se define la consulta que se va a ejecutar
        String query = "MATCH (c:Category) RETURN c.description ORDER BY c.description";
        //Se ejecuta la consulta sobre la sesion
        StatementResult result = session.run(query);
        //Se itera sobre el resultado de la consulta
        while (result.hasNext()) {
            //Sobre el iterador se obtiene el registro
            Record r = result.next();
            //Se obtiene el primer campo de la consulta
            String cat = r.get(0).asString();
            //Se añade la categoría a la lista
            categories.add(cat);
        }

        //Se cierra la sesion
        session.close();
        //Se devuelve la lista de categorias
        return categories;
    }
}
