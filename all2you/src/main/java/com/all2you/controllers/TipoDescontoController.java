package com.all2you.controllers;

import com.all2you.models.encomendas.TipoDesconto;
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
public class TipoDescontoController implements Initializable {

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

    private TipoDesconto tipoDesconto;
    private String title;
    private Integer position;

    private TipoDescontosController tiposDescontosController;
    private EncomendasService service = new EncomendasService();

    public TipoDescontoController() {

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
     * @param tpDesc
     * @param title
     * @param pos
     * @param tiposDescontosCtrl
     */
    public void setUnitTypeView(TipoDesconto tpDesc, String title, Integer pos, TipoDescontosController tiposDescontosCtrl) {

        this.tiposDescontosController = tiposDescontosCtrl;

        if (!isNull(tpDesc)) {
            this.tipoDesconto = tpDesc;

            this.position = pos;

        } else {
            this.tipoDesconto = null;
            this.position = null;
        }

        setValuesToForm();

        this.lblTitulo.setText(title);

    }

    /**
     * Método para aplicar os valores do objeto ao formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.tipoDesconto)) {

            try {
                this.txtId.setText(String.valueOf(this.tipoDesconto.getId()));
                this.txtDescricao.setText(this.tipoDesconto.getDescription());
            } catch (NullPointerException e) {
            }

        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto TipoDesconto, para
     * criar ou editar
     */
    private void setValuesToObject() {

        if (isNull(this.tipoDesconto)) {
            this.tipoDesconto = new TipoDesconto();
        }

        // this.tipoDesconto.setId(Integer.parseInt(txtId.getText()));
        this.tipoDesconto.setDescription(this.txtDescricao.getText());
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

            if (isNull(this.tipoDesconto)) {
                createDiscountType();
            } else {
                updateDiscountType();
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
     * Método para botão Criar / Adicionar Tipo de Desconto
     */
    private void createDiscountType() throws IOException {

        setValuesToObject();
        Boolean response = this.service.createDiscountType(this.tipoDesconto);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Desconto criado com SUCESSO!");

            closeModalView(this.btnGuardar);

            // this.tiposDescontosController.tblTiposDescontos.getItems().add(this.tipoDesconto);
            this.tiposDescontosController.refreshTableFromDB();

        } else {

            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Tipo de Desconto.");
        }

    }

    /**
     * Método para botão Atualizar Tipo de Desconto
     */
    private void updateDiscountType() throws IOException {

        setValuesToObject();
        Boolean response = this.service.updateDiscountType(this.tipoDesconto);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Desconto atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);

//            this.tiposDescontosController.tblTiposDescontos.getItems().set(this.position, this.tipoDesconto);
             this.tiposDescontosController.refreshTableFromDB();

        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Tipo de Desconto.");
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
