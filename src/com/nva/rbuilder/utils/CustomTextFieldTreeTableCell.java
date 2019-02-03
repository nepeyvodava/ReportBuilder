package com.nva.rbuilder.utils;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public class CustomTextFieldTreeTableCell<S, T> extends TreeTableCell<S, T> {

    static final Text helper = new Text();
    static final String DEFAULT_TEXT = helper.getText();

    /***************************************************************************
     *                                                                         *
     * Static cell factories                                                   *
     *                                                                         *
     **************************************************************************/

    /**
     * Provides a {@link TextField} that allows editing of the cell content when
     * the cell is double-clicked, or when
     * {@link TreeTableView#edit(int, TreeTableColumn)} is called.
     * This method will only  work on {@link TreeTableColumn} instances which are of
     * type String.
     *
     * @return A {@link Callback} that can be inserted into the
     * {@link TreeTableColumn#cellFactoryProperty() cell factory property} of a
     * TreeTableColumn, that enables textual editing of the content.
     */
    public static <S> Callback<TreeTableColumn<S, String>, TreeTableCell<S, String>> forTreeTableColumn() {
        return forTreeTableColumn(new DefaultStringConverter());
    }

    /**
     * Provides a {@link TextField} that allows editing of the cell content when
     * the cell is double-clicked, or when
     * {@link TreeTableView#edit(int, TreeTableColumn) } is called.
     * This method will work  on any {@link TreeTableColumn} instance, regardless of
     * its generic type. However, to enable this, a {@link StringConverter} must
     * be provided that will convert the given String (from what the user typed
     * in) into an instance of type T. This item will then be passed along to the
     * {@link TreeTableColumn#onEditCommitProperty()} callback.
     *
     * @param converter A {@link StringConverter} that can convert the given String
     *                  (from what the user typed in) into an instance of type T.
     * @return A {@link Callback} that can be inserted into the
     * {@link TreeTableColumn#cellFactoryProperty() cell factory property} of a
     * TreeTableColumn, that enables textual editing of the content.
     */
    public static <S, T> Callback<TreeTableColumn<S, T>, TreeTableCell<S, T>> forTreeTableColumn(
            final StringConverter<T> converter) {
        return list -> new CustomTextFieldTreeTableCell<>(converter);
    }


    /***************************************************************************
     *                                                                         *
     * Fields                                                                  *
     *                                                                         *
     **************************************************************************/

    private TextField textField;


    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    /**
     * Creates a default TextFieldTreeTableCell with a null converter. Without a
     * {@link StringConverter} specified, this cell will not be able to accept
     * input from the TextField (as it will not know how to convert this back
     * to the domain object). It is therefore strongly encouraged to not use
     * this constructor unless you intend to set the converter separately.
     */
    public CustomTextFieldTreeTableCell() {
        this(null);
    }

    /**
     * Creates a TextFieldTreeTableCell that provides a {@link TextField} when put
     * into editing mode that allows editing of the cell content. This method
     * will work on any TreeTableColumn instance, regardless of its generic type.
     * However, to enable this, a {@link StringConverter} must be provided that
     * will convert the given String (from what the user typed in) into an
     * instance of type T. This item will then be passed along to the
     * {@link TreeTableColumn#onEditCommitProperty()} callback.
     *
     * @param converter A {@link StringConverter converter} that can convert
     *                  the given String (from what the user typed in) into an instance of
     *                  type T.
     */
    public CustomTextFieldTreeTableCell(StringConverter<T> converter) {
        this.getStyleClass().add("text-field-tree-table-cell");
        setConverter(converter);
    }


    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/

    // --- converter
    private ObjectProperty<StringConverter<T>> converter =
            new SimpleObjectProperty<StringConverter<T>>(this, "converter");

    /**
     * The {@link StringConverter} property.
     */
    public final ObjectProperty<StringConverter<T>> converterProperty() {
        return converter;
    }

    /**
     * Sets the {@link StringConverter} to be used in this cell.
     */
    public final void setConverter(StringConverter<T> value) {
        converterProperty().set(value);
    }

    /**
     * Returns the {@link StringConverter} used in this cell.
     */
    public final StringConverter<T> getConverter() {
        return converterProperty().get();
    }


    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            CellUtils.startEdit(this, getConverter(), null, null, textField);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText((String) getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnAction(evt -> { // enable ENTER commit
            commitEdit((T) textField.getText());
        });

//        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        ChangeListener<? super Boolean> changeListener = (observable, oldSelection, newSelection) ->
        {
             if (! newSelection) {
                commitEdit((T) textField.getText());
             }
        };

        textField.focusedProperty().addListener(changeListener);

        textField.setOnKeyPressed((ke) -> {
            if (ke.getCode().equals(KeyCode.ESCAPE)) {
                textField.focusedProperty().removeListener(changeListener);
                cancelEdit();
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }


    @Override
    public void commitEdit(T item) {
        if (isEditing()) {
            super.commitEdit(item);
        } else {
            final TreeTableView<S> table = getTreeTableView();
            if (table != null) {
                TreeTablePosition<S,T> position = new TreeTablePosition(getTreeTableView(), getTreeTableRow().getIndex(), getTableColumn());
                TreeTableColumn.CellEditEvent<S,T> editEvent = new TreeTableColumn.CellEditEvent<S, T>(table, position, TreeTableColumn.<S, T>editCommitEvent(), item);
                Event.fireEvent(getTableColumn(), editEvent);
            }
            updateItem(item, false);
            if (table != null) {
                table.edit(-1, null);
            }

        }
    }

}
