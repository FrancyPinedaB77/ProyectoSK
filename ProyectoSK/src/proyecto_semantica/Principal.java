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
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;

public class Principal {
	public static void main(String[] args) {
	float[] tiempos_load= new float[100];
	float[] tiempos_query= new float[100];
	float[] incrementos= new float[100];

    for(int i=0; i<2; i++){
        
		Timestamp t1 = new Timestamp(System.currentTimeMillis());
		//Se lee el archivo principal //url para convertir: http://mowl-power.cs.man.ac.uk:8080/converter/ 
		String ontologyURL = "file:///D:\\MINE\\Knowledge Semantic\\PROYECTO\\travel2.owl";
		Timestamp t2 = new Timestamp(System.currentTimeMillis());
		float diferencia= t2.getTime()-t1.getTime()+i;
		tiempos_load[i]=diferencia;
		incrementos[i]=i;

		// Se crea el modelo de la ontologia
		OntModel ontology = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		
		//Leyendo la ontología
		ontology.read( ontologyURL, "RDF/XML" );
		
		//tiempo 1 
		Timestamp t3 = new Timestamp(System.currentTimeMillis());
		//Haciendo la consulta
		String queryString = "PREFIX viajes:<http://www.owl-ontologies.com/travel.owl#>"+"\n";
		queryString += "SELECT  ?destino"+ "\n";// ?person, ?company
		queryString += "WHERE {?destino a viajes:BackpackersDestination .}";

		//execute query
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query,ontology);
		ResultSet results = qe.execSelect();
		
		//tiempo 2 
		Timestamp t4 = new Timestamp(System.currentTimeMillis());
		float diferencia2= t4.getTime()-t3.getTime()+i;
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
 