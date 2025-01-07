package com.all2you.controllers;

import com.all2you.models.clientes.contactos.TipoContacto;
import com.all2you.services.ContactosService;
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
public class TipoContactoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtTipo;
    @FXML
    private TextField txtDescricao;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnLimpar;

    private TipoContacto tipoContacto;
    private String title;
    private Integer position;

    private TipoContactosController tiposContactosController;
    private ContactosService service = new ContactosService();

    public TipoContactoController() {

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
     * @param tpCont
     * @param title
     * @param pos
     * @param tiposContactosCtrl
     */
    public void setContactTypeView(TipoContacto tpCont, String title, Integer pos, TipoContactosController tiposContactosCtrl) {

        this.tiposContactosController = tiposContactosCtrl;

        if (!isNull(tpCont)) {
            this.tipoContacto = tpCont;

            this.position = pos;

        } else {
            this.tipoContacto = null;
            this.position = null;
        }

        setValuesToForm();

        this.lblTitulo.setText(title);

    }

    /**
     * Método para aplicar os valores do objeto ao formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.tipoContacto)) {

            try {
                this.txtTipo.setText(this.tipoContacto.getType());
                this.txtDescricao.setText(this.tipoContacto.getDescription());
            } catch (NullPointerException e) {
            }

        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto TipoContacto, para
     * criar ou editar
     */
    private void setValuesToObject() {

        if (isNull(this.tipoContacto)) {
            this.tipoContacto = new TipoContacto();
        }

        this.tipoContacto.setType(this.txtTipo.getText());
        this.tipoContacto.setDescription(this.txtDescricao.getText());
    }

    /**
     * Método para limpar todos os campos
     */
    @FXML
    void cleanFields() {
        this.txtTipo.setText("");
        this.txtDescricao.setText("");
    }

    /**
     * Método para botão de guardar
     *
     * @param event
     */
    @FXML
    void guardar(MouseEvent event) throws IOException {

        if (this.txtTipo.getText().isEmpty() || this.txtDescricao.getText().isEmpty()) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else {

            if (isNull(this.tipoContacto)) {
                createContactType();
            } else {
                updateContactType();
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

        if (!this.txtTipo.getText().isEmpty() || !this.txtDescricao.getText().isEmpty()) {
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
     * Método para botão Criar / Adicionar Tipo de Contacto
     */
    private void createContactType() throws IOException {

        setValuesToObject();
        Boolean response = this.service.createContactType(this.tipoContacto);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Contacto criado com SUCESSO!");

            closeModalView(this.btnGuardar);

            // this.tiposContactosController.tblTiposContactos.getItems().add(this.tipoContacto);
            this.tiposContactosController.refreshTableFromDB();

        } else {

            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Tipo de Contacto.");
        }

    }

    /**
     * Método para botão Atualizar Tipo de Contacto
     */
    private void updateContactType() throws IOException {

        setValuesToObject();
        Boolean response = this.service.updateContactType(this.tipoContacto);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Contacto atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);

//            this.tiposContactosController.tblTiposContactos.getItems().set(this.position, this.tipoContacto);
             this.tiposContactosController.refreshTableFromDB();

        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Tipo de Contacto.");
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
