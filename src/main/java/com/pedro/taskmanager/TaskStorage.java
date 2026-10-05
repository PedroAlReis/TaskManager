package com.pedro.taskmanager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage {

    private static final String FILE_NAME = "tasks.dat";

    
    public static void saveTasks(List<Task> tasks) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(tasks);
            System.out.println("Tarefas salvas com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao salvar tarefas!");
        }
    }

    
    @SuppressWarnings("unchecked")
    public static List<Task> loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>(); 
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Task>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar tarefas!");
            return new ArrayList<>();
        }
    }
}
