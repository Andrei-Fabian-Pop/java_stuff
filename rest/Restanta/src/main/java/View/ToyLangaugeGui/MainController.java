package View.ToyLangaugeGui;

import Controller.Controller;
import Exceptions.MyException;
import Model.ADT.*;
import Model.ProgramState;
import Model.Statements.IStatement;
import Model.Values.Values;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainController {
    private Controller controller;
    @FXML
    public TableView<Pair<Integer, Pair<Integer, List<Integer>>>> CyclicBarrierTableView;
    @FXML
    public TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, String> CyclicBarrierIndexColumn;
    @FXML
    public TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, String> CyclicBarrierValueColumn;
    @FXML
    public TableColumn<Pair<Integer, Pair<Integer, List<Integer>>>, String> CyclicBarrierLoVColumn;
    @FXML
    public TextField PrgStateTextField;
    @FXML
    public TableView<Pair<String, Values>> HeapTableView;
    @FXML
    public TableColumn<Pair<String, Values>, String> Address;
    @FXML
    public TableColumn<Pair<String, Values>, String> Value;
    @FXML
    public ListView<String> OutListView;
    @FXML
    public ListView<String> FileTableListView;
    @FXML
    public TableView<Pair<String, Values>> SymTableView;
    @FXML
    public TableColumn<Pair<String, Values>, String> Variable;
    @FXML
    public TableColumn<Pair<String, Values>, String> Name;
    @FXML
    public ListView<Integer> PrgStateIdListView;
    @FXML
    public ListView<String> ExeStackListView;
    @FXML
    public Button OneStepButton;

    public void initialize() {
        this.Address.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().first));
        this.Value.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        this.Variable.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().first));
        this.Name.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));

        this.CyclicBarrierIndexColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().first.toString()));
        this.CyclicBarrierValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.first.toString()));
        this.CyclicBarrierLoVColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.second.toString()));

        this.OneStepButton.setOnAction(actionEvent -> {
            if (controller == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            boolean programStateLeft = Objects.requireNonNull(getCurrentProgramState()).getExecStack().isEmpty();
            if (programStateLeft) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            try {
                this.controller.oneStepAllGui();
                this.populate();
            } catch (MyException myException) {
                Alert alert = new Alert(Alert.AlertType.ERROR, myException.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });

        this.PrgStateIdListView.setOnMouseClicked(mouseEvent -> this.populate());
    }

    private ProgramState getCurrentProgramState() {
        if (controller.getProgramState().isEmpty()) {
            return null;
        }
        int currentId = this.PrgStateIdListView.getSelectionModel().getSelectedIndex();
        if (currentId < 0) {
            return controller.getProgramState().get(0);
        }
        return controller.getProgramState().get(currentId);
    }

    public void setController(Controller controller) {
        this.controller = controller;
        this.populate();
    }

    private void populate() {
        this.populateHeap();
        this.populateProgramStateIdentifiers();
        this.populateFileTable();
        this.populateOutput();
        this.populateSymbolTable();
        this.populateExecutionStack();
        this.populateCyclicBarrier();
    }

    private void populateExecutionStack() {
        ProgramState state = getCurrentProgramState();
        List<String> executionStackList = new ArrayList<>();
        if (state != null) {
            for (IStatement statement : state.getExecStack().getStack()) {
                executionStackList.add(statement.toString());
            }
        }
        this.ExeStackListView.setItems(FXCollections.observableList(executionStackList));
        this.ExeStackListView.refresh();
    }

    private void populateSymbolTable() {
        ProgramState programState = getCurrentProgramState();
        List<Pair<String, Values>> symTableList = new ArrayList<>();
        if (programState != null) {
            for (Map.Entry<String, Values> entry : programState.getSymTable().getContent().entrySet()) {
                symTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
            }
        }
        this.SymTableView.setItems(FXCollections.observableList(symTableList));
        this.SymTableView.refresh();
    }

    private void populateOutput() {
        IList<String> output;
        if (!controller.getProgramState().isEmpty()) {
            output = controller.getProgramState().get(0).getOut();
        } else {
            output = new MyList<>();
        }

        this.OutListView.setItems(FXCollections.observableList(output.getList()));
        this.OutListView.refresh();
    }

    private void populateFileTable() {
        ArrayList<String> files;
        if (!controller.getProgramState().isEmpty()) {
            files = new ArrayList<>(controller.getProgramState().get(0).getFileTable().keys());
        } else {
            files = new ArrayList<>();
        }

        this.FileTableListView.setItems(FXCollections.observableArrayList(files));
        this.FileTableListView.refresh();
    }

    private void populateProgramStateIdentifiers() {
        List<ProgramState> programStates = controller.getProgramState();
        var idList = programStates.stream().map(ps -> ps.id).toList();
        this.PrgStateIdListView.setItems(FXCollections.observableList(idList));
        this.PrgStateIdListView.refresh();
        this.PrgStateTextField.setText("" + programStates.size());
    }

    private void populateHeap() {
        IHeap heap;
        if (!controller.getProgramState().isEmpty()) {
            heap = controller.getProgramState().get(0).getHeap();
        } else {
            heap = new MyHeap();
        }

        List<Pair<String, Values>> heapTableList = new ArrayList<>();
        for (Map.Entry<String, Values> entry : heap.getContent().entrySet()) {
            heapTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        this.HeapTableView.setItems(FXCollections.observableList(heapTableList));
        this.HeapTableView.refresh();
    }

    private void populateCyclicBarrier() {
        IBarrier barrier;
        if (!controller.getProgramState().isEmpty()) {
            barrier = controller.getProgramState().get(0).getBarrierTable();
        } else {
            barrier = new MyBarrier();
        }

        List<Pair<Integer, Pair<Integer, List<Integer>>>> cyclicBarrierList = new ArrayList<>();
        for (Map.Entry<Integer, Pair<Integer, List<Integer>>> entry : barrier.getBarrierTable().entrySet()) {
            cyclicBarrierList.add(new Pair<>(entry.getKey(), new Pair<>(entry.getValue().first, entry.getValue().second)));
        }
        this.CyclicBarrierTableView.setItems(FXCollections.observableList(cyclicBarrierList));
        this.CyclicBarrierTableView.refresh();
    }
}
