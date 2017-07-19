package org.uniandes.mine.semknowledge.finalproject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class EvaluationModel {
	
	/*
	 * Atributos de la clase
	 */
	private DataModel dataModel = DataModel.getInstance();
	
	private String OWL_PATH = "src/travel2.owl";
	
	
	/*
	 * Métodos de la clase
	 */
	
	// Método constructor
	public EvaluationModel() {}
	
	// Método que inicia la evaluación
	public void startEvaluation( int stopParam, int stepParam, int iterParam ) {
		
		// Reinicia los datasets
		dataModel.getRdfLoadTimeDataset().clear();
		dataModel.getOwlLoadTimeDataset().clear();
		dataModel.getRdfQueryResponseTimeDataset().clear();
		dataModel.getOwlQueryResponseTimeDataset().clear();
		
		for( int i = 1 ; i <= stopParam + 1 ; i = i + stepParam ) {
			
			OntModel owlFile = null;
			
			/*
			 * Load Time - RDF
			 */
			
			List<Double> acum = new ArrayList<Double>();
			for( int j = 0 ; j < iterParam ; j++ ) {
				acum.add( Math.random() * 10 );
			}
			
			dataModel.getRdfLoadTimeDataset().add( i , getMaxValue( acum ) );
			/*
			 * Load Time - OWL
			 */
			//--------------------inicio de casos para clases aleatorias-------------------------
			
			for( int k = 1 ; k <= stepParam ; k++ ) {
				
				// Seleccionar clase aleatoria
				int min=0;
				int max=6; //Numero de clases que se van a usar
				int clase = ThreadLocalRandom.current().nextInt(min, max + 1);
				String statement="";
				switch (clase) {
					
					case 0:{
						statement = "\n" +"	"+ "<owl:NamedIndividual rdf:about=\"http://www.owl-ontologies.com/travel.owl#BackDestination_lugar_"+ k+"\">"+ "\n";
						statement += "		"+ "<rdf:type rdf:resource=\"http://www.owl-ontologies.com/travel.owl#BackpackersDestination\"/>"+"\n";
						statement += "		"+ "<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#sports_tipo_0\"/>"+"\n";
						statement += "		"+ "<hasRating rdf:resource=\ttp://www.owl-ontologies.com/travel.owl#OneStarRating\"/>"+"\n";
						statement += "	"+"</owl:NamedIndividual>"+ "\n\n";						
						break;
					}
					case 1:{
						statement = "\n" +"	"+ "<owl:NamedIndividual rdf:about=\"http://www.owl-ontologies.com/travel.owl#BondiBeach_"+k+"\">"+ "\n";
						statement += "		"+"<rdf:type rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Beach"+"\n";
						statement += "	"+"</owl:NamedIndividual>"+ "\n\n";						
						break;
					}
					case 2:{
						statement = "\n" +"	"+ "<owl:NamedIndividual rdf:about=\"http://www.owl-ontologies.com/travel.owl#BudgetHotel_lugar_"+k+"\">"+ "\n";
						statement += "		"+"<rdf:type rdf:resource=\"http://www.owl-ontologies.com/travel.owl#BudgetHotelDestination\"/>"+"\n";
						statement += "		"+"<hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#OneStarRating\"/>"+"\n";
						statement += "		"+"<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#sports_tipo_0\"/>"+"\n";
						statement += "	"+"</owl:NamedIndividual>"+ "\n\n";		
						break;
					}
					case 3:{
						statement = "\n" +"	"+ "<owl:NamedIndividual rdf:about=\"http://www.owl-ontologies.com/travel.owl#Familiar_lugar_"+k+"\">"+ "\n";
						statement += "		"+"<rdf:type rdf:resource=\"http://www.owl-ontologies.com/travel.owl#FamilyDestination\"/>"+"\n";
						statement += "		"+"<hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#TwoStarRating\"/>"+"\n";
						statement += "		"+"<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Hikin_tipo_0\"/>"+"\n";
						statement += "		"+"<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#sports_tipo_0\"/>"+"\n";
						statement += "	"+"</owl:NamedIndividual>"+ "\n\n";					
						break;
					}
					case 4:{
						statement = "\n" +"	"+ "<owl:NamedIndividual rdf:about=\"http://www.owl-ontologies.com/travel.owl#QuietDestination_lugar_"+k+"\">"+ "\n";
						statement += "		"+"<rdf:type rdf:resource=\"http://www.owl-ontologies.com/travel.owl#QuietDestination\"/>"+"\n";
						statement += "		"+"<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#sports_tipo_0\"/>"+"\n";
						statement += "	"+"</owl:NamedIndividual>"+ "\n\n";
						break;
					}
					case 5:{
						statement = "\n" +"	"+ "<owl:NamedIndividual rdf:about=\"http://www.owl-ontologies.com/travel.owl#RetireDestination_lugar_"+k+"\">"+ "\n";
						statement += "		"+"<rdf:type rdf:resource=\"http://www.owl-ontologies.com/travel.owl#RetireeDestination\"/>"+"\n";
						statement += "		"+"<hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#FourSeasons\"/>"+"\n";
						statement += "		"+"<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Museum_lugar_0\"/>"+"\n";
						statement += "	"+"</owl:NamedIndividual>"+ "\n\n";
						break;
					}
				}
						
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
			}
						
			acum = new ArrayList<Double>();
			for( int j = 0 ; j < iterParam ; j++ ) {
				
				Date initTime = new Date();
				
				owlFile = loadOWLFile();
				
				Date endDate = new Date();
				long diferencia= endDate.getTime() - initTime.getTime();
				
				acum.add( Double.parseDouble( diferencia + "" ) );
				
			}
			
			dataModel.getOwlLoadTimeDataset().add( i , getMaxValue( acum ) );
			
			/*
			 * Query Response Time - RDF
			 */
			
			acum = new ArrayList<Double>();
			for( int j = 0 ; j < iterParam ; j++ ) {
				acum.add( Math.random() * 10 );
			}
			
			dataModel.getRdfQueryResponseTimeDataset().add( i , getMaxValue( acum ) );
			
			/*
			 * Query Response Time - OWL
			 */
			
			acum = new ArrayList<Double>();
			for( int j = 0 ; j < iterParam ; j++ ) {
				
				Date initTime = new Date();
				
				queryOWLFile( owlFile );
				
				Date endDate = new Date();
				long diferencia= endDate.getTime() - initTime.getTime();
				
				acum.add( Double.parseDouble( diferencia + "" ) );
				
			}
			
			dataModel.getOwlQueryResponseTimeDataset().add( i , getMaxValue( acum ) );
			
		}
		
	}
	
	public OntModel loadOWLFile() {
		
		OntModel owlFile = null;
		
		try {
			
			owlFile = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_MICRO_RULE_INF );
			owlFile.read( new FileInputStream( OWL_PATH ), "RDF/XML" );
			
		} catch (FileNotFoundException e) {
			System.err.println( "Error al cargar el archivo OWL" ) ;
			e.printStackTrace();
		}
		
		return owlFile;
		
	}
	
	public void queryOWLFile( OntModel owlFile ) {
		
		String queryString = "PREFIX viajes:<http://www.owl-ontologies.com/travel.owl#>"+"\n";
		queryString += "SELECT  ?destino"+ "\n";
		queryString += "WHERE { ?destino a viajes:City . }";

		//execute query
		Query query = QueryFactory.create( queryString );
		QueryExecution qe = QueryExecutionFactory.create( query, owlFile );
		ResultSet results = qe.execSelect();
		
	}
	
	public double getMaxValue( List<Double> list ) {
		
		double toReturn = 0;
		for( int i = 0 ; i < list.size() ; i++ ) {
			if( list.get( i ) > toReturn ) {
				toReturn = list.get( i );
			}
		}
		
		return toReturn;
		
	}
	
}
