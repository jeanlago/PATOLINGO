package Main;

import Database.BancoDeDados;
import Grafica.TelaInicial;
import java.sql.SQLException;
import javax.swing.*;

public class Main {
    public static BancoDeDados bancoDeDados;

    public static void main(String[] args) {
        try {
            bancoDeDados = new BancoDeDados();
            JFrame frame = new JFrame("OGNILOUD");
            frame.setSize(1280, 720);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);

            new TelaInicial(frame);

            frame.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage());
            System.exit(1);
        }
    }
}
