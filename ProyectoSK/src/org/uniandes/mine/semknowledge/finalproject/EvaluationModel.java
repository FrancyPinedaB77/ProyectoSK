package org.uniandes.mine.semknowledge.finalproject;


public class EvaluationModel {
	
	/*
	 * Atributos de la clase
	 */
	private DataModel dataModel = DataModel.getInstance();
	
	/*
	 * M�todos de la clase
	 */
	
	// M�todo constructor
	public EvaluationModel() {}
	
	// M�todo que inicia la evaluaci�n
	public void startEvaluation( int stopParam ) {
		
		// Reinicia los datasets
		dataModel.getRdfLoadTimeDataset().clear();
		dataModel.getOwlLoadTimeDataset().clear();
		dataModel.getRdfQueryResponseTimeDataset().clear();
		dataModel.getOwlQueryResponseTimeDataset().clear();
		
		for( int i = 0 ; i < stopParam ; i++ ) {
			
			dataModel.getRdfLoadTimeDataset().add( i , Math.random() * 10 );
			dataModel.getOwlLoadTimeDataset().add( i , Math.random() * 10 );
			
			dataModel.getRdfQueryResponseTimeDataset().add( i , Math.random() * 10 );
			dataModel.getOwlQueryResponseTimeDataset().add( i , Math.random() * 10 );
			
		}
		
	}
	
}
