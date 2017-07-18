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
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Principal {
	public static void main(String[] args) {
	float[] tiempos_load= new float[100];
	float[] tiempos_query= new float[100];
	float[] incrementos= new float[100];

    for(int i=0; i<2; i++){
        
		
		//Se lee el archivo principal //url para convertir: http://mowl-power.cs.man.ac.uk:8080/converter/ 
		String ontologyURL = "src/travel_v3.owl";
		
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

		//-----------------------INICIA LA CLASE:                 -------------------------------------//
		
		String statement = "\n" +"	"+ "<owl:NamedIndividual rdf:about=\"http://www.owl-ontologies.com/travel.owl#ThreeStarRating_"+i+"\">"+ "\n";
		statement += "		"+"<rdf:type rdf:resource=\"http://www.owl-ontologies.com/travel.owl#AccommodationRating\"/>"+"\n";
		statement += "	"+"</owl:NamedIndividual>"+ "\n\n";
		System.out.println(statement);
		
		//insert statement
		Path path =Paths.get("src/travel_v3.owl");
		Charset charset = StandardCharsets.UTF_8;
		String content;
		try{
			content =new String(Files.readAllBytes(path),charset);
			content = content.replaceAll("</rdf:RDF>", statement +"</rdf:RDF>");
			Files.write(path, content.getBytes(charset));
		} catch (IOException e){
			//Auto-generated catch block
			e.printStackTrace();		
		}
		
		//-----------------------INICIA LA CLASE:                 -------------------------------------//
			
		System.out.println("Load" + t1 + " " + t2);
		long diferencia= t2.getTime()-t1.getTime();
		tiempos_load[i]=diferencia;
		
		//Haciendo la consulta
		String queryString = "PREFIX viajes:<http://www.owl-ontologies.com/travel.owl#>"+"\n";
		queryString += "SELECT  ?destino"+ "\n";// ?person, ?company
		queryString += "WHERE {?destino a viajes:AccommodationRating .}";

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
 