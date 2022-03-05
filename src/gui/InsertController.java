package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entities.Student;
import model.services.ServiceFactory;
import model.services.StudentService;

public class InsertController implements Initializable {

    private Student student;

    StudentService studentService;

    @FXML
    private Button btnSaveStudent;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField courseTextField;

    @FXML
    private TextField grade1TextField;

    @FXML
    private TextField grade2TextField;

    @FXML
    private TextField grade3TextField;

    @FXML
    private TextField grade4TextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField numberTextField;    

    @FXML
    void btnCancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnSaveStudentAction(ActionEvent event) {
        Student obj = new Student();

        obj.setNumber(Integer.parseInt(numberTextField.getText()));
        obj.setName(nameTextField.getText());
        obj.setCourse(courseTextField.getText());
        obj.setNota1(Float.parseFloat(grade1TextField.getText()));
        obj.setNota2(Float.parseFloat(grade2TextField.getText()));
        obj.setNota3(Float.parseFloat(grade3TextField.getText()));
        obj.setNota4(Float.parseFloat(grade4TextField.getText()));
        
        if(this.student.getNumber() == null)
            studentService.insert(obj);
        else if(this.studentService.findById(obj.getNumber()) != null)
            studentService.update(obj);
        else
            studentService.insert(obj);
        
        Stage stage = (Stage) btnSaveStudent.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        studentService = ServiceFactory.createStudentService();
    }

    public void setStudent(Student student){
        this.student = student;
    }

    public void updateFormData(){
        if(this.student == null)
            throw new IllegalStateException("Student was null");
        this.numberTextField.setText(this.student.getNumber().toString());
        this.nameTextField.setText(this.student.getName().toString());
        this.courseTextField.setText(this.student.getCourse().toString());
        this.grade1TextField.setText(this.student.getNota1().toString());
        this.grade2TextField.setText(this.student.getNota2().toString());
        this.grade3TextField.setText(this.student.getNota3().toString());
        this.grade4TextField.setText(this.student.getNota4().toString());
    }
}
