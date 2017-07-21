package org.uniandes.mine.semknowledge.finalproject.model.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtilities {
	
	protected String FILE_PATH;
	protected String FILE_PATH_TEMP;
	
	public FileUtilities( String FILE_PATH, String FILE_PATH_TEMP ) {
		this.FILE_PATH = FILE_PATH;
		this.FILE_PATH_TEMP = FILE_PATH_TEMP;
		
		duplicateFile();
	}
	
	public void duplicateFile() {
		
		InputStream is = null;
		OutputStream os = null;
		try {
			
			is = new FileInputStream( new File( FILE_PATH ) );
			os = new FileOutputStream( new File( FILE_PATH_TEMP ) );
	        
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
	
	public void removeDuplicatedFile() {
		
		try{

			File file = new File( FILE_PATH_TEMP );
			if(file.delete()){
				System.out.println( "Archivo temporal eliminado satisfactoriamente" );
			}else{
				System.out.println( "No se pudo eliminar el archivo temporal" );
			}
			
		}catch(Exception e){
			e.printStackTrace();
    	}
		
	}
	
}
