/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.controllers;

import java.net.URL;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;

import com.all2you.models.clientes.Cliente;
import com.all2you.services.ClientesService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Marlene Lima
 */
public class ClienteController implements Initializable {

    @FXML
    private Pane clientes;

    @FXML
    private Label lblTitulo;

    @FXML
    private TextField txtNif;

    @FXML
    private TextField txtNome;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnAdicionar;

    private Stage stage;

    private String title;
    private Integer position;
    private Cliente cliente;

    private ClientesController clientesController;
    private ClientesService clienteService = new ClientesService();

    public ClienteController() {

    }

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Método para passar valores do ClientesController
     *
     * @param cliente
     * @param title
     * @param pos
     * @param clientesController
     */
    public void setClienteView(Cliente cliente, String title, Integer pos, ClientesController clientesController) {
        this.clientesController = clientesController;

        if (!isNull(cliente)) {
            this.cliente = cliente;

            this.position = pos;
        } else {
            this.cliente = null;
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
        if (!txtNif.getText().isEmpty() || !txtNome.getText().isEmpty()) {
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
        txtNif.setText("");
        txtNome.setText("");
    }

    /**
     * Método para botão Guardar
     *
     * @param event
     */
    @FXML
    void guardar(MouseEvent event) {

        if (txtNif.getText().isEmpty() && txtNome.getText().isEmpty()) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.cliente == null) {
            createCliente();
        } else {
            updateCliente();
        }

    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuestoForm() {

        if (!isNull(this.cliente)) {

            try {
                this.txtNif.setText(String.valueOf(this.cliente.getNif()));

                this.txtNome.setText(this.cliente.getName());
            } catch (NullPointerException e) {
            }

        }

    }

    /**
     * Método para atribuir valores introduzidos ao objeto Cliente, para criar
     * ou editar
     */
    private void setValuesToObject() {
        if (isNull(this.cliente)) {
            this.cliente = new Cliente();
        }

        this.cliente.setNif(Integer.parseInt(this.txtNif.getText()));
        this.cliente.setName(this.txtNome.getText());
    }

    /**
     * Método de criar Cliente
     */
    public void createCliente() {

        setValuesToObject();

        Boolean response = this.clienteService.createClient(this.cliente);
        if (response) {
            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Cliente criado com SUCESSO!");

            closeModalView(this.btnAdicionar);
            clientesController.comboNome.getItems().addAll(this.cliente);
        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Cliente.");
        }
    }

    /**
     * Método de atualizar Cliente
     */
    public void updateCliente() {

        setValuesToObject();

        Boolean response = this.clienteService.updateClient(this.cliente);
        if (response) {
            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Cliente atualizado com SUCESSO!");

            closeModalView(this.btnAdicionar);
            clientesController.comboNome.getItems().set(this.position, this.cliente);
        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Cliente.");
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
