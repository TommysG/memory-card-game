package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Dialog  {

    @FXML
    private Button yes,no;



    public void yesClicked(){
        Platform.exit();
    }

    public void noClicked(){
        Stage dialog = (Stage) no.getScene().getWindow();
        dialog.close();
    }

}
