package org.uniandes.mine.semknowledge.finalproject.view;


import org.jfree.chart.ChartPanel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.uniandes.mine.semknowledge.finalproject.model.DataModel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;


public class ApplicationView extends ApplicationFrame {
	
	/*
	 * Atributos de la clase
	 */
	private JFormattedTextField stopTxt;
	private JFormattedTextField stepTxt;
	private JFormattedTextField iterTxt;
	private JComboBox<String> caseCbx;
	private JButton startBtn;
	private JFreeChart loadTimeChrat;
	private JFreeChart queryResponseTimeChrat;
	
	private static final long serialVersionUID = 7180530533946576504L;
	
	/*
	 * Métodos de la clase
	 */
	
	// Método constructor
	public ApplicationView( String applicationHeader , String applicationTitle, String applicationSubtitle, String applicationFooter ) {
		
		super( applicationHeader );
		
		JPanel mainPnl = new JPanel();
		mainPnl.setLayout(new BorderLayout());
		this.add( mainPnl );
		
		
		/*
		 * Encabezado
		 */
		JPanel headerPnl = new JPanel();
		headerPnl.setLayout(new BorderLayout());
		mainPnl.add( headerPnl, BorderLayout.NORTH );
		
		// Título
		JLabel titleLbl = new JLabel( applicationTitle, SwingConstants.CENTER );
		titleLbl.setFont( new Font( titleLbl.getFont().getFamily(), Font.BOLD, 28 ) );
		headerPnl.add( titleLbl, BorderLayout.NORTH );
		
		// Subtítulo
		JLabel subtitleLbl = new JLabel( applicationSubtitle, SwingConstants.CENTER );
		subtitleLbl.setFont( new Font( titleLbl.getFont().getFamily(), Font.BOLD, 24 ) );
		headerPnl.add( subtitleLbl, BorderLayout.CENTER );
		
		// Start
		JPanel startPnl = new JPanel();
		
		JLabel experimentLbl = new JLabel( "EXPERIMENTS: " );
		startPnl.add( experimentLbl );
		
		JLabel stopLbl = new JLabel( "Stop Parameter [1-1.000.000]: " );
		startPnl.add( stopLbl );
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter( format );
	    formatter.setValueClass( Integer.class );
	    formatter.setMinimum( 1 );
	    formatter.setMaximum( 1000000 );
	    formatter.setAllowsInvalid( false );
		stopTxt = new JFormattedTextField( formatter );
		stopTxt.setValue( 500 );
		stopTxt.setColumns( 6 );
		stopTxt.setToolTipText( "Cantidad máxima de registros / individuos a cargar / consultar" );
		startPnl.add( stopTxt );
		
		JLabel stepLbl = new JLabel( "Step (1/10/100): " );
		startPnl.add( stepLbl );
		NumberFormat format2 = NumberFormat.getInstance();
	    NumberFormatter formatter2 = new NumberFormatter( format2 );
	    formatter2.setValueClass( Integer.class );
	    formatter2.setMinimum( 1 );
	    formatter2.setMaximum( 1000 );
	    formatter2.setAllowsInvalid( false );
		stepTxt = new JFormattedTextField( formatter2 );
		stepTxt.setValue( 10 );
		stepTxt.setColumns( 4 );
		stepTxt.setToolTipText( "Aumento de la cantidad de registros / individos entre experimentos" );
		startPnl.add( stepTxt );
		
		JLabel iterLbl = new JLabel( "Iterations [1 - 20]: " );
		startPnl.add( iterLbl );
		NumberFormat format3 = NumberFormat.getInstance();
	    NumberFormatter formatter3 = new NumberFormatter( format3 );
	    formatter3.setValueClass( Integer.class );
	    formatter3.setMinimum( 1 );
	    formatter3.setMaximum( 20 );
	    formatter3.setAllowsInvalid( false );
		iterTxt = new JFormattedTextField( formatter3 );
		iterTxt.setValue( 5 );
		iterTxt.setColumns( 3 );
		iterTxt.setToolTipText( "Cantidad de iteraciones por experimento" );
		startPnl.add( iterTxt );
		
		JLabel caseLbl = new JLabel( "Case to consider: " );
		startPnl.add( caseLbl );
		String[] caseOpts = { "Worst", "Mean", "Best" };
		caseCbx = new JComboBox<String>( caseOpts );
		caseCbx.setSelectedIndex( 0 );
		startPnl.add( caseCbx );
		
		startBtn = new JButton( "Start" );
		startPnl.add( startBtn );
		headerPnl.add( startPnl, BorderLayout.SOUTH );
		
		/*
		 * Body
		 */
		JPanel bodyPnl = new JPanel();
		bodyPnl.setLayout(new BorderLayout());
		mainPnl.add( bodyPnl, BorderLayout.CENTER );
		
		
		/*
		 * Load Time Chart
		 */
		loadTimeChrat = ChartFactory.createXYAreaChart(
				"Load Time",
				"Rows / Individuals", "Milliseconds (ms)",
				getLoadTimeDataset(),
				PlotOrientation.VERTICAL,
				true, true, false );
		
		ChartPanel loadTimePnl = new ChartPanel( loadTimeChrat );
		loadTimePnl.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		bodyPnl.add( loadTimePnl, BorderLayout.WEST );
		
		
		/*
		 * Query Response Chart
		 */
		queryResponseTimeChrat = ChartFactory.createXYAreaChart(
				"Query Response Time",
				"Rows / Individuals", "Milliseconds (ms)",
				getQueryResponseTimeDataset(),
				PlotOrientation.VERTICAL,
				true, true, false );
		
		ChartPanel queryResponsePnl = new ChartPanel( queryResponseTimeChrat );
		queryResponsePnl.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		bodyPnl.add( queryResponsePnl, BorderLayout.EAST );
		
		/*
		 * Footer
		 */
		JPanel footerPnl = new JPanel();
		footerPnl.setLayout(new BorderLayout());
		mainPnl.add( footerPnl, BorderLayout.SOUTH );
		
		// Subtítulo
		JLabel footerLbl = new JLabel( applicationFooter, SwingConstants.CENTER );
		footerLbl.setFont( new Font( footerLbl.getFont().getFamily(), Font.BOLD, 20 ) );
		footerPnl.add( footerLbl, BorderLayout.CENTER );
		
	}
	
