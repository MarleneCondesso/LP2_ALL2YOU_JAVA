/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.controllers;

import com.all2you.models.Maquina;
import com.all2you.models.operacoes.TipoOperacao;
import com.all2you.services.MaquinasService;
import com.all2you.services.OperacoesService;
import com.all2you.utils.Estados;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author Marlene Lima
 */
public class MaquinaController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAdicionar;
    @FXML
    private TextField txtId;
    @FXML
    private ComboBox comboEstado;
    @FXML
    private ComboBox<TipoOperacao> comboTpOp;
    private Maquina maquina;
    private Integer position;
    private String title;

    private MaquinasController maquinasController;
    private MaquinasService service = new MaquinasService();
    private OperacoesService operacaoService = new OperacoesService();

    public MaquinaController() {

    }

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        comboEstado.getItems().addAll(Estados.listaEstados());

        comboTpOp.setConverter(new StringConverter<TipoOperacao>() {

            @Override
            public String toString(TipoOperacao tp) {
                return tp.getId();
            }

            @Override
            public TipoOperacao fromString(String string) {
                return null;
            }
        });

        ObservableList obsttp = FXCollections.observableArrayList(operacaoService.getAllOperationTypes());
        comboTpOp.getItems().addAll(obsttp);

    }

    /**
     * Método para passar valores do MaquinasController
     *
     * @param maq
     * @param title
     * @param pos
     * @param maquinasController
     */
    public void setMaquinaView(Maquina maq, String title, Integer pos, MaquinasController maquinasController) {

        this.maquinasController = maquinasController;

        if (!isNull(maq)) {
            this.maquina = maq;

            this.position = pos;
        } else {
            this.maquina = null;

            this.position = null;
        }
        this.lblTitulo.setText(title);
    }

    /**
     * Método para botão de guardar
     *
     * @param event
     */
    @FXML
    void guardar(MouseEvent event) {

        if (txtId.getText().isEmpty()
                || comboTpOp.getSelectionModel().isEmpty()
                || comboEstado.getSelectionModel().isEmpty()) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else {
            if (maquina == null) {
                createMaquina();

            }
        }
    }

    /**
     * Método para botão de cancelar/fechar a janela
     *
     * @param event
     */
    @FXML
    void cancelar(MouseEvent event) {

        if (!txtId.getText().isEmpty()
                || !comboTpOp.getSelectionModel().isEmpty()
                || !comboEstado.getSelectionModel().isEmpty()) {
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
     * Limpa os campos do formulário
     */
    @FXML
    void cleanFields() {
        txtId.setText("");
        comboEstado.getSelectionModel().clearSelection();
        comboTpOp.getSelectionModel().clearSelection();
    }

    /**
     * Método para atribuir valores introduzidos ao objeto Máquina, para criar
     * ou editar
     */
    private void setValuesToObject() {

        if (isNull(this.maquina)) {

            this.maquina = new Maquina();

            this.maquina.setId(txtId.getText());

            if (this.comboEstado.getSelectionModel().getSelectedItem().equals("Ativo")) {
                this.maquina.setStatus(true);
            } else {
                this.maquina.setStatus(false);
            }

            TipoOperacao id = comboTpOp.getSelectionModel().getSelectedItem();
            this.maquina.setOperationType(id);

        }

    }

    /**
     * Método para botão Criar / Adicionar Maquina
     */
    private void createMaquina() {
        setValuesToObject();

        Boolean response = service.createMachine(this.maquina);

        if (response) {
            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Máquina criada com SUCESSO!");

            closeModalView(btnAdicionar);

            maquinasController.refreshTable();
        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar esta Máquina.");
        }

    }

    /**
     * Método para fechar a view
     *
     * @param btn
     */
    public void closeModalView(Button btn) {

        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
