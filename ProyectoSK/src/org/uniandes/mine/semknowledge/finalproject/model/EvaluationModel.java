package org.uniandes.mine.semknowledge.finalproject.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.uniandes.mine.semknowledge.finalproject.model.utilities.OWLUtilities;
import org.uniandes.mine.semknowledge.finalproject.model.utilities.RDFUtilities;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class EvaluationModel {
	
	/*
	 * Atributos de la clase
	 */
	private DataModel dataModel = DataModel.getInstance();
	
	private String RDF_PATH = "src/travel.rdf";
	private String RDF_PATH_TEMP = "src/travel_tmp.rdf";
	private String OWL_PATH = "src/travel.owl";
	private String OWL_PATH_TEMP = "src/travel_tmp.owl";
	
	
	/*
	 * Métodos de la clase
	 */
	
	// Método constructor
	public EvaluationModel() {}
	
	// Método que inicia la evaluación
	public void startEvaluation( int stopParam, int stepParam, int iterParam ) {
		
		System.out.println( "Se correra un total de " + ( stopParam / stepParam ) + "con la siguinete configuración:" );
		System.out.println( " - Número máximo de registros / individos a obtener: " + stopParam );
		System.out.println( " - Aumento en la cantidad de registros / individos en cada experimento: " + stepParam );
		System.out.println( " - Cantidad de iteraciones por cada experimento: " + iterParam );
		
		// Reinicia los datasets
		dataModel.getRdfLoadTimeDataset().clear();
		dataModel.getOwlLoadTimeDataset().clear();
		dataModel.getRdfQueryResponseTimeDataset().clear();
		dataModel.getOwlQueryResponseTimeDataset().clear();
		
		/* 
		 * RDF
		 */
		
		RDFUtilities rdfUtilities = new RDFUtilities( RDF_PATH, RDF_PATH_TEMP );
		
		for( int i =  0 ; i <= stopParam ; i = i + stepParam ) {
			
			Model rdfFile = null;
			List<Double> acumLoadTime = new ArrayList<Double>();
			List<Double> acumQueryResponseTime = new ArrayList<Double>();
			
			// Se ingresan los individuos
			rdfUtilities.createRows( stepParam );
			
			// Iteraciones por experimento
			for( int j = 0 ; j < iterParam ; j++ ) {
				
				// Lectura del tiempo de carga del archivo
				
				Date initTime = new Date();
				
				rdfFile = loadRDFFile();
				
				Date endDate = new Date();
				long diferencia= endDate.getTime() - initTime.getTime();
				
				acumLoadTime.add( Double.parseDouble( diferencia + "" ) );
				
				// Lectura del tiempo de consulta del archivo
							
				initTime = new Date();
				
				queryRDFFile( rdfFile, i );
				
				endDate = new Date();
				diferencia = endDate.getTime() - initTime.getTime();
				
				acumQueryResponseTime.add( Double.parseDouble( diferencia + "" ) );
				
			}
			
			dataModel.getRdfLoadTimeDataset().add( i , getMaxValue( acumLoadTime ) );
			dataModel.getRdfQueryResponseTimeDataset().add( i , getMaxValue( acumQueryResponseTime ) );
			
		}
		
		/* 
		 * OWL
		 */
		
		OWLUtilities owlUtilities = new OWLUtilities( OWL_PATH, OWL_PATH_TEMP );
		
		for( int i =  0 ; i <= stopParam ; i = i + stepParam ) {
			
			OntModel owlFile = null;
			List<Double> acumLoadTime = new ArrayList<Double>();
			List<Double> acumQueryResponseTime = new ArrayList<Double>();
						
			// Se ingresan los individuos
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
				
				queryOWLFile( owlFile, i );
				
				endDate = new Date();
				diferencia = endDate.getTime() - initTime.getTime();
				
				acumQueryResponseTime.add( Double.parseDouble( diferencia + "" ) );
				
			}
			
			dataModel.getOwlLoadTimeDataset().add( i , getMaxValue( acumLoadTime ) );
			dataModel.getOwlQueryResponseTimeDataset().add( i , getMaxValue( acumQueryResponseTime ) );
			
		}
		
		// Se eliminan los archivos RDF y OWL duplicados
		rdfUtilities.removeDuplicatedFile();
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
	
	public Model loadRDFFile() {
		
		Model rdfFile = null;
				
		try {
			
			rdfFile =  ModelFactory.createDefaultModel();
			InputStream in = FileManager.get().open( RDF_PATH_TEMP );
			rdfFile.read(in, null);
			
			if (in == null) {
				throw new IllegalArgumentException( "File: " + RDF_PATH_TEMP + " not found" );
			}
			
		} catch (IllegalArgumentException e) {
			System.err.println( "Error al cargar el archivo OWL" ) ;
			e.printStackTrace();
		}
		
		return rdfFile;
		
	}
	
	public void queryRDFFile( Model rdfFile, int limit ) {
		
		ResultSet results = null;
		
		String queryString = "PREFIX travel:<http://www.owl-ontologies.com/travel.owl#>" +"\n";
		queryString += "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + "\n";
		queryString += "SELECT ?destination ?type ?activity ?accommodation ?rating" + "\n";
		queryString += "WHERE { ?destination travel:type ?type ." + "\n";
		queryString += "OPTIONAL { ?destination travel:hasActivity ?activity }" + "\n";
		queryString += "OPTIONAL { ?destination travel:hasAccommodation ?accommodation }" + "\n";
		queryString += "OPTIONAL { ?destination travel:hasRating ?rating } }" + "\n";
		queryString += "LIMIT " + limit;
		

		//execute query
		Query query = QueryFactory.create( queryString );
		QueryExecution qe = QueryExecutionFactory.create( query, rdfFile );
		results = qe.execSelect();
		System.out.println(ResultSetFormatter.asText(results));
		
	}
	
	public void queryOWLFile( OntModel owlFile, int limit ) {
		
		ResultSet results = null;
		
		String queryString = "PREFIX travel:<http://www.owl-ontologies.com/travel.owl#>" +"\n";
		queryString += "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + "\n";
		queryString += "SELECT ?destination ?type ?activity ?accommodation ?rating" + "\n";
		queryString += "WHERE { ?destination a ?type ." + "\n";
		queryString += "?type rdfs:subClassOf travel:Destination" + "\n";
		queryString += "OPTIONAL { ?destination travel:hasActivity ?activity }" + "\n";
		queryString += "OPTIONAL { ?destination travel:hasAccommodation ?accommodation }" + "\n";
		queryString += "OPTIONAL { ?accommodation travel:hasRating ?rating } }" + "\n";
		queryString += "LIMIT " + limit;
		

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
