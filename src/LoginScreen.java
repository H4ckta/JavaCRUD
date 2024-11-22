import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class LoginScreen {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("LOGIN");
        title.setBounds(200, 50, 200, 40);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(title);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(150, 120, 100, 30);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(250, 120, 200, 30);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        frame.add(emailField);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setBounds(150, 170, 100, 30);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(250, 170, 200, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        frame.add(passwordField);

        JButton loginButton = new JButton("Logar");
        loginButton.setBounds(250, 230, 120, 40);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(100, 149, 237));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        frame.add(loginButton);

        JLabel registerLink = new JLabel("<html><u>Registrar-se</u></html>");
        registerLink.setForeground(new Color(70, 130, 180));
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLink.setBounds(250, 290, 150, 30);
        frame.add(registerLink);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (validateLogin(email, password)) {
                JOptionPane.showMessageDialog(frame, "Login bem-sucedido!");
                frame.dispose();
                new MainScreen(email); // Chama a tela principal com o email do usuário logado
            } else {
                JOptionPane.showMessageDialog(frame, "Email ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();
                RegisterScreen.main(null); // Chama a tela de registro (supondo que ela exista)
            }
        });

        frame.setVisible(true);
    }

    // Validação de login
    private static boolean validateLogin(String email, String password) {
        try (Scanner scanner = new Scanner(new File("users.txt"))) {
            while (scanner.hasNextLine()) {
                String[] userData = scanner.nextLine().split(",");
                if (userData.length >= 3 && userData[1].equals(email) && userData[2].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
