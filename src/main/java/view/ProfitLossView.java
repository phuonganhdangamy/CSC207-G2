package view;

import interface_adapter.profit_loss.ProfitLossController;

import javax.swing.*;
import java.awt.*;

public class ProfitLossView extends JPanel {
    private final String viewname = "ProfitLossView";
    private ProfitLossController profitLossController;
    private JLabel profitLossLabel = new JLabel("Profit Loss");

    public ProfitLossView(ProfitLossController profitLossController) {

        this.profitLossController = profitLossController;
        profitLossLabel = new JLabel("Total Profit/Loss: +XX.XX%");
        profitLossLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profitLossLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        summaryPanel.add(profitLossLabel, BorderLayout.CENTER);
    }
}
