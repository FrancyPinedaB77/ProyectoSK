package org.uniandes.mine.semknowledge.finalproject.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;
import java.io.FileInputStream;
import java.io.FileOutputStream;;

public class OWLUtilities {
	
	private String OWL_PATH;
	private String OWL_PATH_TEMP;
	private int individualsCount;	
	
	public OWLUtilities( String OWL_PATH, String OWL_PATH_TEMP ) {
		this.OWL_PATH = OWL_PATH;
		this.OWL_PATH_TEMP = OWL_PATH_TEMP;
		
		duplicateFile();
		individualsCount = 0;
	}
	
	public void duplicateFile() {
		
		InputStream is = null;
		OutputStream os = null;
		try {
			
			is = new FileInputStream( new File( OWL_PATH ) );
			os = new FileOutputStream( new File( OWL_PATH_TEMP ) );
	        
			byte[] buffer = new byte[1024];
			int length;
			
			while ( (length = is.read( buffer ) ) > 0 ) {
				os.write( buffer, 0, length );
			}
			
			is.close();
			os.close();
			
			System.out.println( "Archivo duplicado satisfactoriamente" );
			
		} catch( IOException e ) {
			e.printStackTrace();
		}
		
	}
	
	public void createIndividuals( int individuals ) {
		
		for ( int i = individualsCount; i < individualsCount + individuals ; i++ ) {
			
			// Seleccionar clase aleatoria
			int min=0;
			int max=6; //Numero de clases que se van a usar
			int individualClass = ThreadLocalRandom.current().nextInt( min, max );
			String statement="";
			
			// Inserta un individio de acuerdo a la clase seleccionada
			switch ( individualClass ) {
				case 0: {
					statement = "\n\n\t" +"	" + "<owl:NamedIndividual rdf:about=\"http://www.owl-ontologies.com/travel.owl#Destination_" + i + "\">" + "\n";
					statement += "\t\t" + "<rdf:type rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Destination\"/>" + "\n";
					statement += "\t\t" + "<hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Accommodation_1\"/>" + "\n";
					statement += "\t\t" + "<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#BunjeeJumping_0\"/>" + "\n";
					statement += "\t"+"</owl:NamedIndividual>" + "\n";
					break;
				}
				case 1: {
					statement = "\n\n\t" +"	" + "<owl:NamedIndividual rdf:about=\"http://www.owl-ontologies.com/travel.owl#Destination_" + i + "\">" + "\n";
					statement += "\t\t" + "<rdf:type rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Destination\"/>" + "\n";
					statement += "\t\t" + "<hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Accommodation_2\"/>" + "\n";
					statement += "\t\t" + "<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Sunbathing_0\"/>" + "\n";
					statement += "\t"+"</owl:NamedIndividual>" + "\n";
					break;
				}
				case 2: {
					statement = "\n\n\t" +"	" + "<owl:NamedIndividual rdf:about=\"http://www.owl-ontologies.com/travel.owl#Destination_" + i + "\">" + "\n";
					statement += "\t\t" + "<rdf:type rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Destination\"/>" + "\n";
					statement += "\t\t" + "<hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Accommodation_2\"/>" + "\n";
					statement += "\t\t" + "<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Hiking_0\"/>" + "\n";
					statement += "\t\t" + "<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Museum_0\"/>" + "\n";
					statement += "\t\t" + "<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Safari_0\"/>" + "\n";
					statement += "\t"+"</owl:NamedIndividual>" + "\n";
					break;
				}
				case 3: {
					statement = "\n\n\t" +"	" + "<owl:NamedIndividual rdf:about=\"http://www.owl-ontologies.com/travel.owl#Destination_" + i + "\">" + "\n";
					statement += "\t\t" + "<rdf:type rdf:resource=\"http://www.owl-ontologies.com/travel.owl#RuralArea\"/>" + "\n";
					statement += "\t\t" + "<hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Accommodation_0\"/>" + "\n";
					statement += "\t\t" + "<hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Accommodation_1\"/>" + "\n";
					statement += "\t\t" + "<hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Accommodation_2\"/>" + "\n";
					statement += "\t\t" + "<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Hiking_0\"/>" + "\n";
					statement += "\t"+"</owl:NamedIndividual>" + "\n";
					break;
				}
				case 4: {
					statement = "\n\n\t" +"	" + "<owl:NamedIndividual rdf:about=\"http://www.owl-ontologies.com/travel.owl#Destination_" + i + "\">" + "\n";
					statement += "\t\t" + "<rdf:type rdf:resource=\"http://www.owl-ontologies.com/travel.owl#RuralArea\"/>" + "\n";
					statement += "\t\t" + "<hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Accommodation_3\"/>" + "\n";
					statement += "\t"+"</owl:NamedIndividual>" + "\n";
					break;
				}
				case 5: {
					statement = "\n\n\t" +"	" + "<owl:NamedIndividual rdf:about=\"http://www.owl-ontologies.com/travel.owl#Destination_" + i + "\">" + "\n";
					statement += "\t\t" + "<rdf:type rdf:resource=\"http://www.owl-ontologies.com/travel.owl#UrbanArea\"/>" + "\n";
					statement += "\t\t" + "<hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Accommodation_0\"/>" + "\n";
					statement += "\t\t" + "<hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Accommodation_1\"/>" + "\n";
					statement += "\t\t" + "<hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Accommodation_2\"/>" + "\n";
					statement += "\t\t" + "<hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Accommodation_3\"/>" + "\n";
					statement += "\t\t" + "<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#BunjeeJumping_0\"/>" + "\n";
					statement += "\t\t" + "<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Hiking_0\"/>" + "\n";
					statement += "\t\t" + "<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Museum_0\"/>" + "\n";
					statement += "\t\t" + "<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Safari_0\"/>" + "\n";
					statement += "\t\t" + "<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Sunbathing_0\"/>" + "\n";
					statement += "\t\t" + "<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Surfing_0\"/>" + "\n";
					statement += "\t\t" + "<hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Yoga_0\"/>" + "\n";
					statement += "\t"+"</owl:NamedIndividual>" + "\n";
					break;
				}
			}
			
			Path path =Paths.get( OWL_PATH_TEMP );
			Charset charset = StandardCharsets.UTF_8;
			String content;
			
			try{
				
				content =new String( Files.readAllBytes( path ), charset );
				content = content.replaceAll( "</rdf:RDF>", statement + "</rdf:RDF>" );
				Files.write(path, content.getBytes( charset ) );
				
			} catch ( IOException e ){
				e.printStackTrace();		
			}
			
		}
		
		individualsCount = individualsCount + individuals;
		
	}
	
	public void removeDuplicatedFile() {
		
		
		
	}
	
}
