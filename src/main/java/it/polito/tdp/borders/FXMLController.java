
package it.polito.tdp.borders;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtAnno;

    @FXML
    private TextArea txtResult;

    
    
    @FXML
    void doCalcolaConfini(ActionEvent event) {

    	txtResult.clear();
    	
    	
    	//IL PROGRAMMA DEVE:
    	
    	//a.PERMETTERE ALL'UTENTE DI INSERIRE UN ANNO 
    	//NELL'INTERVALLO 1816 - 2016:
    	
    	//Controllo formato numerico (try-catch).
    	
    	//Controllo intervallo 1816 - 2016 (if).
    	
    	int anno; 
    	
    	try {
    		
    		anno = Integer.parseInt(txtAnno.getText());
        	
        	if ((anno < 1816) || (anno > 2016)) {
    			txtResult.setText("ERRORE: Inserire un anno nell'intervallo 1816 - 2016.");
    			return;
    		}
    		
    	} catch (NumberFormatException e) {
			txtResult.setText("ERRORE: Inserire l'anno correttamente.");
			return;
		}
    	
    	
    	try {
			
			//b.CREARE IL GRAFO CORRISPONDENTE ALL'ANNO INSERITO:
			model.creaGrafo(anno);
			
			
			//c.STAMPARE L'ELENCO DEGLI STATI (VERTICI DEL GRAFO),
			//INDICANDO PER CIASCUNO IL NUMERO DI STATI CONFINANTI (GRADO VERTICE):
			
			Map<Country,Integer> verticiConGrado = model.getVerticiConGrado();
			
			for (Country c : verticiConGrado.keySet())
				txtResult.appendText(c+"  -  Stati Confinanti: "+verticiConGrado.get(c)+"\n");
			
			
			//d.STAMPARE IL NUMERO DI COMPONENTI CONNESSE DEL GRAFO:/
			txtResult.appendText("\n\nNumero componenti connesse grafo: "+model.getNumComponentiConnesse());
		
			
		} catch (RuntimeException e) {
			txtResult.setText("ERRORE!!");
			return;
		}
    	
    }

    
    
    @FXML
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
