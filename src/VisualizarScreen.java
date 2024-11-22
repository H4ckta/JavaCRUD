import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VisualizarScreen {
    public static void showPopup(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Produtos Cadastrados", true);
        dialog.setSize(500, 300);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(parentFrame);

        String[] columnNames = { "Nome", "Quantidade" };
        List<String[]> data = loadProducts();
        String[][] tableData = data.toArray(new String[0][]);
        JTable table = new JTable(tableData, columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton, BorderLayout.SOUTH);

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
}
