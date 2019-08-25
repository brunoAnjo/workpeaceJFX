package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import moodel.services.DepartamentListService;

public class DepartamentListController implements Initializable{

	private DepartamentListService service;
	
	
	@FXML
	private TableView<Department> tableUsuario;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private TableColumn<Department, String> tableColumnSobrenome;
	
	
	@FXML
	private Button btNew;
	
	private ObservableList<Department> obsList;
	
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.curredStage(event);
		
		Department depart = new Department();
		
		createDialogForm(depart, "/gui/DepartamentForm.fxml", parentStage);
	}
	
	public void setDepartamentService(DepartamentListService ds) {
		this.service = ds;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getScene().getWindow();
		tableUsuario.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView(){
		
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		
		List<Department> list = service.findAll();
		obsList = (ObservableList<Department>) FXCollections.observableArrayList(list);
		tableUsuario.setItems(obsList);
		
	}
	
	private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			DepartamentFormController controler = loader.getController();
			controler.setDepartament(obj);
			controler.setDepartamentService(new DepartamentListService());
			controler.updateDepartament();
			
			Stage dialoStage = new Stage();
			dialoStage.setTitle("Enter Departament data");
			dialoStage.setScene(new Scene(pane));
			dialoStage.setResizable(false);
			dialoStage.initOwner(parentStage);
			dialoStage.initModality(Modality.WINDOW_MODAL);
			dialoStage.showAndWait();
			
			
			
			
		}catch(IOException e) {
			Alerts.showAlerts("IO EXCEPTION", "Erro loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
