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
	
	public OWLUtilities( String OWL_PATH, String OWL_PATH_TEMP ) {
		this.OWL_PATH = OWL_PATH;
		this.OWL_PATH_TEMP = OWL_PATH_TEMP;
		
		duplicateFile();
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
			
		} catch( IOException e ) {
			e.printStackTrace();
		}
		
	}
	
	public void createIndividuals( int individuals ) {
		
		for ( int i = 0; i < individuals ; i++ ) {
			
			// Seleccionar clase aleatoria
			int min=0;
			int max=6; //Numero de clases que se van a usar
			int clase = ThreadLocalRandom.current().nextInt(min, max );
			String statement="";
			
			
		}
		
		
		
		
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
	
	public void removeDuplicatedFile() {
		
		
		
	}
	
}
