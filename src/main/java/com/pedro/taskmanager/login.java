package com.pedro.taskmanager;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class login extends JFrame {

    public login() {
        super("Task Manager - Login");

        // Tema moderno
        FlatDarkLaf.setup();

        // Config da janela
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));
        add(panel, BorderLayout.CENTER);

        // Título
        JLabel label = new JLabel("Task Manager");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Segoe UI", Font.BOLD, 26));
        label.setForeground(Color.WHITE);
        panel.add(label);

        panel.add(Box.createVerticalStrut(40));

        // Campo usuário
        JTextField usernameField = new JTextField();
        styleField(usernameField, "Usuário");
        panel.add(usernameField);

        panel.add(Box.createVerticalStrut(20));

        // Campo senha
        JPasswordField passwordField = new JPasswordField();
        styleField(passwordField, "Senha");
        panel.add(passwordField);

        panel.add(Box.createVerticalStrut(30));

        // Botão Login com glass + animação
        JButton loginButton = glassButton("Entrar");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(loginButton);
        btnLogin.addActionListener(e -> {
    // … validação do login …

    // Abre janela principal
    new TaskManagerWindow();
    
    // Fecha a janela de login
    frame.dispose();
});




        setVisible(true);
    }

    private void styleField(JTextField field, String placeholder) {
        field.putClientProperty("JTextField.placeholderText", placeholder);
        field.setMaximumSize(new Dimension(300, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    }

    private JButton glassButton(String text) {
        JButton btn = new JButton(text) {

            float alpha = 0.85f; // transparência do efeito glass

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Glass translucido
                g2.setColor(new Color(255,255,255,(int)(alpha * 40)));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                // Brilho superior
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(255,255,255,80),
                        0, getHeight(), new Color(255,255,255,0)
                );
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
        btn.setBorder(new EmptyBorder(12, 20, 12, 20));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Animação hover
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

    private void animateAlpha(JButton btn, float targetAlpha) {
        Timer timer = new Timer(15, null);
        timer.addActionListener(e -> {
            try {
                java.lang.reflect.Field f = btn.getClass().getDeclaredField("alpha");
                f.setAccessible(true);
                float alpha = f.getFloat(btn);

                if (Math.abs(alpha - targetAlpha) < 0.02f) {
                    f.setFloat(btn, targetAlpha);
                    btn.repaint();
                    timer.stop();
                } else {
                    f.setFloat(btn, alpha + (targetAlpha > alpha ? 0.03f : -0.03f));
                    btn.repaint();
                }

            } catch (Exception ignored) {}
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new login());

    }
    
}

