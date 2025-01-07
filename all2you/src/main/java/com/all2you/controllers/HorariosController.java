package com.all2you.controllers;

import com.all2you.models.Maquina;
import com.all2you.models.MaquinaHorario;
import com.all2you.models.Operador;
import com.all2you.models.OperadorHorario;
import com.all2you.services.MaquinasService;
import com.all2you.services.OperadoresService;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import jfxtras.scene.control.LocalTimeTextField;

/**
 *
 * @author ruteg
 */
public class HorariosController implements Initializable {

    @FXML
    private LocalTimeTextField maq_seg_ini01;
    @FXML
    private LocalTimeTextField maq_seg_fim01;
    @FXML
    private LocalTimeTextField maq_seg_ini02;
    @FXML
    private LocalTimeTextField maq_seg_fim02;
    @FXML
    private LocalTimeTextField maq_seg_ini03;
    @FXML
    private LocalTimeTextField maq_seg_fim03;
    @FXML
    private LocalTimeTextField maq_ter_ini01;
    @FXML
    private LocalTimeTextField maq_ter_fim01;
    @FXML
    private LocalTimeTextField maq_ter_ini02;
    @FXML
    private LocalTimeTextField maq_ter_fim02;
    @FXML
    private LocalTimeTextField maq_ter_ini03;
    @FXML
    private LocalTimeTextField maq_ter_fim03;
    @FXML
    private LocalTimeTextField maq_qua_ini01;
    @FXML
    private LocalTimeTextField maq_qua_fim01;
    @FXML
    private LocalTimeTextField maq_qua_ini02;
    @FXML
    private LocalTimeTextField maq_qua_fim02;
    @FXML
    private LocalTimeTextField maq_qua_ini03;
    @FXML
    private LocalTimeTextField maq_qua_fim03;
    @FXML
    private LocalTimeTextField maq_qui_ini01;
    @FXML
    private LocalTimeTextField maq_qui_fim01;
    @FXML
    private LocalTimeTextField maq_qui_ini02;
    @FXML
    private LocalTimeTextField maq_qui_fim02;
    @FXML
    private LocalTimeTextField maq_qui_ini03;
    @FXML
    private LocalTimeTextField maq_qui_fim03;
    @FXML
    private LocalTimeTextField maq_sex_ini01;
    @FXML
    private LocalTimeTextField maq_sex_fim01;
    @FXML
    private LocalTimeTextField maq_sex_ini02;
    @FXML
    private LocalTimeTextField maq_sex_fim02;
    @FXML
    private LocalTimeTextField maq_sex_ini03;
    @FXML
    private LocalTimeTextField maq_sex_fim03;
    @FXML
    private LocalTimeTextField maq_sab_ini01;
    @FXML
    private LocalTimeTextField maq_sab_fim01;
    @FXML
    private LocalTimeTextField maq_sab_ini02;
    @FXML
    private LocalTimeTextField maq_sab_fim02;
    @FXML
    private LocalTimeTextField maq_sab_ini03;
    @FXML
    private LocalTimeTextField maq_sab_fim03;
    @FXML
    private LocalTimeTextField maq_dom_ini01;
    @FXML
    private LocalTimeTextField maq_dom_fim01;
    @FXML
    private LocalTimeTextField maq_dom_ini02;
    @FXML
    private LocalTimeTextField maq_dom_fim02;
    @FXML
    private LocalTimeTextField maq_dom_ini03;
    @FXML
    private LocalTimeTextField maq_dom_fim03;
    @FXML
    private LocalTimeTextField op_seg_ini01;
    @FXML
    private LocalTimeTextField op_seg_fim01;
    @FXML
    private LocalTimeTextField op_seg_ini02;
    @FXML
    private LocalTimeTextField op_seg_fim02;
    @FXML
    private LocalTimeTextField op_ter_ini01;
    @FXML
    private LocalTimeTextField op_ter_fim01;
    @FXML
    private LocalTimeTextField op_ter_ini02;
    @FXML
    private LocalTimeTextField op_ter_fim02;
    @FXML
    private LocalTimeTextField op_qua_ini01;
    @FXML
    private LocalTimeTextField op_qua_fim01;
    @FXML
    private LocalTimeTextField op_qua_ini02;
    @FXML
    private LocalTimeTextField op_qua_fim02;
    @FXML
    private LocalTimeTextField op_qui_ini01;
    @FXML
    private LocalTimeTextField op_qui_fim01;
    @FXML
    private LocalTimeTextField op_qui_ini02;
    @FXML
    private LocalTimeTextField op_qui_fim02;
    @FXML
    private LocalTimeTextField op_sex_ini01;
    @FXML
    private LocalTimeTextField op_sex_fim01;
    @FXML
    private LocalTimeTextField op_sex_ini02;
    @FXML
    private LocalTimeTextField op_sex_fim02;
    @FXML
    private LocalTimeTextField op_sab_ini01;
    @FXML
    private LocalTimeTextField op_sab_fim01;
    @FXML
    private LocalTimeTextField op_sab_ini02;
    @FXML
    private LocalTimeTextField op_sab_fim02;
    @FXML
    private LocalTimeTextField op_dom_ini01;
    @FXML
    private LocalTimeTextField op_dom_fim01;
    @FXML
    private LocalTimeTextField op_dom_ini02;
    @FXML
    private LocalTimeTextField op_dom_fim02;

