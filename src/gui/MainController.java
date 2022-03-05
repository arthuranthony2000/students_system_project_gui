package gui;

import java.io.IOException;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.dao.DaoFactory;
import model.services.StudentService;

public class MainController {

    @FXML
    private Menu aboutItem;

    @FXML
    private Menu listItem;

    @FXML
    private Menu reportItem;

    @FXML
    void aboutItemAction(ActionEvent event) {
        loadView("/gui/AboutView.fxml", x -> {
        });
    }

    @FXML
    void listItemAction(ActionEvent event) {
        loadView("/gui/ListView.fxml", (ListController controller) -> {
            controller.setStudentService(new StudentService(DaoFactory.createStudentDao()));
            controller.updateTableView();
        });
    }

    @FXML
    void reportItemAction(ActionEvent event) {
        loadView("/gui/ReportView.fxml", x -> {
        });
    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            Node mainMenu = mainVBox.getChildren().get(0);

            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());

            T controller = loader.getController();
            initializingAction.accept(controller);

        } catch (IOException e) {
            e.printStackTrace();
            // Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }
}
