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
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class EvaluationModel {
	
	/*
	 * Atributos de la clase
	 */
	private DataModel dataModel = DataModel.getInstance();
	
	private String OWL_PATH = "src/travel2.owl";
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
			
			// Se crea el OWL temporal y se ingresan los individuos
			OWLUtilities owlUtils = new OWLUtilities( OWL_PATH, OWL_PATH_TEMP );
			owlUtils.createIndividuals( stopParam );
						
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
			
			// Se eliminan los archivos RDF y OWL duplicados
			owlUtils.removeDuplicatedFile();
			
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
