import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcluirScreen {
    public static void showPopup(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Excluir Produto", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(parentFrame);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        List<String[]> products = loadProducts();
        String[] productNames = products.stream().map(p -> p[0]).toArray(String[]::new);

        JLabel selectLabel = new JLabel("Selecione o Produto:");
        JComboBox<String> productDropdown = new JComboBox<>(productNames);

        JButton deleteButton = new JButton("Excluir");
        JButton cancelButton = new JButton("Cancelar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(selectLabel, gbc);

        gbc.gridx = 1;
        dialog.add(productDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, gbc);

        deleteButton.addActionListener(e -> {
            int selectedIndex = productDropdown.getSelectedIndex();
            if (selectedIndex < 0) {
                JOptionPane.showMessageDialog(dialog, "Selecione um produto válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            products.remove(selectedIndex);

            if (saveAllProducts(products)) {
                JOptionPane.showMessageDialog(dialog, "Produto excluído com sucesso!");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Erro ao excluir o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

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
