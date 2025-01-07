/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.controllers;

import com.all2you.models.artigos.Produto;
import com.all2you.models.clientes.Cliente;
import com.all2you.models.encomendas.Encomenda;
import com.all2you.models.encomendas.LinhaEncomenda;
import com.all2you.services.ArtigosService;
import com.all2you.services.ClientesService;
import com.all2you.services.EncomendasService;
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
public class LinhaEncomendaController implements Initializable {

    @FXML
    private Label lblTitulo;

    @FXML
    private TextField txtId;

    @FXML
    private ComboBox<Produto> comboRef;

    @FXML
    private ComboBox<Produto> comboVersao;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private TextField txtPreco;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnAdicionar;

    private Stage stage;
    private String title;
    private Integer position;
    private Encomenda encomenda;
    private LinhaEncomenda linha;

    private EncomendasController encomendasController;
    private LinhasEncomendasController linhasEncController;
    private ArtigosService serviceArtigos = new ArtigosService();
    private EncomendasService service = new EncomendasService();
    private ClientesService serviceCli = new ClientesService();

    private Cliente cliente;

    public LinhaEncomendaController() {

    }

    /**
     * Método para cancelar criação/edição de Linha Encomenda
     *
     * @param event
     */
    @FXML
    void cancelar(MouseEvent event) {
        if (!comboRef.getSelectionModel().equals(null)
                || !comboVersao.getSelectionModel().equals(null)
                || !txtDescricao.getText().isEmpty()
                || !txtQuantidade.getText().isEmpty()
                || !txtPreco.getText().isEmpty()) {
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
     * Método para limpar campos
     *
     * @param event
     */
    @FXML
    void cleanFields(MouseEvent event) {
        txtId.setText("");
        comboRef.getSelectionModel().clearSelection();
        comboVersao.getSelectionModel().clearSelection();
        txtDescricao.setText("");
        txtQuantidade.setText("");
        txtPreco.setText("");
    }

    /**
     * Método para guardar dados de criação/edição
     *
     * @param event
     */
    @FXML
    void guardar(MouseEvent event) {

        if (comboRef.getSelectionModel().equals(null)
                || comboVersao.getSelectionModel().equals(null)
                || txtDescricao.getText().isEmpty()
                || txtQuantidade.getText().isEmpty()
                || txtPreco.getText().isEmpty()) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.linha == null) {
            createLinhaEncomenda();
        } else {
            updateLinhaEncomenda();
        }
    }

    /**
     * Método de Inicialização (interface Initializable).
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        comboRef.setConverter(new StringConverter<Produto>() {

            @Override
            public String toString(Produto referencia) {
                return referencia.getReference();
            }

            @Override
            public Produto fromString(String string) {
                return null;
            }
        });
        ObservableList obsReferencia = FXCollections.observableArrayList(serviceArtigos.getAllProducts());
        comboRef.getItems().setAll(obsReferencia);

        comboVersao.setConverter(new StringConverter<Produto>() {

            @Override
            public String toString(Produto versao) {
                return versao.getVersion();
            }

            @Override
            public Produto fromString(String string) {
                return null;
            }
        });
        ObservableList obsVersao = FXCollections.observableArrayList(serviceArtigos.getAllProducts());
        comboVersao.getItems().setAll(obsVersao);

    }

    /**
     * Método para passar valores de LinhasEncomendasController
     *
     * @param linha
     * @param title
     * @param pos
     * @param linhasEncController
     */
    public void setLinhaEncomendaView(LinhaEncomenda linha, Encomenda enc, String title, Integer pos, LinhasEncomendasController linhasEncController) {

        this.linhasEncController = linhasEncController;

        if (!isNull(linha)) {
            this.linha = linha;
            this.encomenda = enc;
            this.position = pos;
        } else {
            this.linha = null;
            this.encomenda = enc;
            this.position = null;
        }
        setValuestoForm();
        this.lblTitulo.setText(title);
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuestoForm() {

        if (!isNull(this.linha)) {

            try {
                this.txtId.setText(this.linha.getOrder().getId());
                this.comboRef.getSelectionModel().select(this.linha.getProduct());

                this.comboVersao.getSelectionModel().select(this.linha.getProduct());

                this.txtDescricao.setText(this.linha.getDescription());
                this.txtQuantidade.setText(String.valueOf(this.linha.getQuantity()));
                this.txtPreco.setText(String.valueOf(this.linha.getUnitPrice()));

            } catch (NullPointerException e) {
                System.out.println(e + "Valores NULOS");
            }

        }

        this.txtId.setText(this.encomenda.getId());

    }

    /**
     * Método para atribuir valores introduzidos ao objeto LinhaEncomenda, para
     * criar ou editar
     */
    private LinhaEncomenda setValuesToObject() {

        if (isNull(this.linha)) {
            this.linha = new LinhaEncomenda();
        }
        this.linha.setOrder(this.encomenda);
        Produto referencia = comboRef.getSelectionModel().getSelectedItem();
        this.linha.setProduct(referencia);

        Produto versao = comboVersao.getSelectionModel().getSelectedItem();
        this.linha.setProduct(versao);

        this.linha.setDescription(txtDescricao.getText());
        this.linha.setQuantity(Integer.parseInt(txtQuantidade.getText()));
        this.linha.setUnitPrice(Integer.parseInt(txtPreco.getText()));

        return this.linha;
    }

    /**
     * Método de criar LinhaEncomenda
     */
    public void createLinhaEncomenda() {

        setValuesToObject();

        Boolean response = service.createOrderLine(this.linha);
        if (response) {
            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Linha Encomenda criada com SUCESSO!");

            closeModalView(btnAdicionar);
            linhasEncController.refreshTable(this.encomenda);
        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar a Linha Encomenda.");
        }
    }

    /**
     * Método de atualizar LinhaEncomenda
     */
    public void updateLinhaEncomenda() {

        setValuesToObject();

        Boolean response = service.updateOrderLine(this.linha);
        if (response) {
            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Linha Encomenda atualizada com SUCESSO!");

            closeModalView(btnAdicionar);
            linhasEncController.tvLinhaEncomenda.getItems().set(this.position, this.linha);

        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar a Linha Encomenda.");
        }

    }

    /**
     * Método para fechar a view Linha Encomenda
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
