package com.darkin.electronicordersystem.UIComponents;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.util.function.Consumer;

public class ActionButtonTableCell<S, T> extends TableCell<S, T> {
    private final Button actionButton;
    private final int imageW = 40;

    public ActionButtonTableCell(String label, Consumer<S> function) {
        this.actionButton = new Button(label);
        this.actionButton.setOnAction(e -> function.accept(getCurrentItem()));
        this.actionButton.setMaxWidth(Double.MAX_VALUE);
    }

    public ActionButtonTableCell(Image image, Consumer<S> function){
        this.actionButton = new Button();
        ImageView tmpImageView = new ImageView(image);
        tmpImageView.setPreserveRatio(true);
        tmpImageView.setFitWidth(imageW);
        this.actionButton.setGraphic(tmpImageView);
        this.actionButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.actionButton.setOnAction(e -> function.accept(getCurrentItem()));
        this.actionButton.setMaxWidth(imageW);
    }

    public S getCurrentItem() {
        // No need for a cast here:
        return getTableView().getItems().get(getIndex());
    }

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(String label, Consumer<S> function) {
        return param -> new ActionButtonTableCell<>(label, function);
    }
    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(Image image, Consumer<S> function) {
        return param -> new ActionButtonTableCell<>(image, function);
    }
    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(actionButton);
        }
    }
}