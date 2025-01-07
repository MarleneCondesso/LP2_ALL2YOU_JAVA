package com.all2you.controllers;

import static java.util.Objects.isNull;

import java.io.IOException;
import javafx.scene.input.MouseEvent;
import java.net.URL;
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
import javafx.stage.Stage;
import javafx.util.StringConverter;

import com.all2you.models.operacoes.*;
import com.all2you.models.artigos.Produto;
import com.all2you.services.ArtigosService;
import com.all2you.services.OperacoesService;

/**
 *
 * @author ruteg
 */
public class OperacaoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private ComboBox<TipoOperacao> comboTipoOperacao;
    @FXML
    private ComboBox<Produto> comboProduto;
    @FXML
    private TextField txtOrdem;
    @FXML
    private TextField txtDescricao;
    @FXML
    private TextField txtMaoObra;
    @FXML
    private TextField txtTempo;
    @FXML
    private TextField txtInstrucoes;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnLimpar;

    private Operacao operation;
    private String title;
    private Integer position;

    private OperacoesController operacoesController;
    private OperacoesService service = new OperacoesService();
    private ArtigosService artigosService = new ArtigosService();

    public OperacaoController() {
    }

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.comboTipoOperacao.setConverter(new StringConverter<TipoOperacao>() {
            @Override
            public String toString(TipoOperacao tOp) {
                return tOp.getId();
            }

            @Override
            public TipoOperacao fromString(String string) {
                return null;
            }
        });

        ObservableList obsListTypeOp = FXCollections.observableArrayList(service.getAllOperationTypes());
        this.comboTipoOperacao.getItems().addAll(obsListTypeOp);

        this.comboProduto.setConverter(new StringConverter<Produto>() {
            @Override
            public String toString(Produto prod) {
                return "Ref: " + prod.getReference() + " | Versão: " + prod.getVersion();
            }

            @Override
            public Produto fromString(String string) {
                return null;
            }
        });

        ObservableList obsListProd = FXCollections.observableArrayList(artigosService.getAllProducts());
        this.comboProduto.getItems().addAll(obsListProd);

        // cleanFields();
    }

    /**
     * Método para atribuir os valores à view
     *
     * @param opc
     * @param title
     * @param pos
     * @param operacoesCtrl
     */
    public void setOperationView(Operacao opc, String title, Integer pos, OperacoesController operacoesCtrl) {
        System.out.println("vai fazer set ???????");
        this.operacoesController = operacoesCtrl;

        if (!isNull(opc)) {
            this.operation = opc;

            this.position = pos;

        } else {
            this.operation = null;
            this.position = null;
        }

        setValuesToForm();
        this.lblTitulo.setText(title);
        System.out.println("done");
    }

    /**
     * Método para aplicar os valores do objeto ao formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.operation)) {
            try {
                this.comboTipoOperacao.getSelectionModel().select(this.operation.getOperationType());
                this.comboProduto.getSelectionModel().select(this.operation.getProduct());
                this.txtOrdem.setText(String.valueOf(this.operation.getOrder()));
                this.txtDescricao.setText(this.operation.getDescription());
                this.txtMaoObra.setText(String.valueOf(this.operation.getLabor()));
                this.txtTempo.setText(String.valueOf(this.operation.getTime()));
                this.txtInstrucoes.setText(this.operation.getTechnicalInstructions());
            } catch (NullPointerException e) {
            }

        }
        System.out.println("done setvalues");
    }

    /**
     * Método para atribuir valores introduzidos ao objeto Operação, para criar
     * ou editar
     */
    private void setValuesToObject() {

        if (isNull(this.operation)) {
            this.operation = new Operacao();
        }

        TipoOperacao OpType = this.comboTipoOperacao.getSelectionModel().getSelectedItem();
        this.operation.setOperationType(OpType);
        Produto prod = this.comboProduto.getSelectionModel().getSelectedItem();
        this.operation.setProduct(prod);

        this.operation.setOrder(Integer.parseInt(txtOrdem.getText()));
        this.operation.setDescription(txtDescricao.getText());
        this.operation.setLabor(Integer.parseInt(txtMaoObra.getText()));
        this.operation.setTime(Integer.parseInt(txtTempo.getText()));
        this.operation.setTechnicalInstructions(txtInstrucoes.getText());
    }

    /**
     * Método para limpar todos os campos
     */
    @FXML
    void cleanFields() {
        this.comboTipoOperacao.getSelectionModel().clearSelection();
        this.comboProduto.getSelectionModel().clearSelection();
        this.txtOrdem.setText("");
        this.txtDescricao.setText("");
        this.txtMaoObra.setText("");
        this.txtTempo.setText("");
        this.txtInstrucoes.setText("");
    }

    /**
     * Método para botão de guardar
     *
     * @param event
     */
    @FXML
    void guardar(MouseEvent event) throws IOException {

        if (this.txtOrdem.getText().isEmpty()
                || this.txtMaoObra.getText().isEmpty()
                || this.txtTempo.getText().isEmpty()
                || this.txtDescricao.getText().isEmpty()
                || this.comboTipoOperacao.getSelectionModel().getSelectedItem() == null
                || this.comboProduto.getSelectionModel().getSelectedItem() == null) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else {

            if (isNull(this.operation)) {
                createOperation();
            } else {
                updateOperation();
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

        if (!this.txtOrdem.getText().isEmpty()
                || !this.txtMaoObra.getText().isEmpty()
                || !this.txtTempo.getText().isEmpty()
                || !this.txtDescricao.getText().isEmpty()
                || this.comboTipoOperacao.getSelectionModel().getSelectedItem() != null
                || this.comboProduto.getSelectionModel().getSelectedItem() != null) {
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
     * Método para botão Criar / Adicionar Operação
     */
    private void createOperation() throws IOException {

        setValuesToObject();
        Boolean response = this.service.createOperation(this.operation);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Operação criada com SUCESSO!");

            closeModalView(this.btnGuardar);

            // this.operacoesController.tblOperacoes.getItems().add(this.operation);
            this.operacoesController.refreshTableFromDB();

        } else {

            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar a Operação.");
        }

    }

    /**
     * Método para botão Atualizar Operação
     */
    private void updateOperation() throws IOException {

        setValuesToObject();
        Boolean response = this.service.updateOperation(this.operation);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Operação atualizada com SUCESSO!");

            closeModalView(this.btnGuardar);

//            this.operacoesController.tblOperacoes.getItems().set(this.position, this.operation);
             this.operacoesController.refreshTableFromDB();

        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar a Operação.");
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
