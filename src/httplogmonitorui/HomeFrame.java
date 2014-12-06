/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httplogmonitorui;

import httplogmonitor.DeviceFinder;
import httplogmonitorutil.Alert;
import httplogmonitorutil.HttpObject;
import httplogmonitorutil.Statistics;
import httplogmonitorutil.UserPreferences;
import httplogmonitorutil.Utility;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jnetpcap.PcapIf;

/**
 *
 * @author root
 */
public class HomeFrame extends javax.swing.JFrame {

    LinkedBlockingQueue<UserPreferences> preferenceQueue;
    LinkedBlockingQueue<ArrayList<HttpObject>> mostHitsTopURL;
    LinkedBlockingQueue<Alert> alertQueue;
    LinkedBlockingQueue<Statistics> statsQueue;
    List<PcapIf> devices;
    Alert previousAlert, previousPeakAlert;
    int threshold;
    JPanel alertsPanel;
    String[] columns = {"URI Section","Hit Count"};
    /**
     * Creates new form HomeFrame
     */
    public HomeFrame(LinkedBlockingQueue<UserPreferences> preferenceQueue, LinkedBlockingQueue<Alert> alertQueue, int threshold, LinkedBlockingQueue<Statistics> statsQueue, LinkedBlockingQueue<ArrayList<HttpObject>> mostHitsTopURL)
    {
        this.preferenceQueue = preferenceQueue;
        this.mostHitsTopURL = mostHitsTopURL;
        this.alertQueue = alertQueue;
        this.threshold = threshold;
        this.statsQueue = statsQueue;
        this.mostHitsTopURL = mostHitsTopURL;
        previousAlert = new Alert(null, 0, false);
        previousPeakAlert = new Alert(null, 0, true);
        initComponents();
        initilizeAll();
        try {
            updateNewHits();
        } catch (InterruptedException ex) {
            Logger.getLogger(HomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initilizeAll()
    {
        DeviceFinder deviceFinder = new DeviceFinder();
        devices = deviceFinder.findAllDevices();
        deviceDropDownList.removeAllItems();
        for(PcapIf device : devices)
            deviceDropDownList.addItem(device.getName());
        
        statsTable.setValueAt("Hit Count",0,0);
        alertsPanel = new JPanel();
        alertsPanel.setLayout(new BoxLayout(alertsPanel, BoxLayout.Y_AXIS));
        alertsScrollPane.setViewportView(alertsPanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        alertsScrollPane = new javax.swing.JScrollPane();
        mostHitsScrollPane = new javax.swing.JScrollPane();
        mostHitsTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        statsTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        mostHitsTextField = new javax.swing.JTextField();
        alertTextField = new javax.swing.JTextField();
        deviceDropDownList = new javax.swing.JComboBox();
        thresholdTextField = new javax.swing.JTextField();
        startUpdateButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1024, 480));
        setResizable(false);

        mostHitsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "URL Section", "Hit Count"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        mostHitsTable.setRowHeight(30);
        mostHitsScrollPane.setViewportView(mostHitsTable);

        statsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Statistics", "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        statsTable.setRowHeight(30);
        jScrollPane3.setViewportView(statsTable);

        mostHitsTextField.setText("10");
        mostHitsTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostHitsTextFieldActionPerformed(evt);
            }
        });

