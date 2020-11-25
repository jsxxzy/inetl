// Author: d1y<chenhonzhou@gmail.com>

package io.xxzy.desktop;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.*;
import javax.swing.JList;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * `桌面端` 实现
 * https://stackoverflow.com/q/22869744/10272586
 */
public class go
{
    public static void main(String[] args)
    {
        JFrame guiFrame=new JFrame();
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Client GUI");
        guiFrame.setSize(420,420);
        guiFrame.setLocationRelativeTo(null);

        final JPanel comboPanel = new JPanel();
        JLabel Msg = new JLabel("Type Message");
        JTextField textbox=new JTextField(10);
        comboPanel.add(Msg);
        comboPanel.add(textbox);
//        textbox.addActionListener(this);
        String text = textbox.getText();
        //textArea.append(text + newline);
        //textbox.selectAll();
        textbox.setText("");

        //final JPanel comboPanel1 = new JPanel();
        //JLabel listLb2 = new JLabel("Connect");
        //comboPanel.add(listLb2 );
        JButton connect=new JButton("Connect");
        guiFrame.add(comboPanel,BorderLayout.NORTH);
        guiFrame.add(connect,BorderLayout.SOUTH);
        guiFrame.setVisible(true);
    }
}