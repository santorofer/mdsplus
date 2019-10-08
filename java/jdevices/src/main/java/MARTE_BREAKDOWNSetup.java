/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MARTESetup.java
 *
 * Created on Mar 2, 2011, 1:07:35 PM
 */

/**
 *
 * @author manduchi
 */
public class MARTE_BREAKDOWNSetup extends DeviceSetup {

    /** Creates new form MARTESetup */
    public MARTE_BREAKDOWNSetup() {
	initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

	deviceButtons1 = new DeviceButtons();
	jPanel2 = new javax.swing.JPanel();
	jPanel5 = new javax.swing.JPanel();
	deviceField1 = new DeviceField();
	jPanel8 = new javax.swing.JPanel();
	deviceChoice1 = new DeviceChoice();
	deviceField6 = new DeviceField();
	jPanel9 = new javax.swing.JPanel();
	deviceField7 = new DeviceField();
	deviceField8 = new DeviceField();
	jPanel1 = new javax.swing.JPanel();
	jTabbedPane1 = new javax.swing.JTabbedPane();
	jPanel3 = new javax.swing.JPanel();
	deviceField2 = new DeviceField();
	deviceField3 = new DeviceField();
	deviceField4 = new DeviceField();
	jPanel4 = new javax.swing.JPanel();
	jPanel6 = new javax.swing.JPanel();
	deviceField5 = new DeviceField();
	jPanel34 = new javax.swing.JPanel();
	jPanel83 = new javax.swing.JPanel();
	deviceField74 = new DeviceField();
	jPanel84 = new javax.swing.JPanel();
	deviceField75 = new DeviceField();
	jPanel35 = new javax.swing.JPanel();
	jPanel85 = new javax.swing.JPanel();
	deviceField76 = new DeviceField();
	jPanel86 = new javax.swing.JPanel();
	deviceField77 = new DeviceField();
	jPanel7 = new javax.swing.JPanel();
	jPanel10 = new javax.swing.JPanel();
	deviceField9 = new DeviceField();
	jPanel36 = new javax.swing.JPanel();
	jPanel87 = new javax.swing.JPanel();
	deviceField78 = new DeviceField();
	jPanel88 = new javax.swing.JPanel();
	deviceField79 = new DeviceField();
	jPanel37 = new javax.swing.JPanel();
	jPanel89 = new javax.swing.JPanel();
	deviceField80 = new DeviceField();
	jPanel90 = new javax.swing.JPanel();
	deviceField81 = new DeviceField();
	jPanel11 = new javax.swing.JPanel();
	jPanel12 = new javax.swing.JPanel();
	deviceField10 = new DeviceField();
	jPanel38 = new javax.swing.JPanel();
	jPanel91 = new javax.swing.JPanel();
	deviceField82 = new DeviceField();
	jPanel92 = new javax.swing.JPanel();
	deviceField83 = new DeviceField();
	jPanel39 = new javax.swing.JPanel();
	jPanel93 = new javax.swing.JPanel();
	deviceField84 = new DeviceField();
	jPanel94 = new javax.swing.JPanel();
	deviceField85 = new DeviceField();
	jPanel13 = new javax.swing.JPanel();
	jPanel14 = new javax.swing.JPanel();
	deviceField11 = new DeviceField();
	jPanel40 = new javax.swing.JPanel();
	jPanel95 = new javax.swing.JPanel();
	deviceField86 = new DeviceField();
	jPanel96 = new javax.swing.JPanel();
	deviceField87 = new DeviceField();
	jPanel41 = new javax.swing.JPanel();
	jPanel97 = new javax.swing.JPanel();
	deviceField88 = new DeviceField();
	jPanel98 = new javax.swing.JPanel();
	deviceField89 = new DeviceField();

	setDeviceProvider("localhost");
	setDeviceTitle("MARTe BREAKDOWNSetup");
	setDeviceType("MARTE_BREAKDOWN");
	setHeight(700);
	setWidth(900);
	getContentPane().add(deviceButtons1, java.awt.BorderLayout.PAGE_END);

	jPanel2.setLayout(new java.awt.GridLayout(3, 0));

	deviceField1.setIdentifier("");
	deviceField1.setLabelString("Comment: ");
	deviceField1.setNumCols(30);
	deviceField1.setOffsetNid(1);
	deviceField1.setTextOnly(true);
	jPanel5.add(deviceField1);

	jPanel2.add(jPanel5);

	deviceChoice1.setChoiceItems(new String[] {"SpiderBreakdown"});
	deviceChoice1.setIdentifier("");
	deviceChoice1.setLabelString("Control: ");
	deviceChoice1.setOffsetNid(12);
	deviceChoice1.setUpdateIdentifier("");
	jPanel8.add(deviceChoice1);

	deviceField6.setIdentifier("");
	deviceField6.setLabelString("Contr. Duration(s): ");
	deviceField6.setOffsetNid(5);
	jPanel8.add(deviceField6);

	jPanel2.add(jPanel8);

	deviceField7.setIdentifier("");
	deviceField7.setLabelString("Trig. Time: ");
	deviceField7.setNumCols(25);
	deviceField7.setOffsetNid(4);
	jPanel9.add(deviceField7);

	deviceField8.setIdentifier("");
	deviceField8.setLabelString("Freq. (Hz):");
	deviceField8.setOffsetNid(3);
	jPanel9.add(deviceField8);

	jPanel2.add(jPanel9);

	getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

	jPanel1.setLayout(new java.awt.BorderLayout());

	deviceField2.setIdentifier("");
	deviceField2.setLabelString("Trigger Idx:");
	deviceField2.setNumCols(4);
	deviceField2.setOffsetNid(1339);
	jPanel3.add(deviceField2);

	deviceField3.setIdentifier("");
	deviceField3.setLabelString("Dead Time(s): ");
	deviceField3.setNumCols(4);
	deviceField3.setOffsetNid(1345);
	jPanel3.add(deviceField3);

	deviceField4.setIdentifier("");
	deviceField4.setLabelString("Rise Time(s): ");
	deviceField4.setNumCols(4);
	deviceField4.setOffsetNid(1351);
	jPanel3.add(deviceField4);

	jTabbedPane1.addTab("Common Parameters", jPanel3);

	jPanel4.setPreferredSize(new java.awt.Dimension(200, 3000));
	jPanel4.setLayout(new java.awt.GridLayout(3, 1));

	deviceField5.setIdentifier("");
	deviceField5.setLabelString("Dead Level: ");
	deviceField5.setOffsetNid(1357);
	jPanel6.add(deviceField5);

	jPanel4.add(jPanel6);

	jPanel34.setBorder(javax.swing.BorderFactory.createTitledBorder("Reference Waveform"));
	jPanel34.setLayout(new java.awt.GridLayout(2, 1));

	deviceField74.setIdentifier("");
	deviceField74.setLabelString("Y: ");
	deviceField74.setNumCols(30);
	deviceField74.setOffsetNid(2876);
	jPanel83.add(deviceField74);

	jPanel34.add(jPanel83);

	deviceField75.setIdentifier("");
	deviceField75.setLabelString("X: ");
	deviceField75.setNumCols(30);
	deviceField75.setOffsetNid(2875);
	jPanel84.add(deviceField75);

	jPanel34.add(jPanel84);

	jPanel4.add(jPanel34);

	jPanel35.setBorder(javax.swing.BorderFactory.createTitledBorder("Normalized Recover Waveform"));
	jPanel35.setLayout(new java.awt.GridLayout(2, 1));

	deviceField76.setIdentifier("");
	deviceField76.setLabelString("Y: ");
	deviceField76.setNumCols(30);
	deviceField76.setOffsetNid(2896);
	jPanel85.add(deviceField76);

	jPanel35.add(jPanel85);

	deviceField77.setIdentifier("");
	deviceField77.setLabelString("X: ");
	deviceField77.setNumCols(30);
	deviceField77.setOffsetNid(2895);
	jPanel86.add(deviceField77);

	jPanel35.add(jPanel86);

	jPanel4.add(jPanel35);

	jTabbedPane1.addTab("Wave 1", jPanel4);

	jPanel7.setPreferredSize(new java.awt.Dimension(200, 3000));
	jPanel7.setLayout(new java.awt.GridLayout(3, 1));

	deviceField9.setIdentifier("");
	deviceField9.setLabelString("Dead Level: ");
	deviceField9.setOffsetNid(1363);
	jPanel10.add(deviceField9);

	jPanel7.add(jPanel10);

	jPanel36.setBorder(javax.swing.BorderFactory.createTitledBorder("Reference Waveform"));
	jPanel36.setLayout(new java.awt.GridLayout(2, 1));

	deviceField78.setIdentifier("");
	deviceField78.setLabelString("Y: ");
	deviceField78.setNumCols(30);
	deviceField78.setOffsetNid(2881);
	jPanel87.add(deviceField78);

	jPanel36.add(jPanel87);

	deviceField79.setIdentifier("");
	deviceField79.setLabelString("X: ");
	deviceField79.setNumCols(30);
	deviceField79.setOffsetNid(2880);
	jPanel88.add(deviceField79);

	jPanel36.add(jPanel88);

	jPanel7.add(jPanel36);

	jPanel37.setBorder(javax.swing.BorderFactory.createTitledBorder("Normalized Recover Waveform"));
	jPanel37.setLayout(new java.awt.GridLayout(2, 1));

	deviceField80.setIdentifier("");
	deviceField80.setLabelString("Y: ");
	deviceField80.setNumCols(30);
	deviceField80.setOffsetNid(2901);
	jPanel89.add(deviceField80);

	jPanel37.add(jPanel89);

	deviceField81.setIdentifier("");
	deviceField81.setLabelString("X: ");
	deviceField81.setNumCols(30);
	deviceField81.setOffsetNid(2900);
	jPanel90.add(deviceField81);

	jPanel37.add(jPanel90);

	jPanel7.add(jPanel37);

	jTabbedPane1.addTab("Wave 2", jPanel7);

	jPanel11.setPreferredSize(new java.awt.Dimension(200, 3000));
	jPanel11.setLayout(new java.awt.GridLayout(3, 1));

	deviceField10.setIdentifier("");
	deviceField10.setLabelString("Dead Level: ");
	deviceField10.setOffsetNid(1369);
	jPanel12.add(deviceField10);

	jPanel11.add(jPanel12);

	jPanel38.setBorder(javax.swing.BorderFactory.createTitledBorder("Reference Waveform"));
	jPanel38.setLayout(new java.awt.GridLayout(2, 1));

	deviceField82.setIdentifier("");
	deviceField82.setLabelString("Y: ");
	deviceField82.setNumCols(30);
	deviceField82.setOffsetNid(2886);
	jPanel91.add(deviceField82);

	jPanel38.add(jPanel91);

	deviceField83.setIdentifier("");
	deviceField83.setLabelString("X: ");
	deviceField83.setNumCols(30);
	deviceField83.setOffsetNid(2885);
	jPanel92.add(deviceField83);

	jPanel38.add(jPanel92);

	jPanel11.add(jPanel38);

	jPanel39.setBorder(javax.swing.BorderFactory.createTitledBorder("Normalized Recover Waveform"));
	jPanel39.setLayout(new java.awt.GridLayout(2, 1));

	deviceField84.setIdentifier("");
	deviceField84.setLabelString("Y: ");
	deviceField84.setNumCols(30);
	deviceField84.setOffsetNid(2906);
	jPanel93.add(deviceField84);

	jPanel39.add(jPanel93);

	deviceField85.setIdentifier("");
	deviceField85.setLabelString("X: ");
	deviceField85.setNumCols(30);
	deviceField85.setOffsetNid(2905);
	jPanel94.add(deviceField85);

	jPanel39.add(jPanel94);

	jPanel11.add(jPanel39);

	jTabbedPane1.addTab("Wave3", jPanel11);

	jPanel13.setPreferredSize(new java.awt.Dimension(200, 3000));
	jPanel13.setLayout(new java.awt.GridLayout(3, 1));

	deviceField11.setIdentifier("");
	deviceField11.setLabelString("Dead Level: ");
	deviceField11.setOffsetNid(1375);
	jPanel14.add(deviceField11);

	jPanel13.add(jPanel14);

	jPanel40.setBorder(javax.swing.BorderFactory.createTitledBorder("Reference Waveform"));
	jPanel40.setLayout(new java.awt.GridLayout(2, 1));

	deviceField86.setIdentifier("");
	deviceField86.setLabelString("Y: ");
	deviceField86.setNumCols(30);
	deviceField86.setOffsetNid(2891);
	jPanel95.add(deviceField86);

	jPanel40.add(jPanel95);

	deviceField87.setIdentifier("");
	deviceField87.setLabelString("X: ");
	deviceField87.setNumCols(30);
	deviceField87.setOffsetNid(2890);
	jPanel96.add(deviceField87);

	jPanel40.add(jPanel96);

	jPanel13.add(jPanel40);

	jPanel41.setBorder(javax.swing.BorderFactory.createTitledBorder("Normalized Recover Waveform"));
	jPanel41.setLayout(new java.awt.GridLayout(2, 1));

	deviceField88.setIdentifier("");
	deviceField88.setLabelString("Y: ");
	deviceField88.setNumCols(30);
	deviceField88.setOffsetNid(2911);
	jPanel97.add(deviceField88);

	jPanel41.add(jPanel97);

	deviceField89.setIdentifier("");
	deviceField89.setLabelString("X: ");
	deviceField89.setNumCols(30);
	deviceField89.setOffsetNid(2910);
	jPanel98.add(deviceField89);

	jPanel41.add(jPanel98);

	jPanel13.add(jPanel41);

	jTabbedPane1.addTab("Wave 4", jPanel13);

	jPanel1.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

	getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private DeviceButtons deviceButtons1;
    private DeviceChoice deviceChoice1;
    private DeviceField deviceField1;
    private DeviceField deviceField10;
    private DeviceField deviceField11;
    private DeviceField deviceField2;
    private DeviceField deviceField3;
    private DeviceField deviceField4;
    private DeviceField deviceField5;
    private DeviceField deviceField6;
    private DeviceField deviceField7;
    private DeviceField deviceField74;
    private DeviceField deviceField75;
    private DeviceField deviceField76;
    private DeviceField deviceField77;
    private DeviceField deviceField78;
    private DeviceField deviceField79;
    private DeviceField deviceField8;
    private DeviceField deviceField80;
    private DeviceField deviceField81;
    private DeviceField deviceField82;
    private DeviceField deviceField83;
    private DeviceField deviceField84;
    private DeviceField deviceField85;
    private DeviceField deviceField86;
    private DeviceField deviceField87;
    private DeviceField deviceField88;
    private DeviceField deviceField89;
    private DeviceField deviceField9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel88;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel90;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JPanel jPanel92;
    private javax.swing.JPanel jPanel93;
    private javax.swing.JPanel jPanel94;
    private javax.swing.JPanel jPanel95;
    private javax.swing.JPanel jPanel96;
    private javax.swing.JPanel jPanel97;
    private javax.swing.JPanel jPanel98;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

}