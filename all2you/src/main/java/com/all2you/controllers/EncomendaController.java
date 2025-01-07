package com.all2you.controllers;

import com.all2you.models.clientes.Cliente;
import com.all2you.models.clientes.Morada;
import com.all2you.models.encomendas.Encomenda;
import com.all2you.models.encomendas.TipoDesconto;
import com.all2you.services.ClientesService;
import com.all2you.services.EncomendasService;
import com.all2you.utils.LocalDateTimeAttributeConverter;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author Marlene Lima
 */
public class EncomendaController implements Initializable {

    @FXML
    private Label lblTitulo;

    @FXML
    private TextField txtId;

    @FXML
    private ComboBox<Cliente> comboNif;

    @FXML
    private DatePicker txtDataCriação;

    @FXML
    private DatePicker txtDataDoc;

    @FXML
    private ComboBox<TipoDesconto> comboTpDesconto;

    @FXML
    private TextField txtValorDesc;

    @FXML
    private TextField txtDataModif;

    @FXML
    private ComboBox<Morada> comboMorada;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnAdicionar;

    private Stage stage;
    private String title;
    private Integer position;

    private EncomendasController encomendasController;

    private EncomendasService service = new EncomendasService();
    private ClientesService serviceCli = new ClientesService();

    private Cliente cliente;
    private Encomenda encomenda;

    public EncomendaController() {

    }

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        comboNif.setConverter(new StringConverter<Cliente>() {

            @Override
            public String toString(Cliente cliente) {
                return String.valueOf(cliente.getNif());
            }

            @Override
            public Cliente fromString(String string) {
                return null;
            }
        });

        ObservableList obstNif = FXCollections.observableArrayList(serviceCli.getAllClients());
        comboNif.getItems().addAll(obstNif);

        comboMorada.setConverter(new StringConverter<Morada>() {

            @Override
            public String toString(Morada morada) {
                return morada.getAddress();
            }

            @Override
            public Morada fromString(String string) {
                return null;
            }
        });

        ObservableList obstMorada = FXCollections.observableArrayList(serviceCli.getAllAddresses());
        comboMorada.getItems().addAll(obstMorada);

        comboTpDesconto.setConverter(new StringConverter<TipoDesconto>() {

            @Override
            public String toString(TipoDesconto tpDesconto) {
                return tpDesconto.getDescription();
            }

            @Override
            public TipoDesconto fromString(String string) {
                return null;
            }
        });

