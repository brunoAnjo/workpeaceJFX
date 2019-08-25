package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartamentFormController implements Initializable{
	
	private Department entiti;

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
	
	public void departamentSet(Department entites) {
		this.entiti = entites;
	}
	
	@FXML
	public void onBtSaveAction() {
		System.out.print("Save");
	}
	
	@FXML
	public void onBtCancelActinon() {
		System.out.print("Cancel");
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
