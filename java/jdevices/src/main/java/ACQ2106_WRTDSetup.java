/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fsantoro
 */
public class ACQ2106_WRTDSetup extends DeviceSetup {

    /**
     * Creates new form acq2106_WRTDSetup
     */
    public ACQ2106_WRTDSetup() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deviceButtons1 = new DeviceButtons();
        jPanel1 = new javax.swing.JPanel();
        deviceField1 = new DeviceField();
        deviceChoice1 = new DeviceChoice();
        deviceChoice2 = new DeviceChoice();
        deviceChoice3 = new DeviceChoice();
        deviceDispatch1 = new DeviceDispatch();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        deviceField3 = new DeviceField();
        deviceField11 = new DeviceField();
        deviceField2 = new DeviceField();
        jPanel9 = new javax.swing.JPanel();
        deviceChoice4 = new DeviceChoice();
        label4 = new java.awt.Label();
        label15 = new java.awt.Label();
        label16 = new java.awt.Label();
        label17 = new java.awt.Label();
        label10 = new java.awt.Label();
        label11 = new java.awt.Label();
        label12 = new java.awt.Label();
        label13 = new java.awt.Label();
        label14 = new java.awt.Label();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        deviceField4 = new DeviceField();
        label6 = new java.awt.Label();
        jPanel3 = new javax.swing.JPanel();
        deviceField5 = new DeviceField();
        label5 = new java.awt.Label();
        jPanel4 = new javax.swing.JPanel();
        deviceField6 = new DeviceField();
        label7 = new java.awt.Label();
        jPanel5 = new javax.swing.JPanel();
        deviceField7 = new DeviceField();
        label8 = new java.awt.Label();
        jPanel6 = new javax.swing.JPanel();
        deviceField10 = new DeviceField();
        deviceField8 = new DeviceField();
        label9 = new java.awt.Label();

