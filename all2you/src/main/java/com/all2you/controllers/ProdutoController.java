package com.all2you.controllers;

import com.all2you.models.artigos.Produto;
import com.all2you.models.artigos.TipoUnidade;
import com.all2you.services.ArtigosService;
import com.all2you.utils.Estados;
import java.io.IOException;
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
 * @author ruteg
 */
public class ProdutoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtReferencia;
    @FXML
    private TextField txtVersao;
    @FXML
    private TextField txtDesignacao;
    @FXML
    private TextField txtDesignacaoComercial;
    @FXML
    private TextField txtQtdLote;
    @FXML
    private ComboBox<TipoUnidade> comboTipoUnidade;
    @FXML
    private ComboBox comboEstado;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnLimpar;

    private Produto product;
    private String title;
    private Integer position;

    private ProdutosController produtosController;
    private ArtigosService service = new ArtigosService();

    public ProdutoController() {

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

        this.comboTipoUnidade.setConverter(new StringConverter<TipoUnidade>() {

            @Override
            public String toString(TipoUnidade tp) {
                return tp.getDescription() + " (" + tp.getId() + ")";
            }

            @Override
            public TipoUnidade fromString(String string) {
                return null;
            }

        });

        ObservableList obsList = FXCollections.observableArrayList(service.getAllUnitTypes());
        this.comboTipoUnidade.getItems().addAll(obsList);
        
        // cleanFields();
    }

    /**
     * Método para atribuir os valores à view
     *
     * @param prod
     * @param title
     * @param pos
     * @param produtosCtrl
     */
    public void setProductView(Produto prod, String title, Integer pos, ProdutosController produtosCtrl) {

        this.produtosController = produtosCtrl;

        if (!isNull(prod)) {
            this.product = prod;

            this.position = pos;

        } else {
            this.product = null;
            this.position = null;
        }

        setValuesToForm();
        this.lblTitulo.setText(title);

    }

    /**
     * Método para aplicar os valores do objeto ao formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.product)) {

            try {
                this.txtReferencia.setText(this.product.getReference());
                this.txtVersao.setText(this.product.getVersion());
                this.txtDesignacao.setText(this.product.getDesignation());
                this.txtDesignacaoComercial.setText(this.product.getCommercialDesignation());
                this.txtQtdLote.setText(String.valueOf(this.product.getBatchQuantity()));
                this.comboTipoUnidade.getSelectionModel().select(this.product.getUnitType());
                if (!this.product.getStatus()) {
                    this.comboEstado.getSelectionModel().selectLast();
                }
            } catch (NullPointerException e) {
            }

        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto Produto, para criar
     * ou editar
     */
    private void setValuesToObject() {

        if (isNull(this.product)) {
            this.product = new Produto();
        }

        this.product.setReference(this.txtReferencia.getText());
        this.product.setVersion(this.txtVersao.getText());
        this.product.setDesignation(this.txtDesignacao.getText());
        this.product.setCommercialDesignation(this.txtDesignacaoComercial.getText());
        this.product.setBatchQuantity(Integer.parseInt(this.txtQtdLote.getText()));

        TipoUnidade unitType = comboTipoUnidade.getSelectionModel().getSelectedItem();
        this.product.setUnitType(unitType);
        if (this.comboEstado.getSelectionModel().getSelectedItem() != null) {
            if (this.comboEstado.getSelectionModel().getSelectedItem().equals("Ativo")) {
                this.product.setStatus(true);
            } else {
                this.product.setStatus(false);
            }
        }

    }

    /**
     * Método para limpar todos os campos
     */
    @FXML
    void cleanFields() {
        this.txtReferencia.setText("");
        this.txtVersao.setText("");
        this.txtDesignacao.setText("");
        this.txtDesignacaoComercial.setText("");
        this.txtQtdLote.setText("");
        this.comboTipoUnidade.getSelectionModel().clearSelection();
        this.comboEstado.getSelectionModel().selectFirst();
    }

    /**
     * Método para botão de guardar
     *
     * @param event
     */
    @FXML
    void guardar(MouseEvent event) throws IOException {

        if (this.txtReferencia.getText().isEmpty()
                || this.txtVersao.getText().isEmpty()
                || this.txtDesignacao.getText().isEmpty()
                || this.txtDesignacaoComercial.getText().isEmpty()
                || this.txtQtdLote.getText().isEmpty()
                || this.comboTipoUnidade.getSelectionModel().getSelectedItem() == null
                || this.comboEstado.getSelectionModel().getSelectedItem() == null) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else {

            if (isNull(this.product)) {
                createProduct();
            } else {
                updateProduct();
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

        if (!this.txtReferencia.getText().isEmpty()
                || !this.txtVersao.getText().isEmpty()
                || !this.txtDesignacao.getText().isEmpty()
                || !this.txtDesignacaoComercial.getText().isEmpty()
                || !this.txtQtdLote.getText().isEmpty()
                || this.comboTipoUnidade.getSelectionModel().getSelectedItem() != null
                || this.comboEstado.getSelectionModel().getSelectedItem() != null) {
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
     * Método para botão Criar / Adicionar Produto
     */
    private void createProduct() throws IOException {

        setValuesToObject();
        Boolean response = this.service.createProduct(this.product);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Produto criado com SUCESSO!");

            closeModalView(this.btnGuardar);

            // this.produtosController.tblProdutos.getItems().add(this.product);
            this.produtosController.refreshTableFromDB();

        } else {

            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Produto.");
        }

    }

    /**
     * Método para botão Atualizar Produto
     */
    private void updateProduct() throws IOException {

        setValuesToObject();
        Boolean response = this.service.updateProduct(this.product);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Produto atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);

//            this.produtosController.tblProdutos.getItems().set(this.position, this.product);
             this.produtosController.refreshTableFromDB();

        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Produto.");
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
