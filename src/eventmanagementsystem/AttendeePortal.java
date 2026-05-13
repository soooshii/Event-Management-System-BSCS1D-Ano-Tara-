package eventmanagementsystem;

import java.sql.Connection;
import java.awt.HeadlessException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;

public class AttendeePortal extends javax.swing.JFrame {
    
    public void performDelete() {
        int selectedRow = tblHostedEvents.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select an event from the table first!");
            return;
        }

        // 1. GRAB THE ID (Column 0) INSTEAD OF THE NAME (Column 1)
        int selectedEventId = Integer.parseInt(tblHostedEvents.getValueAt(selectedRow, 0).toString());
        String selectedEventName = tblHostedEvents.getValueAt(selectedRow, 1).toString();

        try {
            int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
                    "Are you sure you want to permanently delete '" + selectedEventName + "'? This will also cancel all registrations.", 
                    "Confirm Deletion", 
                    javax.swing.JOptionPane.YES_NO_OPTION);

            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                java.sql.Connection conn = DatabaseConnection.getConnection();
                
                // 2. DELETE USING THE EXACT ID
                String delRegSql = "DELETE FROM registrations WHERE event_id = ?";
                java.sql.PreparedStatement delRegStmt = conn.prepareStatement(delRegSql);
                delRegStmt.setInt(1, selectedEventId);
                delRegStmt.executeUpdate();

                String delEventSql = "DELETE FROM events WHERE event_id = ?";
                java.sql.PreparedStatement delEventStmt = conn.prepareStatement(delEventSql);
                delEventStmt.setInt(1, selectedEventId);
                delEventStmt.executeUpdate();

                javax.swing.JOptionPane.showMessageDialog(this, "Event deleted successfully!");

