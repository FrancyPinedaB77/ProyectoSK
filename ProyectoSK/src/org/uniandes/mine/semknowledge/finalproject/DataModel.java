package org.uniandes.mine.semknowledge.finalproject;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DataModel {
	
	/*
	 * Atributos de la clase
	 */
	private static DataModel dataModel = new DataModel();
	private XYDataset loadTimeDataset = createLoadTimeDataset();
	private XYDataset queryResponseTimeDataset = createQueryResponseTimeDataset();
	
	private XYSeries rdfLoadTimeDataset;
	private XYSeries owlLoadTimeDataset;
	private XYSeries rdfQueryResponseTimeDataset;
	private XYSeries owlQueryResponseTimeDataset;
	
	
	/*
	 * Métodos de la clase
	 */
	
	// Método constructor
	private DataModel() {}
	
	public static DataModel getInstance() {
		return dataModel;
	}
	
	private XYDataset createLoadTimeDataset() {
		
		rdfLoadTimeDataset = new XYSeries( "RDF" );          
		owlLoadTimeDataset = new XYSeries( "OWL" );
		XYSeriesCollection dataset = new XYSeriesCollection( );          
		dataset.addSeries( rdfLoadTimeDataset );          
		dataset.addSeries( owlLoadTimeDataset );          
		return dataset;
	      
	}
	
	private XYDataset createQueryResponseTimeDataset() {
		
		rdfQueryResponseTimeDataset = new XYSeries( "RDF" );          
		owlQueryResponseTimeDataset = new XYSeries( "OWL" );
		XYSeriesCollection dataset = new XYSeriesCollection( );          
		dataset.addSeries( rdfQueryResponseTimeDataset );          
		dataset.addSeries( owlQueryResponseTimeDataset );          
		return dataset;
	      
	}
	
	// Método que permite obtener la instancia del dataset de tiempo de carga
	public XYDataset getLoadTimeDataset() {
		return loadTimeDataset;
	}
	
	public void setLoadTimeDataset( XYDataset loadTimeDataset ) {
		this.loadTimeDataset = loadTimeDataset;
	}
	
	// Método que permite obtener la instancia del dataset de tiempo de respuesta de la query
	public XYDataset getQueryResponseTimeDataset() {
		return queryResponseTimeDataset;
	}
	
	public void getQueryResponseTimeDataset( XYDataset queryResponseTimeDataset ) {
		this.queryResponseTimeDataset = queryResponseTimeDataset;
	}

	public XYSeries getRdfLoadTimeDataset() {
		return rdfLoadTimeDataset;
	}

	public XYSeries getOwlLoadTimeDataset() {
		return owlLoadTimeDataset;
	}

	public XYSeries getRdfQueryResponseTimeDataset() {
		return rdfQueryResponseTimeDataset;
	}

	public XYSeries getOwlQueryResponseTimeDataset() {
		return owlQueryResponseTimeDataset;
	}
	
}