    @FXML
    private CheckBox maq_seg;
    @FXML
    private CheckBox maq_ter;
    @FXML
    private CheckBox maq_qua;
    @FXML
    private CheckBox maq_qui;
    @FXML
    private CheckBox maq_sex;
    @FXML
    private CheckBox maq_sab;
    @FXML
    private CheckBox maq_dom;
    @FXML
    private CheckBox op_seg;
    @FXML
    private CheckBox op_ter;
    @FXML
    private CheckBox op_qua;
    @FXML
    private CheckBox op_qui;
    @FXML
    private CheckBox op_sex;
    @FXML
    private CheckBox op_sab;
    @FXML
    private CheckBox op_dom;

    @FXML
    private Button btnMaqCancelar;
    @FXML
    private Button btnMaqEliminar;
    @FXML
    private Button btnMaqGuardar;
    @FXML
    private Button btnOpCancelar;
    @FXML
    private Button btnOpEliminar;
    @FXML
    private Button btnOpGuardar;

    @FXML
    private AnchorPane anchMaqBox;
    @FXML
    private AnchorPane anchOpBox;
    @FXML
    private TabPane tabpaneHorarios;
    @FXML
    private Tab tabMaquinas;
    @FXML
    private Tab tabOperadores;
    @FXML
    private ComboBox<Maquina> comboMaquinas;
    @FXML
    private ComboBox<Operador> comboOperadores;

    List<String> codDiasList = new ArrayList(7);

    Operador selectedOperator;
    Maquina selectedMachine;
    List<OperadorHorario> operatorSchedules;
    List<MaquinaHorario> machineSchedules;

    private OperadoresService operadoresService = new OperadoresService();
    private MaquinasService maquinasService = new MaquinasService();

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        codDiasList.add("seg");
        codDiasList.add("ter");
        codDiasList.add("qua");
        codDiasList.add("qui");
        codDiasList.add("sex");
        codDiasList.add("sab");
        codDiasList.add("dom");

        initializeOperator();
        initializeMachine();

        System.out.println("ENTROU  1");

        this.comboMaquinas.setConverter(new StringConverter<Maquina>() {
            @Override
            public String toString(Maquina maq) {
                return maq.getId() + " | " + maq.getOperationType().getId();
            }

            @Override
            public Maquina fromString(String string) {
                return null;
            }
        });
        ObservableList obsListMaq = FXCollections.observableArrayList(maquinasService.getAllMachines());
        this.comboMaquinas.getItems().addAll(obsListMaq);

        this.comboMaquinas.setOnAction((e) -> {
            selectedMachine = this.comboMaquinas.getSelectionModel().getSelectedItem();
            if (!isNull(selectedMachine)) {
                anchMaqBox.setDisable(false);
                refreshMachineScheduleFromDB();
            }
        });

        this.comboOperadores.setConverter(new StringConverter<Operador>() {
            @Override
            public String toString(Operador op) {
                return op.getId() + " - " + op.getName();
            }

            @Override
            public Operador fromString(String string) {
                return null;
            }
        });
        ObservableList obsListOp = FXCollections.observableArrayList(operadoresService.getAllOperators());
        this.comboOperadores.getItems().addAll(obsListOp);

        this.comboOperadores.setOnAction((e) -> {
            selectedOperator = this.comboOperadores.getSelectionModel().getSelectedItem();
            if (!isNull(selectedOperator)) {
                anchOpBox.setDisable(false);
                refreshOperatorScheduleFromDB();
            }
        });

