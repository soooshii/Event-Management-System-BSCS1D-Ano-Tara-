package eventmanagementsystem; // <-- Make sure this matches your actual package name

import javax.swing.*;
import java.awt.*;

public class EventCardUI extends JPanel {

    // Properties to hold the event ID so the view button knows which event to open
    private int eventID;

    // Define the components based on image_7.png
    private JLabel lblStatusIcon; // Green Check, Red X, or Blue Dot
    private JLabel lblEventName;
    private JLabel lblSlots; // "Participating / Max Slots"
    private JLabel lblLocation;
    private JLabel lblDate;
    private JButton btnView; // The right-side interaction button

    // Theme Colors derived from image_4.png
    private final Color COLOR_BG = new java.awt.Color(35, 10, 50); // Deep Purple Background
    private final Color COLOR_TEXT_DIM = new java.awt.Color(160, 160, 160); // Dimmed gray for meta text
    private final Color COLOR_TEXT_ACCENT = new java.awt.Color(100, 200, 255); // Neon Blue for slots

    public EventCardUI(int eventID, String eventName, String slots, String location, String date, String status) {
        this.eventID = eventID;

        // Set up the panel layout (Horizontal list)
        this.setLayout(new GridBagLayout());
        this.setBackground(COLOR_BG);
        // Important: Add a small border to create space between cards
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(50, 50, 70))); 

        initializeComponents();
        layoutComponents();
        setCardData(eventName, slots, location, date, status);
    }

    private void initializeComponents() {
        // Status Icon (mimicking the green check/red X from image_7.png)
        lblStatusIcon = new JLabel();
        lblStatusIcon.setFont(new Font("Monospaced", Font.BOLD, 22)); 

        // Primary Event Name (Large and bold)
        lblEventName = new JLabel();
        lblEventName.setForeground(Color.WHITE);
        lblEventName.setFont(new Font("Monospaced", Font.BOLD, 16));

        // Slots Count ("20 / 25" in image_7.png - blue and monospaced)
        lblSlots = new JLabel();
        lblSlots.setForeground(COLOR_TEXT_ACCENT);
        lblSlots.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Location and Date (Dimmed gray)
        lblLocation = new JLabel();
        lblLocation.setForeground(COLOR_TEXT_DIM);
        lblLocation.setFont(new Font("Monospaced", Font.PLAIN, 12));

        lblDate = new JLabel();
        lblDate.setForeground(COLOR_TEXT_DIM);
        lblDate.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // The interaction button (Mimicking 'View' in image_7.png)
        btnView = new JButton("VIEW");
        // Apply styling to match your Press Start look
        btnView.setFocusPainted(false);
        btnView.setBackground(COLOR_BG);
        btnView.setForeground(new java.awt.Color(255, 0, 150)); // Neon Magenta/Pink
        btnView.setFont(new Font("Monospaced", Font.BOLD, 12));
        btnView.setBorder(BorderFactory.createLineBorder(new java.awt.Color(255, 0, 150), 1)); 
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15); // Consistent spacing around elements

        // --- Column 1: Status Icon ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3; // Icon spans all 3 lines of text
        gbc.weightx = 0.0;
        this.add(lblStatusIcon, gbc);

        // --- Column 2: Event Details (Resetting gridheight) ---
        gbc.gridx = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0; // Pushes the button all the way to the right
        gbc.anchor = GridBagConstraints.WEST; // Align text to the left
        gbc.insets = new Insets(10, 0, 0, 0); // Spacing adjustment

        // Row 1: Event Name
        gbc.gridy = 0;
        this.add(lblEventName, gbc);

        // Row 2: Slots / Location
        gbc.gridy = 1;
        this.add(lblSlots, gbc);
        
        // Minor detail alignment for Location
        gbc.insets = new Insets(2, 0, 0, 0); // Tiny top margin
        gbc.gridy = 2;
        this.add(lblLocation, gbc);

        // Row 3: Date
        gbc.gridy = 3;
        gbc.insets = new Insets(2, 0, 10, 0); // Bottom padding
        this.add(lblDate, gbc);

        // --- Column 3: The View Button ---
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 4; // Span the button height for vertical centering
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST; // Align button to the right
        gbc.insets = new Insets(10, 20, 10, 15);
        this.add(btnView, gbc);
    }

    private void setCardData(String eventName, String slots, String location, String date, String status) {
        lblEventName.setText(eventName.toUpperCase()); // Forces the Retro terminal look
        lblSlots.setText(slots);
        lblLocation.setText(location.toUpperCase());
        lblDate.setText(date.toUpperCase());

        // Handle Status logic
        // status is expected to be "CONCLUDED", "NOT_ATTENDED", or "ACTIVE"
        if ("CONCLUDED".equalsIgnoreCase(status)) {
            // Green check mark logic from image_7.png
            lblStatusIcon.setText("✓"); // Unicode for ease tonight
            lblStatusIcon.setForeground(new java.awt.Color(50, 200, 100)); // Vibrant Neon Green
        } else if ("NOT_ATTENDED".equalsIgnoreCase(status)) {
            // X mark logic from your request
            lblStatusIcon.setText("X");
            lblStatusIcon.setForeground(new java.awt.Color(255, 50, 50)); // Neon Red
        } else {
            // "Active" or upcoming status
            lblStatusIcon.setText("•"); // Simple dot
            lblStatusIcon.setForeground(COLOR_TEXT_ACCENT); // Neon Blue
        }
    }
    
    // Crucial getter so your loop can attach code to the View button!
    public JButton getViewButton() {
        return btnView;
    }
    
    public int getEventID() {
        return eventID;
    }
}