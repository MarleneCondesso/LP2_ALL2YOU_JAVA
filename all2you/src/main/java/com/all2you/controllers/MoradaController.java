/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.controllers;

import com.all2you.models.clientes.Cliente;
import com.all2you.models.clientes.Morada;
import com.all2you.services.ClientesService;
import com.all2you.services.ContactosService;
import com.all2you.utils.Estados;
import java.net.URL;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Marlene Lima
 */
public class MoradaController implements Initializable {

    @FXML
    private Label lblTitulo;

    @FXML
    private TextField txtNomeCliente;

    @FXML
    private TextField txtNIFCliente;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtMorada;

    @FXML
    private TextField txtLocalidade;

    @FXML
    private TextField txtCodPostal;

    @FXML
    private TextField txtPais;

    @FXML
    private ComboBox comboFiscal;

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
    private Morada morada;

    private ClientesController clientesController;

    private ClientesService clienteService = new ClientesService();
    private ContactosService contactosService = new ContactosService();

    /**
     * Método para o botão cancelar/fechar a janela
     *
     * @param event
     */
    @FXML
    void cancelar(MouseEvent event) {
        if (!txtMorada.getText().isEmpty()
                || !comboFiscal.getSelectionModel().getSelectedItem().equals(null)
                || !txtLocalidade.getText().isEmpty()
                || !txtCodPostal.getText().isEmpty()
                || !txtPais.getText().isEmpty()) {
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
        txtId.setText("");
        txtMorada.setText("");
        txtCodPostal.setText("");
        txtLocalidade.setText("");
        txtPais.setText("");
        comboFiscal.getSelectionModel().clearSelection();
    }

    /**
     * Método para botão Guardar
     *
     * @param event
     */
    @FXML
    void guardar(MouseEvent event) {

        if (txtMorada.getText().isEmpty()
                && txtLocalidade.getText().isEmpty()
                && txtCodPostal.getText().isEmpty()
                && txtPais.getText().isEmpty()
                && comboFiscal.getSelectionModel().getSelectedItem().equals(null)) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.morada == null) {
            createMorada();
        } else {
            updateMorada();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.comboFiscal.getItems().addAll(Estados.listaConfirmacao());
        this.comboFiscal.getSelectionModel().selectFirst();

    }

    /**
     * Método para passar valores do ClientesController
     *
     * @param cliente
     * @param morada
     * @param title
     * @param pos
     * @param clientesController
     */
    public void setMoradaView(Cliente cliente, Morada morada, String title, Integer pos, ClientesController clientesController) {
        this.clientesController = clientesController;

        if (!isNull(cliente)) {
            this.cliente = cliente;
            this.morada = morada;
            this.position = pos;
        } else {
            this.morada = null;
            this.position = null;
        }
        setValuestoForm();
        this.lblTitulo.setText(title);
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuestoForm() {

        if (!isNull(this.morada)) {

            try {
                this.txtNomeCliente.setText(this.morada.getClient().getName());
                this.txtNIFCliente.setText(String.valueOf(this.morada.getClient().getNif()));
                this.txtMorada.setText(this.morada.getAddress());
                this.txtLocalidade.setText(this.morada.getLocale());
                this.txtCodPostal.setText(this.morada.getZipCode());
                this.txtPais.setText(this.morada.getCountry());
                if (!this.morada.isIsFiscalAddress()) {
                    this.comboFiscal.getSelectionModel().selectLast();
                } else {
                    this.comboFiscal.getSelectionModel().select(Estados.confirmacaoSimNao(this.morada.isIsFiscalAddress()));
                }
            } catch (NullPointerException e) {
            }

        } else {
            this.txtNomeCliente.setText(this.cliente.getName());
            this.txtNIFCliente.setText(String.valueOf(this.cliente.getNif()));
        }

    }

    /**
     * Método para atribuir valores introduzidos ao objeto Contacto, para criar
     * ou editar
     */
    private void setValuesToObject() {

        if (isNull(this.morada)) {
            this.morada = new Morada();
        }

        this.morada.setClient(this.cliente);
        this.morada.setAddress(this.txtMorada.getText());
        this.morada.setLocale(this.txtLocalidade.getText());
        this.morada.setCountry(this.txtPais.getText());
        this.morada.setZipCode(this.txtCodPostal.getText());

        if (this.comboFiscal.getSelectionModel().getSelectedItem() != null) {
            if (this.comboFiscal.getSelectionModel().getSelectedItem().equals("Sim")) {
                this.morada.setIsFiscalAddress(true);
            } else {
                this.morada.setIsFiscalAddress(false);
            }
        }
    }

    /**
     * Método de criar Morada
     */
    public void createMorada() {

        setValuesToObject();

        Boolean response = this.clienteService.createAddress(this.morada);
        if (response) {
            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Morada criada com SUCESSO!");

            closeModalView(btnGuardar);
            clientesController.refreshTableMoradas(this.cliente);
        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar a Morada.");
        }
    }

    /**
     * Método de atualizar Morada
     */
    public void updateMorada() {

        setValuesToObject();
        Boolean response = this.clienteService.updateAddress(this.morada);
        if (response) {
            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Morada atualizada com SUCESSO!");

            closeModalView(this.btnGuardar);
            clientesController.tvMoradas.getItems().set(this.position, this.morada);
        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar a Morada.");
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
