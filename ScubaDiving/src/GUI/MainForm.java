/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.sun.glass.ui.Screen;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
/**
 *
 * @author Riyan
 */
public class MainForm extends javax.swing.JFrame
{
    DecimalFormat df = new DecimalFormat("0.00");
    GUI.FrameIlustrator fi=new GUI.FrameIlustrator();
    GUI.About about=new GUI.About();
    GUI.Help help=new GUI.Help();
    SDCalcEngine Engine=new GUI.SDCalcEngine();
    double tempNumber,Fg,Pg,P;
    int rows,cols;
    String[] inUser=new String[2];
    int inUserInt[]=new int[4];
    String selectedMode;
    Object[] tbObj=new Object[1];
    DefaultTableModel tbMod=new DefaultTableModel();
    
    /*
        Additional Functions For Calculation
    */
    
    
    
    public void Converter(String param1)
    {
        tempNumber = Engine.convertStringtoDouble(param1);
        if (Engine.isErrorInput == false)
        {
            if (selectedMode.equals("Converter ata to metres"))
            {
                tempNumber = Engine.valueConverter("ata", "m", tempNumber);
                if (Engine.isErrorInput == false)
                {
                    resultTextPane.setText("< Converter Result >\n" + param1 + " ata = " + tempNumber + " metres");
                }
                else
                {
                    if (Engine.errorMsg != null)
                    {
                        JOptionPane.showMessageDialog(this, Engine.errorMsg);
                    }
                }

            } else if (selectedMode.equals("Converter metres to ata"))
            {
                tempNumber = Engine.valueConverter("m", "ata", tempNumber);
                if (Engine.isErrorInput == false)
                {
                    resultTextPane.setText("< Converter Result >\n" + param1 + " metres = " + tempNumber + " ata");
                }
                else
                {
                    if (Engine.errorMsg != null)
                    {
                        JOptionPane.showMessageDialog(this, Engine.errorMsg);
                    }
                }
            }
        } else
        {
            if (Engine.errorMsg != null)
            {
                JOptionPane.showMessageDialog(this, Engine.errorMsg);
            }
        }
    }
    
    public void CountingMOD(String param1,String param2)
    {
        Fg=Engine.convertStringtoDouble(param1);
	Pg=Engine.convertStringtoDouble(param2);
		
		if (Engine.isErrorInput==false)
		{
			Engine.CountMOD(Pg,Fg);
			if (Engine.isErrorInput==false)
			{
                            resultTextPane.setText("< Safe Stats For A Dive With >"
                                    +"\n> Oxygen Percentage : "+Fg+"% O2"
                                    +"\n> Partial Pressure of Oxygen : "+Pg+" ata"
                                    +"\n> Maximum Operating Depth : "+Math.floor(Engine.MODResult)+" metres");
                            
                            O2N2Mixture(Fg);
			}
			else
			{
				if (Engine.errorMsg != null)
				{
					JOptionPane.showMessageDialog(this,Engine.errorMsg);
				}
			}
		}
		else
		{
			if (Engine.errorMsg != null)
			{
                            JOptionPane.showMessageDialog(this,Engine.errorMsg);
			}
		}
		Engine.errorMsg=null;
    }
    
    public void CountingPPO2(String param1,String param2)
    {
        P=Engine.convertStringtoDouble(param1);
	Fg=Engine.convertStringtoDouble(param2);
		
		if (Engine.isErrorInput==false)
		{
			Engine.CountPPO2(Fg,P);
			if (Engine.isErrorInput==false)
			{
                            if (Engine.PPO2Result>=1.1 && Engine.PPO2Result <=1.6)
                            {
				resultTextPane.setText("< Safe Stats For A Dive With >");
                            }
                            else
                            {
                                resultTextPane.setText("< Not Safe Stats For A Dive With >");
                            }
                            resultTextPane.setText(resultTextPane.getText()+"\n> Oxygen Percentage : "+Fg+"% O2"
                                    +"\n> Partial Pressure of Oxygen : "+df.format(Engine.PPO2Result)+" ata"
                                    +"\n> Maximum Operating Depth : "+P+" metres");
                            
                            if (Engine.PPO2Result<1.1 || Engine.PPO2Result >1.6)
				{
					JOptionPane.showMessageDialog(this,"Note:\n-Your Stat of Partial Pressure of Oxygen is "+df.format(Engine.PPO2Result)+" ata"
                                                + "\n-Safe of Partial Pressure of Oxygen is between 1.1 ata and 1.6 ata");
				}
                            
                            
                            O2N2Mixture(Fg);
			}
			else
			{
				if (Engine.errorMsg != null)
				{
					JOptionPane.showMessageDialog(this,Engine.errorMsg);
				}
			}
		}
		else
		{
			if (Engine.errorMsg != null)
			{
                            JOptionPane.showMessageDialog(this,Engine.errorMsg);
			}
		}
		Engine.errorMsg=null;
    }
    
