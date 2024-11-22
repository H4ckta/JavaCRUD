import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainScreen {
    private String loggedUserEmail;
    private String loggedUserName;

    public MainScreen(String loggedUserEmail) {
        this.loggedUserEmail = loggedUserEmail;
        this.loggedUserName = fetchUserName(loggedUserEmail);
        createUI();
    }

    private void createUI() {
        JFrame frame = new JFrame("Tela Principal");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(245, 245, 245));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel registrarLink = createLink("Registrar");
        JLabel visualizarLink = createLink("Visualizar");
        JLabel atualizarLink = createLink("Atualizar");
        JLabel excluirLink = createLink("Excluir");
        JLabel perfilLink = createLink("Perfil");

        JButton logoutButton = new JButton("Sair");
        logoutButton.setFocusPainted(false);
        logoutButton.setBackground(new Color(255, 69, 0));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        topPanel.add(registrarLink);
        topPanel.add(visualizarLink);
        topPanel.add(atualizarLink);
        topPanel.add(excluirLink);
        topPanel.add(perfilLink);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(logoutButton);

        frame.add(topPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Bem-vindo, " + loggedUserName, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);
        frame.add(contentPanel, BorderLayout.CENTER);

        registrarLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                RegistrarScreen.showPopup(frame);
            }
        });

        visualizarLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                VisualizarScreen.showPopup(frame);
            }
        });

        atualizarLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                AtualizarScreen.showPopup(frame); // Chama o popup de atualização
            }
        });
        
        

        excluirLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                ExcluirScreen.showPopup(frame);
            }
        });

        perfilLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                PerfilScreen.showPopup(frame, loggedUserEmail);
            }
        });

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Deseja realmente sair?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                LoginScreen.main(null);
            }
        });

        frame.setVisible(true);
    }

    private JLabel createLink(String text) {
        JLabel link = new JLabel("<html><u>" + text + "</u></html>");
        link.setFont(new Font("Arial", Font.BOLD, 14));
        link.setForeground(new Color(0, 102, 204));
        link.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return link;
    }

    private String fetchUserName(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[1].equals(email)) {
                    return parts[0];
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar o nome do usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return "Usuário";
    }
}
