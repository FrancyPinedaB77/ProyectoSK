package org.uniandes.mine.semknowledge.finalproject;

import org.jfree.ui.RefineryUtilities;
import org.uniandes.mine.semknowledge.finalproject.model.EvaluationModel;
import org.uniandes.mine.semknowledge.finalproject.view.ApplicationController;
import org.uniandes.mine.semknowledge.finalproject.view.ApplicationView;

public class Main {

	public static void main(String[] args) {
		
		// Se crea la instancia de la UI
		ApplicationView appView = new ApplicationView(
				"Semantic Knowledge Engineering and Applications",
				"Final Project: RDF vs OWL Performance Evaluation",
				"Francy Julieth Pineda - Fabian Camilo Peña",
				"Universidad de los Andes - Julio 2017" );
		
		// Se instancia el modelo
		EvaluationModel evalModel = new EvaluationModel();
		
		// Se instancia el controlador de la UI
		new ApplicationController( appView, evalModel );
		
		// Se establecen las propiedades de la UI
		appView.pack();
		RefineryUtilities.centerFrameOnScreen( appView );
		appView.setVisible( true );
		
	}

}
