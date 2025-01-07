package com.all2you.controllers;

import com.all2you.models.Operador;
import com.all2you.services.OperadoresService;
import com.all2you.utils.Estados;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author ruteg
 */
public class OperadorController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtNome;
    @FXML
    private ComboBox comboEstado;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnLimpar;

    private Operador operator;
    private String title;
    private Integer position;

    private OperadoresController operadoresController;
    private OperadoresService service = new OperadoresService();

    public OperadorController() {

    }

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.comboEstado.getItems().addAll(Estados.listaEstados());
        this.comboEstado.getSelectionModel().selectFirst();
        // cleanFields();

    }

    /**
     * Método para atribuir os valores à view
     *
     * @param op
     * @param title
     * @param pos
     * @param operadoresCtrl
     */
    public void setOperatorView(Operador op, String title, Integer pos, OperadoresController operadoresCtrl) {

        this.operadoresController = operadoresCtrl;

        if (!isNull(op)) {
            this.operator = op;

            this.position = pos;

        } else {
            this.operator = null;
            this.position = null;
        }

        setValuesToForm();

        this.lblTitulo.setText(title);

    }

    /**
     * Método para aplicar os valores do objeto ao formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.operator)) {

            try {
                this.txtNome.setText(this.operator.getName());
                if (!this.operator.getStatus()) {
                    this.comboEstado.getSelectionModel().selectLast();
                }
            } catch (NullPointerException e) {
            }

        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto Operador, para criar
     * ou editar
     */
    private void setValuesToObject() {

        if (isNull(this.operator)) {
            this.operator = new Operador();
        }

        this.operator.setName(this.txtNome.getText());

        if (this.comboEstado.getSelectionModel().getSelectedItem() != null) {
            if (this.comboEstado.getSelectionModel().getSelectedItem().equals("Ativo")) {
                this.operator.setStatus(true);
            } else {
                this.operator.setStatus(false);
            }
        }

    }

    /**
     * Método para limpar todos os campos
     */
    @FXML
    void cleanFields() {
        this.txtNome.setText("");
        this.comboEstado.getSelectionModel().selectFirst();
    }

    /**
     * Método para botão de guardar
     *
     * @param event
     */
    @FXML
    void guardar(MouseEvent event) throws IOException {

        if (this.txtNome.getText().isEmpty() || this.comboEstado.getSelectionModel().getSelectedItem() == null) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else {

            if (isNull(this.operator)) {
                createOperator();
            } else {
                updateOperator();
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

        if (!this.txtNome.getText().isEmpty() || this.comboEstado.getSelectionModel().getSelectedItem() != null) {
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
     * Método para botão Criar / Adicionar Operador
     */
    private void createOperator() throws IOException {

        setValuesToObject();
        Boolean response = this.service.createOperator(this.operator);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Operador criado com SUCESSO!");

            closeModalView(this.btnGuardar);

            // this.operadoresController.tblOperadores.getItems().add(this.operator);
            this.operadoresController.refreshTableFromDB();

        } else {

            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Operador.");
        }

    }

    /**
     * Método para botão Atualizar Operador
     */
    private void updateOperator() throws IOException {

        setValuesToObject();
        Boolean response = this.service.updateOperator(this.operator);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Operador atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);

//            this.operadoresController.tblOperadores.getItems().set(this.position, this.operator);
             this.operadoresController.refreshTableFromDB();

        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Operador.");
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
