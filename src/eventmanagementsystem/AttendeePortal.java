/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package eventmanagementsystem;

import java.sql.Connection;
import java.awt.HeadlessException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;

public class AttendeePortal extends javax.swing.JFrame {
    
    public AttendeePortal(String username) {
        initComponents();
        
        this.loggedInUser = username;
        lblWelcome.setText("Welcome to the Portal, " + loggedInUser + "!");
        
        loadAvailableEvents();
        loadComboBoxEvents();
        loadHostedEvents();
        
        jScrollPane1.getViewport().setBackground(tblEvents.getBackground());
        jScrollPane2.getViewport().setBackground(tblAttendees.getBackground());
        
        tblEvents.getTableHeader().setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));
        tblAttendees.getTableHeader().setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));
    }
    
    private void loadComboBoxEvents() {
        try {
            cmbEvents.removeAllItems(); // Clear default items
            cmbEvents.addItem("Select an Event..."); // Placeholder
            
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
            // 1. Get the table model and wipe it clean
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblHostedEvents.getModel();
            model.setRowCount(0); 

            // 2. Connect to the database
            java.sql.Connection conn = DatabaseConnection.getConnection();
            
            // 3. The SQL Query: Only select events where the host is the logged-in user
            String sql = "SELECT event_id, event_name, event_date, location, max_slots FROM events WHERE host_username = ?";
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, loggedInUser); // This guarantees they only see THEIR events!
            
            java.sql.ResultSet rs = stmt.executeQuery();

            // 4. Loop through the results and add them to the table
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("event_id"),
                    rs.getString("event_name"),
                    rs.getString("event_date"),
                    rs.getString("location"),
                    rs.getInt("max_slots")
                });
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading your hosted events: " + e.getMessage());
        }
    }
    
    private void loadAvailableEvents() {
       try {
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblEvents.getModel();
            model.setRowCount(0); 

            java.sql.Connection conn = DatabaseConnection.getConnection();
            
            String sql = "SELECT event_id, event_name, event_date, location, max_slots FROM events"; 
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            java.sql.ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("event_id"),
                    rs.getString("event_name"),
                    rs.getString("event_date"), 
                    rs.getString("location"),
                    rs.getInt("max_slots")
                });
            }
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Discover Events Error: " + e.getMessage());
        }
    }
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AttendeePortal.class.getName());
    private String loggedInUser;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEvents = new javax.swing.JTable();
        btnLogout = new javax.swing.JButton();
        lblWelcome = new javax.swing.JLabel();
        cmbFilter = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtDescription = new javax.swing.JTextField();
        cmbEvents = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAttendees = new javax.swing.JTable();
        btnJoinEvent = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnAddEvent = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHostedEvents = new javax.swing.JTable();
        txtNewDescription = new javax.swing.JTextField();
        txtdescrip = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

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
        jPanel1.setPreferredSize(new java.awt.Dimension(1001, 600));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setOpaque(false);

        tblEvents.setBackground(new java.awt.Color(102, 0, 102));
        tblEvents.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        tblEvents.setForeground(new java.awt.Color(255, 255, 255));
        tblEvents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Event ID", "Event Name", "Event Date", "Location", "Max Slots"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEvents.setFillsViewportHeight(true);
        tblEvents.setOpaque(false);
        tblEvents.setSelectionBackground(new java.awt.Color(102, 0, 153));
        tblEvents.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblEvents.setShowGrid(true);
        jScrollPane1.setViewportView(tblEvents);
        if (tblEvents.getColumnModel().getColumnCount() > 0) {
            tblEvents.getColumnModel().getColumn(0).setResizable(false);
            tblEvents.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 605, 380));

        btnLogout.setBackground(new java.awt.Color(147, 71, 144));
        btnLogout.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addActionListener(this::btnLogoutActionPerformed);
        jPanel1.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 50, -1, -1));

        lblWelcome.setFont(new java.awt.Font("Baskerville Old Face", 1, 24)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(255, 255, 255));
        lblWelcome.setText("Welcome User!");
        jPanel1.add(lblWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, 260, 60));

        cmbFilter.setBackground(new java.awt.Color(102, 0, 153));
        cmbFilter.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        cmbFilter.setForeground(new java.awt.Color(255, 255, 255));
        cmbFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Upcoming Events", "Ended Events", "Full Events", "My Registered Events", " " }));
        cmbFilter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbFilter.addActionListener(this::cmbFilterActionPerformed);
        jPanel1.add(cmbFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 605, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bg homescreen .png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 620));

        jTabbedPane1.addTab("Discover Events", jPanel1);

        jPanel3.setBackground(new java.awt.Color(232, 212, 183));
        jPanel3.setPreferredSize(new java.awt.Dimension(1001, 600));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtDescription.setEditable(false);
        txtDescription.setBackground(new java.awt.Color(102, 0, 102));
        txtDescription.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        txtDescription.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 429, 300));

        cmbEvents.setBackground(new java.awt.Color(102, 0, 153));
        cmbEvents.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        cmbEvents.setForeground(new java.awt.Color(255, 255, 255));
        cmbEvents.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEvents.addActionListener(this::cmbEventsActionPerformed);
        jPanel3.add(cmbEvents, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 240, -1));

        jScrollPane2.setOpaque(false);

        tblAttendees.setBackground(new java.awt.Color(102, 0, 102));
        tblAttendees.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        tblAttendees.setForeground(new java.awt.Color(255, 255, 255));
        tblAttendees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Reg ID", "First Name", "Last Name", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
            tblAttendees.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, 440, 460));

        btnJoinEvent.setBackground(new java.awt.Color(147, 71, 144));
        btnJoinEvent.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnJoinEvent.setForeground(new java.awt.Color(255, 255, 255));
        btnJoinEvent.setText("Join");
        btnJoinEvent.addActionListener(this::btnJoinEventActionPerformed);
        jPanel3.add(btnJoinEvent, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 510, 80, -1));

        btnCancel.setBackground(new java.awt.Color(147, 71, 144));
        btnCancel.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(this::btnCancelActionPerformed);
        jPanel3.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 510, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bg homescreen .png"))); // NOI18N
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 620));

        jTabbedPane1.addTab("Events Information", jPanel3);

        jPanel2.setBackground(new java.awt.Color(232, 212, 183));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAddEvent.setBackground(new java.awt.Color(147, 71, 144));
        btnAddEvent.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnAddEvent.setForeground(new java.awt.Color(255, 255, 255));
        btnAddEvent.setText("Create Event");
        btnAddEvent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddEvent.addActionListener(this::btnAddEventActionPerformed);
        jPanel2.add(btnAddEvent, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 510, 150, -1));

        btnDelete1.setBackground(new java.awt.Color(147, 71, 144));
        btnDelete1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnDelete1.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete1.setText("Delete");
        btnDelete1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete1.addActionListener(this::btnDelete1ActionPerformed);
        jPanel2.add(btnDelete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 470, 120, -1));

        btnUpdate.setBackground(new java.awt.Color(147, 71, 144));
        btnUpdate.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);
        jPanel2.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 470, 120, -1));

        tblHostedEvents.setBackground(new java.awt.Color(102, 0, 102));
        tblHostedEvents.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        tblHostedEvents.setForeground(new java.awt.Color(255, 255, 255));
        tblHostedEvents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Event ID", "Event Name", "Event Date", "Location", "Max Slots"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 435, 500));

        txtNewDescription.setEditable(false);
        txtNewDescription.setBackground(new java.awt.Color(102, 0, 102));
        txtNewDescription.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        txtNewDescription.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(txtNewDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(498, 87, 381, 360));

        txtdescrip.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        txtdescrip.setForeground(new java.awt.Color(255, 255, 255));
        txtdescrip.setText("Description");
        jPanel2.add(txtdescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, 100, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bg homescreen .png"))); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 620));

        jTabbedPane1.addTab("Host Event", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
                .addGap(16, 16, 16))
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

            // --- TASK 2: LOAD THE ATTENDEE ROSTER ---
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblAttendees.getModel();
            model.setRowCount(0); // Wipe the old roster clean

            // We use a JOIN statement to find registrations linked to this specific event name
            String rosterSql = "SELECT r.registration_id, r.first_name, r.last_name, r.attendance_status " +
                               "FROM registrations r JOIN events e ON r.event_id = e.event_id " +
                               "WHERE e.event_name = ?";
            
            java.sql.PreparedStatement rosterStmt = conn.prepareStatement(rosterSql);
            rosterStmt.setString(1, selectedEvent);
            java.sql.ResultSet rosterRs = rosterStmt.executeQuery();

            while (rosterRs.next()) {
                model.addRow(new Object[]{
                    rosterRs.getInt("registration_id"),
                    rosterRs.getString("first_name"),
                    rosterRs.getString("last_name"),
                    rosterRs.getString("attendance_status")
                });
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading event details: " + e.getMessage());
        }      
    }//GEN-LAST:event_cmbEventsActionPerformed

    private void cmbFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFilterActionPerformed
        String filter = (String) cmbFilter.getSelectedItem();
        if (filter == null) return;

        try {
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblEvents.getModel();
            model.setRowCount(0); // Wipe the table clean for the new filtered data

            java.sql.Connection conn = DatabaseConnection.getConnection();
            String sql = "";
            java.sql.PreparedStatement stmt = null;

            // We need to grab their real name just in case they select "My Registered Events"
            String firstName = "";
            String lastName = "";
            if (filter.equals("My Registered Events")) {
                java.sql.PreparedStatement nameStmt = conn.prepareStatement("SELECT first_name, last_name FROM users WHERE username = ?");
                nameStmt.setString(1, loggedInUser);
                java.sql.ResultSet nameRs = nameStmt.executeQuery();
                if (nameRs.next()) {
                    firstName = nameRs.getString("first_name");
                    lastName = nameRs.getString("last_name");
                }
            }

            // --- THE SQL SWITCHBOARD ---
            if (filter.equals("Upcoming Events")) {
                // Only show events where the date is today or in the future
                sql = "SELECT event_id, event_name, event_date, location, max_slots FROM events WHERE event_date >= CURRENT_DATE";
                stmt = conn.prepareStatement(sql);
            } 
            else if (filter.equals("Ended Events")) {
                // The Archive: Only show events where the date is strictly in the past
                sql = "SELECT event_id, event_name, event_date, location, max_slots FROM events WHERE event_date < CURRENT_DATE";
                stmt = conn.prepareStatement(sql);
            } 
            else if (filter.equals("Full Events")) {
                // Uses an SQL subquery to count attendees and see if it equals or exceeds max_slots
                sql = "SELECT e.event_id, e.event_name, e.event_date, e.location, e.max_slots FROM events e " +
                      "WHERE e.max_slots <= (SELECT COUNT(*) FROM registrations r WHERE r.event_id = e.event_id)";
                stmt = conn.prepareStatement(sql);
            } 
            else if (filter.equals("My Registered Events")) {
                // Uses a JOIN to find events specifically tied to this user's name
                sql = "SELECT e.event_id, e.event_name, e.event_date, e.location, e.max_slots " +
                      "FROM events e JOIN registrations r ON e.event_id = r.event_id " +
                      "WHERE r.first_name = ? AND r.last_name = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
            } 
            else {
                // Fallback: Show everything just in case
                sql = "SELECT event_id, event_name, event_date, location, max_slots FROM events";
                stmt = conn.prepareStatement(sql);
            }

            // Execute whichever query was chosen above
            java.sql.ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("event_id"),
                    rs.getString("event_name"),
                    rs.getString("event_date"), // Make sure this matches your DB spelling!
                    rs.getString("location"),   // Make sure this matches your DB spelling!
                    rs.getInt("max_slots")
                });
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Filter Error: " + e.getMessage());
        }
    }//GEN-LAST:event_cmbFilterActionPerformed

    private void btnJoinEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinEventActionPerformed
       String selectedEvent = (String) cmbEvents.getSelectedItem();
        
        if (selectedEvent == null || selectedEvent.equals("Select an Event...")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select an event from the dropdown first!");
            return;
        }

        try {
            java.sql.Connection conn = DatabaseConnection.getConnection();

            // --- 1. GET THE USER'S REAL NAME ---
            String firstName = "";
            String lastName = "";
            java.sql.PreparedStatement userStmt = conn.prepareStatement("SELECT first_name, last_name FROM users WHERE username = ?");
            userStmt.setString(1, loggedInUser);
            java.sql.ResultSet userRs = userStmt.executeQuery();
            if (userRs.next()) {
                firstName = userRs.getString("first_name");
                lastName = userRs.getString("last_name");
            }

            // --- 2. GET THE EVENT ID ---
            int eventId = -1;
            java.sql.PreparedStatement eventStmt = conn.prepareStatement("SELECT event_id FROM events WHERE event_name = ?");
            eventStmt.setString(1, selectedEvent);
            java.sql.ResultSet eventRs = eventStmt.executeQuery();
            if (eventRs.next()) {
                eventId = eventRs.getInt("event_id");
            }

            // --- 3. THE ANTI-SPAM CHECK (No duplicates allowed) ---
            String checkSql = "SELECT * FROM registrations WHERE event_id = ? AND first_name = ? AND last_name = ?";
            java.sql.PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, eventId);
            checkStmt.setString(2, firstName);
            checkStmt.setString(3, lastName);
            if (checkStmt.executeQuery().next()) {
                javax.swing.JOptionPane.showMessageDialog(this, "You are already registered for this event!");
                return; // Stop them here!
            }

            // --- 4. THE CLEAN POP-UP INPUTS ---
            String email = javax.swing.JOptionPane.showInputDialog(this, "Enter your Email Address to register:");
            if (email == null || email.trim().isEmpty()) return; // If they click Cancel, stop the process

            String contact = javax.swing.JOptionPane.showInputDialog(this, "Enter your Contact Number:");
            if (contact == null || contact.trim().isEmpty()) return; // If they click Cancel, stop the process

            // --- 5. EXECUTE THE REGISTRATION ---
            String insertSql = "INSERT INTO registrations (event_id, first_name, last_name, email, contact_number, attendance_status) VALUES (?, ?, ?, ?, ?, 'Confirmed')";
            java.sql.PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, eventId);
            insertStmt.setString(2, firstName);
            insertStmt.setString(3, lastName);
            insertStmt.setString(4, email);
            insertStmt.setString(5, contact);
            insertStmt.executeUpdate();

            javax.swing.JOptionPane.showMessageDialog(this, "Successfully joined the event!");

            // --- 6. INSTANT UI REFRESH ---
            // We call the dropdown action method again to magically refresh the roster table!
            cmbEventsActionPerformed(null); 

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Registration Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnJoinEventActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
// 1. THIS IS THE LINE THAT WENT MISSING! It grabs the text from the dropdown.
    // (Note: Make sure 'cmbEvents' matches your actual dropdown variable name!)
    String selectedEvent = cmbEvents.getSelectedItem().toString();

    // 2. Stop them if they haven't picked an event yet
    if (selectedEvent.equals("Select an Event...") || selectedEvent.trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Please select an event first.", "No Event Selected", javax.swing.JOptionPane.WARNING_MESSAGE);
        return; 
    }

    // 3. The Safety Net: Ask for confirmation
    int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to cancel your registration for this event?", 
            "Confirm Cancellation", 
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.WARNING_MESSAGE);

    // 4. If they clicked "Yes", talk to the database with our new Subquery SQL!
    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        try {
            java.sql.Connection conn = DatabaseConnection.getConnection();

            if (conn != null) {
                // This SQL precisely matches the Event ID, First Name, and Last Name
                String sql = "DELETE FROM registrations " +
                             "WHERE event_id = (SELECT event_id FROM events WHERE event_name = ?) " +
                             "AND first_name = (SELECT first_name FROM users WHERE username = ?) " +
                             "AND last_name = (SELECT last_name FROM users WHERE username = ?)";
                
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                
                // 1. Pass the event name from the dropdown (Java now knows what this is!)
                pst.setString(1, selectedEvent); 
                
                // 2. Pass the logged in username to find their first name
                pst.setString(2, this.loggedInUser); 

                // 3. Pass the logged in username again to find their last name
                pst.setString(3, this.loggedInUser); 

                int rowsDeleted = pst.executeUpdate();

                if (rowsDeleted > 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Registration cancelled successfully!", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "You are not currently registered for this event.", "Notice", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }

                pst.close();
                conn.close();
            }
        } catch (java.sql.SQLException sqlErr) {
            javax.swing.JOptionPane.showMessageDialog(this, "Database Error while cancelling.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            sqlErr.printStackTrace();
        }
    }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEventActionPerformed
      // 1. Create the text fields for the pop-up
        javax.swing.JTextField txtName = new javax.swing.JTextField();
        javax.swing.JTextField txtYear = new javax.swing.JTextField("2026", 4);
        javax.swing.JTextField txtMonth = new javax.swing.JTextField(2);
        javax.swing.JTextField txtDay = new javax.swing.JTextField(2);
        
        // Group them side-by-side
        javax.swing.JPanel datePanel = new javax.swing.JPanel();
        datePanel.add(txtYear);
        datePanel.add(new javax.swing.JLabel("-"));
        datePanel.add(txtMonth);
        datePanel.add(new javax.swing.JLabel("-"));
        datePanel.add(txtDay);
        
        javax.swing.JTextField txtLoc = new javax.swing.JTextField();
        javax.swing.JTextField txtSlots = new javax.swing.JTextField();

        // --- NEW: THE DESCRIPTION BOX ---
        javax.swing.JTextArea txtDesc = new javax.swing.JTextArea(3, 20); // 3 rows tall
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        javax.swing.JScrollPane descScroll = new javax.swing.JScrollPane(txtDesc);

        // 2. Bundle the labels and fields together
        Object[] formFields = {
            "Event Name:", txtName,
            "Event Date (YYYY-MM-DD):", datePanel,
            "Location/Venue:", txtLoc,
            "Max Slots:", txtSlots,
            "Event Description:", descScroll // Added the new box to your pop-up!
        };

        // 3. Trigger the pop-up window
        int result = javax.swing.JOptionPane.showConfirmDialog(this, formFields, "Add New Event", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);

        // 4. If the user clicks "OK"
        if (result == javax.swing.JOptionPane.OK_OPTION) {

            // Validation
            if (txtName.getText().trim().isEmpty() || txtYear.getText().trim().isEmpty() || txtMonth.getText().trim().isEmpty() || txtDay.getText().trim().isEmpty() || txtLoc.getText().trim().isEmpty() || txtSlots.getText().trim().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "All fields are required!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return; 
            }

            // Save to Database
            try {
                java.sql.Connection conn = DatabaseConnection.getConnection();
                
                // --- UPGRADED SQL: Added host_username and description ---
                String sql = "INSERT INTO events (event_name, event_date, location, max_slots, host_username, description) VALUES (?, ?, ?, ?, ?, ?)";
                java.sql.PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setString(1, txtName.getText());
                String formattedDate = txtYear.getText() + "-" + txtMonth.getText() + "-" + txtDay.getText();
                stmt.setString(2, formattedDate);
                stmt.setString(3, txtLoc.getText());
                stmt.setInt(4, Integer.parseInt(txtSlots.getText()));
                
                // --- THE NEW DATA ---
                stmt.setString(5, loggedInUser); // This seamlessly attaches the host!
                stmt.setString(6, txtDesc.getText());

                stmt.executeUpdate();
                javax.swing.JOptionPane.showMessageDialog(this, "Event Successfully Added!");

                // --- INSTANT REFRESH ---
                // Replaces your old loadEventsTable() with the new unified architecture refreshers
                loadComboBoxEvents();
                cmbFilterActionPerformed(null);
                loadHostedEvents();

            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error adding event: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnAddEventActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
       // 1. Check if they actually clicked a row in the table
        int selectedRow = tblHostedEvents.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select an event from the table first!");
            return;
        }

        // 2. Grab the Event Name from the clicked row (Assuming Event Name is in Column 1)
        String selectedEvent = tblHostedEvents.getValueAt(selectedRow, 1).toString();

        try {
            // 3. THE SAFETY POP-UP
            int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
                    "Are you sure you want to permanently delete '" + selectedEvent + "'? This will also cancel all registrations.", 
                    "Confirm Deletion", 
                    javax.swing.JOptionPane.YES_NO_OPTION);

            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                java.sql.Connection conn = DatabaseConnection.getConnection();
                
                // 4. THE SAFE DELETION (Registrations first, then the Event)
                String delRegSql = "DELETE FROM registrations WHERE event_id = (SELECT event_id FROM events WHERE event_name = ?)";
                java.sql.PreparedStatement delRegStmt = conn.prepareStatement(delRegSql);
                delRegStmt.setString(1, selectedEvent);
                delRegStmt.executeUpdate();

                String delEventSql = "DELETE FROM events WHERE event_name = ?";
                java.sql.PreparedStatement delEventStmt = conn.prepareStatement(delEventSql);
                delEventStmt.setString(1, selectedEvent);
                delEventStmt.executeUpdate();

                javax.swing.JOptionPane.showMessageDialog(this, "Event deleted successfully!");

                // 5. INSTANT REFRESH FOR ALL TABS
                loadHostedEvents(); // Refresh Tab 3
                loadComboBoxEvents(); // Refresh Tab 2's dropdown
                cmbFilterActionPerformed(null); // Refresh Tab 1's main table
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Delete Error: " + e.getMessage());
        }
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
                
                String updateSql = "UPDATE events SET event_name = ?, event_date = ?, location = ?, max_slots = ?, description = ? WHERE event_id = ?";
                java.sql.PreparedStatement updateStmt = conn.prepareStatement(updateSql);

                updateStmt.setString(1, txtName.getText());
                String formattedDate = txtYear.getText() + "-" + txtMonth.getText() + "-" + txtDay.getText();
                updateStmt.setString(2, formattedDate);
                updateStmt.setString(3, txtLoc.getText());
                updateStmt.setInt(4, Integer.parseInt(txtSlots.getText()));
                updateStmt.setString(5, txtDesc.getText());
                updateStmt.setInt(6, eventId); // Uses the hidden ID to safely update the exact right row!

                updateStmt.executeUpdate();
                javax.swing.JOptionPane.showMessageDialog(this, "Event Successfully Updated!");

                // 8. INSTANT REFRESH
                loadHostedEvents();
                loadComboBoxEvents();
                cmbFilterActionPerformed(null);
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error updating event: " + e.getMessage());
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblHostedEventsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHostedEventsMouseClicked
        // 1. Get the table model and find out which row was clicked
        DefaultTableModel model = (DefaultTableModel) tblEvents.getModel();
        int selectedRowIndex = tblEvents.getSelectedRow();

        // 2. Grab the hidden Event ID from the very first column (Column 0)
       
    }//GEN-LAST:event_tblHostedEventsMouseClicked

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
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbEvents;
    private javax.swing.JComboBox<String> cmbFilter;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JTable tblAttendees;
    private javax.swing.JTable tblEvents;
    private javax.swing.JTable tblHostedEvents;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtNewDescription;
    private javax.swing.JLabel txtdescrip;
    // End of variables declaration//GEN-END:variables
}
