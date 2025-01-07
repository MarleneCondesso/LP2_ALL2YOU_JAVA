package com.all2you.controllers;

import com.all2you.App;
import com.all2you.models.clientes.Cliente;
import com.all2you.models.clientes.Morada;
import com.all2you.models.clientes.contactos.ContactoCliente;
import com.all2you.services.ClientesService;
import com.all2you.services.ContactosService;
import com.all2you.services.MaquinasService;
import com.all2you.utils.Estados;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author Marlene
 */
public class ClientesController implements Initializable {

    @FXML
    private Pane pane;
    
    @FXML
    private Tab moradasTab;

    @FXML
    private Tab contactosTab;
        
    @FXML
    private Button btnAdicionarM;

    @FXML
    private Button btnEditarM;

    @FXML
    private Button btnEliminarM;

    @FXML
    public TableView<Morada> tvMoradas;

    @FXML
    private TableColumn<Morada, Integer> idCol;

    @FXML
    private TableColumn<Morada, String> EnderecoCol;

    @FXML
    private TableColumn<Morada, String> cdPostalCod;

    @FXML
    private TableColumn<Morada, String> localCod;

    @FXML
    private TableColumn<Morada, String> paisCol;

    @FXML
    private TableColumn<Morada, String> moradaFscCol;

    @FXML
    private Button btnAdicionarC;

    @FXML
    private Button btnEditarC;

    @FXML
    private Button btnEliminarC;

    @FXML
    public TableView<ContactoCliente> tvContactos;

    @FXML
    private TableColumn<ContactoCliente, String> contacCol;

    @FXML
    private TableColumn<ContactoCliente, String> tpContacCol;

    @FXML
    public ComboBox<Cliente> comboNome;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;


    private ClientesService serviceClientes = new ClientesService();
    private ContactosService serviceContactos = new ContactosService();
    
    private MoradaController moradaController;
    private ContactoController contactoController;

    private Scene scene;

    private Stage stage;
    List<Cliente> clienteList;
    ObservableList obsListMorada;
    ObservableList obsListContactos;
    ObservableList obsListContactos1;
    ClienteController clienteController;
    
    
    /**
     * Método para listagem de Morada e Contacto após seleção da combobox Cliente.
     * @param event 
     */
    @FXML
    void acaoComboBox(ActionEvent event){
        
        Cliente cliente = comboNome.getSelectionModel().getSelectedItem();
    
        refreshTableContactos(cliente);
        refreshTableMoradas(cliente);
    }
    /**
     * Método de Adiconar para o botão Adiconar Cliente
     *
     * @param event
     */
    @FXML
    void adicionarCliente(ActionEvent event) {
        
        try {
            showModalClienteView("cliente", null, "Adicionar Cliente", null);
        } catch (IOException ex) {
            System.out.println("ERRO AO ADIONAR CLIENTE, NÃO ABRE VIEW" + ex);
        }
    }

