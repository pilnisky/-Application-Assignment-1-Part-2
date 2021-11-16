/*
 *  UCF COP3330 Fall 2021 Assignment 1 part 2 Solution
 *  Copyright 2021 Palmer Ilnisky 
 */

package ucf.assignments;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.util.Date;

public class Task {
    private String filter;
    private String description;
    private BooleanProperty completed;

    private String dueDate;

    public Task() {
        // initialize task values
        this.description = "click to edit";
        this.completed = new SimpleBooleanProperty();
        this.dueDate = null;
        this.filter = "all";
    }

    public Task(String description, Boolean completed, String dueDate) {
        // initialize task values
        this.description = description;
        this.completed = new SimpleBooleanProperty(completed);
        this.dueDate = dueDate;
        this.filter = "all";
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public final Boolean getCompleted(){return completed.get();}

    public final void setCompleted(Boolean value){completed.set(value);}

    public BooleanProperty completedProperty() {return completed;}

}
