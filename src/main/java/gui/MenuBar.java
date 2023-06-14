package gui;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar implements GUIComponent
{
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu windowMenu;
    private JMenu toolsMenu;
    private JMenu helpMenu;

    // File menu
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveAsMenuItem;
    private JMenuItem exitMenuItem;

    // Edit menu
    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;
    private JMenuItem showHistoryMenuItem;
    private JMenuItem cutMenuItem;
    private JMenuItem copyMenuItem;
    private JMenuItem pasteMenuItem;
    private JMenuItem duplicateMenuItem;
    private JMenuItem deleteMenuItem;

    // Window menu


    // Tools menu


    // Help menu
    private JMenuItem helpMenuItem;
    private JMenuItem contactSupportMenuItem;
    private JMenuItem reportBugMenuItem;
    private JMenuItem submitFeedbackMenuItem;
    private JMenuItem checkForUpdatesMenuItem;
    private JMenuItem aboutMenuItem;

    public MenuBar()
    {
        createAndShowGUI();
    }

    public void createAndShowGUI()
    {
        // File menu
        fileMenu = new JMenu("File");

        openMenuItem = fileMenu.add("Open");
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));

        saveMenuItem = fileMenu.add("Save");
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));

        saveAsMenuItem = fileMenu.add("Save As");
        saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));

        fileMenu.addSeparator();

        exitMenuItem = fileMenu.add("Exit");

        // Edit menu
        editMenu = new JMenu("Edit");

        undoMenuItem = editMenu.add("Undo");
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_DOWN_MASK));

        redoMenuItem = editMenu.add("Redo");
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));

        showHistoryMenuItem = editMenu.add("Show History");
        showHistoryMenuItem.setAccelerator(KeyStroke.getKeyStroke('H', InputEvent.CTRL_DOWN_MASK));

        editMenu.addSeparator();

        cutMenuItem = editMenu.add("Cut");
        cutMenuItem.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_DOWN_MASK));
        copyMenuItem = editMenu.add("Copy");
        copyMenuItem.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK));
        pasteMenuItem = editMenu.add("Pate");
        pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK));
        duplicateMenuItem = editMenu.add("Duplicate");
        duplicateMenuItem.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_DOWN_MASK));
        deleteMenuItem = editMenu.add("Delete");
        deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));

        // Window menu
        windowMenu = new JMenu("Window");

        // Tools menu
        toolsMenu = new JMenu("Tools");

        // Help menu
        helpMenu = new JMenu("Help");

        helpMenuItem = helpMenu.add("Help");
        helpMenu.addSeparator();

        contactSupportMenuItem = helpMenu.add("Contact Support");
        reportBugMenuItem = helpMenu.add("Report a Bug");
        submitFeedbackMenuItem = helpMenu.add("Submit Feedback");
        helpMenu.addSeparator();

        checkForUpdatesMenuItem = helpMenu.add("Check for Updates");
        aboutMenuItem = helpMenu.add("About");

        this.add(fileMenu);
        this.add(editMenu);
        this.add(windowMenu);
        this.add(toolsMenu);
        this.add(helpMenu);
    }
}