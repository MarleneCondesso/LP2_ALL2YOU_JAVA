package com.all2you.controllers;

import com.all2you.models.operacoes.TipoOperacao;
import com.all2you.services.OperacoesService;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Marlene Lima
 */
public class TipoOperacaoController implements Initializable {

    @FXML
    private Pane tpOperacao;

    @FXML
    private Label lblTitulo;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtId = new TextField();

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnAdicionar;

    private Stage stage;

    private String title;
    private Integer position;
    private TipoOperacao tipoOperacao;

    private TipoOperacoesController tpOperacoesController;
    private OperacoesService opService = new OperacoesService();

    public TipoOperacaoController() {

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
     * Método para passar valores do TipoOperacoesController
     *
     * @param tpOp
     * @param title
     * @param pos
     * @param tpOperacoesController
     */
    public void setTipoOperacaoView(TipoOperacao tpOp, String title, Integer pos, TipoOperacoesController tpOperacoesController) {
        this.tpOperacoesController = tpOperacoesController;

        if (!isNull(tpOp)) {
            this.tipoOperacao = tpOp;

            this.position = pos;
        } else {
            this.tipoOperacao = null;

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
        if (!txtId.getText().isEmpty() || txtDescricao.getText().isEmpty()) {
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
        txtDescricao.setText("");
    }

    /**
     * Método para botão Guardar
     *
     * @param event
     */
    @FXML
    void guardar(MouseEvent event) {

        if (txtId.getText().isEmpty() && txtDescricao.getText().isEmpty()) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.tipoOperacao == null) {
            createTipoOperacao();
        } else {
            updateTipoOperacao();
        }

    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuestoForm() {

        if (!isNull(this.tipoOperacao)) {

            try {
                this.txtId.setText(this.tipoOperacao.getId());

                this.txtDescricao.setText(this.tipoOperacao.getDescription());
            } catch (NullPointerException e) {
            }

        }

    }

    /**
     * Método para atribuir valores introduzidos ao objeto Tipo Operação, para
     * criar ou editar
     */
    private void setValuesToObject() {

        if (isNull(this.tipoOperacao)) {
            this.tipoOperacao = new TipoOperacao();

            this.tipoOperacao.setId(txtId.getText());

            this.tipoOperacao.setDescription(txtDescricao.getText());
        }

        this.tipoOperacao.setId(this.tipoOperacao.getId());

        this.tipoOperacao.setDescription(txtDescricao.getText());

    }

    /**
     * Método de criar Tipo Operação
     */
    public void createTipoOperacao() {

        setValuesToObject();

        Boolean response = opService.createTypeOperation(this.tipoOperacao);
        if (response) {
            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Operação criado com SUCESSO!");

            closeModalView(btnAdicionar);
            tpOperacoesController.refreshTable();
        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Tipo de Operação.");
        }
    }

    /**
     * Método de atualizar Tipo de Operação
     */
    public void updateTipoOperacao() {

        setValuesToObject();

        Boolean response = opService.updateTypeOperation(this.tipoOperacao);
        if (response) {
            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Operação atualizado com SUCESSO!");

            closeModalView(btnAdicionar);
//            tpOperacoesController.tvTipoOperacao.getItems().set(this.position, this.tipoOperacao);
            tpOperacoesController.refreshTable();

        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Tipo de Operação.");
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
