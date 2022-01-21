/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mainpackage.MainRun;

/**
 *
 * @author Saleh
 */
public class Menu {

    private JMenuBar menubar;
    private JMenu menuFile;
    private JMenu menuView;
    private JMenuItem itemInsertEmployee;
    private JMenuItem itemMetalLookAndFeel;
    private JMenuItem itemSystemLookAndFeel;
    private NewEmployeeDialog newEmployeeDialog;
    private Preferences prefs;
    private LookAndFeelLisener lookAndFeelLisener;
    public static final String THEME = "Look_and_feel";

    public Menu() {

        super();

    }

    public void createMenuBar() {

        MenuItemActions menuItemActions = new MenuItemActions();
        prefs = Preferences.userNodeForPackage(Menu.class);

        menubar = new JMenuBar();
        menuFile = new JMenu("File");
        menuView = new JMenu("View");
        itemInsertEmployee = new JMenuItem("New Employee");
        itemMetalLookAndFeel = new JMenuItem("Java theme");
        itemSystemLookAndFeel = new JMenuItem("System theme");
        menuFile.add(itemInsertEmployee);
        menuView.add(itemMetalLookAndFeel);
        menuView.add(itemSystemLookAndFeel);
        itemInsertEmployee.addActionListener(menuItemActions);
        itemMetalLookAndFeel.addActionListener(menuItemActions);
        itemSystemLookAndFeel.addActionListener(menuItemActions);
        menubar.add(menuFile);
        menubar.add(menuView);
    }

    public JMenuBar getMenuBar() {
        return this.menubar;
    }

    public void setLookAndFeelListener(LookAndFeelLisener lookAndFeelLisener) {
        this.lookAndFeelLisener = lookAndFeelLisener;
    }

    private class MenuItemActions implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == itemInsertEmployee) {
                newEmployeeDialog = new NewEmployeeDialog(MainRun.getFrame(), "Create New Employee", true);
            } else if (source == itemMetalLookAndFeel || source == itemSystemLookAndFeel) {
                try {
                    String themeName = null;
                    if (source == itemMetalLookAndFeel) {
                        themeName = UIManager.getCrossPlatformLookAndFeelClassName();
                        UIManager.setLookAndFeel(themeName);
                        prefs.put(THEME, themeName);
                    } else if (source == itemSystemLookAndFeel) {
                        themeName = UIManager.getSystemLookAndFeelClassName();
                        UIManager.setLookAndFeel(themeName);
                        prefs.put(THEME, themeName);
                    }
                    lookAndFeelLisener.lookAndFeelChanged();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}