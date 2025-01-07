package com.all2you.controllers;

import com.all2you.models.artigos.Componente;
import com.all2you.models.artigos.TipoUnidade;
import com.all2you.models.operacoes.Operacao;
import com.all2you.services.ArtigosService;
import com.all2you.services.OperacoesService;
import java.io.IOException;
import javafx.scene.input.MouseEvent;
import java.math.BigDecimal;
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
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author ruteg
 */
public class ComponenteController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtReferencia;
    @FXML
    private TextField txtVersao;
    @FXML
    private ComboBox<Operacao> comboOperacao;
    @FXML
    private ComboBox<TipoUnidade> comboTipoUnidade;
    @FXML
    private TextField txtDesignacaoComercial;
    @FXML
    private TextField txtQuantidade;
    @FXML
    private ComboBox<String> comboAlternativa;

    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnLimpar;

    private Componente component;
    private String title;
    private Integer position;

    private ComponentesController componentesController;
    private ArtigosService service = new ArtigosService();
    private OperacoesService operacoesService = new OperacoesService();

    public ComponenteController() {
    }

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.comboOperacao.setConverter(new StringConverter<Operacao>() {
            @Override
            public String toString(Operacao op) {

                String val = "";

                if (op.getDescription() != null) {
                    val += op.getDescription() + " | ";
                }
                val += "Tipo: " + op.getOperationType().getId();
                val += " | Ref: " + op.getProduct().getReference();
                val += " | V: " + op.getProduct().getVersion();
                val += " | Ordem: " + op.getOrder();

                return val;
            }

            @Override
            public Operacao fromString(String string) {
                return null;
            }
        });
        ObservableList obsListOp = FXCollections.observableArrayList(operacoesService.getAllOperations());
        this.comboOperacao.getItems().addAll(obsListOp);

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

        /*
        this.comboAlternativa.setConverter(new StringConverter<Componente>() {
            @Override
            public String toString(Componente comp) {
                return comp.getReference();
            }
            @Override
            public Componente fromString(String string) {
                return null;
            }
        });
         */
        ObservableList<Componente> obsListComp = FXCollections.observableArrayList(service.getAllComponents());

        String[] filteredListComp = new String[obsListComp.size()];
        for (int i = 1; i < filteredListComp.length; i++) {
            String val = obsListComp.get(i).getReference();
            if (!obsListComp.get(i).getComercialDesignation().isEmpty()) {
                val += " (" + obsListComp.get(i).getComercialDesignation() + ")";
            }

            filteredListComp[i] = val;
        }
        this.comboAlternativa.getItems().addAll(filteredListComp);

        // cleanFields();
    }

    /**
     * Método para atribuir os valores à view
     *
     * @param comp
     * @param title
     * @param pos
     * @param componentesCtrl
     */
    public void setComponentView(Componente comp, String title, Integer pos, ComponentesController componentesCtrl) {

        this.componentesController = componentesCtrl;

        if (!isNull(comp)) {
            this.component = comp;

            this.position = pos;

        } else {
            this.component = null;
            this.position = null;
        }

        setValuesToForm();
        this.lblTitulo.setText(title);

    }

    /**
     * Método para aplicar os valores do objeto ao formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.component)) {

            try {
                this.txtReferencia.setText(this.component.getReference());
                this.txtVersao.setText(this.component.getVersion());
                this.comboOperacao.getSelectionModel().select(this.component.getOperation());
                this.txtDesignacaoComercial.setText(this.component.getComercialDesignation());
                this.txtQuantidade.setText(this.component.getQuantity().toString());
                this.comboTipoUnidade.getSelectionModel().select(this.component.getUnitType());
                if (!this.component.getAlternative().isEmpty()) {
                    this.comboAlternativa.getSelectionModel().select(this.component.getAlternative());
                }

                this.txtReferencia.setEditable(false);
                this.txtVersao.setEditable(false);

            } catch (NullPointerException e) {
            }

        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto Componente, para
     * criar ou editar
     */
    private void setValuesToObject() {

        if (isNull(this.component)) {
            this.component = new Componente();
        }

        this.component.setReference(txtReferencia.getText());
        this.component.setVersion(txtVersao.getText());

        Operacao op = this.comboOperacao.getSelectionModel().getSelectedItem();
        this.component.setOperation(op);

        TipoUnidade unitType = comboTipoUnidade.getSelectionModel().getSelectedItem();
        this.component.setUnitType(unitType);

        this.component.setComercialDesignation(txtDesignacaoComercial.getText());
        this.component.setQuantity(new BigDecimal(txtQuantidade.getText()));

        // Componente comp = this.comboAlternativa.getSelectionModel().getSelectedItem();
        this.component.setAlternative(this.comboAlternativa.getSelectionModel().getSelectedItem());

    }

    /**
     * Método para limpar todos os campos
     */
    @FXML
    void cleanFields() {
        this.txtReferencia.clear();
        this.txtVersao.clear();
        this.comboOperacao.getSelectionModel().clearSelection();
        this.txtDesignacaoComercial.clear();
        this.txtQuantidade.clear();
        this.comboTipoUnidade.getSelectionModel().clearSelection();
        this.comboAlternativa.getSelectionModel().clearSelection();
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
                || this.txtDesignacaoComercial.getText().isEmpty()
                || this.txtQuantidade.getText().isEmpty()
                || this.comboTipoUnidade.getSelectionModel().getSelectedItem() == null
                || this.comboOperacao.getSelectionModel().getSelectedItem() == null) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else {

            if (isNull(this.component)) {
                createComponent();
            } else {
                updateComponent();
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
                || !this.txtDesignacaoComercial.getText().isEmpty()
                || !this.txtQuantidade.getText().isEmpty()
                || this.comboTipoUnidade.getSelectionModel().getSelectedItem() != null
                || this.comboOperacao.getSelectionModel().getSelectedItem() != null) {
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
     * Método para botão Criar / Adicionar Componente
     */
    private void createComponent() throws IOException {

        setValuesToObject();
        Boolean response = this.service.createComponent(this.component);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Componente criado com SUCESSO!");

            closeModalView(this.btnGuardar);

            // this.componentesController.tblComponentes.getItems().add(this.component);
            this.componentesController.refreshTableFromDB();

        } else {

            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Componente.");
        }

    }

    /**
     * Método para botão Atualizar Componente
     */
    private void updateComponent() throws IOException {

        setValuesToObject();
        Boolean response = this.service.updateComponent(this.component);

        if (response) {

            AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Componente atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);

//            this.componentesController.tblComponentes.getItems().set(this.position, this.component);
             this.componentesController.refreshTableFromDB();

        } else {
            AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Componente.");
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
