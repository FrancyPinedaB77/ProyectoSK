package org.uniandes.mine.semknowledge.finalproject.model.utilities;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RDFUtilities extends FileUtilities {
	
	private int rowsCount;	
	
	public RDFUtilities( String RDF_PATH, String RDF_PATH_TEMP ) {
		super( RDF_PATH, RDF_PATH_TEMP );
		
		rowsCount = 0;
	}
	
	public void createRows( int rows ) {
		
		for ( int i = rowsCount; i < rowsCount + rows ; i++ ) {
			
			String statement = "\n\n\t" + "<rdf:Description rdf:about=\"http://www.owl-ontologies.com/travel.owl#Destination_" + i + "\">" + "\n";
			statement += "\t\t" + "<travel:type rdf:resource=\"http://www.owl-ontologies.com/travel.owl#AccommodationType_0\"/>" + "\n";
			statement += "\t\t" + "<travel:hasAccommodation rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Accommodation_0\"/>" + "\n";
			statement += "\t\t" + "<travel:hasActivity rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Activity_0\"/>" + "\n";
			statement += "\t\t" + "<travel:hasRating rdf:resource=\"http://www.owl-ontologies.com/travel.owl#Rating_0\"/>" + "\n";
			statement += "\t" + "</rdf:Description>" + "\n";
			
			Path path = Paths.get( FILE_PATH_TEMP );
			Charset charset = StandardCharsets.UTF_8;
			String content;
			
			try{
				
				content = new String( Files.readAllBytes( path ), charset );
				content = content.replaceAll( "</rdf:RDF>", statement + "</rdf:RDF>" );
				Files.write(path, content.getBytes( charset ) );
				
			} catch ( IOException e ){
				e.printStackTrace();		
			}
			
		}
		
		rowsCount = rowsCount + rows;
		
	}
	
}
