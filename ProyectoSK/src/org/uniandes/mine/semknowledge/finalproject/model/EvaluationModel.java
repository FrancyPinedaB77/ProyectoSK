package org.uniandes.mine.semknowledge.finalproject.model;

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
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class EvaluationModel {
	
	/*
	 * Atributos de la clase
	 */
	private DataModel dataModel = DataModel.getInstance();
	
	private String OWL_PATH = "src/travel.owl";
	private String OWL_PATH_TEMP = "src/travel_tmp.owl";
	
	
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
		
		OWLUtilities owlUtilities = new OWLUtilities( OWL_PATH, OWL_PATH_TEMP );
		
		for( int i =  stepParam ; i <= stopParam ; i = i + stepParam ) {
			
			OntModel owlFile = null;
			List<Double> acumLoadTime = new ArrayList<Double>();
			List<Double> acumQueryResponseTime = new ArrayList<Double>();
						
			// Se crea el OWL temporal y se ingresan los individuos
			owlUtilities.createIndividuals( stepParam );
			
			// Iteraciones por experimento
			for( int j = 0 ; j < iterParam ; j++ ) {
				
				// Lectura del tiempo de carga del archivo
				
				Date initTime = new Date();
				
				owlFile = loadOWLFile();
				
				Date endDate = new Date();
				long diferencia= endDate.getTime() - initTime.getTime();
				
				acumLoadTime.add( Double.parseDouble( diferencia + "" ) );
				
				// Lectura del tiempo de consulta del archivo
							
				initTime = new Date();
				
				queryOWLFile( owlFile );
				
				endDate = new Date();
				diferencia = endDate.getTime() - initTime.getTime();
				
				acumQueryResponseTime.add( Double.parseDouble( diferencia + "" ) );
				
			}
			
			dataModel.getOwlLoadTimeDataset().add( i , getMaxValue( acumLoadTime ) );
			dataModel.getOwlQueryResponseTimeDataset().add( i , getMaxValue( acumQueryResponseTime ) );
			
		}
		
		// Se eliminan los archivos RDF y OWL duplicados
		owlUtilities.removeDuplicatedFile();
		
	}
	
	public OntModel loadOWLFile() {
		
		OntModel owlFile = null;
		
		try {
			
			owlFile = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_MICRO_RULE_INF );
			owlFile.read( new FileInputStream( OWL_PATH_TEMP ), "RDF/XML" );
			
		} catch (FileNotFoundException e) {
			System.err.println( "Error al cargar el archivo OWL" ) ;
			e.printStackTrace();
		}
		
		return owlFile;
		
	}
	
	public void queryOWLFile( OntModel owlFile ) {
		
		ResultSet results = null;
		
		String queryString = "PREFIX viajes:<http://www.owl-ontologies.com/travel.owl#>" +"\n";
		queryString += "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + "\n";
		queryString += "SELECT ?destino (SAMPLE(?type) AS ?type) (SAMPLE(?activity) AS ?activity) (SAMPLE(?accommodation) AS ?accommodation) (SAMPLE(?rating) AS ?rating)" + "\n";
		queryString += "WHERE { ?destino a ?type ." + "\n";
		queryString += "?type rdfs:subClassOf viajes:Destination" + "\n";
		queryString += "OPTIONAL { ?destino viajes:hasActivity ?activity }" + "\n";
		queryString += "OPTIONAL { ?destino viajes:hasAccommodation ?accommodation }" + "\n";
		queryString += "OPTIONAL { ?accommodation viajes:hasRating ?rating } }" + "\n";
		queryString += "GROUP BY ?destino";

		//execute query
		Query query = QueryFactory.create( queryString );
		QueryExecution qe = QueryExecutionFactory.create( query, owlFile );
		results = qe.execSelect();
		System.out.println(ResultSetFormatter.asText(results));
		
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