	public int showStartExecutionMessage() {
		
		String message = "Se correran un total de " + ( getStopValue() / getStepValue() ) + " experimentos con la siguiente configuración:\n\n" ;
		message += " - Número máximo de registros / individos a cardar / consultar: " + getStopValue() + "\n";
		message += " - Aumento de la cantidad de registros / individos entre experimentos: " + getStepValue() + "\n";
		message += " - Cantidad de iteraciones por experimento: " + getIterValue() + "\n";
		message += " - Caso: " + getCaseValue() + "\n\n";
		message += "¿Iniciar? \n";
		
		return JOptionPane.showConfirmDialog( null, message, "Inicio de la experimentación", JOptionPane.YES_NO_OPTION );
		
	}
	
	public void showStopExecutionMessage( double time ) {
		
		JOptionPane.showMessageDialog( null, "Fin de la experimentación\n\nTiempo total transcurrido: " + ( time / 1000 ) + " segundos" );
		
	}
	
	// Devuelve el valor actual digitado por el usuario en el campo de texto
	public int getStopValue() {
		return ( int ) stopTxt.getValue();
	}
	
	public int getStepValue() {
		return ( int ) stepTxt.getValue();
	}
	
	public void setStepValue( int text ) {
		stepTxt.setValue( text );
	}
	
	public int getIterValue() {
		return ( int ) iterTxt.getValue();
	}
	
	public String getCaseValue() {
		return ( String ) caseCbx.getSelectedItem();
	}
	
	public JButton getStartBtn() {
		return startBtn;
	}

	// Listener del botón que inicial el proceso en el modelo
	void addStartListener( ActionListener listener ) {
		startBtn.addActionListener( listener );
    }
	
	// Método que permite obtener la instancia del dataset de tiempo de carga
	private XYDataset getLoadTimeDataset( ) {
		return DataModel.getInstance().getLoadTimeDataset();
	}
	
	// Método que permite obtener la instancia del dataset de tiempo de respuesta de la query
	private XYDataset getQueryResponseTimeDataset( ) {
		return DataModel.getInstance().getQueryResponseTimeDataset();
	}

}
