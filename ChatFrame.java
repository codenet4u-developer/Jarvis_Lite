import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;

public class ChatFrame extends JFrame {
    private final JPanel chatContainer;
    private final JScrollPane scrollPane;
    private final JTextField inputField;
    private final JButton sendButton;

    private final BotEngin engine;
    private final MessageLog log;

    // Sleek Dark Theme Color Palette
    private final Color COLOR_BG = new Color(24, 25, 29);
    private final Color COLOR_SIDEBAR = new Color(30, 32, 38);
    private final Color COLOR_PANEL = new Color(42, 45, 53);
    private final Color COLOR_USER_BUBBLE = new Color(79, 70, 229); // Accent Indigo
    private final Color COLOR_BOT_BUBBLE = new Color(45, 48, 56);
    private final Color COLOR_TEXT = new Color(240, 243, 246);
    private final Color COLOR_MUTED = new Color(156, 163, 175);

    public ChatFrame() {
        this.engine = new BotEngin();
        this.log = new MessageLog();

        // 1. Frame Configuration
        setTitle("AI Desktop Assistant");
        setSize(950, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout());

        // 2. Left Sidebar Panel
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(COLOR_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(20, 15, 20, 15));

        JLabel sidebarTitle = new JLabel("Chats");
        sidebarTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sidebarTitle.setForeground(COLOR_TEXT);
        sidebar.add(sidebarTitle);
        sidebar.add(Box.createVerticalStrut(15));

        JLabel historyItem1 = new JLabel("• Current Session");
        historyItem1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        historyItem1.setForeground(COLOR_MUTED);
        sidebar.add(historyItem1);

        add(sidebar, BorderLayout.WEST);

        // 3. Central Chat Main Area
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(COLOR_BG);

        chatContainer = new JPanel();
        chatContainer.setLayout(new BoxLayout(chatContainer, BoxLayout.Y_AXIS));
        chatContainer.setBackground(COLOR_BG);
        chatContainer.setBorder(new EmptyBorder(20, 30, 20, 30));

        scrollPane = new JScrollPane(chatContainer);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // 4. Bottom Floating Prompt Box Panel (Rounded Input Container)
        JPanel promptWrapper = new JPanel(new BorderLayout());
        promptWrapper.setBackground(COLOR_BG);
        promptWrapper.setBorder(new EmptyBorder(10, 30, 20, 30));

        // Panel using custom rounded border for smooth curved corners
        JPanel inputCard = new JPanel(new BorderLayout(10, 10));
        inputCard.setBackground(COLOR_PANEL);
        inputCard.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(20),
                new EmptyBorder(10, 16, 10, 16)
        ));

        inputField = new JTextField("Write your prompt here..");
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setForeground(COLOR_MUTED);
        inputField.setBackground(COLOR_PANEL);
        inputField.setCaretColor(COLOR_TEXT);
        inputField.setBorder(null);

        // Placeholder effect behavior
        inputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inputField.getText().equals("Write your prompt here..")) {
                    inputField.setText("");
                    inputField.setForeground(COLOR_TEXT);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (inputField.getText().isEmpty()) {
                    inputField.setText("Write your prompt here..");
                    inputField.setForeground(COLOR_MUTED);
                }
            }
        });

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sendButton.setBackground(COLOR_USER_BUBBLE);
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sendButton.setBorder(new EmptyBorder(8, 18, 8, 18));

        sendButton.addActionListener(this::handleSend);
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleSend(null);
                }
            }
        });

        inputCard.add(inputField, BorderLayout.CENTER);
        inputCard.add(sendButton, BorderLayout.EAST);
        promptWrapper.add(inputCard, BorderLayout.CENTER);

        centerPanel.add(promptWrapper, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        // Initial Greeting
        addMessageBubble("Hello! Ask me any question, live weather update, time, or general query.", false);
    }

    private void handleSend(ActionEvent e) {
        String text = inputField.getText().trim();
        if (text.isEmpty() || text.equals("Write your prompt here..")) return;

        // Add User Message
        log.addMessage("User", text);
        addMessageBubble(text, true);
        inputField.setText("");
        inputField.setEnabled(false);
        sendButton.setEnabled(false);

        // Background Processing
        new Thread(() -> {
            String botReply = engine.getAnswer(text);
            SwingUtilities.invokeLater(() -> {
                log.addMessage("Bot", botReply);
                addMessageBubble(botReply, false);
                inputField.setEnabled(true);
                sendButton.setEnabled(true);
                inputField.requestFocus();
            });
        }).start();
    }

    // Creates styled chat bubbles with proper word-wrapping and alignment
    private void addMessageBubble(String text, boolean isUser) {
        JPanel wrapper = new JPanel(new FlowLayout(isUser ? FlowLayout.RIGHT : FlowLayout.LEFT, 0, 6));
        wrapper.setBackground(COLOR_BG);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JTextArea bubbleArea = new JTextArea(text);
        bubbleArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bubbleArea.setForeground(COLOR_TEXT);
        bubbleArea.setBackground(isUser ? COLOR_USER_BUBBLE : COLOR_BOT_BUBBLE);
        bubbleArea.setEditable(false);
        bubbleArea.setLineWrap(true);
        bubbleArea.setWrapStyleWord(true);

        int width = Math.min(520, Math.max(120, text.length() * 8));
        bubbleArea.setSize(new Dimension(width, Integer.MAX_VALUE));
        bubbleArea.setBorder(new EmptyBorder(12, 16, 12, 16));

        wrapper.add(bubbleArea);
        chatContainer.add(wrapper);
        chatContainer.add(Box.createVerticalStrut(8));

        chatContainer.revalidate();
        chatContainer.repaint();

        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    // Custom Rounded Border class for smooth curved corners in Swing
    private static class RoundedBorder extends AbstractBorder {
        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(new Color(60, 64, 75)); // Border color
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius / 2, this.radius / 2, this.radius / 2, this.radius / 2);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = this.radius / 2;
            return insets;
        }
    }

  public static void main(String[] args) {

    SwingUtilities.invokeLater(() -> {
        new ChatFrame().setVisible(true);
    });
}
}