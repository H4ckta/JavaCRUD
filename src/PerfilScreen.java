import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PerfilScreen {
    public static void showPopup(JFrame parentFrame, String loggedUserEmail) {
        JDialog dialog = new JDialog(parentFrame, "Atualizar Perfil", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(parentFrame);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String loggedUserName = fetchUserName(loggedUserEmail);

        JLabel nameLabel = new JLabel("Nome:");
        JTextField nameField = new JTextField(loggedUserName, 20);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(loggedUserEmail, 20);

        JLabel passwordLabel = new JLabel("Senha:");
        JPasswordField passwordField = new JPasswordField(20);

        JButton saveButton = new JButton("Salvar");
        JButton cancelButton = new JButton("Cancelar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(nameLabel, gbc);

        gbc.gridx = 1;
        dialog.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(emailLabel, gbc);

        gbc.gridx = 1;
        dialog.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(passwordLabel, gbc);

        gbc.gridx = 1;
        dialog.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            String newName = nameField.getText().trim();
            String newEmail = emailField.getText().trim();
            String newPassword = new String(passwordField.getPassword()).trim();

            if (newName.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (updateUserProfile(loggedUserEmail, newName, newEmail, newPassword)) {
                JOptionPane.showMessageDialog(dialog, "Perfil atualizado com sucesso!");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Erro ao atualizar o perfil.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private static String fetchUserName(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[1].equals(email)) {
                    return parts[0]; // Retorna o nome do usuário
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar o nome do usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return "Usuário"; // Nome padrão caso o email não seja encontrado
    }

    private static boolean updateUserProfile(String loggedUserEmail, String newName, String newEmail, String newPassword) {
        List<String[]> users = new ArrayList<>();
        boolean isUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    if (parts[1].equals(loggedUserEmail)) {
                        parts[0] = newName;
                        parts[1] = newEmail;
                        parts[2] = newPassword;
                        isUpdated = true;
                    }
                    users.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (isUpdated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
                for (String[] user : users) {
                    writer.write(String.join(",", user));
                    writer.newLine();
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }
}
