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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.dto.StudentDTO;
import model.entities.Student;
import model.services.StudentService;

public class ListController implements Initializable {

    private StudentService studentService;

    private ObservableList<StudentDTO> obsList;

    @FXML
    private Button btnNewStudent;

    @FXML
    private Button btnDeleteStudent;

    @FXML
    private Button btnUpdateStudent;

    @FXML
    private TableView<StudentDTO> tableViewStudent;

    @FXML
    private TableColumn<StudentDTO, Integer> tableColumnNumber;

    @FXML
    private TableColumn<StudentDTO, String> tableColumnName;

    @FXML
    private TableColumn<StudentDTO, String> tableColumnCourse;

    @FXML
    private TableColumn<StudentDTO, Float> tableColumnGrade1;

    @FXML
    private TableColumn<StudentDTO, Float> tableColumnGrade2;

    @FXML
    private TableColumn<StudentDTO, Float> tableColumnGrade3;

    @FXML
    private TableColumn<StudentDTO, Float> tableColumnGrade4;

    @FXML
    private TableColumn<StudentDTO, Float> tableColumnAverage;

    @FXML
    private TableColumn<StudentDTO, String> tableColumnSituation;

    @FXML
    void btnNewStudentAction(ActionEvent event) {
        Student student = new Student();
        createDialogForm(student, "/gui/InsertView.fxml", Utils.currentStage(event));
        updateTableView();
    }

    @FXML
    void btnDeleteStudentAction(ActionEvent event) {
        try {

            // Stage stage = (Stage) Main.getMainScene().getWindow();
            // TablePosition tp = tableViewStudent.getFocusModel().getFocusedCell();
            // Object id = tp.getTableColumn().getCellData(tp.getRow());
            Object selectedItems = tableViewStudent.getSelectionModel().getSelectedItems().get(0);
            StudentDTO student = (StudentDTO) selectedItems;
            Stage stage = (Stage) Main.getMainScene().getWindow();
            Integer id = student.getNumber();

            studentService.deleteById((Integer) id);
            updateTableView();

        } catch (IndexOutOfBoundsException e) {
        }
    }

    @FXML
    void btnUpdateStudentAction(ActionEvent event) {
        try {
            Object selectedItems = tableViewStudent.getSelectionModel().getSelectedItems().get(0);
            StudentDTO student = (StudentDTO) selectedItems;
            Stage stage = (Stage) Main.getMainScene().getWindow();

            createDialogForm(StudentDTO.convertToStudent(student), "InsertView.fxml", stage);
            updateTableView();
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    private void initializeNodes() {
        tableColumnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        tableColumnGrade1.setCellValueFactory(new PropertyValueFactory<>("grade1"));
        tableColumnGrade2.setCellValueFactory(new PropertyValueFactory<>("grade2"));
        tableColumnGrade3.setCellValueFactory(new PropertyValueFactory<>("grade3"));
        tableColumnGrade4.setCellValueFactory(new PropertyValueFactory<>("grade4"));
        tableColumnAverage.setCellValueFactory(new PropertyValueFactory<>("average"));
        tableColumnSituation.setCellValueFactory(new PropertyValueFactory<>("situation"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewStudent.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView() {
        if (studentService == null) {
            throw new IllegalStateException("Student Service was null");
        }

        List<StudentDTO> list = studentService.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewStudent.setItems(obsList);
    }

    public void createDialogForm(Student student, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            InsertController insertController = loader.getController();
            insertController.setStudent(student);

            if (student.getNumber() != null)
                insertController.updateFormData();

            Stage dialogStage = new Stage();

            dialogStage.setTitle("Enter Student data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }
}
