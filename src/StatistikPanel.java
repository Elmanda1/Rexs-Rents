import javax.swing.*;
import java.awt.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class StatistikPanel {
    public static JPanel create() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // --- LEFT PANEL ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(255, 204, 0));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(150, "", "Lamborghini stargazer");
        dataset.addValue(120, "", "BMW X3");
        dataset.addValue(100, "", "Honda Civic");
        dataset.addValue(80, "", "Toyota Fortuner");
        dataset.addValue(60, "", "Hyundai IONIQ");
        dataset.addValue(40, "", "Toyota Alphard");

        JFreeChart barChart = ChartFactory.createBarChart(
                "Statistik",
                "Model Mobil",
                "Jumlah Penyewaan",
                dataset,
                PlotOrientation.HORIZONTAL,
                false, true, false);

        // Set chart title color (navy)
        barChart.getTitle().setPaint(new Color(35, 47, 89));

        // Set bar color (orange)
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255, 140, 0));

        // Set axis label color (blue)
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelPaint(new Color(51, 100, 255)); // "Model Mobil" label
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
        domainAxis.setTickLabelFont(new Font("poppinsFont", Font.PLAIN, 12));
        domainAxis.setTickLabelPaint(new Color(35, 47, 89)); // navy for tick labels

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelPaint(new Color(51, 100, 255)); // "Jumlah Penyewaan" label
        rangeAxis.setTickLabelFont(new Font("poppinsFont", Font.PLAIN, 12));
        rangeAxis.setTickLabelPaint(new Color(35, 47, 89)); // navy for tick labels

        // DO NOT force chartPanel width
        ChartPanel chartPanel = new ChartPanel(barChart);
        leftPanel.add(chartPanel, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        mainPanel.add(leftPanel, gbc);

        // --- RIGHT PANEL ---
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE); // optional, set your own color

        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.weightx = 1.0;
        rightGbc.fill = GridBagConstraints.BOTH;

        JPanel topRightPanel = new JPanel(new GridBagLayout());
        topRightPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        rightGbc.weighty = 0.5;
        rightGbc.gridy = 0;
        rightPanel.add(topRightPanel, rightGbc);

        JPanel bottomRightPanel = new JPanel(new GridBagLayout());
        rightGbc.gridy = 1;
        rightPanel.add(bottomRightPanel, rightGbc);

        // Add right panel with 50% weight
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        mainPanel.add(rightPanel, gbc);

        JLabel topTitle = new JLabel("Mobil Dengan Penyewaan Terbanyak");
        topTitle.setFont(new Font("poppinsFont", Font.BOLD, 22));
        topTitle.setForeground(new Color(35, 47, 89));
        GridBagConstraints topTitleGbc = new GridBagConstraints();
        topTitleGbc.gridx = 0;
        topTitleGbc.gridy = 0;
        topTitleGbc.gridwidth = 1;
        topTitleGbc.insets = new Insets(0, 10, 0, 10);
        topTitleGbc.anchor = GridBagConstraints.WEST;
        topRightPanel.add(topTitle, topTitleGbc);

        // Statistik rows for topRightPanel, start from rowGbc.gridy = 1
        GridBagConstraints rowGbc = new GridBagConstraints();
        rowGbc.gridx = 0;
        rowGbc.fill = GridBagConstraints.HORIZONTAL;
        rowGbc.weightx = 1.0;
        rowGbc.insets = new Insets(10, 10, 10, 10);

        rowGbc.gridy = 1;
        topRightPanel.add(createStatistikRow(1, "Lamborghini", "stargazer", 150), rowGbc);
        rowGbc.gridy = 2;
        topRightPanel.add(createStatistikRow(2, "BMW", "X3", 120), rowGbc);
        rowGbc.gridy = 3;
        topRightPanel.add(createStatistikRow(3, "Honda", "Civic", 100), rowGbc);
        rowGbc.gridy = 4;
        topRightPanel.add(createStatistikRow(4, "Toyota", "Fortuner", 80), rowGbc);

        // --- Add this before adding statistik rows to bottomRightPanel ---
        JLabel bottomTitle = new JLabel("Pelanggan Dengan Penyewaan Terbanyak");
        bottomTitle.setFont(new Font("poppinsFont", Font.BOLD, 22));
        bottomTitle.setForeground(new Color(35, 47, 89));
        GridBagConstraints bottomTitleGbc = new GridBagConstraints();
        bottomTitleGbc.gridx = 0;
        bottomTitleGbc.gridy = 0;
        bottomTitleGbc.gridwidth = 1;
        bottomTitleGbc.insets = new Insets(0, 10, 0, 10);
        bottomTitleGbc.anchor = GridBagConstraints.WEST;
        bottomRightPanel.add(bottomTitle, bottomTitleGbc);

        // Statistik rows for bottomRightPanel, start from bottomRowGbc.gridy = 1
        GridBagConstraints bottomRowGbc = new GridBagConstraints();
        bottomRowGbc.gridx = 0;
        bottomRowGbc.fill = GridBagConstraints.HORIZONTAL;
        bottomRowGbc.weightx = 1.0;
        bottomRowGbc.insets = new Insets(10, 10, 10, 10);

        bottomRowGbc.gridy = 1;
        bottomRightPanel.add(createStatistikRow(1, "John", "Doe", 30), bottomRowGbc);
        bottomRowGbc.gridy = 2;
        bottomRightPanel.add(createStatistikRow(2, "Jane", "Smith", 25), bottomRowGbc);
        bottomRowGbc.gridy = 3;
        bottomRightPanel.add(createStatistikRow(3, "Bob", "Wilson", 20), bottomRowGbc);
        bottomRowGbc.gridy = 4;
        bottomRightPanel.add(createStatistikRow(4, "Alice", "Brown", 18), bottomRowGbc);

        return mainPanel;
    }

    private static JPanel createStatistikRow(int number, String merk, String model, int jumlahSewa) {
        JPanel rowPanel = Utility.createRoundedPanel(new GridBagLayout(), new Color(220, 230, 250));
        rowPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rowPanel.setPreferredSize(new Dimension(0, 60)); // Height only, width will stretch

        GridBagConstraints gbc = new GridBagConstraints();

        // Number label
        JLabel numberLabel = new JLabel(number + ".");
        numberLabel.setFont(new Font("poppinsFont", Font.BOLD, 22));
        numberLabel.setForeground(new Color(51, 100, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 0, 0, 15);
        rowPanel.add(numberLabel, gbc);

        // Merk & model label
        JLabel carLabel = new JLabel(merk + " " + model);
        carLabel.setFont(new Font("poppinsFont", Font.BOLD, 20));
        carLabel.setForeground(new Color(51, 40, 255));
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 0, 15);
        gbc.anchor = GridBagConstraints.WEST;
        rowPanel.add(carLabel, gbc);

        // Penyewaan label
        JLabel sewaLabel = new JLabel("Penyewaan: " + jumlahSewa + " kali");
        sewaLabel.setFont(new Font("poppinsFont", Font.PLAIN, 18));
        sewaLabel.setForeground(new Color(35, 47, 89));
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.EAST;
        rowPanel.add(sewaLabel, gbc);

        return rowPanel;
    }

}