        System.out.println("ENTROU  2");
    }

    private void initializeMachine() {
        cleanFieldsMachine();
        this.comboMaquinas.getSelectionModel().clearSelection();
        selectedMachine = null;
        anchMaqBox.setDisable(true);
//        
//        if(machineSchedules.size() > 0) {
//            machineSchedules.clear();
//        }

    }

    private void initializeOperator() {
        cleanFieldsOperator();
        this.comboOperadores.getSelectionModel().clearSelection();

        selectedOperator = null;
        anchOpBox.setDisable(true);
        // operatorSchedules = null;
    }

    @FXML
    private void cancelarHorarioMaquina(ActionEvent event) {
        System.out.println("Cancela");
        initializeMachine();
    }

    @FXML
    private void cancelarHorarioOperador(ActionEvent event) {
        System.out.println("Cancela");
        initializeOperator();
    }

    @FXML
    private void eliminarHorarioMaquina(ActionEvent event) {

        if (!isNull(selectedMachine)) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar o Horário desta Máquina?");

            if (alerta.getButton().get() == ButtonType.OK) {

                List responseList = new ArrayList(machineSchedules.size());

                for (MaquinaHorario machSch : machineSchedules) {
                    Boolean response = maquinasService.deleteSchedule(machSch);
                    responseList.add(response);
                }

                Boolean bool = responseList.contains(false);
                
                if (!bool) {

                    refreshOperatorScheduleFromDB();
                    AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Horário da Máquina eliminado com SUCESSO!");

                } else {
                    AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o Horário desta Máquina.");
                }

            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Máquina em falta", "Por favor, selecione uma Máquina.");
        }

    }

    @FXML
    private void eliminarHorarioOperador(ActionEvent event) {

        if (!isNull(selectedOperator)) {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar o Horário deste Operador?");

            if (alerta.getButton().get() == ButtonType.OK) {

                List responseList = new ArrayList(machineSchedules.size());

                for (MaquinaHorario machSch : machineSchedules) {
                    Boolean response = maquinasService.deleteSchedule(machSch);
                    responseList.add(response);
                }

                Boolean bool = responseList.contains(false);
                
                if (!bool) {

                    refreshOperatorScheduleFromDB();

                    AlertBoxController alertaConf = new AlertBoxController(Alert.AlertType.CONFIRMATION, "SUCESSO", "Horário do Operador eliminado com SUCESSO!");

                } else {
                    AlertBoxController alertaErr = new AlertBoxController(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o Horário deste Operador.");
                }

            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }
        } else {
            AlertBoxController alerta = new AlertBoxController(Alert.AlertType.ERROR, "Operador em falta", "Por favor, selecione um Operador.");
        }

    }

    @FXML
    private void guardarHorarioMaquina(ActionEvent event) {

    }

    @FXML
    private void guardarHorarioOperador(ActionEvent event) {

    }

    private void refreshOperatorScheduleFromDB() {
        operatorSchedules = operadoresService.getAllSchedules(selectedOperator);
        setFieldsOperator();
    }

    private void refreshMachineScheduleFromDB() {
        machineSchedules = maquinasService.getAllSchedules(selectedMachine);
        // setFieldsMachine();
    }

    private void setFieldsMachine() {

        if (machineSchedules.size() > 0) {
            for (MaquinaHorario maq : machineSchedules) {

                String idDia = getDayForId(maq.getDay().getDia());
                if (!idDia.isEmpty()) {

                    // setText(maq.getDay());
                }
            }
        } else {

        }

    }

    private String getDayForId(String dia) {
        String idDia = "";
        switch (dia) {
            case "Segunda":
                idDia = "seg";
                break;
            case "Terca":
                idDia = "ter";
                break;
            case "Quarta":
                idDia = "qua";
                break;
            case "Quinta":
                idDia = "qui";
                break;
            case "Sexta":
                idDia = "sex";
                break;
            case "Sabado":
                idDia = "sab";
                break;
            case "Domingo":
                idDia = "dom";
                break;
        }
        return idDia;
    }

    private void setFieldsOperator() {
        
        

        if (operatorSchedules.size() > 0) {
            for (OperadorHorario op : operatorSchedules) {
                String idDia = getDayForId(op.getDay().getDia());
                if (idDia != "") {
                    // setText(maq.getDay());
                }
                System.out.println("OP: " + op.getDay().getDia() + " " + op.getBeginAt() + " " + op.getEndAt());
            }
        } else {

        }

    }

    private void cleanFieldsMachine() {
        maq_seg.selectedProperty().setValue(false);
        maq_ter.selectedProperty().setValue(false);
        maq_qua.selectedProperty().setValue(false);
        maq_qui.selectedProperty().setValue(false);
        maq_sex.selectedProperty().setValue(false);
        maq_sab.selectedProperty().setValue(false);
        maq_dom.selectedProperty().setValue(false);

        for (int i = 1; i < 4; i++) {
//            maq_seg_ini01
//            maq_seg_fim01
        }
    }

    private void cleanFieldsOperator() {
        op_seg.selectedProperty().setValue(false);
        op_ter.selectedProperty().setValue(false);
        op_qua.selectedProperty().setValue(false);
        op_qui.selectedProperty().setValue(false);
        op_sex.selectedProperty().setValue(false);
        op_sab.selectedProperty().setValue(false);
        op_dom.selectedProperty().setValue(false);

        for (int i = 1; i < 3; i++) {
//            op_seg_ini01
//            op_seg_fim01
        }
    }

}
