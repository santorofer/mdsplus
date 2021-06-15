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
        deviceLabel1 = new DeviceLabel();
        jPanel10 = new javax.swing.JPanel();
        deviceField11 = new DeviceField();
        deviceLabel2 = new DeviceLabel();
        jPanel7 = new javax.swing.JPanel();
        deviceField2 = new DeviceField();
        deviceLabel3 = new DeviceLabel();
        jPanel9 = new javax.swing.JPanel();
        deviceField9 = new DeviceField();
        deviceLabel4 = new DeviceLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        deviceField4 = new DeviceField();
        deviceLabel5 = new DeviceLabel();
        jPanel3 = new javax.swing.JPanel();
        deviceField5 = new DeviceField();
        deviceLabel6 = new DeviceLabel();
        jPanel4 = new javax.swing.JPanel();
        deviceField6 = new DeviceField();
        deviceLabel7 = new DeviceLabel();
        jPanel5 = new javax.swing.JPanel();
        deviceField7 = new DeviceField();
        deviceLabel8 = new DeviceLabel();
        jPanel6 = new javax.swing.JPanel();
        deviceField10 = new DeviceField();
        deviceField8 = new DeviceField();
        deviceLabel9 = new DeviceLabel();

        // setDeviceProvider("172.20.240.104:9998");
        setDeviceProvider("localhost");
        setDeviceTitle("WRTD for MDSplus");
        setDeviceType("acq2106_WRTD");
        setHeight(300);
        setUpdateEvent("");
        setWidth(1200);
        getContentPane().setLayout(new java.awt.BorderLayout(5, 0));
        getContentPane().add(deviceButtons1, java.awt.BorderLayout.PAGE_END);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        deviceField1.setIdentifier("");
        deviceField1.setLabelString("Node IP");
        deviceField1.setNumCols(20);
        deviceField1.setOffsetNid(1);
        deviceField1.setTextOnly(true);
        jPanel1.add(deviceField1);

        deviceChoice1.setChoiceItems(new String[] {"FPTRG", "HDMI"});
        deviceChoice1.setIdentifier("");
        deviceChoice1.setLabelString("WR Trigger Source");
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

        deviceField3.setIdentifier("");
        deviceField3.setLabelString("Message ID to Transmit");
        deviceField3.setNumCols(20);
        deviceField3.setOffsetNid(18);
        deviceField3.setTextOnly(true);
        jPanel8.add(deviceField3);

        deviceLabel1.setLabelString("Message ID to be used to trigger devices that are listening to it.");
        jPanel8.add(deviceLabel1);

        jTabbedPane2.addTab("Global ID", jPanel8);

        deviceField11.setIdentifier("");
        deviceField11.setLabelString("PG Site N message ID");
        deviceField11.setOffsetNid(19);
        jPanel10.add(deviceField11);

        deviceLabel2.setLabelString("Message ID associated only to PG site N");
        jPanel10.add(deviceLabel2);

        jTabbedPane2.addTab("PG Site ID", jPanel10);

        deviceField2.setIdentifier("");
        deviceField2.setLabelString("DELTA NS [msec]");
        deviceField2.setOffsetNid(12);
        jPanel7.add(deviceField2);
        deviceField2.getAccessibleContext().setAccessibleDescription("Sets WR \"safe time for broadcasts\" the message, i.e. WRTT_TAI = TAI_TIME_NOW + WRTD_DELTA_NS. 50msec ");

        deviceLabel3.setLabelString("Sets WR \"safe time for broadcasts\" the message ID, i.e. WRTT_TAI = TAI_TIME_NOW + WRTD_DELTA_NS");
        jPanel7.add(deviceLabel3);

        jTabbedPane2.addTab("DELTA NS", null, jPanel7, "");

        deviceField9.setLabelString("nsec per tick [nsec]");
        jPanel9.add(deviceField9);

        deviceLabel4.setLabelString("TICKNS is the MBCLK tick interval, i.e. 1. / MBCLK [in nsec]");
        jPanel9.add(deviceLabel4);

        jTabbedPane2.addTab("TICKNS", jPanel9);

        jTabbedPane1.addTab("WR Global Initialization", jTabbedPane2);
        jTabbedPane2.getAccessibleContext().setAccessibleName("WRTD ID Global");
        jTabbedPane2.getAccessibleContext().setAccessibleDescription("");

        deviceField4.setIdentifier("");
        deviceField4.setLabelString("RX [1=ON, 0=OFF)");
        deviceField4.setNumCols(2);
        deviceField4.setOffsetNid(21);
        jPanel2.add(deviceField4);

        deviceLabel5.setLabelString("Turn on or off the receiver");
        jPanel2.add(deviceLabel5);

        jTabbedPane3.addTab("WRTD RX", jPanel2);

        deviceField5.setIdentifier("");
        deviceField5.setLabelString("TX (1=ON, 0=OFF)");
        deviceField5.setNumCols(2);
        deviceField5.setOffsetNid(1);
        jPanel3.add(deviceField5);

        deviceLabel6.setLabelString("Turn on or off the transmitter");
        jPanel3.add(deviceLabel6);

        jTabbedPane3.addTab("WRTD TX", jPanel3);

        deviceField6.setIdentifier("");
        deviceField6.setLabelString("RX Matches for WRTT0");
        deviceField6.setNumCols(20);
        deviceField6.setOffsetNid(14);
        deviceField6.setTextOnly(true);
        jPanel4.add(deviceField6);
        deviceField6.getAccessibleContext().setAccessibleDescription("match any of these triggers to initiate WRTT0");

        deviceLabel7.setLabelString("Filter which messages will be used to trigger WRTT0. Format: comma separated strings.");
        jPanel4.add(deviceLabel7);

        jTabbedPane3.addTab("RX M0", jPanel4);

        deviceField7.setIdentifier("");
        deviceField7.setLabelString("RX Matches for WRTT1");
        deviceField7.setNumCols(20);
        deviceField7.setOffsetNid(15);
        deviceField7.setTextOnly(true);
        jPanel5.add(deviceField7);
        deviceField7.getAccessibleContext().setAccessibleDescription("match any of these triggers to initiate WRTT1");

        deviceLabel8.setLabelString("Filter which messages will be used to trigger WRTT1. Format: comma separated strings.");
        jPanel5.add(deviceLabel8);

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

        deviceLabel9.setLabelString("One message, two triggers: first WRTT0, Delay [nsec], then WRTT1");
        jPanel6.add(deviceLabel9);

        jTabbedPane3.addTab("RX DTP", jPanel6);

        jTabbedPane1.addTab("WR RX/TX", jTabbedPane3);
        jTabbedPane3.getAccessibleContext().setAccessibleName("WRTF RX");
        jTabbedPane3.getAccessibleContext().setAccessibleDescription("");

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private DeviceButtons deviceButtons1;
    private DeviceChoice deviceChoice1;
    private DeviceChoice deviceChoice2;
    private DeviceChoice deviceChoice3;
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
    private DeviceField deviceField9;
    private DeviceLabel deviceLabel1;
    private DeviceLabel deviceLabel2;
    private DeviceLabel deviceLabel3;
    private DeviceLabel deviceLabel4;
    private DeviceLabel deviceLabel5;
    private DeviceLabel deviceLabel6;
    private DeviceLabel deviceLabel7;
    private DeviceLabel deviceLabel8;
    private DeviceLabel deviceLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    // End of variables declaration//GEN-END:variables
}