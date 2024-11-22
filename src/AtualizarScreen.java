import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AtualizarScreen {
    public static void showPopup(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Atualizar Produto", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(parentFrame);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Carregar produtos do arquivo
        List<String[]> products = loadProducts();
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Nenhum produto encontrado para atualizar.", "Informação", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Array para exibir os nomes dos produtos no JComboBox
        String[] productNames = products.stream().map(p -> p[0]).toArray(String[]::new);

        JLabel selectLabel = new JLabel("Selecione o Produto:");
        JComboBox<String> productDropdown = new JComboBox<>(productNames);

        JLabel nameLabel = new JLabel("Novo Nome:");
        JTextField nameField = new JTextField(20);

        JLabel quantityLabel = new JLabel("Nova Quantidade:");
        JTextField quantityField = new JTextField(20);

        JButton updateButton = new JButton("Atualizar");
        JButton cancelButton = new JButton("Cancelar");

        // Adicionando componentes ao popup
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(selectLabel, gbc);

        gbc.gridx = 1;
        dialog.add(productDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(nameLabel, gbc);

        gbc.gridx = 1;
        dialog.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(quantityLabel, gbc);

        gbc.gridx = 1;
        dialog.add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, gbc);

        // Ação do botão "Atualizar"
        updateButton.addActionListener(e -> {
            int selectedIndex = productDropdown.getSelectedIndex();
            if (selectedIndex < 0) {
                JOptionPane.showMessageDialog(dialog, "Selecione um produto válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String newName = nameField.getText().trim();
            String quantityText = quantityField.getText().trim();

            if (newName.isEmpty() || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int newQuantity = Integer.parseInt(quantityText);
                if (newQuantity <= 0) {
                    JOptionPane.showMessageDialog(dialog, "A quantidade deve ser maior que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Atualizar os dados do produto selecionado
                products.get(selectedIndex)[0] = newName;
                products.get(selectedIndex)[1] = String.valueOf(newQuantity);

                // Salvar alterações no arquivo
                if (saveAllProducts(products)) {
                    JOptionPane.showMessageDialog(dialog, "Produto atualizado com sucesso!");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Erro ao atualizar o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Quantidade deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ação do botão "Cancelar"
        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    // Método para carregar os produtos do arquivo
    private static List<String[]> loadProducts() {
        List<String[]> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("produto.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    products.add(parts);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar os produtos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return products;
    }

    // Método para salvar os produtos atualizados no arquivo
    private static boolean saveAllProducts(List<String[]> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("produto.txt"))) {
            for (String[] product : products) {
                writer.write(product[0] + "," + product[1]);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
