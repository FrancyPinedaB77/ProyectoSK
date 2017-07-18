package org.uniandes.mine.semknowledge.finalproject.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.uniandes.mine.semknowledge.finalproject.EvaluationModel;

public class ApplicationController {
	
	/*
	 * Atributos de la clase
	 */
	private ApplicationView appView;
	private EvaluationModel evalModel;
	
	
	/*
	 * Métodos de la clase
	 */
	
	// Método constructor
	public ApplicationController( ApplicationView appView, EvaluationModel evalModel ) {
		this.appView = appView;
		this.evalModel  = evalModel;
        
		// Add listeners to the view
		this.appView.addStartListener( new StartListener() );
		
    }
	
	/*
	 * Clases Listeners
	 */
	
	class StartListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			int stopParam = Integer.parseInt( appView.getUserInput().replace( ".", "" ) );
			
			if( stopParam > 0 ) {
				
				evalModel.startEvaluation( stopParam );
				
			}
			
		}
		
	}
	
}
