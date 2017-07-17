package proyecto_semantica;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;

public class Principal {
	public static void main(String[] args) {
	float[] tiempos_load= new float[100];
	float[] tiempos_query= new float[100];
	float[] incrementos= new float[100];

    for(int i=0; i<10; i++){
        
		
		//Se lee el archivo principal //url para convertir: http://mowl-power.cs.man.ac.uk:8080/converter/ 
		String ontologyURL = "src/travel2.owl";
		
		//incrementos[i]=i;

		// Se crea el modelo de la ontologia
		OntModel ontology = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		
		Date t1 = new Date();
		
		//Leyendo la ontología
		try {
			ontology.read( new FileInputStream(ontologyURL), "RDF/XML" );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date t2 = new Date();
		System.out.println("Load" + t1 + " " + t2);
		long diferencia= t2.getTime()-t1.getTime();
		tiempos_load[i]=diferencia;
		
		//Haciendo la consulta
		String queryString = "PREFIX viajes:<http://www.owl-ontologies.com/travel.owl#>"+"\n";
		queryString += "SELECT  ?destino"+ "\n";// ?person, ?company
		queryString += "WHERE {?destino a viajes:City .}";

		//execute query
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query,ontology);
		
		//tiempo 1 
		Date t3 = new Date();
		
		ResultSet results = qe.execSelect();
		
		//tiempo 2 
		Date t4 = new Date();
		System.out.println("Query" + t3 + " " + t4);
		long diferencia2= t4.getTime()-t3.getTime();
		tiempos_query[i]=diferencia2;
		// print results nicely
		System.out.println(ResultSetFormatter.asText(results));

    }
    
    System.out.println(Arrays.toString(tiempos_load));
    System.out.println(Arrays.toString(tiempos_query));

    
	}
}

/*Listado de posibles paginas .owl
 * https://protegewiki.stanford.edu/wiki/Protege_Ontology_Library 
 * http://swl.slis.indiana.edu/repository/
 * 
 * 
 * */

/*Listado de clases con mayor propiedades
 * Clases con mas relaciones

-BackpackersDestination (5)
-QuietDestination (-1)
-BudgetHotelDestination (3)
-FamilyDestination (2)
-RetireeDestination (2)
-City (2)
-NationalPark (3)

-------------Otros-----------------

-Adventure
-Relaxation
-Sightseeing
-Sports
 * 
 */
 