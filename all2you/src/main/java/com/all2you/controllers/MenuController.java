package com.all2you.controllers;

import com.all2you.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;


public class MenuController implements Initializable {

    Button btConfiguracoes = new Button("Configurações");
    Button btProduto = new Button("Produtos");
    Button btMaquina = new Button("Máquinas");
    Button btOperador = new Button("Operadores");
    Button btOperacao = new Button("Operações");
    Button btComponente = new Button("Componentes");
    Button btTipoOperacao = new Button("Tipo de Operações");
    Button btHorario = new Button("Horários");
    Button btTarefa = new Button("Tarefas");
    
    Button btTipoUnidade = new Button("Tipo de Unidades");
    Button btTipoDesconto = new Button("Tipo de Desconto");
    Button btTipoContacto = new Button("Tipo de Contactos");
    Button btTipoEstado = new Button("Tipo de Estados (Encomendas)");
    Button btGestaoCliente = new Button("Gestão de Clientes");
    Button btGestaoEncomenda = new Button("Gestão de Encomendas");
    Button btFichaTecnica = new Button("Fichas Técnicas");
    Button btStock = new Button("Stock");
    Button btHome = new Button("Menu Inicial");
    
    @FXML
    private Button btnSair;
    @FXML
    private StackPane menuLateral;
    @FXML
    private StackPane stack;
    @FXML
    private ImageView imagemStack;
    
    StackPane menuLateralSecundario = new StackPane();
    
    
    VBox vboxMenuSecundarioFichasTecnicas = new VBox();
    VBox vboxMenuSecundarioConfiguracoes = new VBox();
    VBox vboxMenu = new VBox();

     /**
     * Método de Inicialização (interface Initializable).
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuLateral.setStyle("-fx-background-color: #FFFFFF");
        
        setActionsOnButtons();
        configButtons();
        fillMenu();

    }
    
    /**
     * Ação do botão Sair.
     * @param event 
     */
    @FXML
    void exitApp(ActionEvent event) {

        AlertBoxController alerta = new AlertBoxController(Alert.AlertType.CONFIRMATION, "Sair da Aplicação", "Tem a certeza que deseja sair da Aplicação?");

        if (alerta.getButton().get() == ButtonType.OK) {
            ((Stage) btnSair.getScene().getWindow()).close();
        } else {
            alerta.getAlert().close();
        }
    }

