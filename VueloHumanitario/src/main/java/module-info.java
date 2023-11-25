module VueloHumanitario {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.code.vuelohumanitario to javafx.fxml;
    exports com.code.vuelohumanitario;
}