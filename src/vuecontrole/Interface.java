package vuecontrole;

import algo.HeuristiqueConstructive;
import static algo.MinimalSolution.minimalSolution;
import algo.PrintSolution;
import algo.ReadInstance;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import dao.DaoFactory;
import dao.InstanceDao;
import dao.PersistenceType;
import dao.PlanningDao;
import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import modele.*;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class Interface extends javax.swing.JFrame {

    //private Instance instance = null;
    //private Planning planning = null;
    /**
     * Creates new form Interface
     */
    public Interface() {
        initComponents();
        initialisationWindow();
        this.setVisible(true);  //Display the window
        fillComboBoxDataset();
        fillComboBoxInstances();
        jTree1.setModel(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonUpload = new javax.swing.JButton();
        jComboBoxInstances = new javax.swing.JComboBox<>();
        jButtonRefresh = new javax.swing.JButton();
        jLabelInstance = new javax.swing.JLabel();
        jComboBoxDataset = new javax.swing.JComboBox<>();
        jLabelDataset = new javax.swing.JLabel();
        jComboBoxSolutions = new javax.swing.JComboBox<>();
        jLabelSolution = new javax.swing.JLabel();
        jButtonGenerate = new javax.swing.JButton();
        jButtonDownload = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonUpload.setText("Upload an Instance");
        jButtonUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUploadActionPerformed(evt);
            }
        });

        jButtonRefresh.setText("Refresh All");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });

        jLabelInstance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelInstance.setText("Instance :");

        jComboBoxDataset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDatasetActionPerformed(evt);
            }
        });

        jLabelDataset.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDataset.setText("Dataset :");

        jComboBoxSolutions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MinimalSolution" }));

        jLabelSolution.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelSolution.setText("Solution :");

        jButtonGenerate.setText("Generate");
        jButtonGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenerateActionPerformed(evt);
            }
        });

        jButtonDownload.setText("Download Solution");
        jButtonDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDownloadActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jTree1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonDownload, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelInstance, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelDataset, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelSolution, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxDataset, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBoxSolutions, 0, 258, Short.MAX_VALUE)
                                    .addComponent(jComboBoxInstances, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonUpload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonGenerate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonUpload)
                    .addComponent(jComboBoxDataset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDataset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRefresh)
                    .addComponent(jComboBoxInstances, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelInstance))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxSolutions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSolution)
                    .addComponent(jButtonGenerate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jButtonDownload)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxDatasetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDatasetActionPerformed
        fillComboBoxInstances();
    }//GEN-LAST:event_jComboBoxDatasetActionPerformed

    private void jButtonDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDownloadActionPerformed
        if (jComboBoxInstances.getSelectedItem() != null
                && jComboBoxSolutions.getSelectedItem() != null) {

            DaoFactory factory = DaoFactory.getDaoFactory(PersistenceType.Jpa);

            InstanceDao instancemanager = factory.getInstanceDao();
            PlanningDao planningManager = factory.getPlanningDao();

            Instance instance = instancemanager.findByName(jComboBoxInstances.getSelectedItem().toString());
            Planning planning = planningManager.findByAlgoNameAndInstance(jComboBoxSolutions.getSelectedItem().toString(), instance);
            if (planning == null) {
                JOptionPane d = new JOptionPane();
                d.showMessageDialog(this, "This solution is not generated");
            } else {
                //the solution is already calculated
                JFileChooser input = new JFileChooser();
                input.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                input.setAcceptAllFileFilterUsed(false);
                int result = input.showSaveDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String path = input.getSelectedFile().getAbsolutePath();
                    System.out.println(path);
                    path = path + "\\" + instance.getName() + "-" + planning.getAlgoName() + ".txt";
                    System.out.println(path);
                    try {
                        PrintSolution.print(instance, planning, path);
                        JOptionPane d = new JOptionPane();
                        d.showMessageDialog(this, "File created :\n" + path);
                    } catch (IOException ex) {
                        System.err.println("ERROR : " + ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_jButtonDownloadActionPerformed

    private void jButtonUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUploadActionPerformed
        JFileChooser input = new JFileChooser();
        int result = input.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = input.getSelectedFile().getAbsolutePath();
            System.out.println(path);
            String extension = "";
            int i = path.lastIndexOf('.');
            if (i > 0) {
                extension = path.substring(i + 1);
            }
            System.out.println("Extension : " + extension);
            if (extension == null) {
                JOptionPane d = new JOptionPane();
                d.showMessageDialog(this, "Wrong file type.");
            } else if (!extension.equals("txt")) {
                JOptionPane d = new JOptionPane();
                d.showMessageDialog(this, "Please, choose a text file.");
            } else {
                path = path.replace("\\", "\\\\");
                System.out.println(path);
                Instance instance = ReadInstance.readInstance(path);
                refreshComboBox();
                //displayTree();
                //jButton2.setEnabled(true);
            }
        }
    }//GEN-LAST:event_jButtonUploadActionPerformed

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        refreshComboBox();
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    private void jButtonGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerateActionPerformed
        if (jComboBoxInstances.getSelectedItem() != null
                && jComboBoxSolutions.getSelectedItem() != null) {

            DaoFactory factory = DaoFactory.getDaoFactory(PersistenceType.Jpa);

            InstanceDao instancemanager = factory.getInstanceDao();
            PlanningDao planningManager = factory.getPlanningDao();

            Instance instance = instancemanager.findByName(jComboBoxInstances.getSelectedItem().toString());
//            System.out.println(instance);
            Planning planning = planningManager.findByAlgoNameAndInstance(jComboBoxSolutions.getSelectedItem().toString(), instance);
            // This solution doesn't exist yet for this instance
            if (planning == null) {
                switch (jComboBoxSolutions.getSelectedItem().toString()) {
                    case "MinimalSolution":
                        try {
                            HeuristiqueConstructive heuristique = new HeuristiqueConstructive(instance);
                            heuristique.minimalSolution();
                        } catch (IOException ex) {
                            System.err.println("ERROR : " + ex);
                        }
                        break;
                }
            } else {
                //the solution is already calculated
                JOptionPane d = new JOptionPane();
                d.showMessageDialog(this, "This solution is already calculated");
            }
        }
    }//GEN-LAST:event_jButtonGenerateActionPerformed

    private void fillComboBoxDataset() {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        DaoFactory factory = DaoFactory.getDaoFactory(PersistenceType.Jpa);
        InstanceDao instancemanager = factory.getInstanceDao();
        Collection<Instance> instanceList = instancemanager.findAll();
        List<String> DatasetList = new ArrayList<>();
        for (Instance i : instanceList) {
            if (!DatasetList.contains(i.getDataset())) {
                DatasetList.add(i.getDataset());
                dcbm.addElement(i.getDataset());
            }
        }
        jComboBoxDataset.setModel(dcbm);
    }

    private void fillComboBoxInstances() {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        DaoFactory factory = DaoFactory.getDaoFactory(PersistenceType.Jpa);
        InstanceDao instancemanager = factory.getInstanceDao();
        if (jComboBoxDataset.getSelectedItem() != null) {
            Collection<Instance> instanceList = instancemanager.findByDataset(jComboBoxDataset.getSelectedItem().toString());
            for (Instance i : instanceList) {
                dcbm.addElement(i.getName());
            }
            jComboBoxInstances.setModel(dcbm);
        }
    }

    public void refreshComboBox() {
        fillComboBoxInstances();
        fillComboBoxDataset();
        System.out.println("ComboBox REFRESHED");
    }

    /**
     * Initialisation of the window with default parameters
     */
    private void initialisationWindow() {
        //this.setSize(700, 500);            //Set width : 600 et height 400 ; coord (0,0) => Top & Left
        this.setLocationRelativeTo(null);  //Position on the screen : null is centrale position
        this.setTitle("Upload");            //Window's title
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);    //Lors de la fermeture de la fenetre, quitte l’application (fait un appel à System.exit()).
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);   //During the window closing,  destroy the window object
        this.getContentPane().setBackground(Color.white);       // Set the background color
    }

    public void displayTree() {

        if (jComboBoxInstances.getSelectedItem() != null
                && jComboBoxSolutions.getSelectedItem() != null) {

            DaoFactory factory = DaoFactory.getDaoFactory(PersistenceType.Jpa);

            InstanceDao instancemanager = factory.getInstanceDao();
            PlanningDao planningManager = factory.getPlanningDao();

            Instance instance = instancemanager.findByName(jComboBoxInstances.getSelectedItem().toString());
            Planning planning = planningManager.findByAlgoNameAndInstance(jComboBoxSolutions.getSelectedItem().toString(), instance);

            DefaultMutableTreeNode root = new DefaultMutableTreeNode(instance.getName());
            if (planning == null) {
                JOptionPane d = new JOptionPane();
                d.showMessageDialog(this, "This solution is not generated");
            } else {

            }
        }

    }

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interface().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDownload;
    private javax.swing.JButton jButtonGenerate;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jButtonUpload;
    private javax.swing.JComboBox<String> jComboBoxDataset;
    private javax.swing.JComboBox<String> jComboBoxInstances;
    private javax.swing.JComboBox<String> jComboBoxSolutions;
    private javax.swing.JLabel jLabelDataset;
    private javax.swing.JLabel jLabelInstance;
    private javax.swing.JLabel jLabelSolution;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
