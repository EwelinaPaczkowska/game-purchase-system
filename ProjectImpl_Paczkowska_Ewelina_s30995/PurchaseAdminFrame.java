import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseAdminFrame extends JFrame {
    private static final Color BACKGROUND = new Color(221, 236, 232);
    private static final Color PANEL_BACKGROUND = new Color(246, 250, 248);
    private static final Color FIELD_BACKGROUND = new Color(239, 246, 244);
    private static final Color TEXT = new Color(39, 69, 86);
    private static final Color MUTED_TEXT = new Color(34, 128, 119);
    private static final Color DARK_TEXT = new Color(39, 69, 86);
    private static final Color BORDER = new Color(116, 190, 181);
    private static final Color ACCENT = new Color(41, 163, 148);
    private static final Color BUTTON = new Color(228, 103, 78);
    private static final Color PURCHASE_BACKGROUND = PANEL_BACKGROUND;
    private static final Color PURCHASE_TEXT = TEXT;
    private static final Color PURCHASE_BORDER = BORDER;

    private JPanel mainPanel;
    private JList<Player> playersList;
    private JList<Purchase> ownedGamesList;
    private JComboBox<Game> gamesComboBox;
    private JTextField purchaseDateField;
    private JButton purchaseButton;
    private JLabel selectedPlayerLabel;
    private JLabel statusLabel;
    private JLabel gameNameLabel;
    private JLabel gameReleaseDateLabel;
    private JLabel gameDescriptionLabel;
    private JLabel gameTypeLabel;
    private JLabel gameRamLabel;
    private JLabel gameTagsLabel;
    private JLabel gameMultiplayerLabel;
    private JLabel gameDevelopersLabel;
    private JList<String> gameReviewsList;
    private JPanel purchasePanel;

    private final DefaultListModel<Player> playersModel = new DefaultListModel<>();
    private final DefaultListModel<Purchase> ownedGamesModel = new DefaultListModel<>();
    private final DefaultListModel<String> gameReviewsModel = new DefaultListModel<>();
    private boolean startupDataMessageShown;

    public PurchaseAdminFrame() {
        createUIComponents();
        setTitle("Administrator - game purchases");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setMinimumSize(new Dimension(1180, 780));
        setSize(new Dimension(1180, 780));
        setLocationRelativeTo(null);

        configureRenderers();
        loadPlayers();
        refreshSelectedPlayer();
        showStartupDataMessageIfNeeded();

        playersList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshSelectedPlayer();
            }
        });
        gamesComboBox.addActionListener(e -> refreshSelectedGameDetails());
        purchaseButton.addActionListener(e -> purchaseSelectedGame());
    }

    private void createUIComponents() {
        mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(new EmptyBorder(14, 14, 14, 14));

        playersList = new JList<>();
        ownedGamesList = new JList<>();
        gamesComboBox = new JComboBox<>();
        purchaseDateField = new JTextField(LocalDate.now().toString());
        purchaseButton = new JButton("Buy game");
        selectedPlayerLabel = new JLabel("Select a player");
        statusLabel = new JLabel(" ");
        gameNameLabel = new JLabel("-");
        gameReleaseDateLabel = new JLabel("-");
        gameDescriptionLabel = new JLabel("-");
        gameTypeLabel = new JLabel("-");
        gameRamLabel = new JLabel("-");
        gameTagsLabel = new JLabel("-");
        gameMultiplayerLabel = new JLabel("-");
        gameDevelopersLabel = new JLabel("-");
        gameReviewsList = new JList<>();

        JPanel playersPanel = new JPanel(new BorderLayout(8, 8));
        playersPanel.setBorder(createSectionBorder("Players"));
        playersPanel.add(new JScrollPane(playersList), BorderLayout.CENTER);

        JPanel playerGamesPanel = new JPanel(new BorderLayout(8, 8));
        playerGamesPanel.setPreferredSize(new Dimension(760, 260));
        playerGamesPanel.setBorder(createSectionBorder("Player games"));
        playerGamesPanel.add(selectedPlayerLabel, BorderLayout.NORTH);
        playerGamesPanel.add(new JScrollPane(ownedGamesList), BorderLayout.CENTER);

        purchasePanel = new JPanel(new GridBagLayout());
        purchasePanel.setBorder(createPurchaseBorder("Game purchase"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        purchasePanel.add(createFieldLabel("Game"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        purchasePanel.add(gamesComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        purchasePanel.add(createFieldLabel("Date"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        purchasePanel.add(purchaseDateField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        purchasePanel.add(purchaseButton, gbc);

        JPanel gameDetailsPanel = new JPanel(new GridBagLayout());
        gameDetailsPanel.setPreferredSize(new Dimension(760, 230));
        gameDetailsPanel.setBorder(createSectionBorder("Selected game details"));
        addDetailRow(gameDetailsPanel, 0, "Name:", gameNameLabel);
        addDetailRow(gameDetailsPanel, 1, "Release date:", gameReleaseDateLabel);
        addDetailRow(gameDetailsPanel, 2, "Description:", gameDescriptionLabel);
        addDetailRow(gameDetailsPanel, 3, "Type:", gameTypeLabel);
        addDetailRow(gameDetailsPanel, 4, "Required RAM:", gameRamLabel);
        addDetailRow(gameDetailsPanel, 5, "Tags:", gameTagsLabel);
        addDetailRow(gameDetailsPanel, 6, "Multiplayer max players:", gameMultiplayerLabel);
        addDetailRow(gameDetailsPanel, 7, "Developers:", gameDevelopersLabel);

        JPanel reviewsPanel = new JPanel(new BorderLayout(8, 8));
        reviewsPanel.setPreferredSize(new Dimension(760, 95));
        reviewsPanel.setBorder(createSectionBorder("Reviews"));
        reviewsPanel.add(new JScrollPane(gameReviewsList), BorderLayout.CENTER);

        JPanel gameInfoPanel = new JPanel(new BorderLayout(10, 10));
        gameInfoPanel.add(gameDetailsPanel, BorderLayout.NORTH);
        gameInfoPanel.add(reviewsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(gameInfoPanel, BorderLayout.CENTER);
        bottomPanel.add(purchasePanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.add(playerGamesPanel, BorderLayout.CENTER);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playersPanel, rightPanel);
        splitPane.setResizeWeight(0.28);
        splitPane.setDividerLocation(260);

        mainPanel.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        applyTheme();
    }

    private void configureRenderers() {
        playersList.setModel(playersModel);
        ownedGamesList.setModel(ownedGamesModel);
        gameReviewsList.setModel(gameReviewsModel);
        playersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ownedGamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gameReviewsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        playersList.setCellRenderer((list, value, index, isSelected, cellHasFocus) ->
                styleListCell(new DefaultListCellRenderer().getListCellRendererComponent(
                        list,
                        value.getName(),
                        index,
                        isSelected,
                        cellHasFocus
                ), isSelected));

        ownedGamesList.setCellRenderer((list, value, index, isSelected, cellHasFocus) ->
                styleListCell(new DefaultListCellRenderer().getListCellRendererComponent(
                        list,
                        value.getGame().getName() + " | purchased: " + value.getPurchaseDate(),
                        index,
                        isSelected,
                        cellHasFocus
                ), isSelected));

        gamesComboBox.setRenderer((list, value, index, isSelected, cellHasFocus) ->
                styleListCell(new DefaultListCellRenderer().getListCellRendererComponent(
                        list,
                        value == null ? "" : value.getName(),
                        index,
                        isSelected,
                        cellHasFocus
                ), isSelected));

        gameReviewsList.setCellRenderer((list, value, index, isSelected, cellHasFocus) ->
                styleListCell(new DefaultListCellRenderer().getListCellRendererComponent(
                        list,
                        value,
                        index,
                        isSelected,
                        cellHasFocus
                ), isSelected));
    }

    private void loadPlayers() {
        playersModel.clear();
        ObjectPlus.getFromExtent(Player.class).stream()
                .sorted(Comparator.comparing(Player::getName))
                .forEach(playersModel::addElement);

        if (!playersModel.isEmpty()) {
            playersList.setSelectedIndex(0);
        }
    }

    private void refreshSelectedPlayer() {
        Player player = playersList.getSelectedValue();
        ownedGamesModel.clear();
        gamesComboBox.removeAllItems();

        if (player == null) {
            selectedPlayerLabel.setText("Select a player");
            purchaseButton.setEnabled(false);
            statusLabel.setText("Player cannot be null");
            refreshSelectedGameDetails();
            return;
        }

        selectedPlayerLabel.setText("Player games: " + player.getName());
        player.getGameSet().stream()
                .sorted(Comparator.comparing(purchase -> purchase.getGame().getName()))
                .forEach(ownedGamesModel::addElement);

        List<Game> games = ObjectPlus.getFromExtent(Game.class).stream()
                .sorted(Comparator.comparing(Game::getName))
                .collect(Collectors.toList());

        for (Game game : games) {
            gamesComboBox.addItem(game);
        }

        purchaseButton.setEnabled(gamesComboBox.getItemCount() > 0);
        statusLabel.setText(games.isEmpty() ? "Game cannot be null" : " ");
        refreshSelectedGameDetails();
    }

    private boolean hasGame(Player player, Game game) {
        return player.getGameSet().stream().anyMatch(purchase -> purchase.getGame() == game);
    }

    private void addDetailRow(JPanel panel, int row, String label, JLabel valueLabel) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.insets = new Insets(3, 4, 3, 4);
        labelConstraints.anchor = GridBagConstraints.WEST;
        panel.add(createFieldLabel(label), labelConstraints);

        GridBagConstraints valueConstraints = new GridBagConstraints();
        valueConstraints.gridx = 1;
        valueConstraints.gridy = row;
        valueConstraints.insets = new Insets(3, 4, 3, 4);
        valueConstraints.anchor = GridBagConstraints.WEST;
        valueConstraints.fill = GridBagConstraints.HORIZONTAL;
        valueConstraints.weightx = 1;
        panel.add(valueLabel, valueConstraints);
    }

    private Border createSectionBorder(String title) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(new LineBorder(BORDER), title);
        titledBorder.setTitleColor(MUTED_TEXT);
        titledBorder.setTitleFont(new Font("Dialog", Font.BOLD, 12));
        return BorderFactory.createCompoundBorder(titledBorder, new EmptyBorder(5, 5, 5, 5));
    }

    private Border createPurchaseBorder(String title) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(new LineBorder(PURCHASE_BORDER), title);
        titledBorder.setTitleColor(PURCHASE_TEXT);
        titledBorder.setTitleFont(new Font("Dialog", Font.BOLD, 12));
        return BorderFactory.createCompoundBorder(titledBorder, new EmptyBorder(5, 5, 5, 5));
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(new Font("Dialog", Font.BOLD, 12));
        return label;
    }

    private void applyTheme() {
        mainPanel.setBackground(BACKGROUND);
        styleContainer(mainPanel);

        styleList(playersList);
        styleList(ownedGamesList);
        styleList(gameReviewsList);

        purchaseDateField.setBackground(FIELD_BACKGROUND);
        purchaseDateField.setForeground(DARK_TEXT);
        purchaseDateField.setCaretColor(DARK_TEXT);
        purchaseDateField.setBorder(new LineBorder(BORDER));

        gamesComboBox.setBackground(FIELD_BACKGROUND);
        gamesComboBox.setForeground(DARK_TEXT);

        purchaseButton.setBackground(BUTTON);
        purchaseButton.setForeground(Color.WHITE);
        purchaseButton.setFont(new Font("Dialog", Font.BOLD, 12));
        purchaseButton.setFocusPainted(false);
        purchaseButton.setBorder(new EmptyBorder(7, 14, 7, 14));

        selectedPlayerLabel.setForeground(TEXT);
        selectedPlayerLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        statusLabel.setForeground(MUTED_TEXT);
        stylePurchasePanel();
    }

    private void stylePurchasePanel() {
        if (purchasePanel == null) {
            return;
        }

        purchasePanel.setBackground(PURCHASE_BACKGROUND);
        for (Component component : purchasePanel.getComponents()) {
            if (component instanceof JLabel) {
                component.setForeground(PURCHASE_TEXT);
            }
        }
    }

    private void styleContainer(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JPanel) {
                component.setBackground(PANEL_BACKGROUND);
            }
            if (component instanceof JLabel) {
                component.setForeground(TEXT);
            }
            if (component instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) component;
                scrollPane.setBorder(new LineBorder(BORDER));
                scrollPane.getViewport().setBackground(FIELD_BACKGROUND);
            }
            if (component instanceof JSplitPane) {
                JSplitPane splitPane = (JSplitPane) component;
                splitPane.setBackground(BACKGROUND);
                splitPane.setBorder(new LineBorder(BORDER));
                splitPane.setDividerSize(7);
            }
            if (component instanceof Container) {
                styleContainer((Container) component);
            }
        }
    }

    private void styleList(JList<?> list) {
        list.setBackground(FIELD_BACKGROUND);
        list.setForeground(DARK_TEXT);
        list.setSelectionBackground(ACCENT);
        list.setSelectionForeground(Color.WHITE);
        list.setFixedCellHeight(22);
        list.setBorder(new EmptyBorder(3, 3, 3, 3));
    }

    private Component styleListCell(Component component, boolean isSelected) {
        component.setForeground(isSelected ? Color.WHITE : DARK_TEXT);
        component.setBackground(isSelected ? ACCENT : FIELD_BACKGROUND);
        if (component instanceof JComponent) {
            ((JComponent) component).setBorder(new EmptyBorder(2, 5, 2, 5));
        }
        return component;
    }

    private void refreshSelectedGameDetails() {
        Game game = (Game) gamesComboBox.getSelectedItem();

        if (game == null) {
            gameNameLabel.setText("-");
            gameReleaseDateLabel.setText("-");
            gameDescriptionLabel.setText("-");
            gameTypeLabel.setText("-");
            gameRamLabel.setText("-");
            gameTagsLabel.setText("-");
            gameMultiplayerLabel.setText("-");
            gameDevelopersLabel.setText("-");
            gameReviewsModel.clear();
            return;
        }

        gameNameLabel.setText(game.getName());
        gameReleaseDateLabel.setText(game.getReleased_date().toString());
        gameDescriptionLabel.setText(toHtml(game.getDescription().orElse("No description")));
        gameTypeLabel.setText(game.getType().toString());
        gameRamLabel.setText(game.getRequired_ram() + " GB");
        gameTagsLabel.setText(toHtml(String.join(", ", game.getTags())));
        gameMultiplayerLabel.setText(getMultiplayerDetails(game));
        gameDevelopersLabel.setText(toHtml(getDeveloperDetails(game)));
        refreshGameReviews(game);
    }

    private String getMultiplayerDetails(Game game) {
        if (!game.getType().contains(GameType.MULTIPLAYER)) {
            return "Not available";
        }

        return String.valueOf(game.getMultiplayer().getMaxPlayers());
    }

    private String getDeveloperDetails(Game game) {
        if (game.getDevelopers().isEmpty()) {
            return "No developers assigned";
        }

        return game.getDevelopers().stream()
                .map(Developer::getName)
                .sorted()
                .collect(Collectors.joining(", "));
    }

    private void refreshGameReviews(Game game) {
        gameReviewsModel.clear();

        game.getReviews().stream()
                .sorted(Comparator.comparing(Review::getContent))
                .map(Review::getContent)
                .forEach(gameReviewsModel::addElement);

        if (gameReviewsModel.isEmpty()) {
            gameReviewsModel.addElement("No reviews yet.");
        }
    }

    private String toHtml(String text) {
        return "<html><body style='width: 330px'>" + escapeHtml(text) + "</body></html>";
    }

    private String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private void showStartupDataMessageIfNeeded() {
        if (startupDataMessageShown) {
            return;
        }

        startupDataMessageShown = true;

        if (ObjectPlus.getFromExtent(Player.class).isEmpty()) {
            showError("No registered players found.");
            return;
        }

        if (ObjectPlus.getFromExtent(Game.class).isEmpty()) {
            showError("No games found.");
        }
    }

    private void purchaseSelectedGame() {
        Player player = playersList.getSelectedValue();
        Game game = (Game) gamesComboBox.getSelectedItem();

        if (player == null || game == null) {
            showError(player == null ? "Player cannot be null" : "Game cannot be null");
            return;
        }

        if (hasGame(player, game)) {
            showError("The game will not be added to the player's account again.");
            return;
        }

        try {
            LocalDate purchaseDate = LocalDate.parse(purchaseDateField.getText().trim());
            player.addGame(game, purchaseDate);
            ObjectPlus.saveExtent();
            refreshSelectedPlayer();
            statusLabel.setText("Purchase added: " + player.getName() + " -> " + game.getName());
        } catch (DateTimeParseException e) {
            showError("Date must use the YYYY-MM-DD format.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void showError(String message) {
        statusLabel.setText(message);
        JOptionPane.showMessageDialog(this, message, "Cannot add purchase", JOptionPane.ERROR_MESSAGE);
    }
}
