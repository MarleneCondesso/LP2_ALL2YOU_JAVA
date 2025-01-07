/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.controllers;

import com.all2you.models.clientes.Cliente;
import com.all2you.models.clientes.Morada;
import com.all2you.models.clientes.contactos.ContactoCliente;
import com.all2you.models.clientes.contactos.ObservacaoContactoCliente;
import com.all2you.models.clientes.contactos.TipoContacto;
import com.all2you.services.ClientesService;
import com.all2you.services.ContactosService;
import java.net.URL;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author Marlene Lima
 */
public class ContactoController implements Initializable {

    @FXML
    private Label lblTitulo;

    @FXML
    private TextField txtNomeCliente;

    @FXML
    private TextField txtNifCliente;

    @FXML
    private TextField txtContacto;

    @FXML
    private ComboBox<TipoContacto> comboTpContacto;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    private Stage stage;

    private String title;
    private Integer position;
    private Cliente cliente;
    private ContactoCliente contacto;
    private ClientesController clientesController;
    private ClientesService clienteService = new ClientesService();
    private ContactosService contactosService = new ContactosService();

    public ContactoController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.comboTpContacto.setConverter(new StringConverter<TipoContacto>() {
            @Override
            public String toString(TipoContacto tp) {
                return tp.getType();
            }

            @Override
            public TipoContacto fromString(String string) {
                return null;
            }
        });
        System.out.println("oolaaaa");

        ObservableList obsListOp = FXCollections.observableArrayList(contactosService.getAllContactTypes());
        this.comboTpContacto.getItems().addAll(obsListOp);

    }

    /**
     * Método para passar valores do ClientesController
     *
     * @param cliente
     * @param contacto
     * @param title
     * @param pos
     * @param clientesController
     */
    public void setContactoView(ContactoCliente contacto, Cliente cliente, String title, Integer pos, ClientesController clientesController) {
        this.clientesController = clientesController;

        if (!isNull(cliente)) {
            this.cliente = cliente;
            this.contacto = contacto;
            this.position = pos;
        } else {

            this.contacto = null;
            this.position = null;
        }
        setValuestoForm();
        this.lblTitulo.setText(title);
    }

    /**
     * Método para o botão cancelar/fechar a janela
     *
     * @param event
     */
    @FXML
    void cancelar(MouseEvent event) {
        if (!txtContacto.getText().isEmpty()
                || !comboTpContacto.getSelectionModel().getSelectedItem().equals(null)) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.CONFIRMATION, "Cancelar", "Tem a certeza que deseja cancelar? Vai perder as informações preenchidas.");

            if (alerta.getButton().get() == ButtonType.OK) {
                closeModalView(btnCancelar);
            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }
        } else {
            closeModalView(btnCancelar);
        }
    }

    /**
     * Método para o botão Limpar
     *
     * @param event
     */
    @FXML
    void cleanFields(MouseEvent event) {
        txtContacto.setText("");
        comboTpContacto.getSelectionModel().clearSelection();
    }

    /**
     * Método para botão Guardar
     *
     * @param event
     */
    @FXML
    void guardar(MouseEvent event) {

        if (txtContacto.getText().isEmpty()
                && comboTpContacto.getSelectionModel().getSelectedItem().equals(null)) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.contacto == null) {
            createContacto();
        } else {
            updateContacto();
        }

    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuestoForm() {
        System.out.println(this.cliente.getName());
        if (!isNull(this.cliente)) {

            try {
                this.txtNomeCliente.setText(this.cliente.getName());
                this.txtNifCliente.setText(String.valueOf(String.valueOf(this.cliente.getNif())));
                this.txtContacto.setText(String.valueOf(this.contacto.getContact()));

                this.comboTpContacto.getSelectionModel().select(this.contacto.getContactType());

            } catch (NullPointerException e) {
            }

        } else {
            this.txtNomeCliente.setText(this.cliente.getName());
            this.txtNifCliente.setText(String.valueOf(this.cliente.getNif()));
        }

    }

    /**
     * Método para atribuir valores introduzidos ao objeto Contacto, para criar
     * ou editar
     */
    private void setValuesToObject() {
        if (isNull(this.contacto)) {
            this.contacto = new ContactoCliente();
        }

        this.contacto.setClient(this.cliente);
        this.contacto.getClient().setName(txtNomeCliente.getText());
        this.contacto.setContact(txtContacto.getText());

        TipoContacto tp = comboTpContacto.getSelectionModel().getSelectedItem();
        this.contacto.setContactType(tp);

    }

    /**
     * Método de criar Contacto
     */
    public void createContacto() {

        setValuesToObject();

        Boolean response = contactosService.createContact(this.contacto);
        if (response) {
            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Contacto criado com SUCESSO!");

            closeModalView(this.btnGuardar);
            clientesController.refreshTableContactos(this.cliente);
        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Contacto.");
        }
    }

    /**
     * Método de atualizar Contacto
     */
    public void updateContacto() {

        setValuesToObject();

        Boolean response = contactosService.updateContact(this.contacto);
        if (response) {
            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Contacto atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);
            clientesController.tvContactos.getItems().set(this.position, this.contacto);
        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Contacto.");
        }

    }

    /**
     * Método para fechar a view
     *
     * @param btn
     */
    public void closeModalView(Button btn) {
        try {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        } catch (RuntimeException e) {

        }
    }
}
