package com.pedro.taskmanager;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskManagerWindow());
    }
}