    /**
     * Método de Adicionar para o botão Adicionar Contacto
     *
     * @param event
     */
    @FXML
    void adicionarContacto(ActionEvent event) {
        Cliente cliente = comboNome.getSelectionModel().getSelectedItem();
        
        if (cliente != null){
            try {
                showModalContactoView("contacto", cliente, null, "Adicionar Contacto ao Cliente", null);
            } catch (IOException ex) {
                System.out.println("ERRO AO ADICIONAR CLIENTE, NÃO ABRE VIEW" + ex);
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Cliente em falta", "Por favor, selecione um Cliente.");
        }
    }

    /**
     * Método de Adicionar para o botão Adicionar Morada
     *
     * @param event
     */
    @FXML
    void adicionarMorada(ActionEvent event) {
        Cliente cliente = comboNome.getSelectionModel().getSelectedItem();
        
        if(cliente != null){
            try {
                showModalMoradaView("morada", cliente, null, "Adicionar Morada ao Cliente", null);
            } catch (IOException ex) {
                System.out.println("ERRO AO ADIONAR CLIENTE, NÃO ABRE VIEW" + ex);
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Cliente em falta", "Por favor, selecione um Cliente.");
        }
    }

    /**
     * Método de Editar para o botão Editar Cliente
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void editarCliente(ActionEvent event) throws IOException {
        Cliente cliente = comboNome.getSelectionModel().getSelectedItem();
        Integer pos = comboNome.getSelectionModel().getSelectedIndex();
        
        if (!isNull(cliente)) {

            showModalClienteView("cliente", cliente, "Editar Cliente", pos);

        } else {
            AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Cliente em falta", "Por favor, selecione um Cliente.");
        }
    }

    /**
     * Método de Editar para o botão Editar Contacto
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void editarContacto(ActionEvent event) {
        Cliente cliente = comboNome.getSelectionModel().getSelectedItem();
        ContactoCliente contacto = tvContactos.getSelectionModel().getSelectedItem();
        Integer pos = tvContactos.getSelectionModel().getSelectedIndex();
        
        if (cliente != null){
            if (!isNull(contacto)) {

                try {
                    showModalContactoView("contacto", cliente, contacto, "Editar Contacto do Cliente", pos);
                } catch (IOException ex) {
                    System.out.println("Erro na edição de contacto" + ex);
                }

            } else {
                AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Contacto em falta", "Por favor, selecione um Contacto.");
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Cliente em falta", "Por favor, selecione um Cliente.");
        }    
    }

    /**
     * Método de Editar para o botão Editar Morada
     *
     * @param event
     */
    @FXML
    void editarMorada(ActionEvent event) {
        Cliente cliente = comboNome.getSelectionModel().getSelectedItem();
        Morada morada = tvMoradas.getSelectionModel().getSelectedItem();
        Integer pos = tvMoradas.getSelectionModel().getSelectedIndex();

        if (cliente != null){
            if (!isNull(morada)) {

                try {
                    showModalMoradaView("morada", cliente, morada, "Editar Morada do Cliente", pos);
                } catch (IOException ex) {
                    Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Morada em falta", "Por favor, selecione uma Morada.");
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Cliente em falta", "Por favor, selecione um Cliente.");
        }    
    }

    /**
     * Método de Eliminar para o botão Eliminar Contacto
     *
     * @param event
     */
    @FXML
    void eliminarContacto(ActionEvent event) {
        Cliente cliente = comboNome.getSelectionModel().getSelectedItem();
        ContactoCliente contacto = tvContactos.getSelectionModel().getSelectedItem();

        if(cliente != null){
            if (contacto != null) {
                AlertBoxController alerta = new AlertBoxController(AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Contacto?");

                if (alerta.getButton().get() == ButtonType.OK) {

                    Boolean response = serviceContactos.deleteContact(contacto);

                    if (response) {
                        AlertBoxController alertaConf = new AlertBoxController(AlertType.CONFIRMATION, "SUCESSO", "Contacto eliminado com SUCESSO!");

                        refreshTableContactos(cliente);
                    } else {
                        AlertBoxController alertaErr = new AlertBoxController(AlertType.ERROR, "ERRO", "Não foi possível eliminar o Contacto.");
                    }
                }
            } else {
                AlertBoxController alertaErr = new AlertBoxController(AlertType.ERROR, "Contacto em falta", "Por favor, selecione um Contacto.");
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Cliente em falta", "Por favor, selecione um Cliente.");
        }
    }

    /**
     * Método de Eliminar para o botão Eliminar Morada
     *
     * @param event
     */
    @FXML
    void eliminarMorada(ActionEvent event) {
        Cliente cliente = comboNome.getSelectionModel().getSelectedItem();
        Morada morada = tvMoradas.getSelectionModel().getSelectedItem();
        
        if (cliente != null){
            if (morada != null) {
                AlertBoxController alerta = new AlertBoxController(AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar esta Morada?");

                if (alerta.getButton().get() == ButtonType.OK) {

                    Boolean response = serviceClientes.deleteAddress(morada);

                    if (response) {
                        AlertBoxController alertaConf = new AlertBoxController(AlertType.CONFIRMATION, "SUCESSO", "Morada eliminada com SUCESSO!");

                        refreshTableMoradas(cliente);
                    } else {
                        AlertBoxController alertaErr = new AlertBoxController(AlertType.ERROR, "ERRO", "Não foi possível eliminar a Morada.");
                    }
                }
            } else {
                 AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Morada em falta", "Por favor, selecione uma Morada.");
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(AlertType.ERROR, "Cliente em falta", "Por favor, selecione um Cliente.");
        }
    }

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
        
        comboNome.setConverter(new StringConverter<Cliente>(){
           
            @Override
            public String toString(Cliente cliente) {
                return "Nome : "+ cliente.getName() + ""+ " | " + "NIF : " + cliente.getNif();
            }

            @Override
            public Cliente fromString(String string) {
                return null;
            }
        });
        
        ObservableList obsCliente = FXCollections.observableArrayList(serviceClientes.getAllClients());
        comboNome.getItems().addAll(obsCliente);
        
    }

    /**
     * Método de atualização dos dados na tabela Moradas do Cliente
     */
    public void refreshTableMoradas(Cliente cliente) {
        obsListMorada = FXCollections.observableArrayList(serviceClientes.getAllAddressesByClient(cliente.getNif()));
        configColumsMoradas();
        tvMoradas.getItems().setAll(obsListMorada);
    }

    /**
     * Método de atualização dos dados na tabela Contactos do Cliente
     */
    public void refreshTableContactos(Cliente cliente) {
        obsListContactos = FXCollections.observableArrayList(serviceContactos.getAllContactsByClient(cliente.getNif()));
        configColumsContactos();
        tvContactos.getItems().setAll(obsListContactos);
    }

    /**
     * Método de Configuração dos dados na tabela Moradas do Cliente
     */
    private void configColumsMoradas() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        EnderecoCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        localCod.setCellValueFactory(new PropertyValueFactory<>("locale"));
        cdPostalCod.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        paisCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        moradaFscCol.setCellValueFactory(morada -> new SimpleStringProperty(Estados.confirmacaoSimNao(morada.getValue().isIsFiscalAddress())));
    }

    /**
     * Método de Configuração dos dados na tabela Contactos do Cliente
     */
    private void configColumsContactos() {
        contacCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tpContacCol.setCellValueFactory(tp -> new SimpleObjectProperty(tp.getValue().getContactType().getType()));

    }

    /**
     * Método para abrir a view da Morada de Cliente
     *
     * @param fxml
     * @param op
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalMoradaView(String fxml, Cliente cliente, Morada morada, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        moradaController = fxmlLoader.getController();
        moradaController.setMoradaView(cliente, morada, titulo, pos, this);

        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método para abrir a view de Cliente
     *
     * @param fxml
     * @param op
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalClienteView(String fxml, Cliente cliente, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        clienteController = fxmlLoader.getController();
        clienteController.setClienteView(cliente, titulo, pos, this);

        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método para abrir a view do Contacto de Cliente
     *
     * @param fxml
     * @param op
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalContactoView(String fxml, Cliente cliente, ContactoCliente contacto, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(App.viewsPath + fxml + ".fxml"));
        fxmlLoader.load();

        contactoController = fxmlLoader.getController();
        contactoController.setContactoView(contacto, cliente, titulo, pos, this);

        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}