        ObservableList obstTpDesconto = FXCollections.observableArrayList(service.getAllDiscountTypes());
        comboTpDesconto.getItems().setAll(obstTpDesconto);

    }

    /**
     * Método para passar valores do EncomendasController
     *
     * @param enc
     * @param title
     * @param pos
     * @param encomendasController
     */
    public void setEncomendaView(Encomenda enc, String title, Integer pos, EncomendasController encomendasController) {

        this.encomendasController = encomendasController;

        if (!isNull(enc)) {
            this.encomenda = enc;

            this.position = pos;
        } else {
            this.encomenda = null;

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

        if (!txtId.getText().isEmpty() || !comboNif.getSelectionModel().equals(null)
                || !txtDataCriação.getValue().equals(null) || !comboMorada.getSelectionModel().equals(null)
                || !txtDataDoc.getValue().equals(null) || !comboTpDesconto.getSelectionModel().equals(null)
                || !txtValorDesc.getText().isEmpty() || !txtDataModif.getText().isEmpty()) {
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
        comboNif.getSelectionModel().clearSelection();
        comboMorada.getSelectionModel().clearSelection();
        txtDataCriação.setValue(null);
        txtDataDoc.setValue(null);
        comboTpDesconto.getSelectionModel().clearSelection();
        txtValorDesc.setText("");
        txtDataModif.setText("");
    }

    /**
     * Método para botão Guardar
     *
     * @param event
     */
    @FXML
    void guardar(MouseEvent event) {
        if (txtId.getText().isEmpty() || comboNif.getSelectionModel().equals(null)
                || comboMorada.getSelectionModel().equals(null) || comboTpDesconto.getSelectionModel().equals(null)
                || txtDataCriação.getValue().equals(null) || txtDataDoc.getValue().equals(null)
                || txtValorDesc.getText().isEmpty() || txtDataModif.getText().isEmpty()) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.encomenda == null) {
            createEncomenda();
        } else {
            updateEncomenda();
        }
    }

    /**
     * Método de ação da ComboBox Nif do Cliente para controlo de comportamento
     * da ComboBox Morada do Cliente.
     *
     * @param event
     */
    @FXML
    void comboNif(ActionEvent event) {

        Cliente cliente = comboNif.getSelectionModel().getSelectedItem();
        comboMorada.getItems().setAll(serviceCli.getAllAddressesByClient(cliente.getNif()));
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuestoForm() {

        if (!isNull(this.encomenda)) {

            try {
                this.txtId.setText(this.encomenda.getId());
                this.comboNif.getSelectionModel().select(this.encomenda.getClient());

                Cliente cliente = comboNif.getSelectionModel().getSelectedItem();
                this.comboMorada.getItems().setAll(serviceCli.getAllAddressesByClient(cliente.getNif()));

                this.comboMorada.getSelectionModel().select(this.encomenda.getMorada());

                this.txtDataCriação.setValue(this.encomenda.getCreationDate());
                this.txtDataDoc.setValue(this.encomenda.getDocumentDate());
                this.comboTpDesconto.getSelectionModel().select(this.encomenda.getDiscountType());
                this.txtValorDesc.setText(String.valueOf(this.encomenda.getDiscount()));
                this.txtDataModif.setText(String.valueOf(this.encomenda.getLastModification()));

            } catch (NullPointerException e) {
                System.out.println(e + "Valores NULOS");
            }

        }

        this.txtDataModif.setText(LocalDateTimeAttributeConverter.patternFormater.format(LocalDateTime.now()));

    }

    /**
     * Método para atribuir valores introduzidos ao objeto Encomenda, para criar
     * ou editar
     */
    private void setValuesToObject() {

        if (isNull(this.encomenda)) {
            this.encomenda = new Encomenda();
        }

        this.encomenda.setId(txtId.getText());
        Cliente nif = comboNif.getSelectionModel().getSelectedItem();
        this.encomenda.setClient(nif);

        Morada morada = comboMorada.getSelectionModel().getSelectedItem();
        this.encomenda.setMorada(morada);

        this.encomenda.setDocumentDate(LocalDate.from(txtDataDoc.getValue()));

        TipoDesconto typeDesc = comboTpDesconto.getSelectionModel().getSelectedItem();
        this.encomenda.setDiscountType(typeDesc);

        this.encomenda.setDiscount(new Double(txtValorDesc.getText()));

        this.encomenda.setCreationDate(LocalDate.from(txtDataCriação.getValue()));

        this.encomenda.setLastModification(LocalDate.parse(txtDataModif.getText()));

//        this.encomenda.setId(this.encomenda.getId());
//
//        this.encomenda.setLastModification(LocalDate.now());
    }

    /**
     * Método de criar Encomenda
     */
    public void createEncomenda() {

        setValuesToObject();

        Boolean response = service.createOrder(this.encomenda);
        if (response) {
            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Encomenda criada com SUCESSO!");

            closeModalView(btnAdicionar);
            encomendasController.refreshTable();
        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar a Encomenda.");
        }
    }

    /**
     * Método de atualizar Encomenda
     */
    public void updateEncomenda() {

        setValuesToObject();

        Boolean response = service.updateOrder(this.encomenda);
        if (response) {
            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Encomenda atualizada com SUCESSO!");

            closeModalView(btnAdicionar);
            encomendasController.tvEncomenda.getItems().set(this.position, this.encomenda);
            encomendasController.refreshTable();

        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar a Encomenda.");
        }
    }

    /**
     * Método para fechar a view Encomenda
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