    /**
     * Método de associar ações aos botões do Menu Iniciao e Menu Secundário.
     */
    public void setActionsOnButtons() {

        btConfiguracoes.setOnMouseClicked((MouseEvent e) -> {
            menuLateral.getChildren().setAll(menuLateralSecundario);
                fillMenuSecundarioConfigurações();
        });
        btComponente.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("componentes"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        btTarefa.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("tarefas"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        btHorario.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("horarios_1"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        btMaquina.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("maquinas"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        btOperacao.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("operacoes"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        btOperador.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("operadores"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        btProduto.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("produtos"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        btTipoOperacao.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("tipoOperacoes"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        btGestaoEncomenda.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("encomendas"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });

        btGestaoCliente.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("clientes"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        btTipoContacto.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("tipoContactos"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        btTipoUnidade.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("tipoUnidades"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        btTipoDesconto.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("tipoDescontos"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        btTipoEstado.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("tipoEstados"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        btFichaTecnica.setOnMouseClicked((MouseEvent e) -> {
            menuLateral.getChildren().setAll(menuLateralSecundario);
            fillMenuSecundarioFichasTecnicas();

            try {
                stack.getChildren().setAll(App.loadFXML("fichasTecnicas"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        btHome.setOnMouseClicked((MouseEvent e) -> {
            stack.getChildren().setAll(imagemStack);
            menuLateral.getChildren().setAll(vboxMenu);
        });
        btStock.setOnMouseClicked((MouseEvent e) -> {
            try {
                stack.getChildren().setAll(App.loadFXML("stocks"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });

    }
    
    /**
     * Configuração de Menu Inicial
     */
    private void fillMenu(){
        menuLateral.getChildren().clear();
        vboxMenu.getChildren().addAll(btGestaoEncomenda, btGestaoCliente, btFichaTecnica, btConfiguracoes);
        vboxMenu.setAlignment(Pos.TOP_CENTER);
        vboxMenu.setSpacing(15);
        vboxMenu.setStyle("-fx-padding: 10px;\n"
                + "    -fx-border-insets: 10px;\n"
                + "    -fx-background-insets: 10px;");
        menuLateral.getChildren().setAll(vboxMenu);
        
    }
        /**
     *  Método de configuração de Menu Secundário de Configurações
     */
    private void fillMenuSecundarioConfigurações(){
            menuLateralSecundario.getChildren().clear();
            menuLateralSecundario.setStyle("-fx-background-color: #FFFFFF");
            menuLateralSecundario.setAlignment(Pos.TOP_CENTER);

            vboxMenuSecundarioConfiguracoes.getChildren().setAll(btHome, btComponente, btMaquina, btHorario,btOperacao, btOperador, btProduto, btTarefa, btTipoContacto, btTipoDesconto, btTipoEstado, btTipoOperacao,btTipoUnidade);
            vboxMenuSecundarioConfiguracoes.setAlignment(Pos.TOP_CENTER);
            vboxMenuSecundarioConfiguracoes.setSpacing(25);
            vboxMenuSecundarioConfiguracoes.setStyle("-fx-padding: 10px;\n"
                + "    -fx-border-insets: 10px;\n"
                + "    -fx-background-insets: 10px;");
            
            
            menuLateralSecundario.getChildren().setAll(vboxMenuSecundarioConfiguracoes);
    }
    /**
     *  Método de configuração de Menu Secundário de Ficha de Técnicas
     */
    private void fillMenuSecundarioFichasTecnicas(){
            menuLateralSecundario.getChildren().clear();
            menuLateralSecundario.setStyle("-fx-background-color: #FFFFFF");
            menuLateralSecundario.setAlignment(Pos.TOP_CENTER);

            vboxMenuSecundarioFichasTecnicas.getChildren().setAll(btHome, btStock);
            vboxMenuSecundarioFichasTecnicas.setAlignment(Pos.TOP_CENTER);
            vboxMenuSecundarioFichasTecnicas.setSpacing(15);
            vboxMenuSecundarioFichasTecnicas.setStyle("-fx-padding: 10px;\n"
                + "    -fx-border-insets: 10px;\n"
                + "    -fx-background-insets: 10px;");
            
            
            menuLateralSecundario.getChildren().setAll(vboxMenuSecundarioFichasTecnicas);
    }
    /**
     * Método de configuração de aspeto dos Botões dos Menus.
     */
    public void configButtons() {
        
        btConfiguracoes.setCursor(Cursor.HAND);
        btMaquina.setCursor(Cursor.HAND);
        btHorario.setCursor(Cursor.HAND);
        btOperacao.setCursor(Cursor.HAND);
        btTipoOperacao.setCursor(Cursor.HAND);
        btProduto.setCursor(Cursor.HAND);
        btOperador.setCursor(Cursor.HAND);
        btTarefa.setCursor(Cursor.HAND);
        btComponente.setCursor(Cursor.HAND);
        btTipoContacto.setCursor(Cursor.HAND);
        btTipoDesconto.setCursor(Cursor.HAND);
        btTipoEstado.setCursor(Cursor.HAND);
        btTipoUnidade.setCursor(Cursor.HAND);

        btConfiguracoes.setPrefWidth(220);
        btMaquina.setPrefWidth(220);
        btHorario.setPrefWidth(220);
        btOperacao.setPrefWidth(220);
        btTipoOperacao.setPrefWidth(220);
        btProduto.setPrefWidth(220);
        btTarefa.setPrefWidth(220);
        btOperador.setPrefWidth(220);
        btComponente.setPrefWidth(220);
        btTipoContacto.setPrefWidth(220);
        btTipoDesconto.setPrefWidth(220);
        btTipoEstado.setPrefWidth(220);
        btTipoUnidade.setPrefWidth(220);

        btConfiguracoes.setPrefHeight(35);
        btMaquina.setPrefHeight(35);
        btHorario.setPrefHeight(35);
        btOperacao.setPrefHeight(35);
        btTarefa.setPrefHeight(35);
        btTipoOperacao.setPrefHeight(35);
        btProduto.setPrefHeight(35);
        btOperador.setPrefHeight(35);
        btComponente.setPrefHeight(35);
        btTipoContacto.setPrefHeight(35);
        btTipoDesconto.setPrefHeight(35);
        btTipoEstado.setPrefHeight(35);
        btTipoUnidade.setPrefHeight(35);

        btConfiguracoes.setStyle("-fx-background-color: #FCBD6D;; -fx-background-radius: 80;");
        btTarefa.setStyle("-fx-background-color: #FCBD6D;; -fx-background-radius: 80;");
        btComponente.setStyle("-fx-background-color: #FCBD6D;; -fx-background-radius: 80;");
        btMaquina.setStyle("-fx-background-color: #FCBD6D;; -fx-background-radius: 80;");
        btHorario.setStyle("-fx-background-color: #FCBD6D;; -fx-background-radius: 80;");
        btOperacao.setStyle("-fx-background-color: #FCBD6D; -fx-background-radius: 80;");
        btOperador.setStyle("-fx-background-color: #FCBD6D; -fx-background-radius: 80;");
        btProduto.setStyle("-fx-background-color: #FCBD6D; -fx-background-radius: 80;");
        btTipoOperacao.setStyle("-fx-background-color: #FCBD6D; -fx-background-radius: 80;");
        btGestaoCliente.setStyle("-fx-background-color: #FCBD6D; -fx-background-radius: 80;");
        btGestaoEncomenda.setStyle("-fx-background-color: #FCBD6D; -fx-background-radius: 80;");
        btTipoContacto.setStyle("-fx-background-color: #FCBD6D; -fx-background-radius: 80;");
        btTipoDesconto.setStyle("-fx-background-color: #FCBD6D; -fx-background-radius: 80;");
        btTipoEstado.setStyle("-fx-background-color: #FCBD6D; -fx-background-radius: 80;");
        btTipoUnidade.setStyle("-fx-background-color: #FCBD6D; -fx-background-radius: 80;");
        

        btFichaTecnica.setCursor(Cursor.HAND);
        btHome.setCursor(Cursor.HAND);
        btGestaoEncomenda.setCursor(Cursor.HAND);
        btGestaoCliente.setCursor(Cursor.HAND);
        btStock.setCursor(Cursor.HAND);

        btFichaTecnica.setPrefWidth(220);
        btHome.setPrefWidth(220);
        btGestaoEncomenda.setPrefWidth(220);
        btGestaoCliente.setPrefWidth(220);
        btStock.setPrefWidth(220);


        btFichaTecnica.setPrefHeight(35);
        btHome.setPrefHeight(35);
        btGestaoEncomenda.setPrefHeight(35);
        btGestaoCliente.setPrefHeight(35);
        btStock.setPrefHeight(35);

        btFichaTecnica.setStyle("-fx-background-color: #FCBD6D; -fx-background-radius: 80; ");
        btHome.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 80; -fx-border-color:  #B0B4C3; -fx-border-width: 5; -fx-border-radius:80;");
        btnSair.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 80; -fx-border-color:  #B0B4C3; -fx-border-width: 5; -fx-border-radius:80;");
        btStock.setStyle("-fx-background-color: #FCBD6D; -fx-background-radius: 80;");
        btGestaoEncomenda.setStyle("-fx-background-color: #FCBD6D; -fx-background-radius: 80; ");
        btGestaoCliente.setStyle("-fx-background-color: #FCBD6D; -fx-background-radius: 80; ");
        
        // ATRIBUIR ICONS A BOTÕES 
        FontAwesomeIconFactory.get().setIcon(btGestaoCliente, FontAwesomeIcon.USERS, "1.3em");
        FontAwesomeIconFactory.get().setIcon(btGestaoEncomenda, FontAwesomeIcon.ARCHIVE, "1.3em");
        FontAwesomeIconFactory.get().setIcon(btFichaTecnica, FontAwesomeIcon.FILES_ALT, "1.3em");
        FontAwesomeIconFactory.get().setIcon(btConfiguracoes, FontAwesomeIcon.COGS, "1.3em");
        FontAwesomeIconFactory.get().setIcon(btHome, FontAwesomeIcon.CHEVRON_LEFT, "1.3em");
        
        FontAwesomeIconFactory.get().setIcon(btTarefa, FontAwesomeIcon.ANGLE_RIGHT, "em");
        FontAwesomeIconFactory.get().setIcon(btComponente, FontAwesomeIcon.ANGLE_RIGHT, "em");
        FontAwesomeIconFactory.get().setIcon(btProduto, FontAwesomeIcon.ANGLE_RIGHT, "1em");
        FontAwesomeIconFactory.get().setIcon(btMaquina, FontAwesomeIcon.ANGLE_RIGHT, "1em");
        FontAwesomeIconFactory.get().setIcon(btHorario, FontAwesomeIcon.ANGLE_RIGHT, "1em");
        FontAwesomeIconFactory.get().setIcon(btOperador, FontAwesomeIcon.ANGLE_RIGHT, "1em");
        FontAwesomeIconFactory.get().setIcon(btOperacao, FontAwesomeIcon.ANGLE_RIGHT, "1em");
        FontAwesomeIconFactory.get().setIcon(btTipoOperacao, FontAwesomeIcon.ANGLE_RIGHT, "1em");
        FontAwesomeIconFactory.get().setIcon(btTipoDesconto, FontAwesomeIcon.ANGLE_RIGHT, "1em");
        FontAwesomeIconFactory.get().setIcon(btTipoContacto, FontAwesomeIcon.ANGLE_RIGHT, "1em");
        FontAwesomeIconFactory.get().setIcon(btTipoUnidade, FontAwesomeIcon.ANGLE_RIGHT, "1em");
        FontAwesomeIconFactory.get().setIcon(btTipoEstado, FontAwesomeIcon.ANGLE_RIGHT, "1em");
        FontAwesomeIconFactory.get().setIcon(btStock, FontAwesomeIcon.ANGLE_RIGHT, "1em");
    }
    

}
