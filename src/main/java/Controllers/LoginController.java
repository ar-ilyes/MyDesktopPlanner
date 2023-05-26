package Controllers;

import Modules.Modal;
import Modules.UserNotFound;
import Modules.WrongPassword;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController implements Initializable {
    @FXML
    TextField thePseudo;
    @FXML
    TextField thePassword;
    @FXML
    Button LoginBtn;
    @FXML
    Button NewAccount;
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        LoginBtn.setOnAction(e -> {
           try {
               onLogin();
           }catch(WrongPassword ex) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Mot de passe incorrect");
                alert.setContentText("Veuillez réessayer");
                alert.showAndWait();
           }catch(UserNotFound ex){
               Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Utilisateur non trouvé");
                alert.setContentText("Veuillez réessayer");
                alert.showAndWait();
           } catch (Exception ex) {
               throw new RuntimeException(ex);
           }
        });
        NewAccount.setOnAction(e -> {
            try {
                onNewAccount();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public void onLogin() throws UserNotFound, WrongPassword{
        //loop over utilisateur list in myPlannerApp to find the user
        String pseudo=thePseudo.getText();
        String motDePasse=thePassword.getText();
        if(Modal.getMyPlannerApp().getUsers().containsKey(pseudo)){
            if(Modal.getMyPlannerApp().getUsers().get(pseudo).getMotDePasse().equals(motDePasse)){
                Modal.getMyPlannerApp().setCurrentUser(Modal.getMyPlannerApp().getUsers().get(pseudo));
                try {
                    Modal.getInstance().getAppView().ShowCalendarPage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                throw new WrongPassword();
            }
        }
        else{
            throw new UserNotFound();
        }
    }
    public void onNewAccount() throws IOException {
        Modal.getInstance().getAppView().ShowRegister();
    }
}
