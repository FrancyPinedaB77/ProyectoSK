package org.uniandes.mine.semknowledge.finalproject.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import org.uniandes.mine.semknowledge.finalproject.model.EvaluationModel;

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
			
			int stopParam = appView.getStopValue();
			int stepParam = appView.getStepValue();
			int iterParam = appView.getIterValue();
			String caseParam = appView.getCaseValue();
			
			if( stepParam != 1 && stepParam != 10 && stepParam != 100 ) {
				stepParam = 10;
				appView.setStepValue( stepParam );
			}
			
			if( appView.showStartExecutionMessage() == 0 ) {
				
				Date startDate = new Date();
				
				evalModel.startEvaluation( stopParam, stepParam, iterParam, caseParam );
				appView.showStopExecutionMessage( new Date().getTime() - startDate.getTime() );
			}
			
		}
		
	}
	
}
