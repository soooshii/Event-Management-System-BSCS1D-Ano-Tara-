/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package eventmanagementsystem;

import javax.swing.JOptionPane;

public class AttendeePortal extends javax.swing.JFrame {
    
    public AttendeePortal(String username) {
        initComponents();
        
        this.loggedInUser = username;
        lblWelcome.setText("Welcome to the Portal, " + loggedInUser + "!");
        
        loadAvailableEvents();
        loadMySchedule();
    }
    
    private void loadMySchedule() {
        try {
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblMySchedule.getModel();
            model.setRowCount(0);

            java.sql.Connection conn = DatabaseConnection.getConnection();

            String firstName = "";
            String lastName = "";
            java.sql.PreparedStatement nameStmt = conn.prepareStatement("SELECT first_name, last_name FROM users WHERE username = ?");
            nameStmt.setString(1, loggedInUser);
            java.sql.ResultSet nameRs = nameStmt.executeQuery();
            
            if (nameRs.next()) {
                firstName = nameRs.getString("first_name");
                lastName = nameRs.getString("last_name");
            }

            String sql = "SELECT r.registration_id, e.event_name, e.event_date, e.venue, r.attendance_status " +
                         "FROM registrations r JOIN events e ON r.event_id = e.event_id " +
                         "WHERE r.first_name = ? AND r.last_name = ?";
            
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            java.sql.ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("registration_id"),
                    rs.getString("event_name"),
                    rs.getString("event_date"), 
                    rs.getString("venue"),   
                    rs.getString("attendance_status")
                });
            }
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading schedule: " + e.getMessage());
        }
    }
    
    private void loadAvailableEvents() {
       try {
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblEvents.getModel();
            model.setRowCount(0); 

            java.sql.Connection conn = DatabaseConnection.getConnection();
            
            String sql = "SELECT event_id, event_name, event_date, venue, max_slots FROM events"; 
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            java.sql.ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("event_id"),
                    rs.getString("event_name"),
                    rs.getString("event_date"), 
                    rs.getString("venue"),
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
        lblContact = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtContact = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        btnJoin = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMySchedule = new javax.swing.JTable();
        btnCancel = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        lblWelcome.setText("Welcome User!");

        lblContact.setText("Contact");

        txtEmail.addActionListener(this::txtEmailActionPerformed);

        lblEmail.setText("Email");

        btnJoin.setText("Join");
        btnJoin.addActionListener(this::btnJoinActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtContact)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblContact)
                                            .addComponent(lblEmail))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(btnJoin)
                                .addGap(0, 107, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(lblWelcome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogout)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLogout)
                    .addComponent(lblWelcome))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(lblEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblContact)
                        .addGap(5, 5, 5)
                        .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnJoin)))
                .addGap(0, 43, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Event Registration", jPanel1);

        tblMySchedule.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblMySchedule);
        if (tblMySchedule.getColumnModel().getColumnCount() > 0) {
            tblMySchedule.getColumnModel().getColumn(0).setResizable(false);
            tblMySchedule.getColumnModel().getColumn(4).setResizable(false);
        }

        btnCancel.setText("Cancel Registration");
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        txtSearch.setText("Search Event...");
        txtSearch.addActionListener(this::txtSearchActionPerformed);
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(btnCancel))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel)
                .addGap(57, 57, 57))
        );

        jTabbedPane1.addTab("Event Cancellation", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnJoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinActionPerformed

        int selectedRow = tblEvents.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please click an event from the table first!");
            return;
        }
        if (txtEmail.getText().trim().isEmpty() || txtContact.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please provide your Email and Contact Number!");
            return;
        }

        int eventId = (int) tblEvents.getValueAt(selectedRow, 0);

        try {
            java.sql.Connection conn = DatabaseConnection.getConnection();

            java.sql.PreparedStatement maxStmt = conn.prepareStatement("SELECT max_slots FROM events WHERE event_id = ?");
            maxStmt.setInt(1, eventId);
            java.sql.ResultSet maxRs = maxStmt.executeQuery();
            int maxSlots = 0;
            if (maxRs.next()) maxSlots = maxRs.getInt("max_slots");

            java.sql.PreparedStatement countStmt = conn.prepareStatement("SELECT COUNT(*) AS total FROM registrations WHERE event_id = ?");
            countStmt.setInt(1, eventId);
            java.sql.ResultSet countRs = countStmt.executeQuery();
            int currentRegs = 0;
            if (countRs.next()) currentRegs = countRs.getInt("total");

            if (currentRegs >= maxSlots) {
                javax.swing.JOptionPane.showMessageDialog(this, "Sorry! This event is completely full.");
                return; 
            }

            String firstName = "";
            String lastName = "";
            java.sql.PreparedStatement nameStmt = conn.prepareStatement("SELECT first_name, last_name FROM users WHERE username = ?");
            nameStmt.setString(1, loggedInUser); 
            java.sql.ResultSet nameRs = nameStmt.executeQuery();
            
            if (nameRs.next()) {
                firstName = nameRs.getString("first_name");
                lastName = nameRs.getString("last_name");
            }

            String sql = "INSERT INTO registrations (event_id, first_name, last_name, email, contact_number, attendance_status) VALUES (?, ?, ?, ?, ?, ?)";
            java.sql.PreparedStatement insertStmt = conn.prepareStatement(sql);
            insertStmt.setInt(1, eventId);
            insertStmt.setString(2, firstName);
            insertStmt.setString(3, lastName);
            insertStmt.setString(4, txtEmail.getText().trim());
            insertStmt.setString(5, txtContact.getText().trim());
            insertStmt.setString(6, "Registered");

            insertStmt.executeUpdate();

            javax.swing.JOptionPane.showMessageDialog(this, "Success! You are registered for the event.");
            
            txtEmail.setText("");
            txtContact.setText("");
            

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnJoinActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed

    }//GEN-LAST:event_txtEmailActionPerformed

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

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
       // 1. Did they actually click an event from the table?
        int selectedRow = tblMySchedule.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select an event from your schedule to cancel!");
            return;
        }

        // 2. Grab the Registration ID from the very first column (Column 0)
        int regId = (int) tblMySchedule.getValueAt(selectedRow, 0);

        // 3. The Safety Check: Make sure they didn't misclick
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to cancel your registration for this event? This cannot be undone.", 
                "Confirm Cancellation", 
                javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            try {
                java.sql.Connection conn = DatabaseConnection.getConnection();

                // 4. Execute the deletion in MySQL
                String sql = "DELETE FROM registrations WHERE registration_id = ?";
                java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, regId);
                
                stmt.executeUpdate();

                javax.swing.JOptionPane.showMessageDialog(this, "Registration cancelled successfully. Your slot has been freed!");

                // 5. Refresh the personal schedule table so the event instantly vanishes from the screen
                loadMySchedule(); 

            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        // 1. Get the current table model
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblEvents.getModel();
        
        // 2. Create the Sorter and attach it to the table
        javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter = new javax.swing.table.TableRowSorter<>(model);
        tblEvents.setRowSorter(sorter);
        
        // 3. Grab the text the user just typed
        String searchString = txtSearch.getText();
        
        // 4. Filter the table!
        if (searchString.length() == 0) {
            sorter.setRowFilter(null); // If the box is empty, show everything
        } else {
            // The "(?i)" part is a regex trick that makes your search ignore capital letters!
            sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + searchString));
        }
    }//GEN-LAST:event_txtSearchKeyPressed

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
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnJoin;
    private javax.swing.JButton btnLogout;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblContact;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JTable tblEvents;
    private javax.swing.JTable tblMySchedule;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
