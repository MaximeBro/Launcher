package fr.universecorp.launcher;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("javafx.application.Application");
            Application.launch(Launcher.class, args);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
