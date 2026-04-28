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
    }
    
    private void loadComboBoxEvents() {
        try {
            cmbEventSelect.removeAllItems(); // Clear default items
            cmbEventSelect.addItem("Select an Event..."); // Placeholder
            
            java.sql.Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT event_name FROM events";
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            java.sql.ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                cmbEventSelect.addItem(rs.getString("event_name"));
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
        jPanel3 = new javax.swing.JPanel();
        txtDescription = new javax.swing.JTextField();
        cmbEventSelect = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAttendees = new javax.swing.JTable();
        btnJoinEvent = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnAddEvent = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHostedEvents = new javax.swing.JTable();
        txtNewDescription = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(232, 212, 183));

        jPanel1.setBackground(new java.awt.Color(232, 212, 183));

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

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblEvents);
        if (tblEvents.getColumnModel().getColumnCount() > 0) {
            tblEvents.getColumnModel().getColumn(0).setResizable(false);
            tblEvents.getColumnModel().getColumn(4).setResizable(false);
        }

        btnLogout.setText("Logout");
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        lblWelcome.setFont(new java.awt.Font("Baskerville Old Face", 1, 24)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(255, 255, 255));
        lblWelcome.setText("Welcome User!");

        cmbFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Upcoming Events", "Ended Events", "Full Events", "My Registered Events", " " }));
        cmbFilter.addActionListener(this::cmbFilterActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(175, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblWelcome)
                        .addGap(238, 238, 238)
                        .addComponent(btnLogout)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(133, 133, 133))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLogout)
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblWelcome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Discover Events", jPanel1);

        jPanel3.setBackground(new java.awt.Color(232, 212, 183));

        txtDescription.setEditable(false);

        cmbEventSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEventSelect.addActionListener(this::cmbEventSelectActionPerformed);

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

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblAttendees);
        if (tblAttendees.getColumnModel().getColumnCount() > 0) {
            tblAttendees.getColumnModel().getColumn(0).setResizable(false);
        }

        btnJoinEvent.setText("Join");
        btnJoinEvent.addActionListener(this::btnJoinEventActionPerformed);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addComponent(cmbEventSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(btnJoinEvent)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel)))
                .addGap(44, 44, 44)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addComponent(cmbEventSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnJoinEvent)
                    .addComponent(btnCancel))
                .addGap(55, 55, 55))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Events Information", jPanel3);

        jPanel2.setBackground(new java.awt.Color(232, 212, 183));

        btnAddEvent.setText("Create Event");
        btnAddEvent.addActionListener(this::btnAddEventActionPerformed);

        btnDelete1.setText("Delete");
        btnDelete1.addActionListener(this::btnDelete1ActionPerformed);

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

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

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblHostedEvents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHostedEventsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHostedEvents);

        jLabel1.setText("Description");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNewDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(btnAddEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(btnDelete1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtNewDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddEvent)
                    .addComponent(btnUpdate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jTabbedPane1.addTab("Host Event", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
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

    private void cmbEventSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEventSelectActionPerformed
// 1. Grab the name they just clicked
        String selectedEvent = (String) cmbEventSelect.getSelectedItem();

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
    }//GEN-LAST:event_cmbEventSelectActionPerformed

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
       String selectedEvent = (String) cmbEventSelect.getSelectedItem();
        
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
            cmbEventSelectActionPerformed(null); 

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Registration Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnJoinEventActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
      // 1. Create the text fields for the pop-up
        javax.swing.JTextField txtName = new javax.swing.JTextField();
        // Create 3 smaller text boxes (the number inside sets the visual width)
        javax.swing.JTextField txtYear = new javax.swing.JTextField("2026", 4); // Pre-filled with 2026!
        javax.swing.JTextField txtMonth = new javax.swing.JTextField(2);
        javax.swing.JTextField txtDay = new javax.swing.JTextField(2);
        
        // Group them side-by-side in a mini panel with dashes in between
        javax.swing.JPanel datePanel = new javax.swing.JPanel();
        datePanel.add(txtYear);
        datePanel.add(new javax.swing.JLabel("-"));
        datePanel.add(txtMonth);
        datePanel.add(new javax.swing.JLabel("-"));
        datePanel.add(txtDay);
        javax.swing.JTextField txtLoc = new javax.swing.JTextField();
        javax.swing.JTextField txtSlots = new javax.swing.JTextField();

        // 2. Bundle the labels and fields together into an array
        Object[] formFields = {
            "Event Name:", txtName,
            "Event Date (YYYY-MM-DD):", datePanel, // <-- We put the whole panel here!
            "Location:", txtLoc,
            "Max Slots:", txtSlots
        };

        // 3. Trigger the pop-up window
        int result = JOptionPane.showConfirmDialog(this, formFields, "Add New Event", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // 4. If the user clicks "OK" on the pop-up
        if (result == JOptionPane.OK_OPTION) {

            // Validation: Make sure they didn't leave anything blank
            if (txtName.getText().trim().isEmpty() || txtYear.getText().trim().isEmpty() || txtMonth.getText().trim().isEmpty() || txtDay.getText().trim().isEmpty() || txtLoc.getText().trim().isEmpty() || txtSlots.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Stop the code
            }

            // Save to Database
            try {
                try (java.sql.Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "INSERT INTO events (event_name, event_date, location, max_slots) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);

                    stmt.setString(1, txtName.getText());
                    
        String formattedDate = txtYear.getText() + "-" + txtMonth.getText() + "-" + txtDay.getText();
        stmt.setString(2, formattedDate);
                    stmt.setString(3, txtLoc.getText());
                    stmt.setInt(4, Integer.parseInt(txtSlots.getText())); // Converts the slot text to an integer

                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Event Successfully Added!");
                }

                // Refresh the table instantly to show the new event
               

            } catch (HeadlessException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error adding event: " + e.getMessage());
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
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
    private javax.swing.JComboBox<String> cmbEventSelect;
    private javax.swing.JComboBox<String> cmbFilter;
    private javax.swing.JLabel jLabel1;
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
    // End of variables declaration//GEN-END:variables
}
