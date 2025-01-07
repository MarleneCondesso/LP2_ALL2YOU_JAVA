package com.all2you;

import java.io.IOException;

import com.all2you.services.Service;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    private Stage stage;
    private static Scene scene;
    public static String viewsPath = "./views/";
    private StackPane layout;
    private AnchorPane menuLateral;

    /**
     * Método Stage -> Arranque inicial da Aplicação
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        
        
        this.stage = stage;
        stage.setTitle("ALL 2 YOU APP");

        layout();
        menuLateral();
    }
    /**
     * Método de iniciação de Primeiro Stage.
     */
    private void layout() {
        try {
            layout = (StackPane) (loadFXML("home"));

            scene = new Scene(layout);
//            scene.getStylesheets().add(getClass().getResource("/main.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método de junção de Menu Lateral ao Primeiro Stage.
     */
    public void menuLateral() {
        try {
            menuLateral = (AnchorPane) (loadFXML("menu"));

            layout.getChildren().add(menuLateral);
        } catch (IOException ex) {
            System.out.println("erro no menu lateral");
            ex.printStackTrace();
        }
    }
    
    public static void setRoot(String fxml) throws IOException {
       scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(viewsPath + fxml + ".fxml"));
        return fxmlLoader.load();

    }

    public static void main(String[] args) {
        Service.setConfiguration("hibernate.cfg.xml");

        // ClientesService cs = new ClientesService();
        
        // Morada m = cs.getAllAddresses().get(1);

        // Cliente c = clientService.getClient(123);
        
        // System.out.println("Cliente: " + c.getName());        
        // System.out.println("Morada: " + c.getAddresses().get(0).getAddress());

        // m.setIsFiscalAddress(true);
        
        // cs.updateAddress(m);

        
//        com.all2you.tests.OperadoresServiceTest.testGetAndListAllOperators();

//        com.all2you.services.test clientService = new test();


        //ClienteServiceTest cst = new ClienteServiceTest();
//        ContactosServiceTest contactoServiceTest = new ContactosServiceTest();

        
        //cst.testAll();
//        contactoServiceTest.testAll();

        launch();
    }
}
