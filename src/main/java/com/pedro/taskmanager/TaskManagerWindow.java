package com.pedro.taskmanager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.formdev.flatlaf.FlatDarkLaf;
import java.util.List;
import java.util.ArrayList;

public class TaskManagerWindow extends JFrame {

    private DefaultListModel<String> taskListModel = new DefaultListModel<>();
    private JList<String> taskList = new JList<>(taskListModel);
    private java.util.List<Task> tasks = new java.util.ArrayList<>();

    public TaskManagerWindow() {
        super("Task Manager - Modern UI");

        FlatDarkLaf.setup();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // ===================== CARREGAR TAREFAS =====================
        tasks = TaskStorage.loadTasks();
        for (Task t : tasks) {
            taskListModel.addElement(t.toString());
        }

        // ===================== MENU LATERAL =====================
        JPanel sideMenu = new JPanel(new GridLayout(6, 1, 0, 10));
        sideMenu.setPreferredSize(new Dimension(230, 0));
        sideMenu.setBackground(new Color(25, 25, 25));
        sideMenu.setBorder(new EmptyBorder(30, 10, 30, 10));

        sideMenu.add(styledButton("Home"));
        sideMenu.add(styledButton("Tarefas"));
        sideMenu.add(styledButton("Estatísticas"));
        sideMenu.add(styledButton("Configurações"));

        // ===================== CABEÇALHO =====================
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(30, 30, 30));
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel header = new JLabel("Gerenciador de Tarefas - Modern UI");
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 26));
        topPanel.add(header);

        // ===================== PAINEL CENTRAL =====================
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(20, 20, 20));

        JPanel mainCard = roundedPanel();
        mainCard.setLayout(new BorderLayout());
        mainCard.setPreferredSize(new Dimension(900, 600)); // 🔥 PAINEL MAIOR
        mainCard.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel cardTitle = new JLabel("Suas tarefas");
        cardTitle.setForeground(Color.WHITE);
        cardTitle.setFont(new Font("Arial", Font.BOLD, 20));
        mainCard.add(cardTitle, BorderLayout.NORTH);

        // ===================== LISTA DE TAREFAS =====================
        taskList.setFont(new Font("Arial", Font.PLAIN, 17));
        taskList.setBackground(new Color(60, 60, 60));
        taskList.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(taskList);
        scroll.setBorder(null);
        mainCard.add(scroll, BorderLayout.CENTER);

        // ===================== CAMPO + BOTÃO =====================
        JPanel inputArea = new JPanel(new BorderLayout(10, 10));
        inputArea.setBackground(new Color(40, 40, 40));
        inputArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputArea.add(inputField, BorderLayout.CENTER);

        JButton addTaskButton = styledButton("Adicionar");
        inputArea.add(addTaskButton, BorderLayout.EAST);

        mainCard.add(inputArea, BorderLayout.SOUTH);

        // Ação do botão de adicionar tarefa
        inputField.addActionListener(e -> addTaskButton.doClick());

        addTaskButton.addActionListener(e -> {
            String text = inputField.getText().trim();
            if (!text.isEmpty()) {

                Task t = new Task(text);
                tasks.add(t);

                taskListModel.addElement(t.toString());
                inputField.setText("");

                TaskStorage.saveTasks(tasks);
            }
        });

        centerPanel.add(mainCard);

        // ===================== RODAPÉ =====================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(30, 30, 30));
        bottomPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel footer = new JLabel("© 2025 Pedro's Dev - All Rights Reserved");
        footer.setForeground(Color.WHITE);
        bottomPanel.add(footer);

        // ===================== ADD AO FRAME PRINCIPAL =====================
        add(topPanel, BorderLayout.NORTH);
        add(sideMenu, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ------------------------ PAINEL ARREDONDADO ------------------------
    private JPanel roundedPanel() {
        return new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(45, 45, 45));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);
                g2.dispose();
                super.paintComponent(g);
            }
        };
    }

    // ------------------------ BOTÃO ESTILIZADO + ANIMAÇÃO ------------------------
    private JButton styledButton(String text) {
        JButton btn = new JButton(text) {

            float alpha = 0.85f;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // fundo glass
                g2.setColor(new Color(255, 255, 255, (int)(alpha * 45)));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                // brilho
                GradientPaint gp = new GradientPaint(0, 0, new Color(255,255,255,60),
                                                     0, getHeight(), new Color(255,255,255,0));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight()/2, 25, 25);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setBorder(new EmptyBorder(12, 20, 12, 20));

        // animação hover
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                animateAlpha(btn, 1.0f);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                animateAlpha(btn, 0.85f);
            }
        });

        return btn;
    }

    private void animateAlpha(JButton btn, float target) {
        Timer timer = new Timer(15, null);
        timer.addActionListener(e -> {
            try {
                var field = btn.getClass().getDeclaredField("alpha");
                field.setAccessible(true);
                float a = field.getFloat(btn);
                if (Math.abs(a - target) > 0.01f) {
                    a += (target - a) * 0.15f;
                    field.setFloat(btn, a);
                    btn.repaint();
                } else {
                    timer.stop();
                }
            } catch (Exception ignored) {}
        });
        timer.start();
    }


    public static void main(String[] args) {
        new TaskManagerWindow();
    }
}
