package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listener.DataChangerListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import moodel.services.DepartamentListService;

public class DepartamentFormController implements Initializable{
	
	private Department entiti;
	
	private DepartamentListService service;
	
	private List<DataChangerListener> dataChangerListeners =  new ArrayList<>();

	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setDepartament(Department entites) {
		this.entiti = entites;
	}
	
	public void setDepartamentService(DepartamentListService service) {
		this.service = service;
	}
	
	public void subscribeDataChangerListener(DataChangerListener listener) {
		dataChangerListeners.add(listener);
	}
	
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		
		if(entiti == null) {
			throw new IllegalStateException("Entity was null");
		}
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		
		try {
			
			entiti = getFromData();
			service.saverOrUpdate(entiti);
			notfyDaraChangerListener();
			Utils.curredStage(event).close();
			
		}catch(DbException e) {
			Alerts.showAlerts("Erro saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notfyDaraChangerListener() {
		
		for(DataChangerListener listener : dataChangerListeners) {
			listener.onDataChanger();
		}
		
	}

	private Department getFromData() {
		Department obj = new Department();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		
		return obj;
	}

	@FXML
	public void onBtCancelActinon(ActionEvent event) {
		Utils.curredStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updateDepartament() {
		if(entiti == null) {
			throw new IllegalStateException("Entidade esta nula");
		}
		
		txtId.setId(String.valueOf(entiti.getId()));
		txtName.setText(entiti.getName());
	}
}
