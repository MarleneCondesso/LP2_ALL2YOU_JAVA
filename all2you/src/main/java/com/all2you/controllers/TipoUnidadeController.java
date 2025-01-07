package com.all2you.controllers;

import com.all2you.models.artigos.TipoUnidade;
import com.all2you.services.ArtigosService;
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
public class TipoUnidadeController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtUnidade;
    @FXML
    private TextField txtDescricao;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnLimpar;

    private TipoUnidade tipoUnidade;
    private String title;
    private Integer position;

    private TipoUnidadesController tiposUnidadesController;
    private ArtigosService service = new ArtigosService();

    public TipoUnidadeController() {

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
     * @param tpUn
     * @param title
     * @param pos
     * @param tiposUnidadesCtrl
     */
    public void setUnitTypeView(TipoUnidade tpUn, String title, Integer pos, TipoUnidadesController tiposUnidadesCtrl) {

        this.tiposUnidadesController = tiposUnidadesCtrl;

        if (!isNull(tpUn)) {
            this.tipoUnidade = tpUn;

            this.position = pos;

        } else {
            this.tipoUnidade = null;
            this.position = null;
        }

        setValuesToForm();

        this.lblTitulo.setText(title);

    }

    /**
     * Método para aplicar os valores do objeto ao formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.tipoUnidade)) {

            try {
                this.txtUnidade.setText(this.tipoUnidade.getId());
                this.txtDescricao.setText(this.tipoUnidade.getDescription());
            } catch (NullPointerException e) {
            }

        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto TipoUnidade, para
     * criar ou editar
     */
    private void setValuesToObject() {

        if (isNull(this.tipoUnidade)) {
            this.tipoUnidade = new TipoUnidade();
        }

        this.tipoUnidade.setId(this.txtUnidade.getText().toUpperCase());
        this.tipoUnidade.setDescription(this.txtDescricao.getText());
    }

    /**
     * Método para limpar todos os campos
     */
    @FXML
    void cleanFields() {
        this.txtUnidade.setText("");
        this.txtDescricao.setText("");
    }

    /**
     * Método para botão de guardar
     *
     * @param event
     */
    @FXML
    void guardar(MouseEvent event) throws IOException {

        if (this.txtUnidade.getText().isEmpty() || this.txtDescricao.getText().isEmpty()) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else {

            if (isNull(this.tipoUnidade)) {
                createUnitType();
            } else {
                updateUnitType();
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

        if (!this.txtUnidade.getText().isEmpty() || !this.txtDescricao.getText().isEmpty()) {
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
     * Método para botão Criar / Adicionar Tipo de Unidade
     */
    private void createUnitType() throws IOException {

        setValuesToObject();
        Boolean response = this.service.createUnitType(this.tipoUnidade);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Unidade criado com SUCESSO!");

            closeModalView(this.btnGuardar);

            // this.tiposUnidadesController.tblTiposUnidades.getItems().add(this.tipoUnidade);
            this.tiposUnidadesController.refreshTableFromDB();

        } else {

            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Tipo de Unidade.");
        }

    }

    /**
     * Método para botão Atualizar Tipo de Unidade
     */
    private void updateUnitType() throws IOException {

        setValuesToObject();
        Boolean response = this.service.updateUnitType(this.tipoUnidade);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Unidade atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);
            
//            this.tiposUnidadesController.tblTiposUnidades.getItems().set(this.position, this.tipoUnidade);
             this.tiposUnidadesController.refreshTableFromDB();

        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Tipo de Unidade.");
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
