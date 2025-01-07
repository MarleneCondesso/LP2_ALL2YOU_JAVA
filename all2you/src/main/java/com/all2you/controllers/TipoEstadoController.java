package com.all2you.controllers;

import com.all2you.models.encomendas.TipoEstado;
import com.all2you.services.EncomendasService;
import java.io.IOException;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author ruteg
 */
public class TipoEstadoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtDescricao;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnLimpar;

    private TipoEstado tipoEstado;
    private String title;
    private Integer position;

    private TipoEstadosController tiposEstadosController;
    private EncomendasService service = new EncomendasService();

    public TipoEstadoController() {

    }

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // cleanFields();
    }

    /**
     * Método para atribuir os valores à view
     *
     * @param tpEst
     * @param title
     * @param pos
     * @param tiposEstadosCtrl
     */
    public void setStateTypeView(TipoEstado tpEst, String title, Integer pos, TipoEstadosController tiposEstadosCtrl) {

        this.tiposEstadosController = tiposEstadosCtrl;

        if (!isNull(tpEst)) {
            this.tipoEstado = tpEst;

            this.position = pos;

        } else {
            this.tipoEstado = null;
            this.position = null;
        }

        setValuesToForm();

        this.lblTitulo.setText(title);

    }

    /**
     * Método para aplicar os valores do objeto ao formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.tipoEstado)) {

            try {
                this.txtId.setText(String.valueOf(this.tipoEstado.getId()));
                this.txtDescricao.setText(this.tipoEstado.getDescription());
            } catch (NullPointerException e) {
            }

        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto TipoEstado, para
     * criar ou editar
     */
    private void setValuesToObject() {

        if (isNull(this.tipoEstado)) {
            this.tipoEstado = new TipoEstado();
        }

        // this.tipoEstado.setId(Integer.parseInt(txtId.getText()));
        this.tipoEstado.setDescription(this.txtDescricao.getText());
    }

    /**
     * Método para limpar todos os campos
     */
    @FXML
    void cleanFields() {
        // this.txtId.setText("");
        this.txtDescricao.setText("");
    }

    /**
     * Método para botão de guardar
     *
     * @param event
     */
    @FXML
    void guardar(MouseEvent event) throws IOException {

        if (this.txtDescricao.getText().isEmpty()) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else {

            if (isNull(this.tipoEstado)) {
                createStateType();
            } else {
                updateStateType();
            }
        }
    }

    /**
     * Método para botão de cancelar/fechar a janela
     *
     * @param event
     */
    @FXML
    void cancelar(MouseEvent event) throws IOException {

        if (!this.txtDescricao.getText().isEmpty()) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.CONFIRMATION, "Cancelar", "Tem a certeza que deseja cancelar? Vai perder as informações preenchidas.");

            if (alerta.getButton().get() == ButtonType.OK) {
                closeModalView(this.btnCancelar);
            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }
        } else {
            closeModalView(this.btnCancelar);
        }
    }

    /**
     * Método para botão Criar / Adicionar Tipo de Estado
     */
    private void createStateType() throws IOException {

        setValuesToObject();
        Boolean response = this.service.createStateType(this.tipoEstado);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Estado criado com SUCESSO!");

            closeModalView(this.btnGuardar);

            // this.tiposEstadosController.tblTiposEstados.getItems().add(this.tipoEstado);
            this.tiposEstadosController.refreshTableFromDB();

        } else {

            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Tipo de Estado.");
        }

    }

    /**
     * Método para botão Atualizar Tipo de Desconto
     */
    private void updateStateType() throws IOException {

        setValuesToObject();
        Boolean response = this.service.updateStateType(this.tipoEstado);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Estado atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);

//            this.tiposEstadosController.tblTiposEstados.getItems().set(this.position, this.tipoEstado);
             this.tiposEstadosController.refreshTableFromDB();

        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Tipo de Estado.");
        }
    }

    /**
     * Método para fechar a view
     *
     * @param btn
     * @throws IOException
     */
    public void closeModalView(Button btn) throws IOException {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
