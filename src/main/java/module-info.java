module com.darkin.electronicordersystem {
    //extra needed for the fxml and graphics
    requires javafx.graphics;

    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    //needed for sql connection
    requires java.sql;
    requires mysql.connector.j;
    requires java.sql.rowset;
    requires java.desktop;
//    requires mysql.connector.java;

    opens com.darkin.electronicordersystem to javafx.fxml;
    exports com.darkin.electronicordersystem;
    exports com.darkin.electronicordersystem.models;
    opens com.darkin.electronicordersystem.models to javafx.fxml;
}