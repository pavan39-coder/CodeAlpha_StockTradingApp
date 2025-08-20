import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.*;

class Stock {
    String name;
    double price;

    Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();

    void buy(String stockName, int quantity) {
        holdings.put(stockName, holdings.getOrDefault(stockName, 0) + quantity);
    }

    void sell(String stockName, int quantity) {
        if (holdings.containsKey(stockName) && holdings.get(stockName) >= quantity) {
            holdings.put(stockName, holdings.get(stockName) - quantity);
        } else {
            JOptionPane.showMessageDialog(null, "Not enough shares to sell!");
        }
    }

    String display() {
        if (holdings.isEmpty()) return "Portfolio is empty.";
        StringBuilder sb = new StringBuilder("Your Portfolio:\n");
        for (var entry : holdings.entrySet()) {
            sb.append(entry.getKey()).append(" -> ")
              .append(entry.getValue()).append(" shares\n");
        }
        return sb.toString();
    }
}

public class StockTradingApp extends JFrame {
    private JTextArea marketArea, portfolioArea;
    private JTextField stockField, qtyField;
    private Portfolio portfolio;
    private List<Stock> market;

    public StockTradingApp() {
        setTitle("Stock Trading Platform");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Market Data
        marketArea = new JTextArea(5, 30);
        marketArea.setEditable(false);
        portfolioArea = new JTextArea(5, 30);
        portfolioArea.setEditable(false);

        JPanel topPanel = new JPanel(new GridLayout(2,1));
        topPanel.add(new JScrollPane(marketArea));
        topPanel.add(new JScrollPane(portfolioArea));

        add(topPanel, BorderLayout.CENTER);

        // Buy/Sell Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Stock:"));
        stockField = new JTextField(10);
        bottomPanel.add(stockField);

        bottomPanel.add(new JLabel("Quantity:"));
        qtyField = new JTextField(5);
        bottomPanel.add(qtyField);

        JButton buyBtn = new JButton("Buy");
        JButton sellBtn = new JButton("Sell");
        bottomPanel.add(buyBtn);
        bottomPanel.add(sellBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // Market + Portfolio
        market = new ArrayList<>();
        market.add(new Stock("Apple", 150));
        market.add(new Stock("Google", 2800));
        market.add(new Stock("Tesla", 700));

        portfolio = new Portfolio();
        updateMarket();
        updatePortfolio();

        // Button Actions
        buyBtn.addActionListener(e -> buyStock());
        sellBtn.addActionListener(e-> sellStock());
    }

    private void updateMarket() {
        StringBuilder sb = new StringBuilder("Market Stocks:\n");
        for (Stock s : market) {
            sb.append(s.name).append(" -> $").append(s.price).append("\n");
        }
        marketArea.setText(sb.toString());
    }

    private void updatePortfolio() {
        portfolioArea.setText(portfolio.display());
    }

    private void buyStock() {
        String stock = stockField.getText();
        int qty;
        try {
            qty = Integer.parseInt(qtyField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid quantity!");
            return;
        }
        portfolio.buy(stock, qty);
        updatePortfolio();
    }

    private void sellStock() {
        String stock = stockField.getText();
        int qty;
        try {
            qty = Integer.parseInt(qtyField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid quantity!");
            return;
        }
        portfolio.sell(stock, qty);
        updatePortfolio();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StockTradingApp().setVisible(true);
        });
    }
}
