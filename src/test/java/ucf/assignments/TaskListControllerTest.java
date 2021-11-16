/*
 *  UCF COP3330 fall 2021 Assignment 4 Solution
 *  Copyright 2021 Palmer Ilnisky
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskListControllerTest {

    @Test
    void createTask() {
        // create new TaskListController
        // assert that creating a task brings the initial array to a size of 1

        TaskListController controller = new TaskListController();
        assertEquals(1, controller.createTask().size());
    }

    @Test
    void showIncompleteTasks() {
        // create new TaskListController
        // initialize the list view to show all items
        // call the showIncompleteTasks function
        // assert that displayed lists = only the incomplete tasks
        TaskListController controller = new TaskListController();
        assertEquals("incomplete", controller.showIncompleteTasks());
    }

    @Test
    void showCompletedTasks() {
        // create new TaskListController
        // initialize the list view to show all tasks
        // call the showCompletedTasks function
        // assert that displayed lists = only the complete tasks
        TaskListController controller = new TaskListController();
        assertEquals("complete", controller.showCompleteTasks());
    }

    @Test
    void showAllTasks() {
        // create new TaskListController
        // initialize the list view to show only the incomplete/complete tasks
        // call the showAllTasks function
        // assert that displayed lists = shows all the items.
        TaskListController controller = new TaskListController();
        assertEquals("all", controller.showAllTasks());
    }

    @Test
    void ascendSort() {
        // create new ToDoList object
        // initialize the ToDoList with x number of tasks in ascending order
        // create a test ToDoList
        // initialize test ToDoList with same tasks in any order except ascending order
        // call the ascendSort function on the test ToDoList
        // assert that the function return at index 0 = the latest date
        TaskListController controller = new TaskListController();
        ObservableList<Task> taskList = FXCollections.observableArrayList(
                new Task("desc 1", true, "2021-06-02"),
                new Task("desc 1", true, "2021-06-01"),
                new Task("desc 1", true, "2021-06-03")
        );
        controller.setTasks(taskList);
        ObservableList<Task> sortedList = controller.ascendSort();
        Task task = sortedList.get(0);
        assertEquals("2021-06-01", task.getDueDate());
    }

    @Test
    void descendSort() {
        // create new ToDoList object
        // initialize the ToDoList with x number of tasks in descending order
        // create a test ToDoList
        // initialize test ToDoList with same tasks in any order except descending order
        // call the descendSort function on the test ToDoList
        // assert that the function return at index 0 = the earliest date
        TaskListController controller = new TaskListController();
        ObservableList<Task> taskList = FXCollections.observableArrayList(
                new Task("desc 1", true, "2021-06-02"),
                new Task("desc 1", true, "2021-06-01"),
                new Task("desc 1", true, "2021-06-03")
        );
        controller.setTasks(taskList);
        ObservableList<Task> sortedList = controller.descendSort();
        Task task = sortedList.get(0);
        assertEquals("2021-06-03", task.getDueDate());
    }

    @Test
    void saveListButtonClicked() {
        // create new File object, firstFile
        // write 'test' to file
        // save the file
        // create a second File object, secondFile
        // assert that saveList(secondFile, 'test') == firstFile
    }
}