        setDeviceProvider("172.20.240.104:9998");
        setDeviceTitle("WRTD for MDSplus");
        setDeviceType("acq2106_WRTD");
        setHeight(500);
        setUpdateEvent("");
        setWidth(1400);
        getContentPane().setLayout(new java.awt.BorderLayout(5, 0));
        getContentPane().add(deviceButtons1, java.awt.BorderLayout.PAGE_END);

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5);
        flowLayout1.setAlignOnBaseline(true);
        jPanel1.setLayout(flowLayout1);

        deviceField1.setIdentifier("");
        deviceField1.setLabelString("Node IP     ");
        deviceField1.setNumCols(20);
        deviceField1.setOffsetNid(1);
        deviceField1.setTextOnly(true);
        jPanel1.add(deviceField1);
        deviceField1.getAccessibleContext().setAccessibleName("");

        deviceChoice1.setChoiceItems(new String[] {"FPTRG", "HDMI"});
        deviceChoice1.setIdentifier("");
        deviceChoice1.setLabelString("WR Trigger Source  ");
        deviceChoice1.setOffsetNid(5);
        deviceChoice1.setUpdateIdentifier("");
        jPanel1.add(deviceChoice1);

        deviceChoice2.setChoiceItems(new String[] {"d0", "d1", "d2", "d3", "d4", "d5", "d6", "d7", "TRGIN", "WRTT"});
        deviceChoice2.setIdentifier("");
        deviceChoice2.setLabelString("Pulse Generator Trigger Source");
        deviceChoice2.setOffsetNid(9);
        deviceChoice2.setUpdateIdentifier("");
        jPanel1.add(deviceChoice2);

        deviceChoice3.setChoiceIntValues(new int[] {0, 1, 2, 3, 4, 5, 6});
        deviceChoice3.setChoiceItems(new String[] {"0", "1", "2", "3", "4", "5", "6"});
        deviceChoice3.setIdentifier("");
        deviceChoice3.setLabelString("DIO Site #");
        deviceChoice3.setOffsetNid(8);
        deviceChoice3.setUpdateIdentifier("");
        jPanel1.add(deviceChoice3);
        deviceChoice3.getAccessibleContext().setAccessibleName("");
        deviceChoice3.getAccessibleContext().setAccessibleDescription("This is also WRTD_TX_MASK selects the DIO units that respond");

        jPanel1.add(deviceDispatch1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel8.setLayout(new java.awt.GridLayout(3, 1));

        deviceField3.setIdentifier("");
        deviceField3.setLabelString("Message ID to Transmit");
        deviceField3.setNumCols(40);
        deviceField3.setOffsetNid(18);
        jPanel8.add(deviceField3);

        deviceField11.setIdentifier("");
        deviceField11.setLabelString("PG Site N message ID to Transmit");
        deviceField11.setNumCols(40);
        deviceField11.setOffsetNid(19);
        jPanel8.add(deviceField11);

        deviceField2.setIdentifier("");
        deviceField2.setLabelString("Safe time to broadcasts message ID [DELTA NS in msec]");
        deviceField2.setNumCols(40);
        deviceField2.setOffsetNid(12);
        jPanel8.add(deviceField2);
        deviceField2.getAccessibleContext().setAccessibleDescription("Sets WR \"safe time for broadcasts\" the message, i.e. WRTT_TAI = TAI_TIME_NOW + WRTD_DELTA_NS. 50msec ");

        jTabbedPane2.addTab("Message IDs", jPanel8);
        jPanel8.getAccessibleContext().setAccessibleName("");

        jPanel9.setLayout(new java.awt.GridLayout(11, 1));

        deviceChoice4.setChoiceFloatValues(new float[] {195.3125f, 97.6562f, 48.8281f, 30.5176f, 25.0f});
        deviceChoice4.setChoiceItems(new String[] {"195.3125", "97.6562", "48.8281", "30.5176", "25.0000"});
        deviceChoice4.setIdentifier("");
        deviceChoice4.setLabelString("TICKNS [nsec per tick]");
        deviceChoice4.setOffsetNid(11);
        deviceChoice4.setUpdateIdentifier("");
        jPanel9.add(deviceChoice4);

        label4.setAlignment(java.awt.Label.CENTER);
        label4.setText("TICKNS is the MBCLK tick interval, i.e. 1. / MBCLK [in nsec]");
        jPanel9.add(label4);

        label15.setAlignment(java.awt.Label.CENTER);
        label15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label15.setText("Choices of TICKNS for 423 modules");
        jPanel9.add(label15);

        label16.setAlignment(java.awt.Label.CENTER);
        label16.setText("TICKNS = 25.0000");
        jPanel9.add(label16);

        label17.setAlignment(java.awt.Label.CENTER);
        label17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label17.setText("Choices of TICKNS for 435 modules");
        jPanel9.add(label17);

        label10.setAlignment(java.awt.Label.CENTER);
        label10.setText("SR = 10 KHz  =>  TICKNS = 195.3125");
        jPanel9.add(label10);
        label10.getAccessibleContext().setAccessibleDescription("");

        label11.setAlignment(java.awt.Label.CENTER);
        label11.setText("SR = 20 KHz  =>  TICKNS = 97.6562");
        jPanel9.add(label11);
        label11.getAccessibleContext().setAccessibleDescription("");

        label12.setAlignment(java.awt.Label.CENTER);
        label12.setText("SR = 40 KHz  =>  TICKNS = 48.8281");
        jPanel9.add(label12);
        label12.getAccessibleContext().setAccessibleDescription("");

        label13.setAlignment(java.awt.Label.CENTER);
        label13.setText("SR =  80 KHz  =>  TICKNS = 48.8281");
        jPanel9.add(label13);

        label14.setAlignment(java.awt.Label.CENTER);
        label14.setText("SR = 128 KHz  => TICKNS = 30.5176");
        jPanel9.add(label14);

        jTabbedPane2.addTab("TICKNS", jPanel9);

        jTabbedPane1.addTab("WR Global Initialization", jTabbedPane2);
        jTabbedPane2.getAccessibleContext().setAccessibleName("WRTD ID Global");
        jTabbedPane2.getAccessibleContext().setAccessibleDescription("");

        deviceField4.setIdentifier("");
        deviceField4.setLabelString("RX [1=ON, 0=OFF)");
        deviceField4.setNumCols(1);
        deviceField4.setOffsetNid(21);
        jPanel2.add(deviceField4);

        label6.setText("Turns on or off the receiver");
        jPanel2.add(label6);
        label6.getAccessibleContext().setAccessibleDescription("");

        jTabbedPane3.addTab("WRTD RX", jPanel2);

        deviceField5.setIdentifier("");
        deviceField5.setLabelString("TX (1=ON, 0=OFF)");
        deviceField5.setNumCols(1);
        deviceField5.setOffsetNid(20);
        jPanel3.add(deviceField5);

        label5.setText("Turns on or off the transmitter");
        jPanel3.add(label5);
        label5.getAccessibleContext().setAccessibleName("Turn on or off the transmitter");

        jTabbedPane3.addTab("WRTD TX", jPanel3);

        deviceField6.setIdentifier("");
        deviceField6.setLabelString("RX Matches for WRTT0");
        deviceField6.setNumCols(100);
        deviceField6.setOffsetNid(14);
        deviceField6.setTextOnly(true);
        jPanel4.add(deviceField6);
        deviceField6.getAccessibleContext().setAccessibleDescription("match any of these triggers to initiate WRTT0");

        label7.setName(""); // NOI18N
        label7.setText("Filters which messages will be used to trigger WRTT0. Format: comma separated strings.");
        jPanel4.add(label7);

        jTabbedPane3.addTab("RX M0", jPanel4);

        deviceField7.setIdentifier("");
        deviceField7.setLabelString("RX Matches for WRTT1");
        deviceField7.setNumCols(100);
        deviceField7.setOffsetNid(15);
        deviceField7.setTextOnly(true);
        jPanel5.add(deviceField7);
        deviceField7.getAccessibleContext().setAccessibleDescription("match any of these triggers to initiate WRTT1");

        label8.setText("Filters which messages will be used to trigger WRTT1. Format: comma separated strings.");
        jPanel5.add(label8);

        jTabbedPane3.addTab("RX M1", jPanel5);

        deviceField10.setIdentifier("");
        deviceField10.setLabelString("Delay [nsec]");
        deviceField10.setOffsetNid(17);
        jPanel6.add(deviceField10);

        deviceField8.setIdentifier("");
        deviceField8.setLabelString("Double Tap Message");
        deviceField8.setNumCols(20);
        deviceField8.setOffsetNid(16);
        deviceField8.setTextOnly(true);
        jPanel6.add(deviceField8);

        label9.setText("One message, two triggers: first WRTT0, Delay [nsec], then WRTT1");
        jPanel6.add(label9);

        jTabbedPane3.addTab("RX DTP", jPanel6);

        jTabbedPane1.addTab("WR RX/TX", jTabbedPane3);
        jTabbedPane3.getAccessibleContext().setAccessibleName("WRTF RX");
        jTabbedPane3.getAccessibleContext().setAccessibleDescription("");

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleName("");
        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private DeviceButtons deviceButtons1;
    private DeviceChoice deviceChoice1;
    private DeviceChoice deviceChoice2;
    private DeviceChoice deviceChoice3;
    private DeviceChoice deviceChoice4;
    private DeviceDispatch deviceDispatch1;
    private DeviceField deviceField1;
    private DeviceField deviceField10;
    private DeviceField deviceField11;
    private DeviceField deviceField2;
    private DeviceField deviceField3;
    private DeviceField deviceField4;
    private DeviceField deviceField5;
    private DeviceField deviceField6;
    private DeviceField deviceField7;
    private DeviceField deviceField8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private java.awt.Label label10;
    private java.awt.Label label11;
    private java.awt.Label label12;
    private java.awt.Label label13;
    private java.awt.Label label14;
    private java.awt.Label label15;
    private java.awt.Label label16;
    private java.awt.Label label17;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private java.awt.Label label9;
    // End of variables declaration//GEN-END:variables
}
