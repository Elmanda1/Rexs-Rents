import javax.swing.*;
import java.awt.*;
import java.util.List;
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
import org.jfree.chart.ui.RectangleInsets;  // Tambahkan import ini

public class StatistikPanel {
    public static JPanel create() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        List<String> top4Mobil = Mobil.getTopMobilByPenyewaan(4);
        List<String> top6Mobil = Mobil.getTopMobilByPenyewaan(6);
        List<String> top4Pelanggan = Pelanggan.getTopPelanggan(4);

        // --- LEFT PANEL ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int rowNumber = 1;
        for (String entry : top6Mobil) {
            String[] parts = entry.split(",");
            if (parts.length == 2) {
                String namaMobil = parts[0].trim();
                int jumlah = Integer.parseInt(parts[1].trim());
                dataset.addValue(jumlah, "", namaMobil);
            }
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Statistik",
                "Model Mobil",
                "Jumlah Penyewaan",
                dataset,
                PlotOrientation.HORIZONTAL,
                false, true, false);

        barChart.getTitle().setPaint(new Color(51, 40, 255));

        // Set bar color (orange)
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255, 140, 0));

        // Set axis label color (blue)
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelPaint(new Color(51, 100, 255)); // "Model Mobil" label
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
        domainAxis.setTickLabelFont(new Font("poppinsFont", Font.PLAIN, 12));
        domainAxis.setTickLabelPaint(new Color(51, 40, 255)); // navy for tick labels

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelPaint(new Color(51, 100, 255)); // "Jumlah Penyewaan" label
        rangeAxis.setTickLabelFont(new Font("poppinsFont", Font.PLAIN, 12));
        rangeAxis.setTickLabelPaint(new Color(51, 40, 255)); // navy for tick labels

        // Konfigurasi plot dan chart background
        plot.setBackgroundPaint(new Color(220, 230, 250)); // Background chart biru muda
        plot.setRangeGridlinePaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.WHITE);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setBackground(Color.WHITE); // Background panel putih
        barChart.setBackgroundPaint(Color.WHITE); // Background chart putih

        // Set warna domain dan range plot ke putih untuk area di luar grafik
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        plot.setOutlinePaint(Color.WHITE);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));

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
        topRightPanel.setBackground(Color.WHITE);
        rightGbc.weighty = 0.5;
        rightGbc.gridy = 0;
        rightPanel.add(topRightPanel, rightGbc);

        JPanel bottomRightPanel = new JPanel(new GridBagLayout());
        bottomRightPanel.setBackground(Color.WHITE);
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
        rowNumber = 1;
        for (String entry : top4Mobil) {
            String[] parts = entry.split(",");
            if (parts.length == 2) {
                String namaMobil = parts[0].trim();
                int jumlah = Integer.parseInt(parts[1].trim());
                String[] merkModel = namaMobil.split(" ", 2);
                String merk = merkModel.length > 0 ? merkModel[0] : "";
                String model = merkModel.length > 1 ? merkModel[1] : "";
                topRightPanel.add(createStatistikRow(rowNumber, merk, model, jumlah), rowGbc);
                rowGbc.gridy++;
                rowNumber++;
            }
        }

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
        int pelangganRowNumber = 1;
        for (String entry : top4Pelanggan) {
            String[] parts = entry.split("\\|\\|");
            if (parts.length == 2) {
                String nama = parts[0].trim();
                int jumlah = Integer.parseInt(parts[1].trim());
                bottomRightPanel.add(createStatistikRow(pelangganRowNumber, nama, "", jumlah), bottomRowGbc);
                bottomRowGbc.gridy++;
                pelangganRowNumber++;
            }
        }

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
