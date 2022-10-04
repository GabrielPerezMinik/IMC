package imc;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {

	private VBox vbox = new VBox();

	private HBox pesohbox = new HBox();
	private HBox alturahbox = new HBox();
	private HBox imchbox = new HBox();

	private TextField pesoText = new TextField();
	private TextField alturaText = new TextField();

	private Label pesolabel = new Label();
	private Label kglabel = new Label();
	private Label alturalabel = new Label();
	private Label cmlabel = new Label();
	private Label imclabel = new Label();
	private Label estatuslabel = new Label();

	private DoubleProperty pesoProperty = new SimpleDoubleProperty();
	private DoubleProperty alturaProperty = new SimpleDoubleProperty();
	private DoubleProperty imcProperty = new SimpleDoubleProperty();
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		pesolabel.setText("Peso:");
		kglabel.setText("Kg");
		alturalabel.setText("Altura:");
		cmlabel.setText("cm");
		imclabel.setText("IMC: (peso/(altura^2))");
		estatuslabel.setText("Bajo peso | Normal | Sobrepeso | Obeso");

		vbox.getChildren().addAll(pesohbox, alturahbox, imchbox, estatuslabel);

		pesohbox.getChildren().addAll(pesolabel, pesoText, kglabel, alturalabel, alturaText, cmlabel, imclabel);
		pesohbox.setAlignment(Pos.CENTER);

		alturahbox.getChildren().addAll(alturalabel, alturaText, cmlabel);
		alturahbox.setAlignment(Pos.CENTER);

		imchbox.getChildren().addAll(imclabel);
		imchbox.setAlignment(Pos.CENTER);

		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox, 320, 200);
		primaryStage.setTitle("IMC.fxml");
		primaryStage.setScene(scene);
		primaryStage.show();

		
		
		pesoText.textProperty().bindBidirectional(pesoProperty, new NumberStringConverter());
		alturaText.textProperty().bindBidirectional(alturaProperty, new NumberStringConverter());
			
		//preguntar 
			
		alturaProperty.addListener((o,ov,nv)->{
			double newValue= nv.doubleValue();
			if(newValue!=0) {
				imclabel.textProperty().bindBidirectional(imcProperty,new NumberStringConverter());
			}
		});
		
		imcProperty.bind(pesoProperty.divide((alturaProperty.divide(100)).multiply(alturaProperty.divide(100))));
		
		//En los listener se pueden poner funciones
		//imcProperty.addListener((o,ov,nv) ->{ System.out.println(calculoIMC(nv.intValue(), ov.intValue()));});
		
		imcProperty.addListener((o,ov,nv) ->{
			double newValue= nv.doubleValue();
			if (newValue < 18.5) {
				estatuslabel.setText("Bajo peso");
			} else if (newValue >= 18.5 && newValue < 25) {
				estatuslabel.setText("Normal");
			} else if (newValue >= 18.5 && newValue < 30) {
				estatuslabel.setText("Sobrepeso");
			} else if (newValue > 30) {
				estatuslabel.setText("Obeso");
			}
		});

	}

	public double calculoIMC(int peso, int altura) {

		double imc;

		imc = peso / (altura ^ 2);

		return imc;
	}

	public static void main(String[] args) {

		launch(args);
	}

}
