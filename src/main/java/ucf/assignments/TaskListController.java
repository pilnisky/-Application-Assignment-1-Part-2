/*
 *  UCF COP3330 Fall 2021 Assignment 1 part 2 Solution
 *  Copyright 2021 Palmer Ilnisky
 */


package ucf.assignments;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class TaskListController implements Initializable {

    public static String filter = "all";

    @FXML public  TableView<Task> table;
    @FXML private TableColumn<Task, String> descriptionColumn;
    @FXML private TableColumn<Task, Boolean> completedColumn;
    @FXML private TableColumn<Task, Date> dueDateColumn;

    @FXML
    public void incompleteTasksButtonClicked(ActionEvent actionEvent) {
        filter = "incomplete";
        showIncompleteTasks();
    }

    @FXML
    public void completedTasksButtonClicked(ActionEvent actionEvent) {
        filter = "completed";
        showCompleteTasks();
    }

    @FXML
    public void allTasksButtonClicked(ActionEvent actionEvent) {
        showAllTasks();
    }

    @FXML
    public void newTaskButtonClicked(ActionEvent actionEvent) {
        createTask();
    }

    @FXML
    public void ascendingButtonClicked(ActionEvent actionEvent) {

        table.setItems(ascendSort());
    }

    @FXML
    public void descendingButtonClicked(ActionEvent actionEvent) {
        table.setItems(descendSort());
    }

    @FXML
    public void deleteButtonClicked(ActionEvent actionEvent) {
        Task task = table.getSelectionModel().getSelectedItem();
        table.getItems().removeAll(task);
        deleteTask(task);
    }
    @FXML
    public void clearListButtonClicked(ActionEvent actionEvent) {
        table.getItems().clear();
        clearList();
    }

    @FXML
    public void saveListButtonClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select output destination");
        File file = fileChooser.showSaveDialog(new Stage());
        saveList(file);
    }

    @FXML
    public void loadListButtonClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select output destination");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null)
            loadList(file);
        //loadList();
    }


    public String showIncompleteTasks() {
        // set filter to incomplete
        // update table
        filter = "incomplete";
        ObservableList<Task> taskList = tasks.filtered(task -> !task.getCompleted());
        if (table != null) table.setItems(taskList);
        return filter;
    }

    public String showCompleteTasks() {
        // set filter to all
        // update table
        filter = "complete";
        ObservableList<Task> taskList = tasks.filtered(task -> task.getCompleted());
        if (table != null) table.setItems(taskList);
        return filter;
    }

    public String showAllTasks() {
        // set filter to all
        // update table
        filter = "all";
        if(this.table != null) this.table.setItems(this.tasks);
        return filter;
    }

    public ObservableList<Task> createTask() {
        // create new Task object
        // initialize Task object
        // add Task object to ToDoList
        // return ToDoList
        Task task = new Task();
        tasks.add(task);
        return tasks;
    }

    private ObservableList<Task> deleteTask(Task task) {
        // remove task from list
        // return to-do list
        tasks.remove(task);
        return tasks;
    }

    public ObservableList<Task> clearList() {
        // clear tasks
        tasks.clear();
        return tasks;
    }


    public ObservableList<Task> ascendSort() {
        // create a new comparator with variable date
        // sort the ToDoList in ascending order
//        ObservableList<Task> taskList = this.tasks.sorted((a, b) -> {
//            return LocalDate.parse(a.getDueDate()).compareTo(LocalDate.parse(b.getDueDate()));
//        });

        ObservableList<Task> taskList = this.tasks.sorted(
                Comparator.comparing((task) -> {
                    return task.getDueDate() != null && !task.getDueDate().isEmpty() ? LocalDate.parse(task.getDueDate()) : null;
                }, Comparator.nullsLast(Comparator.naturalOrder()))
        );
        return taskList;

    }

    public ObservableList<Task> descendSort() {
        // create a new comparator with variable date
        // sort the ToDoList in descending order
        // return ToDoList
//        ObservableList<Task> taskList = this.tasks.sorted((a, b) -> {
//            if(a.getDueDate() != null && b.getDueDate() == null) {
//                return 1;
//            } else if (a.getDueDate() == null && b.getDueDate() != null) {
//                return -1;
//            }
//            if (a.getDueDate() == null) {
//                return b.getDueDate() == null ? 0 : 1;
//            }
//
//            if (b.getDueDate() == null) {
//                return 1;
//            }
//
//            return LocalDate.parse(a.getDueDate()).compareTo(LocalDate.parse(b.getDueDate()));
//
//        });
        ObservableList<Task> taskList = this.tasks.sorted(
                Comparator.comparing((task) -> {
                    return task.getDueDate() != null && !task.getDueDate().isEmpty() ? LocalDate.parse(task.getDueDate()) : null;
                }, Comparator.nullsLast(Comparator.reverseOrder()))
        );
        return taskList;
    }

    public void setTasks(ObservableList<Task> tasks) {
        this.tasks = tasks;
    }

    public File saveList(File file) {
        // opens file browser
        // outputs file to destination
        // returns file
        try {
            PrintWriter printWriter = new PrintWriter(file);
            com.google.gson.Gson gson = new GsonBuilder().registerTypeAdapter(Task.class, new Serializer())
                        .setPrettyPrinting().create();
            String taskJson = gson.toJson(tasks.toArray());
            printWriter.write(taskJson);
            printWriter.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

    public File loadList(File file) {
        // create GSON instance
        // create reader
        // convert JSON array to ObservableArray of tasks
        // return ObservableArray

        try {
            Reader reader = Files.newBufferedReader(file.toPath());
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Task.class, new Deserializer())
                    .create();
            List<Task> list = gson.fromJson(reader, new TypeToken<ArrayList<Task>>() {}.getType());
            tasks = FXCollections.observableArrayList(list);
            this.table.setItems(tasks);
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return file;
    }

    static ObservableList<Task> tasks = FXCollections.observableArrayList();
    FilteredList<Task> filteredList = new FilteredList<>(tasks, t -> true);



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        completedColumn.setCellValueFactory(new PropertyValueFactory<Task, Boolean>("completed"));
        completedColumn.setCellFactory( lambda -> new CheckBoxTableCell<>());

        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(event -> {
            Task task = event.getRowValue();
            task.setDescription(event.getNewValue());
        });

        //sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(filteredList);

        Callback cellDateFactory = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn col) {
                return new DatePickerCell(col);
            }
        };

        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        dueDateColumn.setCellFactory(cellDateFactory);
        dueDateColumn.setEditable(true);
        dueDateColumn.setOnEditCommit(event -> {
            Task task = event.getRowValue();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            task.setDueDate(sdf.format(event.getNewValue()));
        });
    }
}