                loadHostedEvents();
                loadComboBoxEvents(); 
                // Safely refresh the Discover Events cards too!
                loadAvailableEvents(cmbFilter.getSelectedItem().toString()); 
            }
            
        // 3. THIS IS THE CATCH BLOCK THAT WENT MISSING!
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Delete Error: " + e.getMessage());
        }
    }
    
    public void performCreate() {
           
        javax.swing.JTextField txtName = new javax.swing.JTextField();
        javax.swing.JTextField txtYear = new javax.swing.JTextField("2026", 4);
        javax.swing.JTextField txtMonth = new javax.swing.JTextField(2);
        javax.swing.JTextField txtDay = new javax.swing.JTextField(2);
        
        javax.swing.JPanel datePanel = new javax.swing.JPanel();
        datePanel.add(txtYear);
        datePanel.add(new javax.swing.JLabel("-"));
        datePanel.add(txtMonth);
        datePanel.add(new javax.swing.JLabel("-"));
        datePanel.add(txtDay);
        
        javax.swing.JTextField txtLoc = new javax.swing.JTextField();
        javax.swing.JTextField txtSlots = new javax.swing.JTextField();

        javax.swing.JTextArea txtDesc = new javax.swing.JTextArea(3, 20); // 3 rows tall
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        javax.swing.JScrollPane descScroll = new javax.swing.JScrollPane(txtDesc);

        Object[] formFields = {
            "Event Name:", txtName,
            "Event Date (YYYY-MM-DD):", datePanel,
            "Location/Venue:", txtLoc,
            "Max Slots:", txtSlots,
            "Event Description:", descScroll
        };

        int result = javax.swing.JOptionPane.showConfirmDialog(this, formFields, "Add New Event", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);

        if (result == javax.swing.JOptionPane.OK_OPTION) {

            if (txtName.getText().trim().isEmpty() || txtYear.getText().trim().isEmpty() || txtMonth.getText().trim().isEmpty() || txtDay.getText().trim().isEmpty() || txtLoc.getText().trim().isEmpty() || txtSlots.getText().trim().isEmpty() || txtDesc.getText().trim().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "All fields are required!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return; 
            }
            
            int slots;
            String formattedDate; // Define this here so the database block can see it

            try {
                // 2. Validate Slots (Positive Integer check)
                slots = Integer.parseInt(txtSlots.getText().trim());
                if (slots <= 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Max Slots must be 1 or greater!", "Invalid Number", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 3. DATE VALIDATION: This prevents past dates like Feb 12 when today is May 12
                int year = Integer.parseInt(txtYear.getText().trim());
                int month = Integer.parseInt(txtMonth.getText().trim());
                int day = Integer.parseInt(txtDay.getText().trim());

                // Format with leading zeros (e.g., month 5 becomes "05") to prevent crashes
                formattedDate = String.format("%d-%02d-%02d", year, month, day);

                java.time.LocalDate eventDate = java.time.LocalDate.parse(formattedDate);
                java.time.LocalDate today = java.time.LocalDate.now();

                if (eventDate.isBefore(today)) {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "Error: You cannot pick a past date!\nToday is " + today,
                            "Date Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return; // Stops the code here!
                }

            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Please enter valid numbers for Date and Max Slots.", "Input Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 4. Database Insertion (Updated to use our validated variables)
            try {
                java.sql.Connection conn = DatabaseConnection.getConnection();
                String sql = "INSERT INTO events (event_name, event_date, location, max_slots, host_username, description) VALUES (?, ?, ?, ?, ?, ?)";
                java.sql.PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setString(1, txtName.getText());
                stmt.setString(2, formattedDate); // Uses the validated future date
                stmt.setString(3, txtLoc.getText());
                stmt.setInt(4, slots);            // Uses the validated positive integer

                stmt.setString(5, loggedInUser);
                stmt.setString(6, txtDesc.getText());

                stmt.executeUpdate();
                javax.swing.JOptionPane.showMessageDialog(this, "Event Successfully Added!");

                loadComboBoxEvents();
                loadHostedEvents();

            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error adding event: " + e.getMessage());
            }
        }
    }
    
    public AttendeePortal(String username) {
        initComponents(); // This MUST stay at the top to build the UI

        // 1. Assign the passed username to your global variable
        this.loggedInUser = username;

        // 2. Update the label to show the name immediately
        lblWelcome.setText("Welcome to the Portal, " + this.loggedInUser + "!");

        // 3. Initialize your data
        loadAvailableEvents("");
        loadComboBoxEvents();
        loadHostedEvents();

        // 4. Visual formatting for tables and scrolls
        jScrollPane2.getViewport().setBackground(tblAttendees.getBackground());

        tblAttendees.getTableHeader().setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));
        tblHostedEvents.getTableHeader().setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));

        scrollDiscoverEvents.getViewport().setBackground(new java.awt.Color(35, 10, 50));
        scrollDiscoverEvents.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        // Initial load of events
        loadAvailableEvents("Upcoming Events");
    }
    
    public void loadAvailableEvents(String filterSelection) {
       javax.swing.JPanel cardContainer = new javax.swing.JPanel();
        cardContainer.setLayout(new javax.swing.BoxLayout(cardContainer, javax.swing.BoxLayout.Y_AXIS)); 
        cardContainer.setBackground(new java.awt.Color(35, 10, 50)); 
        scrollDiscoverEvents.setViewportView(cardContainer);

        try {
            java.sql.Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                
                // 1. The Base Query (No date rules yet)
                String sql = "SELECT e.event_id, e.event_name, e.max_slots, e.location, e.event_date, " +
                             "(SELECT COUNT(*) FROM registrations r WHERE r.event_id = e.event_id) AS current_participants, " +
                             "CASE WHEN e.event_date < CURDATE() THEN 'CONCLUDED' ELSE 'ACTIVE' END AS event_state " +
                             "FROM events e ";
                
                // 2. The Dynamic Filter Logic!
                if (filterSelection.equals("Ended Events")) {
                    sql += "WHERE e.event_date < CURDATE() ";
                } else if (filterSelection.equals("Upcoming Events")) {
                    sql += "WHERE e.event_date >= CURDATE() ";
                } 
                // If the selection is "All Events", it skips the WHERE clause entirely and shows everything!

                // 3. Finish the query by sorting it
                sql += "ORDER BY e.event_date ASC";
                
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                java.sql.ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    int eventId = rs.getInt("event_id");
                    String eventName = rs.getString("event_name");
                    int maxSlots = rs.getInt("max_slots");
                    int participating = rs.getInt("current_participants");
                    String location = rs.getString("location");
                    java.sql.Date dateSql = rs.getDate("event_date");
                    
                    String slotsText = participating + " / " + maxSlots;
                    String dateText = dateSql != null ? dateSql.toString() : "TBD";
                    String statusState = rs.getString("event_state");

                    EventCardUI card = new EventCardUI(eventId, eventName, slotsText, location, dateText, statusState);

                    card.getViewButton().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            javax.swing.JOptionPane.showMessageDialog(null, "Opening details for: " + eventName);
                        }
                    });

                    cardContainer.add(card);
                }

                rs.close();
                pst.close();
                conn.close();
                
                cardContainer.revalidate();
                cardContainer.repaint();
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void loadComboBoxEvents() {
        try {
            cmbEvents.removeAllItems(); 
            cmbEvents.addItem("Select an Event..."); 
            
            java.sql.Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT event_name FROM events";
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            java.sql.ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                cmbEvents.addItem(rs.getString("event_name"));
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Combo Box Error: " + e.getMessage());
        }
    }
    
    private void loadHostedEvents() {
        try {
            
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblHostedEvents.getModel();
            model.setRowCount(0); 

            java.sql.Connection conn = DatabaseConnection.getConnection();
            
            String sql = "SELECT e.event_id, e.event_name, e.event_date, e.location, e.max_slots, "
                    + "(SELECT COUNT(*) FROM registrations r WHERE r.event_id = e.event_id) AS reg_count "
                    + "FROM events e WHERE e.host_username = ?";
            
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, loggedInUser); 
            
            java.sql.ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("event_id"),
                    rs.getString("event_name"),
                    rs.getString("event_date"),
                    rs.getString("location"),
                    rs.getInt("max_slots"),
                    rs.getInt("reg_count")
                });
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading your hosted events: " + e.getMessage());
        }
    }
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AttendeePortal.class.getName());
    private String loggedInUser;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        scrollDiscoverEvents = new javax.swing.JScrollPane();
        btnRefreshDiscover = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        cmbFilter = new javax.swing.JComboBox<>();
        lblWelcome = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnRefreshHost = new javax.swing.JButton();
        btnAddEvent = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHostedEvents = new javax.swing.JTable();
        txtNewDescription = new javax.swing.JTextField();
        txtdescrip = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtDescription = new javax.swing.JTextField();
        cmbEvents = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAttendees = new javax.swing.JTable();
        btnJoinEvent = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Events Management System");
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1001, 657));
        setResizable(false);

        jTabbedPane1.setBackground(new java.awt.Color(106, 0, 102));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        jTabbedPane1.setOpaque(true);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1001, 657));

        jPanel1.setBackground(new java.awt.Color(232, 212, 183));
        jPanel1.setPreferredSize(new java.awt.Dimension(1001, 657));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        scrollDiscoverEvents.setBorder(null);
        scrollDiscoverEvents.setForeground(new java.awt.Color(35, 10, 50));
        scrollDiscoverEvents.setOpaque(false);
        jPanel1.add(scrollDiscoverEvents, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 600, 320));

        btnRefreshDiscover.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnRefreshDiscover.setForeground(new java.awt.Color(255, 255, 255));
        btnRefreshDiscover.setText("↻ Refresh");
        btnRefreshDiscover.setOpaque(true);
        btnRefreshDiscover.addActionListener(this::btnRefreshDiscoverActionPerformed);
        jPanel1.add(btnRefreshDiscover, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 490, -1, -1));

        btnLogout.setBackground(new java.awt.Color(147, 71, 144));
        btnLogout.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addActionListener(this::btnLogoutActionPerformed);
        jPanel1.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 50, -1, -1));

        cmbFilter.setBackground(new java.awt.Color(102, 0, 153));
        cmbFilter.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        cmbFilter.setForeground(new java.awt.Color(255, 255, 255));
        cmbFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Upcoming Events", "Ended Events", "Full Events", "My Registered Events", " " }));
        cmbFilter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbFilter.addActionListener(this::cmbFilterActionPerformed);
        jPanel1.add(cmbFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 600, 20));

        lblWelcome.setFont(new java.awt.Font("Baskerville Old Face", 1, 24)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lblWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 550, 60));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bg homescreen .png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 620));

        jTabbedPane1.addTab("Discover Events", jPanel1);

        jPanel2.setBackground(new java.awt.Color(232, 212, 183));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnRefreshHost.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnRefreshHost.setForeground(new java.awt.Color(255, 255, 255));
        btnRefreshHost.setText("↻ Refresh");
        btnRefreshHost.setOpaque(true);
        btnRefreshHost.addActionListener(this::btnRefreshHostActionPerformed);
        jPanel2.add(btnRefreshHost, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, -1, -1));

        btnAddEvent.setBackground(new java.awt.Color(147, 71, 144));
        btnAddEvent.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnAddEvent.setForeground(new java.awt.Color(255, 255, 255));
        btnAddEvent.setText("Create Event");
        btnAddEvent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddEvent.addActionListener(this::btnAddEventActionPerformed);
        jPanel2.add(btnAddEvent, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 510, 150, -1));

        btnDelete1.setBackground(new java.awt.Color(147, 71, 144));
        btnDelete1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnDelete1.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete1.setText("Delete");
        btnDelete1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete1.addActionListener(this::btnDelete1ActionPerformed);
        jPanel2.add(btnDelete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 470, 120, -1));

        btnUpdate.setBackground(new java.awt.Color(147, 71, 144));
        btnUpdate.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);
        jPanel2.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 470, 120, -1));

        tblHostedEvents.setBackground(new java.awt.Color(102, 0, 102));
        tblHostedEvents.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        tblHostedEvents.setForeground(new java.awt.Color(255, 255, 255));
        tblHostedEvents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Event ID", "Event Name", "Event Date", "Location", "Max Slots", "Registrant"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHostedEvents.setFillsViewportHeight(true);
        tblHostedEvents.setShowGrid(true);
        tblHostedEvents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHostedEventsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHostedEvents);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 550, 500));

        txtNewDescription.setEditable(false);
        txtNewDescription.setBackground(new java.awt.Color(102, 0, 102));
        txtNewDescription.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        txtNewDescription.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(txtNewDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, 381, 360));

        txtdescrip.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        txtdescrip.setForeground(new java.awt.Color(255, 255, 255));
        txtdescrip.setText("Description");
        jPanel2.add(txtdescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 60, 100, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bg homescreen .png"))); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 620));

        jTabbedPane1.addTab("Host Event", jPanel2);

        jPanel3.setBackground(new java.awt.Color(232, 212, 183));
        jPanel3.setPreferredSize(new java.awt.Dimension(1001, 657));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtDescription.setEditable(false);
        txtDescription.setBackground(new java.awt.Color(102, 0, 102));
        txtDescription.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        txtDescription.setForeground(new java.awt.Color(255, 255, 255));
        txtDescription.addActionListener(this::txtDescriptionActionPerformed);
        jPanel3.add(txtDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 400, 570, 140));

        cmbEvents.setBackground(new java.awt.Color(102, 0, 153));
        cmbEvents.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        cmbEvents.setForeground(new java.awt.Color(255, 255, 255));
        cmbEvents.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEvents.addActionListener(this::cmbEventsActionPerformed);
        jPanel3.add(cmbEvents, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 440, 220, -1));

        jScrollPane2.setOpaque(false);

        tblAttendees.setBackground(new java.awt.Color(102, 0, 102));
        tblAttendees.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        tblAttendees.setForeground(new java.awt.Color(255, 255, 255));
        tblAttendees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Reg ID", "First Name", "Last Name", "Email", "Phone Number", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAttendees.setFillsViewportHeight(true);
        tblAttendees.setShowGrid(true);
        jScrollPane2.setViewportView(tblAttendees);
        if (tblAttendees.getColumnModel().getColumnCount() > 0) {
            tblAttendees.getColumnModel().getColumn(3).setPreferredWidth(200);
        }

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 820, 310));

        btnJoinEvent.setBackground(new java.awt.Color(147, 71, 144));
        btnJoinEvent.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnJoinEvent.setForeground(new java.awt.Color(255, 255, 255));
        btnJoinEvent.setText("Join");
        btnJoinEvent.addActionListener(this::btnJoinEventActionPerformed);
        jPanel3.add(btnJoinEvent, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 480, 80, -1));

        btnCancel.setBackground(new java.awt.Color(147, 71, 144));
        btnCancel.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(this::btnCancelActionPerformed);
        jPanel3.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 480, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bg homescreen .png"))); // NOI18N
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 620));

        jTabbedPane1.addTab("Events Information", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Logout Confirmation",
            JOptionPane.YES_NO_OPTION);

        // 2. If the user clicks "Yes"
        if (confirm == JOptionPane.YES_OPTION) {
            // Create a new instance of the Login Screen
            LoginScreen login = new LoginScreen();

            // Make the Login Screen visible
            login.setVisible(true);

            // Close (dispose) the current Dashboard Screen
            this.dispose();
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void cmbEventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEventsActionPerformed
// 1. Grab the name they just clicked
        String selectedEvent = (String) cmbEvents.getSelectedItem();

        // Safety check: Did they click the placeholder or empty space?
        if (selectedEvent == null || selectedEvent.equals("Select an Event...")) {
            txtDescription.setText(""); // Clear the box
            ((javax.swing.table.DefaultTableModel) tblAttendees.getModel()).setRowCount(0); // Clear the table
            return; 
        }

        try {
            java.sql.Connection conn = DatabaseConnection.getConnection();

            // --- TASK 1: LOAD THE DESCRIPTION ---
            String descSql = "SELECT description FROM events WHERE event_name = ?";
            java.sql.PreparedStatement descStmt = conn.prepareStatement(descSql);
            descStmt.setString(1, selectedEvent);
            java.sql.ResultSet descRs = descStmt.executeQuery();

            if (descRs.next()) {
                String desc = descRs.getString("description");
                // If the description is blank in SQL, we put a nice placeholder message
                if (desc == null || desc.trim().isEmpty()) {
                    txtDescription.setText("No description has been provided for this event yet.");
                } else {
                    txtDescription.setText(desc);
                }
            }

            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblAttendees.getModel();
            model.setRowCount(0);

            // Make sure these column names match your database exactly
            // Change your rosterSql to this:
            String rosterSql = "SELECT r.registration_id, r.first_name, r.last_name, r.email, r.contact_number, r.attendance_status "
                    + "FROM registrations r "
                    + "JOIN events e ON r.event_id = e.event_id "
                    + "WHERE e.event_name = ?";
            
            java.sql.PreparedStatement rosterStmt = conn.prepareStatement(rosterSql);
            rosterStmt.setString(1, selectedEvent);
            java.sql.ResultSet rosterRs = rosterStmt.executeQuery();

            while (rosterRs.next()) {
                model.addRow(new Object[]{
                    rosterRs.getInt("registration_id"),
                    rosterRs.getString("first_name"),
                    rosterRs.getString("last_name"),
                    rosterRs.getString("email"),
                    rosterRs.getString("contact_number"),
                    rosterRs.getString("attendance_status")
                });
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading event details: " + e.getMessage());
        }      
    }//GEN-LAST:event_cmbEventsActionPerformed

    private void cmbFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFilterActionPerformed
    // Tell Java exactly what was clicked, then grab its text!
    javax.swing.JComboBox sourceCombo = (javax.swing.JComboBox) evt.getSource();
    String selectedFilter = sourceCombo.getSelectedItem().toString();
    loadAvailableEvents(selectedFilter);
    }//GEN-LAST:event_cmbFilterActionPerformed

    private void btnJoinEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinEventActionPerformed
       String selectedEvent = (String) cmbEvents.getSelectedItem();
        
        if (selectedEvent == null || selectedEvent.equals("Select an Event...")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select an event from the dropdown first!");
            return;
        }

        try {
            java.sql.Connection conn = DatabaseConnection.getConnection();

            String firstName = "";
            String lastName = "";
            java.sql.PreparedStatement userStmt = conn.prepareStatement("SELECT first_name, last_name FROM users WHERE username = ?");
            userStmt.setString(1, loggedInUser);
            java.sql.ResultSet userRs = userStmt.executeQuery();
            if (userRs.next()) {
                firstName = userRs.getString("first_name");
                lastName = userRs.getString("last_name");
            }

            int eventId = -1;
            java.sql.PreparedStatement eventStmt = conn.prepareStatement("SELECT event_id FROM events WHERE event_name = ?");
            eventStmt.setString(1, selectedEvent);
            java.sql.ResultSet eventRs = eventStmt.executeQuery();
            if (eventRs.next()) {
                eventId = eventRs.getInt("event_id");
            }

            String checkSql = "SELECT * FROM registrations WHERE event_id = ? AND first_name = ? AND last_name = ?";
            java.sql.PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, eventId);
            checkStmt.setString(2, firstName);
            checkStmt.setString(3, lastName);
            if (checkStmt.executeQuery().next()) {
                javax.swing.JOptionPane.showMessageDialog(this, "You are already registered for this event!");
                return; 
            }
            
            String checkCapacitySql = "SELECT e.max_slots, (SELECT COUNT(*) FROM registrations r WHERE r.event_id = e.event_id) AS current_count "
                    + "FROM events e WHERE e.event_name = ?";
            java.sql.PreparedStatement capacityStmt = conn.prepareStatement(checkCapacitySql);
            capacityStmt.setString(1, selectedEvent);
            java.sql.ResultSet rs = capacityStmt.executeQuery();

            if (rs.next()) {
                if (rs.getInt("current_count") >= rs.getInt("max_slots")) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Registration Failed: Event is full!", "Event Full", javax.swing.JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            String email = javax.swing.JOptionPane.showInputDialog(this, "Enter your Email Address to register:");
            if (email == null || email.trim().isEmpty()) return;
            
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            if (!email.matches(emailRegex)) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Registration Failed: Please enter a valid email address (e.g., name@example.com).",
                        "Invalid Email Format",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return; // Stops the code from continuing
            }

            String contact = javax.swing.JOptionPane.showInputDialog(this, "Enter your Contact Number:");
            if (contact == null || contact.trim().isEmpty()) return; 
            
            if (contact.length() != 11 || !contact.matches("\\d+")) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Registration Failed: Contact number must be exactly 11 digits and contain only numbers.", 
                "Invalid Contact Number", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return; // Stops the code from reaching the INSERT statement
        }

            String insertSql = "INSERT INTO registrations (event_id, first_name, last_name, email, contact_number, attendance_status) VALUES (?, ?, ?, ?, ?, 'Confirmed')";
            java.sql.PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, eventId);
            insertStmt.setString(2, firstName);
            insertStmt.setString(3, lastName);
            insertStmt.setString(4, email);
            insertStmt.setString(5, contact);
            insertStmt.executeUpdate();

            javax.swing.JOptionPane.showMessageDialog(this, "Successfully joined the event!");
            cmbEventsActionPerformed(null);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Registration Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnJoinEventActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed

    String selectedEvent = cmbEvents.getSelectedItem().toString();

    if (selectedEvent.equals("Select an Event...") || selectedEvent.trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Please select an event first.", "No Event Selected", javax.swing.JOptionPane.WARNING_MESSAGE);
        return; 
    }

    int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to cancel your registration for this event?", 
            "Confirm Cancellation", 
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.WARNING_MESSAGE);

    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        try {
            java.sql.Connection conn = DatabaseConnection.getConnection();

            if (conn != null) {
              
                String sql = "DELETE FROM registrations " +
                             "WHERE event_id = (SELECT event_id FROM events WHERE event_name = ?) " +
                             "AND first_name = (SELECT first_name FROM users WHERE username = ?) " +
                             "AND last_name = (SELECT last_name FROM users WHERE username = ?)";
                
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                
                pst.setString(1, selectedEvent); 
                
                pst.setString(2, this.loggedInUser); 

                pst.setString(3, this.loggedInUser); 

                int rowsDeleted = pst.executeUpdate();

                if (rowsDeleted > 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Registration cancelled successfully!", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "You are not currently registered for this event.", "Notice", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }

                pst.close();
                conn.close();
                cmbEventsActionPerformed(null);
            }
        } catch (java.sql.SQLException sqlErr) {
            javax.swing.JOptionPane.showMessageDialog(this, "Database Error while cancelling.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            sqlErr.printStackTrace();
        }
    }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEventActionPerformed
        performCreate();
    }//GEN-LAST:event_btnAddEventActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        performDelete();
    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
     // 1. Check if they actually clicked a row
        int selectedRow = tblHostedEvents.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select an event from the table first!");
            return;
        }

        try {
            // 2. Grab the current data from the table (Assuming Event ID is Column 0)
            int eventId = Integer.parseInt(tblHostedEvents.getValueAt(selectedRow, 0).toString());
            String currentName = tblHostedEvents.getValueAt(selectedRow, 1).toString();
            String currentDate = tblHostedEvents.getValueAt(selectedRow, 2).toString();
            String currentLocation = tblHostedEvents.getValueAt(selectedRow, 3).toString();
            String currentSlots = tblHostedEvents.getValueAt(selectedRow, 4).toString();
            
            int registrantCount = Integer.parseInt(tblHostedEvents.getValueAt(selectedRow, 5).toString());

            // Split the YYYY-MM-DD date back into 3 pieces for your specific UI
            String[] dateParts = currentDate.split("-");
            String yyyy = (dateParts.length == 3) ? dateParts[0] : "";
            String mm = (dateParts.length == 3) ? dateParts[1] : "";
            String dd = (dateParts.length == 3) ? dateParts[2] : "";

            // 3. Get the current description directly from the database
            String currentDesc = "";
            java.sql.Connection conn = DatabaseConnection.getConnection();
            String descSql = "SELECT description FROM events WHERE event_id = ?";
            java.sql.PreparedStatement descStmt = conn.prepareStatement(descSql);
            descStmt.setInt(1, eventId);
            java.sql.ResultSet rs = descStmt.executeQuery();
            if (rs.next()) {
                currentDesc = rs.getString("description");
            }

            // 4. Create the text fields, but PRE-FILL them with the current data
            javax.swing.JTextField txtName = new javax.swing.JTextField(currentName);
            javax.swing.JTextField txtYear = new javax.swing.JTextField(yyyy, 4);
            javax.swing.JTextField txtMonth = new javax.swing.JTextField(mm, 2);
            javax.swing.JTextField txtDay = new javax.swing.JTextField(dd, 2);
            
            javax.swing.JPanel datePanel = new javax.swing.JPanel();
            datePanel.add(txtYear); datePanel.add(new javax.swing.JLabel("-"));
            datePanel.add(txtMonth); datePanel.add(new javax.swing.JLabel("-"));
            datePanel.add(txtDay);
            
            javax.swing.JTextField txtLoc = new javax.swing.JTextField(currentLocation);
            javax.swing.JTextField txtSlots = new javax.swing.JTextField(currentSlots);

            javax.swing.JTextArea txtDesc = new javax.swing.JTextArea(currentDesc, 3, 20);
            txtDesc.setLineWrap(true); txtDesc.setWrapStyleWord(true);
            javax.swing.JScrollPane descScroll = new javax.swing.JScrollPane(txtDesc);

            // 5. Bundle it all together
            Object[] formFields = {
                "Event Name:", txtName,
                "Event Date (YYYY-MM-DD):", datePanel,
                "Location:", txtLoc,
                "Max Slots:", txtSlots,
                "Event Description:", descScroll
            };

            // 6. Trigger the pop-up window
            int result = javax.swing.JOptionPane.showConfirmDialog(this, formFields, "Update Event Details", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);

            // 7. If the user clicks "OK", update the database
            if (result == javax.swing.JOptionPane.OK_OPTION) {
                
                String formattedDate = txtYear.getText() + "-" + txtMonth.getText() + "-" + txtDay.getText();

                // If the date is different AND people have already joined...
                if (!formattedDate.equals(currentDate) && registrantCount > 0) {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "Update Denied: You cannot change the date because " + registrantCount + " users have already registered!",
                            "Update Blocked",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                    return; // STOP the update here
                }
                
                String updateSql = "UPDATE events SET event_name = ?, event_date = ?, location = ?, max_slots = ?, description = ? WHERE event_id = ?";
                java.sql.PreparedStatement updateStmt = conn.prepareStatement(updateSql);

                updateStmt.setString(1, txtName.getText());
                updateStmt.setString(2, formattedDate);
                updateStmt.setString(3, txtLoc.getText());
                updateStmt.setInt(4, Integer.parseInt(txtSlots.getText()));
                updateStmt.setString(5, txtDesc.getText());
                updateStmt.setInt(6, eventId);

                updateStmt.executeUpdate();
                javax.swing.JOptionPane.showMessageDialog(this, "Event Successfully Updated!");

                // 8. INSTANT REFRESH
                loadHostedEvents();
                loadComboBoxEvents();
                
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error updating event: " + e.getMessage());
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblHostedEventsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHostedEventsMouseClicked
                                                   
        int selectedRow = tblHostedEvents.getSelectedRow();
        if (selectedRow == -1) return;

        // Grab the hidden Event ID from Column 0
        int eventId = Integer.parseInt(tblHostedEvents.getValueAt(selectedRow, 0).toString());

        try {
            java.sql.Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT description FROM events WHERE event_id = ?";
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, eventId);
            java.sql.ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String desc = rs.getString("description");
                if (desc == null || desc.trim().isEmpty()) {
                    txtNewDescription.setText("No description provided.");
                } else {
                    txtNewDescription.setText(desc);
                }
            }
        } catch (Exception e) {
            txtNewDescription.setText("Error loading description.");
        }
    }//GEN-LAST:event_tblHostedEventsMouseClicked

    private void btnRefreshDiscoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshDiscoverActionPerformed

    String currentFilter = cmbFilter.getSelectedItem().toString();
    loadAvailableEvents(currentFilter);
    }//GEN-LAST:event_btnRefreshDiscoverActionPerformed

    private void btnRefreshHostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshHostActionPerformed
// 1. Refresh the table data
    loadHostedEvents();
    
    // 2. Also refresh the ComboBox on the third tab just to keep everything perfectly synced
    loadComboBoxEvents();
    
    // 3. Clear the description box on the right so it doesn't show old data
    txtNewDescription.setText("");    }//GEN-LAST:event_btnRefreshHostActionPerformed

    private void txtDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescriptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescriptionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> new AttendeePortal("Test User").setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddEvent;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete1;
    private javax.swing.JButton btnJoinEvent;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnRefreshDiscover;
    private javax.swing.JButton btnRefreshHost;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbEvents;
    private javax.swing.JComboBox<String> cmbFilter;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JScrollPane scrollDiscoverEvents;
    private javax.swing.JTable tblAttendees;
    private javax.swing.JTable tblHostedEvents;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtNewDescription;
    private javax.swing.JLabel txtdescrip;
    // End of variables declaration//GEN-END:variables
}
