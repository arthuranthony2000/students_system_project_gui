package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.dao.DaoFactory;
import model.dto.StudentDTO;
import model.services.StudentService;

public class ReportController implements Initializable {
    @FXML
    private BarChart<String, Integer> studentReportChart;

    @FXML
    private Label averageLabel;

    private StudentService studentService;

    private List<StudentDTO> listOfStudents;

    private Integer total;
    private Integer approved;
    private Integer disapproved;
    private Integer inRecovery;
    private Float general_average;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.studentService = new StudentService(DaoFactory.createStudentDao());
        this.listOfStudents = studentService.findAll();

        this.total = 0;
        this.approved = 0;
        this.disapproved = 0;
        this.inRecovery = 0;

        Float average = 0.0f;
        for(StudentDTO s: listOfStudents){
            average += s.getAverage();
            if(s.getSituation().equalsIgnoreCase("APROVADO"))
                this.approved++;
            else if(s.getSituation().equalsIgnoreCase("REPROVADO"))
                this.disapproved++;
            else if(s.getSituation().equalsIgnoreCase("RECUPERAÇÃO"))
                this.inRecovery++;
        }
        this.general_average = average / listOfStudents.size();

        this.averageLabel.setText(String.format("Average: %2.2f", general_average));

        XYChart.Series<String, Integer> series1 = new XYChart.Series();

        series1.setName("All courses");
        series1.getData().add(new XYChart.Data("Approved", this.approved));
        series1.getData().add(new XYChart.Data("Disapproved", this.disapproved));
        series1.getData().add(new XYChart.Data("In Recovery", this.inRecovery));

        studentReportChart.getData().addAll(series1);
    }
}