    public void CountingPPO2T(String param1,String param2)
    {
        inUser=param1.split(" ");
        resultTable.setModel(tbMod);
        
        if (inUser.length != 2)
        {
            Engine.isErrorInput = true;
            inUser = new String[2];
            Engine.errorMsg = "\n! Error ! : Input number can't less than 2 or  more than 2 parameters";
        }
        else
        {
            inUserInt[0]=Engine.convertStringtoInteger(inUser[0]);
            inUserInt[1]=Engine.convertStringtoInteger(inUser[1]);
            if ((inUserInt[0] < 18 || inUserInt[0] > 50)
                    || (inUserInt[1] < 18 || inUserInt[1] > 50))
            {
                Engine.isErrorInput = true;
                Engine.errorMsg = " ! Error ! -Only requires percentages of Oxygen from 18% to 50%.";
            } else
            {
                Engine.isErrorInput = false;
            }
            
            
        }
        
        if (Engine.isErrorInput==false)
        {
            inUser=param2.split(" ");
            
            if (inUser.length != 2)
            {
                Engine.isErrorInput = true;
                inUser = new String[2];
                Engine.errorMsg = "\n! Error ! : Input number can't less than 2 or  more than 2 parameters";
            } else
            {
                inUserInt[2] = Engine.convertStringtoInteger(inUser[0]);
                inUserInt[3] = Engine.convertStringtoInteger(inUser[1]);
                if ((inUserInt[2] <3 ||inUserInt[3] >70))
                {
                    Engine.isErrorInput = true;
                    Engine.errorMsg = " ! Error ! -The Depths should from 3 metres to 70 metres.";
                } else
                {
                    Engine.isErrorInput = false;
                }

            }
        }
        
        if (Engine.isErrorInput==false)
        {
            rows=inUserInt[3]-inUserInt[2];
            cols=inUserInt[1]-inUserInt[0];
            
            jLabel5.setText("PPO2 Table Result For "+inUserInt[0]+" - "+inUserInt[1]+" O2 Percentage and "+inUserInt[2]
                    +" - "+inUserInt[3]+" Metres Depths");
            //drawing coloms on table
            if (rows>=1 && cols>=1)
            {
                //addcollumn
                for (int j = cols + 1; j >= 0; j--)
                {
                    if (j == cols + 1)
                    {
                        tbMod.addColumn("\\");
                    } else
                    {
                        tbMod.addColumn((inUserInt[1] - j));
                    }
                }
                
                //addrows
                rows=0;
                for (int i = inUserInt[2]; i < inUserInt[3]; i += 3)
                {
                    tbObj[0]=i;
                    tbMod.addRow(tbObj);
                    rows+=1;
                }
                
                int k=0;
                for (int i = inUserInt[2]; i < inUserInt[3]; i += 3)
                {
                    P = i;
                    
                    for (int j=0;j<tbMod.getColumnCount();j++)
                    {
                        if (j != tbMod.getColumnCount()-1)
                        {
                            Fg = inUserInt[0]+j;
                            if (Engine.CountPPO2(Fg, P) <= 1.6)
                            {

                                tbMod.setValueAt(df.format(Engine.CountPPO2(Fg, P)), k, j+1);

                            } else
                            {

                                tbMod.setValueAt("", k, j+1);
                            }
                        }

                    }
                    k+=1;
                }
                  
            }
            else
            {
                JOptionPane.showMessageDialog(this,"\n! Error !\n-Range Oxygen or Depths didn't valid ! \n-Prove your input is min value then max value");
            }
            
            
        }
        else
        {
            if (Engine.errorMsg != null)
            {
                JOptionPane.showMessageDialog(this,Engine.errorMsg);
            }
        }
        
        Engine.errorMsg=null;
    };
    
