package org.uniandes.mine.semknowledge.finalproject.view;


import org.jfree.chart.ChartPanel;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.uniandes.mine.semknowledge.finalproject.DataModel;
import org.uniandes.mine.semknowledge.finalproject.EvaluationModel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;


public class ApplicationView extends ApplicationFrame {
	
	/*
	 * Atributos de la clase
	 */
	private JFormattedTextField stopTxt;
	private JFormattedTextField stepTxt;
	private JFormattedTextField iterTxt;
	private JButton startBtn;
	private JFreeChart loadTimeChrat;
	private JFreeChart queryResponseTimeChrat;
	
	private static final long serialVersionUID = 7180530533946576504L;
	
	/*
	 * Métodos de la clase
	 */
	
	// Método constructor
	public ApplicationView( String applicationHeader , String applicationTitle, String applicationSubtitle ) {
		
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
		
		JLabel stopLbl = new JLabel( "Stop Parameter: " );
		startPnl.add( stopLbl );
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter( format );
	    formatter.setValueClass( Integer.class );
	    formatter.setMinimum( 1 );
	    formatter.setMaximum( 100000 );
	    formatter.setAllowsInvalid( false );
		stopTxt = new JFormattedTextField( formatter );
		stopTxt.setValue( 100 );
		stopTxt.setColumns( 6 );
		startPnl.add( stopTxt );
		
		JLabel stepLbl = new JLabel( "Step (1/10/100): " );
		startPnl.add( stepLbl );
		NumberFormat format2 = NumberFormat.getInstance();
	    NumberFormatter formatter2 = new NumberFormatter( format2 );
	    formatter2.setValueClass( Integer.class );
	    formatter2.setMinimum( 1 );
	    formatter2.setMaximum( 100 );
	    formatter2.setAllowsInvalid( false );
		stepTxt = new JFormattedTextField( formatter2 );
		stepTxt.setValue( 10 );
		stepTxt.setColumns( 4 );
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
		iterTxt.setValue( 20 );
		iterTxt.setColumns( 3 );
		startPnl.add( iterTxt );
		
		startBtn = new JButton( "Start" );
		startPnl.add( startBtn );
		headerPnl.add( startPnl, BorderLayout.CENTER );
		
		/*
		 * Body
		 */
		JPanel bodyPnl = new JPanel();
		bodyPnl.setLayout(new BorderLayout());
		mainPnl.add( bodyPnl, BorderLayout.SOUTH );
		
		
		/*
		 * Load Time Chart
		 */
		loadTimeChrat = ChartFactory.createXYAreaChart(
				"Load Time - Worst Case",
				"Rows", "Milliseconds (ms)",
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
				"Query Response Time - Worst Case",
				"Rows", "Milliseconds (ms)",
				getQueryResponseTimeDataset(),
				PlotOrientation.VERTICAL,
				true, true, false );
		
		ChartPanel queryResponsePnl = new ChartPanel( queryResponseTimeChrat );
		queryResponsePnl.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		bodyPnl.add( queryResponsePnl, BorderLayout.EAST );
		
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
