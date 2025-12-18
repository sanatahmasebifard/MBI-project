import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
public class BMICalculator extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(66, 133, 244);
    private static final Color SECONDARY_COLOR = new Color(234, 67, 53);
    private static final Color SUCCESS_COLOR = new Color(52, 168, 83);
    private static final Color WARNING_COLOR = new Color(251, 188, 5);
    private static final Color DANGER_COLOR = new Color(210, 37, 22);
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JComboBox<String> genderComboBox;
    private JTextField heightField;
    private JTextField weightField;
    private JLabel resultLabel;
    private JLabel categoryLabel;
    private JPanel resultColorPanel;
    private Map<String, Color> themeColors;
    private String currentTheme = "آبی";

    public BMICalculator() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        initializeThemes();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        createWelcomePage();
        createCalculatorPage();
        add(cardPanel);
        setVisible(true);
    }

    private void initializeThemes() {
        themeColors = new HashMap<>();
        themeColors.put("آبی", PRIMARY_COLOR);
        themeColors.put("سبز", new Color(76, 175, 80));
        themeColors.put("بنفش", new Color(156, 39, 176));
        themeColors.put("نارنجی", new Color(255, 152, 0));
        themeColors.put("صورتی", new Color(233, 30, 99));
    }

    private void createWelcomePage() {
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(Color.WHITE);
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(800, 80));

        JLabel titleLabel = new JLabel("محاسبه‌گر شاخص توده بدنی (BMI)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        JLabel iconLabel = new JLabel("🏋️", SwingConstants.CENTER);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 100));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setText("به برنامه محاسبه‌گر شاخص توده بدنی خوش آمدید!\n\n");
        descriptionArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
        descriptionArea.setForeground(Color.BLACK);
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton startButton = new JButton("شروع محاسبه BMI");
        startButton.setFont(new Font("Tahoma", Font.BOLD, 18));
        startButton.setBackground(SUCCESS_COLOR);
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> cardLayout.show(cardPanel, "calculator"));
        contentPanel.add(iconLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(descriptionArea);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(startButton);
        welcomePanel.add(headerPanel, BorderLayout.NORTH);
        welcomePanel.add(contentPanel, BorderLayout.CENTER);
        cardPanel.add(welcomePanel, "welcome");
    }

    private void createCalculatorPage() {
        JPanel calculatorPanel = new JPanel(new BorderLayout());
        calculatorPanel.setBackground(Color.WHITE);
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(800, 70));

        JLabel titleLabel = new JLabel("محاسبه BMI", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        JButton backButton = new JButton("بازگشت");
        backButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        backButton.setBackground(new Color(255, 255, 255, 150));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            resetCalculator();
            cardLayout.show(cardPanel, "welcome");
        });

        headerPanel.add(backButton, BorderLayout.WEST);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                "لطفا اطلاعات خود را وارد کنید"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel genderLabel = new JLabel("جنسیت:");
        genderLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        genderLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        inputPanel.add(genderLabel, gbc);

        String[] genders = {"انتخاب کنید", "مرد", "زن"};
        genderComboBox = new JComboBox<>(genders);
        genderComboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
        genderComboBox.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        inputPanel.add(genderComboBox, gbc);
        JLabel heightLabel = new JLabel("قد (سانتی‌متر):");
        heightLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        genderLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        inputPanel.add(heightLabel, gbc);

        heightField = new JTextField(15);
        heightField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        inputPanel.add(heightField, gbc);
        JLabel weightLabel = new JLabel("وزن (کیلوگرم):");
        weightLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        genderLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        inputPanel.add(weightLabel, gbc);

        weightField = new JTextField(15);
        weightField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        inputPanel.add(weightField, gbc);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        JButton calculateButton = new JButton("محاسبه BMI");
        calculateButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        calculateButton.setBackground(SUCCESS_COLOR);
        calculateButton.setForeground(Color.BLACK);
        calculateButton.setFocusPainted(false);
        calculateButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        calculateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        calculateButton.addActionListener(e -> calculateBMI());
        JButton resetButton = new JButton("پاک کردن");
        resetButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        resetButton.setBackground(WARNING_COLOR);
        resetButton.setForeground(Color.BLACK);
        resetButton.setFocusPainted(false);
        resetButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetButton.addActionListener(e -> resetCalculator());

        buttonPanel.add(calculateButton);
        buttonPanel.add(resetButton);
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                "نتایج محاسبه BMI"));
        resultPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultColorPanel = new JPanel();
        resultColorPanel.setPreferredSize(new Dimension(300, 20));
        resultColorPanel.setBackground(Color.LIGHT_GRAY);
        resultColorPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel = new JLabel("لطفا اطلاعات خود را وارد و دکمه محاسبه را بزنید");
        resultLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        resultLabel.setForeground(new Color(70, 70, 70));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoryLabel = new JLabel("");
        categoryLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextArea guideArea = new JTextArea();
        guideArea.setText("راهنمای تفسیر BMI:\n" +
                "• کمتر از ۱۸.۵: کمبود وزن\n" +
                "• ۱۸.۵ تا ۲۴.۹: وزن طبیعی\n" +
                "• ۲۵ تا ۲۹.۹: اضافه وزن\n" +
                "• ۳۰ و بیشتر: چاقی");
        guideArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
        guideArea.setForeground(Color.BLACK);
        guideArea.setEditable(false);
        guideArea.setBackground(new Color(245, 245, 245));
        guideArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        guideArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        themePanel.setBackground(Color.WHITE);
        themePanel.setBorder(BorderFactory.createTitledBorder("تغییر رنگ زمینه"));

        for (String themeName : themeColors.keySet()) {
            JButton themeButton = new JButton(themeName);
            themeButton.setBackground(themeColors.get(themeName));
            themeButton.setForeground(Color.WHITE);
            themeButton.setFocusPainted(false);
            themeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            themeButton.addActionListener(e -> changeTheme(themeName));
            themePanel.add(themeButton);
        }
        resultPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        resultPanel.add(resultColorPanel);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        resultPanel.add(resultLabel);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        resultPanel.add(categoryLabel);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        resultPanel.add(guideArea);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        resultPanel.add(themePanel);
        contentPanel.add(inputPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(resultPanel);
        calculatorPanel.add(headerPanel, BorderLayout.NORTH);
        calculatorPanel.add(contentPanel, BorderLayout.CENTER);

        cardPanel.add(calculatorPanel, "calculator");
    }

    private void calculateBMI() {
        try {
            String gender = (String) genderComboBox.getSelectedItem();
            String heightText = heightField.getText().trim();
            String weightText = weightField.getText().trim();
            if (gender.equals("انتخاب کنید")) {
                JOptionPane.showMessageDialog(this, "لطفا جنسیت خود را انتخاب کنید.",
                        "خطا", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (heightText.isEmpty() || weightText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "لطفا تمام فیلدها را پر کنید.",
                        "خطا", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double height = Double.parseDouble(heightText);
            double weight = Double.parseDouble(weightText);

            if (height <= 0 || weight <= 0) {
                JOptionPane.showMessageDialog(this, "قد و وزن باید اعداد مثبت باشند.",
                        "خطا", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (height > 300) {
                JOptionPane.showMessageDialog(this, "قد وارد شده غیرمنطقی است. لطفا قد را به سانتی‌متر وارد کنید.",
                        "خطا", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double heightInMeter = height / 100;
            double bmi = weight / (heightInMeter * heightInMeter);
            DecimalFormat df = new DecimalFormat("#.##");
            String bmiFormatted = df.format(bmi);
            String category;
            Color categoryColor;

            if (bmi < 18.5) {
                category = "کمبود وزن";
                categoryColor = WARNING_COLOR;
            } else if (bmi < 25) {
                category = "وزن طبیعی";
                categoryColor = SUCCESS_COLOR;
            } else if (bmi < 30) {
                category = "اضافه وزن";
                categoryColor = WARNING_COLOR;
            } else {
                category = "چاقی";
                categoryColor = DANGER_COLOR;
            }
            resultLabel.setText("شاخص توده بدنی (BMI) شما: " + bmiFormatted);
            resultLabel.setForeground(categoryColor);
            String genderText = gender.equals("مرد") ? "آقا" : "خانم";
            categoryLabel.setText("وضعیت شما (" + genderText + "): " + category);
            categoryLabel.setForeground(categoryColor);
            resultColorPanel.setBackground(categoryColor);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "لطفا اعداد معتبر برای قد و وزن وارد کنید.",
                    "خطا", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetCalculator() {
        genderComboBox.setSelectedIndex(0);
        heightField.setText("");
        weightField.setText("");
        resultLabel.setText("لطفا اطلاعات خود را وارد و دکمه محاسبه را بزنید");
        resultLabel.setForeground(Color.BLACK);
        categoryLabel.setText("");
        resultColorPanel.setBackground(Color.LIGHT_GRAY);
    }

    private void changeTheme(String themeName) {
        currentTheme = themeName;
        Color themeColor = themeColors.get(themeName);
        Component[] components = cardPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                Component[] subComponents = panel.getComponents();

                for (Component subComp : subComponents) {
                    if (subComp instanceof JPanel) {
                        JPanel subPanel = (JPanel) subComp;
                        if (subPanel.getComponentCount() > 0) {
                            Component header = subPanel.getComponent(0);
                            if (header instanceof JPanel) {
                                header.setBackground(themeColor);
                            }
                        }
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(this,
                "تم برنامه به " + themeName + " تغییر کرد.",
                "تغییر تم",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new BMICalculator());
    }
}