    public void CountingEAD(String param1,String param2)
    {
        P=Engine.convertStringtoDouble(param1);
	Fg=Engine.convertStringtoDouble(param2);

        if (Engine.isErrorInput == false)
        {
            Engine.CountEAD(Fg, P);
            if (Engine.isErrorInput == false)
            {
                resultTextPane.setText("< Equivalent Air Depth Calculation Result >"
                        + "\n> Oxygen Percentage : " + Fg + "% O2"
                        + "\n> Maximum Operating Depth : " + P + " metres"
                        + "\n> Equivalent Air Depth : " + Math.round(Engine.valueConverter("ata", "meter", Engine.EADResult )) + " metres");
                O2N2Mixture(Fg);
            } else
            {
                if (Engine.errorMsg != null)
                {
                    JOptionPane.showMessageDialog(this, Engine.errorMsg);
                }
            }
        } else
        {
            if (Engine.errorMsg != null)
            {
                JOptionPane.showMessageDialog(this, Engine.errorMsg);
            }
        }

        Engine.errorMsg = null;
    }
    
    public void CountingEADT(String param1,String param2)
    {
        
        inUser=param1.split(" ");
        resultTable.setModel(tbMod);
        
        if (inUser.length != 2)
        {
            Engine.isErrorInput = true;
            inUser = new String[2];
            Engine.errorMsg = "\n! Error ! : Input number can't less than 2 or  more than 2 parameters";
        }
        else
        {
            inUserInt[0]=Engine.convertStringtoInteger(inUser[0]);
            inUserInt[1]=Engine.convertStringtoInteger(inUser[1]);
            
            if ((inUserInt[0] < 18 || inUserInt[0] > 50)
                    || (inUserInt[1] < 18 || inUserInt[1] > 50))
            {
                Engine.isErrorInput = true;
                Engine.errorMsg = " ! Error ! -Only requires percentages of Oxygen from 18% to 50%.";
            } else
            {
                Engine.isErrorInput = false;
            }
            
        }
        
        if (Engine.isErrorInput==false)
        {
            inUser=param2.split(" ");
            
            if (inUser.length != 2)
            {
                Engine.isErrorInput = true;
                inUser = new String[2];
                Engine.errorMsg = "\n! Error ! : Input number can't less than 2 or  more than 2 parameters";
            } else
            {
                inUserInt[2] = Engine.convertStringtoInteger(inUser[0]);
                inUserInt[3] = Engine.convertStringtoInteger(inUser[1]);
                if ((inUserInt[2] <3 ||inUserInt[3] >70))
                {
                    Engine.isErrorInput = true;
                    Engine.errorMsg = " ! Error ! -The Depths should from 3 metres to 70 metres.";
                } else
                {
                    Engine.isErrorInput = false;
                }

            }
        }
        
        if (Engine.isErrorInput==false)
        {
            if (((inUserInt[0]>=18 && inUserInt[0]<=50) && (inUserInt[1]>=18 && inUserInt[1]<=50))
				&& ((inUserInt[2]>=3 && inUserInt[2]<=70) && (inUserInt[3]>=3 && inUserInt[3]<=70)))
            {
                rows=inUserInt[3]-inUserInt[2];
                cols=inUserInt[1]-inUserInt[0];
            
                jLabel5.setText("EAD Table Result For "+inUserInt[0]+" - "+inUserInt[1]+" O2 Percentage and "+inUserInt[2]
                    +" - "+inUserInt[3]+" Metres Depths");
                //drawing coloms on table
                if (rows >= 1 && cols >= 1)
                {
                    //addcollumn
                    for (int j = cols + 1; j >= 0; j--)
                    {
                        if (j == cols + 1)
                        {
                            tbMod.addColumn("\\");
                        } else
                        {
                            tbMod.addColumn((inUserInt[1] - j));
                        }
                    }

                    rows = 0;
                    for (int i = inUserInt[2]; i < inUserInt[3]; i += 3)
                    {
                        tbObj[0] = i;
                        tbMod.addRow(tbObj);
                        rows += 1;
                    }

                    int k = 0;
                    for (int i = inUserInt[2]; i < inUserInt[3]; i += 3)
                    {
                        P = i;

                        for (int j = 0; j < tbMod.getColumnCount(); j++)
                        {
                            if (j != tbMod.getColumnCount() - 1)
                            {
                                Fg = inUserInt[0] + j;
                                
                                tbMod.setValueAt(Math.round(Engine.valueConverter("ata", "meter",Engine.CountEAD(Fg, P))),k, j + 1);
                            }

                        }
                        k += 1;
                    }
                } 
                else
                {
                    JOptionPane.showMessageDialog(this, "\n! Error !\n-Range Oxygen or Depths didn't valid !\n-Prove your input is min value then max value");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"\n! Error !\n-For the purpose of this assignment Oxygen\n-Percentage set 18% upto 50%. i.e. 18 - 50 inclusive"
						+ "\n-And for Depths is set from 3m upto 70m. i.e. 3 - 70 inclusive");
			
            }
        }
        else
        {
            if (Engine.errorMsg != null)
            {
                JOptionPane.showMessageDialog(this,Engine.errorMsg);
            }
        }
        
        Engine.errorMsg=null;
    };
    