class DatePickerCell extends  TableCell<Task, String> {
    private DatePicker datePicker;
    private TableColumn column;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public DatePickerCell(TableColumn column) {
        // instantiate new DatePicker
        // edit setOnAction behavior
        // override methods to update date cell appropriately
        // reflect date cell with model date variable

        super();
        this.column = column;
        this.datePicker = new DatePicker();
        this.datePicker.setOnAction(e -> {


            if (this.datePicker.getValue() != null) {
                commitEdit(this.datePicker.getValue().toString());
                updateItem(this.datePicker.getValue().toString(), isEmpty());
                this.datePicker.getEditor().setText(this.datePicker.getValue().toString());
                if (getTableRow().getIndex() != -1) {
                    getTableView().getItems().get(getTableRow().getIndex()).setDueDate(this.datePicker.getValue().toString());
                }

            }


        });

        setGraphic(this.datePicker);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setEditable(true);
    }
    @Override
    public void startEdit() {
        super.startEdit();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }
    @Override
    protected void updateItem(String date, boolean empty) {
        super.updateItem(date, empty);
        this.datePicker.setVisible(!empty);

        if (date != null && !date.isEmpty()) {
            this.datePicker.setValue(LocalDate.parse(date));
            this.datePicker.getEditor().setText(date);
        }

    }

}
