package View.ToyLangaugeGui;

import Controller.Controller;
import Exceptions.MyException;
import Model.ADT.*;
import Model.ProgramState;
import Model.Statements.IStatement;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.*;
import java.util.concurrent.locks.Lock;

public class MainController {
    @FXML
    public TableView<Pair<Integer, String>> lockTable;
    @FXML
    public TableColumn<Pair<Integer, String>, String> lockTableC1;
    @FXML
    public TableColumn<Pair<Integer, String>, String> lockTableC2;
    private Controller controller;
    @FXML
    public TextField PrgStateTextField;
    @FXML
    public TableView<Pair<String, Model.Values.Value>> HeapTableView;
    @FXML
    public TableColumn<Pair<String, Model.Values.Value>, String> Address;
    @FXML
    public TableColumn<Pair<String, Model.Values.Value>, String> Value;
    @FXML
    public ListView<String> OutListView;
    @FXML
    public ListView<String> FileTableListView;
    @FXML
    public TableView<Pair<String, Model.Values.Value>> SymTableView;
    @FXML
    public TableColumn<Pair<String, Model.Values.Value>, String> Variable;
    @FXML
    public TableColumn<Pair<String, Model.Values.Value>, String> Name;
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
        this.lockTableC1.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().first.toString()));
        this.lockTableC2.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second));

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
        this.populateLock();
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
        List<Pair<String, Model.Values.Value>> symTableList = new ArrayList<>();
        if (programState != null) {
            for (Map.Entry<String, Model.Values.Value> entry : programState.getSymTable().getContent().entrySet()) {
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

        List<Pair<String, Model.Values.Value>> heapTableList = new ArrayList<>();
        for (Map.Entry<String, Model.Values.Value> entry : heap.getContent().entrySet()) {
            heapTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        this.HeapTableView.setItems(FXCollections.observableList(heapTableList));
        this.HeapTableView.refresh();
    }

    private void populateLock() {
        ILock lock;
        if (!controller.getProgramState().isEmpty()) {
            lock = controller.getProgramState().get(0).getLockTable();
        } else {
            lock = new MyLock();
        }

        List<Pair<Integer, String>> lockList = new ArrayList<>();
        for (Map.Entry<Integer, Pair<Lock, Integer>> entry : lock.getLockTable().entrySet()) {
            lockList.add(new Pair<>(entry.getKey(), (entry.getValue().second == null) ? "null" : entry.getValue().second.toString()));
        }
        this.lockTable.setItems(FXCollections.observableList(lockList));
        this.lockTable.refresh();
    }
}