        alertTextField.setText("120");
        alertTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alertTextFieldActionPerformed(evt);
            }
        });

        deviceDropDownList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        thresholdTextField.setText("20");

        startUpdateButton.setText("Start");
        startUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startUpdateButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel2.setText("Maximum Hit Period");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel3.setText("Alert Period");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel4.setText("Alert Threshold");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel5.setText("Device");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(startUpdateButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(mostHitsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(alertTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jLabel3)))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(thresholdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(deviceDropDownList, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(30, 30, 30)))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mostHitsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(alertTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deviceDropDownList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(thresholdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(startUpdateButton)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setText("HTTP Log Monitor");

        jLabel6.setText("ALERTS");

        jLabel7.setText("Inputs");

        jLabel8.setText("Statistics");

        jLabel9.setText("Maximum Hit Sections");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mostHitsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addComponent(jLabel9)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(alertsScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(290, 290, 290))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(293, 293, 293))
            .addGroup(layout.createSequentialGroup()
                .addGap(426, 426, 426)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(alertsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mostHitsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startUpdateButtonActionPerformed
        // TODO add your handling code here:
        if(!validator())
        {
            JOptionPane.showMessageDialog(this, "Check your input.");
            return;
        }
        startUpdateButton.setText("Update");
        this.threshold = Integer.parseInt(thresholdTextField.getText());
        UserPreferences userPreferences = new UserPreferences(Integer.parseInt(thresholdTextField.getText()), devices.get(deviceDropDownList.getSelectedIndex()), Integer.parseInt(mostHitsTextField.getText())*1000, Integer.parseInt(alertTextField.getText())*1000);
        try {
            preferenceQueue.put(userPreferences);
        } catch (InterruptedException ex) {
            Logger.getLogger(HomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_startUpdateButtonActionPerformed
    
    public boolean validator()
    {
        try
        {
            Integer.parseInt(thresholdTextField.getText());
            Integer.parseInt(alertTextField.getText());
            Integer.parseInt(mostHitsTextField.getText());
            if(deviceDropDownList.getItemCount() > 0)
                return true;
            return false;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
    private void mostHitsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostHitsTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mostHitsTextFieldActionPerformed

    private void alertTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alertTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_alertTextFieldActionPerformed

    public void updateNewHits() throws InterruptedException
    {
        //DefaultTableModel mostHitsTableModel = (DefaultTableModel) mostHitsTable.getModel();
        Timer timer = new Timer(0, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateMostHitsTable();
                updateStatsTable();
                postAlerts();
            }
         });

         timer.setDelay(5*1000); // delay for 30 seconds
         timer.start();
    }
    
    public void updateMostHitsTable()
    {
        if(mostHitsTopURL.isEmpty())
            return;
        final DefaultTableModel tableModel = new DefaultTableModel(columns,0);
        try {
            ArrayList<HttpObject> topURLs = mostHitsTopURL.take();
            if(topURLs.isEmpty())
                return;
            for(HttpObject obj : topURLs)
            {
                tableModel.addRow(new String[]{Utility.getSection(obj.getUrl()), String.valueOf(obj.getHitCount())});
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(HomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        mostHitsTable.setModel(tableModel);
        mostHitsTable.repaint();
        mostHitsTable.revalidate();
    }
    
    public void updateStatsTable()
    {
        if(statsQueue.isEmpty())
            return;
        try {
            Statistics stats = null;
            while(!statsQueue.isEmpty())
                stats = statsQueue.take();
            statsTable.setValueAt(stats.getHitCount(), 0, 1);
        } catch (InterruptedException ex) {
            Logger.getLogger(HomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        statsTable.repaint();
        statsTable.revalidate();
    }
    
    public void postAlerts()
    {
        if(alertQueue.isEmpty())
            return;
        try {
            while(!alertQueue.isEmpty())
            {
                Alert alert = alertQueue.take();
                if(alert.getAlertType() != previousAlert.getAlertType())
                {
                    System.out.println("adding alert");
                    JPanel newPanel = new JPanel();
                    newPanel.setPreferredSize(new Dimension(530, 25));
                    JTextField trafficTextField = new JTextField();
                    trafficTextField.setBounds(0, 0, 520, 20);
                    if(alert.getAlertType())
                    {
                        trafficTextField.setText("High traffic alert - hits = "+alert.getHitCount()+", triggered at "+alert.getAlertTime());
                        trafficTextField.setForeground(Color.red);
                    }
                    else
                    {
                        JPanel newPanel2 = new JPanel();
                        newPanel2.setPreferredSize(new Dimension(530, 25));
                        JTextField peakTrafficTextField = new JTextField();
                        peakTrafficTextField.setBounds(0, 0, 520, 20);
                        peakTrafficTextField.setText("Peak traffic - hits = "+previousPeakAlert.getHitCount()+", triggered at "+previousPeakAlert.getAlertTime());
                        peakTrafficTextField.setForeground(Color.red);
                        peakTrafficTextField.setEditable(false);
                        newPanel2.add(peakTrafficTextField);
                        alertsPanel.add(newPanel2);
                        trafficTextField.setText("Traffic back to normal - hits = "+alert.getHitCount()+", at "+alert.getAlertTime());
                        previousPeakAlert = new Alert(null, 0, true);
                    }
                    trafficTextField.setEditable(false);
                    newPanel.add(trafficTextField);
                    alertsPanel.add(newPanel);
                }
                if(alert.getHitCount() > previousPeakAlert.getHitCount())
                {
                    previousPeakAlert = alert;
                }
                if(previousAlert.getHitCount() > alert.getHitCount() && previousAlert.getHitCount() > threshold)
                {
                    
                }
                alertsPanel.repaint();
                alertsPanel.revalidate();
                alertsScrollPane.repaint();
                alertsScrollPane.revalidate();
                previousAlert = alert;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(HomeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        statsTable.repaint();
        statsTable.revalidate();
        
    }
    /**
     * @param args the command line arguments
     */
    public static void Begin(LinkedBlockingQueue<UserPreferences> preferenceQueue, LinkedBlockingQueue<Alert> alertQueue, int threshold, LinkedBlockingQueue<Statistics> statsQueue, LinkedBlockingQueue<ArrayList<HttpObject>> mostHitsTopURL)
    {
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeFrame(preferenceQueue, alertQueue, threshold, statsQueue, mostHitsTopURL).setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField alertTextField;
    private javax.swing.JScrollPane alertsScrollPane;
    private javax.swing.JComboBox deviceDropDownList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane mostHitsScrollPane;
    private javax.swing.JTable mostHitsTable;
    private javax.swing.JTextField mostHitsTextField;
    private javax.swing.JButton startUpdateButton;
    private javax.swing.JTable statsTable;
    private javax.swing.JTextField thresholdTextField;
    // End of variables declaration//GEN-END:variables
}
