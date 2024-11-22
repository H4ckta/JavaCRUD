import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class RegisterScreen {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Registrar");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("REGISTRAR");
        title.setBounds(200, 50, 200, 40);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(title);

        JLabel nameLabel = new JLabel("Nome:");
        nameLabel.setBounds(150, 120, 100, 30);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(250, 120, 200, 30);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(150, 170, 100, 30);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(250, 170, 200, 30);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(emailField);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setBounds(150, 220, 100, 30);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(250, 220, 200, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(passwordField);

        JButton registerButton = new JButton("Registrar");
        registerButton.setBounds(250, 280, 120, 40);
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setBackground(new Color(60, 179, 113));
        registerButton.setForeground(Color.WHITE);
        frame.add(registerButton);

        JLabel loginLink = new JLabel("<html><u>Voltar para Login</u></html>");
        loginLink.setForeground(new Color(70, 130, 180));
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.setBounds(250, 340, 150, 30);
        frame.add(loginLink);

        registerButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (isEmailRegistered(email)) {
                JOptionPane.showMessageDialog(frame, "Este email já está registrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (registerUser(name, email, password)) {
                JOptionPane.showMessageDialog(frame, "Usuário registrado com sucesso!");
                frame.dispose();
                LoginScreen.main(null); // Voltar ao login após registro
            } else {
                JOptionPane.showMessageDialog(frame, "Erro ao registrar usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();
                LoginScreen.main(null);
            }
        });

        frame.setVisible(true);
    }

    // Verifica se o e-mail já está registrado
    private static boolean isEmailRegistered(String email) {
        try (Scanner scanner = new Scanner(new File("users.txt"))) {
            while (scanner.hasNextLine()) {
                String[] userData = scanner.nextLine().split(",");
                if (userData.length >= 2 && userData[1].equals(email)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            // Se o arquivo não existir, retorna false (nenhum usuário registrado ainda)
        }
        return false;
    }

    // Registro de usuário (inclui nome)
    private static boolean registerUser(String name, String email, String password) {
        try (FileWriter writer = new FileWriter("users.txt", true)) {
            writer.write(name + "," + email + "," + password + "\n");
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
