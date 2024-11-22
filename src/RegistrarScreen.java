import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class RegistrarScreen {
    public static void showPopup(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Registrar Produto", true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(parentFrame);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Nome do Produto:");
        JTextField nameField = new JTextField(20);

        JLabel quantityLabel = new JLabel("Quantidade:");
        JTextField quantityField = new JTextField(20);

        JButton saveButton = new JButton("Salvar");
        JButton cancelButton = new JButton("Cancelar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(nameLabel, gbc);

        gbc.gridx = 1;
        dialog.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(quantityLabel, gbc);

        gbc.gridx = 1;
        dialog.add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            String productName = nameField.getText().trim();
            String quantityText = quantityField.getText().trim();

            if (productName.isEmpty() || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(dialog, "A quantidade deve ser maior que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (saveProduct(productName, quantity)) {
                    JOptionPane.showMessageDialog(dialog, "Produto registrado com sucesso!");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Erro ao salvar o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Quantidade deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private static boolean saveProduct(String name, int quantity) {
        try (FileWriter writer = new FileWriter("produto.txt", true)) {
            writer.write(name + "," + quantity + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
