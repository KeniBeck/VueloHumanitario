package com.code.vuelohumanitario;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Optional;
import java.util.Stack;

public class HumanitaryController {
    public TextField txt_nombrePasajero;
    public TableView<cls_pasajero> table_listaPasajeros;
    public TableColumn <cls_pasajero,String> ColName;
    public TableColumn<cls_pasajero,Integer> ColEdad;
    public TextField txt_desabordarEdad;

    private Stack<cls_pasajero> colaNinos = new Stack<>();
    private Stack<cls_pasajero> colaJovenes = new Stack<>();
    private Stack<cls_pasajero> colaAncianos = new Stack<>();


    public TextField txt_edadPasajero;
    public Button btn_RegistrarPasajero;

    public void OnRegistrarPasajero(ActionEvent actionEvent) {
        String nombre = txt_nombrePasajero.getText();
        String edadStr = txt_edadPasajero.getText();

        try {
            int edad = Integer.parseInt(edadStr);
            cls_pasajero pasajero = new cls_pasajero(nombre, edad);

            if (edad >= 2 && edad <= 12) {
                colaNinos.push(pasajero);
            } else if (edad >= 15 && edad <= 25) {
                colaJovenes.push(pasajero);
            } else if (edad >= 60 && edad <= 80) {
                colaAncianos.push(pasajero);
            } else {
                mostrarAlerta("Edad no válida para el abordaje.");
                return;
            }

            mostrarAlerta("Pasajero registrado: " + pasajero.getNombre() + ", Edad: " + pasajero.getEdad());
        } catch (NumberFormatException e) {
            mostrarAlerta("Por favor, ingresa una edad válida.");
        }
    }
    public void initialize() {
        // Configuración de las columnas del TableView
        ColName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        ColEdad.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEdad()).asObject());


        // Inicialización de las pilas (puedes cargar datos de prueba aquí si lo deseas)
    }
    private void mostrarTabla(Stack<cls_pasajero> cola) {
        ObservableList<cls_pasajero> pasajeros = FXCollections.observableArrayList(cola);
        table_listaPasajeros.setItems(pasajeros);
    }
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void OnListaBoys(MouseEvent mouseEvent) {
        mostrarTabla(colaNinos);
    }

    public void OnListaJovenes(MouseEvent mouseEvent) {
        mostrarTabla(colaJovenes);
    }

    public void OnListaAncianos(MouseEvent mouseEvent) {
        mostrarTabla(colaAncianos);
    }

    public void OnLimpiar(ActionEvent actionEvent) {
        txt_nombrePasajero.clear();
        txt_edadPasajero.clear();
        txt_nombrePasajero.requestFocus();
    }

    public void OnBajarPersona(ActionEvent actionEvent) {
        try {
            int edadDesabordar = Integer.parseInt(txt_desabordarEdad.getText());

            // Buscar el pasajero con la edad especificada en la tabla
            Optional<cls_pasajero> pasajeroDesabordar = table_listaPasajeros.getItems().stream()
                    .filter(pasajero -> pasajero.getEdad() == edadDesabordar)
                    .findFirst();

            if (pasajeroDesabordar.isPresent()) {
                // Eliminar el pasajero de la tabla
                table_listaPasajeros.getItems().remove(pasajeroDesabordar.get());
                mostrarAlerta("Pasajero desabordado exitosamente.");
            } else {
                mostrarAlerta("No se encontró un pasajero con la edad especificada.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Por favor, ingresa una edad válida para desabordar.");
        }
    }
    }