    public void CountingBestMix(String param1,String param2)
    {
        Pg = Engine.convertStringtoDouble(param1);
        P = Engine.convertStringtoDouble(param2);

        if (Engine.isErrorInput == false)
        {
            Engine.CountBestMix(Pg, P);
            if (Engine.isErrorInput == false)
            {
               
                resultTextPane.setText("< BestMix Calculation >\n> Oxygen Percentage : " + Math.round(Engine.BestMixResult) + "% O2"
                        + "\n> Partial Pressure of Oxygen : " + Pg + " ata"
                        + "\n> Maximum Operating Depth : " + P + " metres");
                        O2N2Mixture(Math.round(Engine.BestMixResult));
            } else
            {
                if (Engine.errorMsg != null)
                {
                    JOptionPane.showMessageDialog(this, Engine.errorMsg);
                }
            }
        } else
        {
            if (Engine.errorMsg != null)
            {
                JOptionPane.showMessageDialog(this, Engine.errorMsg);
            }
        }
        Engine.errorMsg = null;
    };
    
    public void clearTable()
    {
        tbMod.setColumnCount(0);
        tbMod.setRowCount(0);
        
    }

    /**
     * Creates new form MainForm
     */
    public MainForm()
    {
        initComponents();
        jComboBox1.setSelectedIndex(1);
        jComboBox1.setSelectedIndex(0);
        clearTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        labelParam2 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textParam1 = new javax.swing.JTextField();
        textParam2 = new javax.swing.JTextField();
        labelParam1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        ButtonDoCount = new javax.swing.JButton();
        ButtonReset = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultTextPane = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        labelAppTitle = new javax.swing.JLabel();
        labelAbout = new javax.swing.JLabel();
        labelHelp = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Scuba Diving Calculation Assistant");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                formMouseEntered(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener()
        {
            public void windowStateChanged(java.awt.event.WindowEvent evt)
            {
                formWindowStateChanged(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(0, 153, 255));

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        labelParam2.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        labelParam2.setForeground(new java.awt.Color(255, 255, 255));
        labelParam2.setText("Paramater 2");
        labelParam2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Calculation Mode");

        textParam1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textParam1.setForeground(new java.awt.Color(0, 153, 255));
        textParam1.setToolTipText("Insert First Parameter");

        textParam2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textParam2.setForeground(new java.awt.Color(0, 153, 255));
        textParam2.setToolTipText("Insert Second Parameter");

        labelParam1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        labelParam1.setForeground(new java.awt.Color(255, 255, 255));
        labelParam1.setText("Paramater 1");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(0, 153, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MOD", "SMOD", "Best Mix", "PPO2", "PPO2 Table", "EAD", "EAD Table", "Converter ata to metres", "Converter metres to ata" }));
        jComboBox1.setToolTipText("Choose Calculation Mode");
        jComboBox1.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent evt)
            {
                jComboBox1ItemStateChanged(evt);
            }
        });

        ButtonDoCount.setBackground(new java.awt.Color(255, 255, 255));
        ButtonDoCount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ButtonDoCount.setForeground(new java.awt.Color(0, 153, 255));
        ButtonDoCount.setText("Count <>");
        ButtonDoCount.setToolTipText("Do Counting Calculation");
        ButtonDoCount.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ButtonDoCountActionPerformed(evt);
            }
        });

        ButtonReset.setBackground(new java.awt.Color(255, 255, 255));
        ButtonReset.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ButtonReset.setForeground(new java.awt.Color(0, 153, 255));
        ButtonReset.setText("Reset");
        ButtonReset.setToolTipText("Reset All Values");
        ButtonReset.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ButtonResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelParam1)
                            .addComponent(textParam1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(textParam2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelParam2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonDoCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelParam2)
                    .addComponent(labelParam1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonReset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textParam2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textParam1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonDoCount, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(0, 153, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Results");

        resultTextPane.setEditable(false);
        resultTextPane.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        resultTextPane.setForeground(new java.awt.Color(0, 153, 255));
        resultTextPane.setToolTipText("Show Results Of Non Table Calculation");
        jScrollPane1.setViewportView(resultTextPane);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 275, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 255));
        jPanel3.setToolTipText("Show Results of Table Calculation");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Table Results");

        resultTable.setBackground(new java.awt.Color(0, 153, 204));
        resultTable.setForeground(new java.awt.Color(255, 255, 255));
        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {},
                {}
            },
            new String []
            {

            }
        ));
        resultTable.setEnabled(false);
        jScrollPane2.setViewportView(resultTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labelAppTitle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelAppTitle.setForeground(new java.awt.Color(255, 255, 255));
        labelAppTitle.setText("Scuba Diving Calculator Assitant");

        labelAbout.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelAbout.setForeground(new java.awt.Color(255, 255, 255));
        labelAbout.setText("About");
        labelAbout.setToolTipText("About Program and Authors");
        labelAbout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelAbout.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                labelAboutMouseClicked(evt);
            }
        });

        labelHelp.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelHelp.setForeground(new java.awt.Color(255, 255, 255));
        labelHelp.setText("Help");
        labelHelp.setToolTipText("Help For Use The Program");
        labelHelp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelHelp.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                labelHelpMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelAppTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelHelp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelAbout))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelAppTitle)
                    .addComponent(jLabel1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelHelp)
                            .addComponent(labelAbout))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_jComboBox1ItemStateChanged
    {//GEN-HEADEREND:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        selectedMode=jComboBox1.getSelectedItem().toString();
        jLabel3.setText(selectedMode+" Calculation Result");
        if (selectedMode.equals("MOD"))
        {
            labelParam1.setText("[Percentage of Oxygen]");
            labelParam2.setText("[Partial Pressure of Oxygen 1.1 to 1.6]");
            if (textParam2.isEnabled()==false)
            {
                textParam2.enable();
            }
        }
        else if (selectedMode.equals("SMOD"))
        {
            labelParam1.setText("[Percentage of Oxygen]");
            labelParam2.setText("[Partial Pressure of Oxygen Standard 1.4]");
            if (textParam2.isEnabled()==true)
            {
                textParam2.disable();
            }
        }else if (selectedMode.equals("PPO2"))
        {
            labelParam1.setText("[Depth of the dive( metres )]");
            labelParam2.setText("[Percentage of Oxygen]");
            if (textParam2.isEnabled()==false)
            {
                textParam2.enable();
            }
        }else if (selectedMode.equals("PPO2 Table") || selectedMode.equals("EAD Table"))
        {
            jLabel3.setText("Non Table Calculation Result");
            labelParam1.setText("[Start & End Percentage of Oxygen]");
            labelParam2.setText("[Start & End Depth of the disve( metres )]");
            if (textParam2.isEnabled()==false)
            {
                textParam2.enable();
            }
        }else if (selectedMode.equals("EAD"))
        {
            labelParam1.setText("[Depth of the dive( metres )]");
            labelParam2.setText("[Percentage of Oxygen]");
            if (textParam2.isEnabled()==false)
            {
                textParam2.enable();
            }
        }
        else if (selectedMode.equals("Best Mix"))
        {
            labelParam1.setText("[Partial Pressure of Oxygen 1.1 to 1.6]");
            labelParam2.setText("[Depth of the dive( metres )]");
            if (textParam2.isEnabled()==false)
            {
                textParam2.enable();
            }
        }
        else if (selectedMode.contains("Converter"))
        {
            if (selectedMode.equals("Converter ata to metres"))
            {
                labelParam1.setText("[Ata Value To Convert]");
            }
            else if (selectedMode.equals("Converter metres to ata"))
            {
                labelParam1.setText("[Metres Value To Convert]");
            }
            labelParam2.setText("");
            if (textParam2.isEnabled()==true)
            {
                textParam2.disable();
            }
        }
        ButtonDoCount.setText("Count "+selectedMode);
        ButtonReset.doClick();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void ButtonResetActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ButtonResetActionPerformed
    {//GEN-HEADEREND:event_ButtonResetActionPerformed
        // TODO add your handling code here:
        textParam1.setText(null);
        textParam2.setText(null);
        resultTextPane.setText(null);
        clearTable();
        fi.setVisible(false);
        fi.dispose();
        if (selectedMode.equals("PPO2 Table") || selectedMode.equals("EAD Table"))
        {
            jLabel5.setText(selectedMode+" Calculation Result");
        }
        else
        {
            jLabel5.setText("Tabel Calculation Results");
        }
    }//GEN-LAST:event_ButtonResetActionPerformed

    private void ButtonDoCountActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ButtonDoCountActionPerformed
    {//GEN-HEADEREND:event_ButtonDoCountActionPerformed
        // TODO add your handling code here:
        if (selectedMode.equals("MOD"))
        {
            CountingMOD(textParam1.getText(),textParam2.getText());
        }
        else if (selectedMode.equals("SMOD"))
        {
            CountingMOD(textParam1.getText(),"1.4");
        }
        else if (selectedMode.equals("PPO2"))
        {
            CountingPPO2(textParam1.getText(),textParam2.getText());
            
        }else if (selectedMode.equals("EAD"))
        {
            CountingEAD(textParam1.getText(),textParam2.getText());
            
        }else if (selectedMode.equals("Best Mix"))
        {
            CountingBestMix(textParam1.getText(),textParam2.getText());
        }
        else if (selectedMode.equals("PPO2 Table"))
        {
            clearTable();
            CountingPPO2T(textParam1.getText(),textParam2.getText());
        }
        else if (selectedMode.equals("EAD Table"))
        {
            clearTable();
            CountingEADT(textParam1.getText(),textParam2.getText());
        }
        else if (selectedMode.contains("Converter"))
        {
            Converter(textParam1.getText());
        }
        
    }//GEN-LAST:event_ButtonDoCountActionPerformed
  
    public void O2N2Mixture(double O2)
    {
        String o2;
        fi.o2Stat="O2 Percentage : "+O2+"%";
        fi.n2Stat="N2 Percentage : "+(100-O2)+"%";
        o2=String.valueOf(Math.round(O2)).toString();
        fi.O2percentage=Integer.valueOf(o2).intValue();
        
        if (O2<=100)
        {
            if (fi.isVisible() == true)
            {
                fi.setVisible(false);
                fi.dispose();
            }
            fi.setVisible(true);
            fi.setLocation(this.getX() + this.getWidth() - 2, this.getY());
        }
        else
        {
            int yes=JOptionPane.YES_OPTION;
            if (JOptionPane.showConfirmDialog(null,"Oxygen Percentage Out Of 100% ! its "+O2+
                    "% \nThis'll make O2 N2 Mixture ilustration getting out of cylinder\nDo you want to continue ?","Warning", JOptionPane.YES_NO_OPTION)==yes)
            {
                if (fi.isVisible() == true)
                {
                    fi.setVisible(false);
                    fi.dispose();
                }
                fi.setVisible(true);
                fi.setLocation(this.getX() + this.getWidth() - 2, this.getY());
            }
            else
            {
                if (fi.isVisible() == true)
                {
                    fi.setVisible(false);
                    fi.dispose();
                }
            }
        }
        
    }
    
    
    private void labelHelpMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_labelHelpMouseClicked
    {//GEN-HEADEREND:event_labelHelpMouseClicked
        // TODO add your handling code here:
        if (help.isVisible()==true)
        {
            help.setVisible(false);
            help.dispose();
        }
        
            help.setVisible(true);
            help.setLocation(this.getX(),this.getY());
    }//GEN-LAST:event_labelHelpMouseClicked

    private void labelAboutMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_labelAboutMouseClicked
    {//GEN-HEADEREND:event_labelAboutMouseClicked
        
        // TODO add your handling code here:
        if (about.isVisible()==true)
        {
            about.setVisible(false);
            about.dispose();
        }
        
            about.setVisible(true);
            about.setLocation(this.getX(),this.getY());
    }//GEN-LAST:event_labelAboutMouseClicked

    private void formMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseEntered
    {//GEN-HEADEREND:event_formMouseEntered
        // TODO add your handling code here:
        if (fi.isVisible()==true)
        {
            fi.setLocation(this.getX()+this.getWidth()-2,this.getY());
        }
    }//GEN-LAST:event_formMouseEntered

    private void formWindowStateChanged(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowStateChanged
    {//GEN-HEADEREND:event_formWindowStateChanged
        // TODO add your handling code here:
            fi.setState(this.getState());
    }//GEN-LAST:event_formWindowStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonDoCount;
    private javax.swing.JButton ButtonReset;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelAbout;
    private javax.swing.JLabel labelAppTitle;
    private javax.swing.JLabel labelHelp;
    private javax.swing.JLabel labelParam1;
    private javax.swing.JLabel labelParam2;
    private javax.swing.JTable resultTable;
    private javax.swing.JTextPane resultTextPane;
    private javax.swing.JTextField textParam1;
    private javax.swing.JTextField textParam2;
    // End of variables declaration//GEN-END:variables
}
