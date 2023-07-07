package gui.components.dialog;

import com.jogamp.opengl.GL2;
import engine.assets.Icons;
import gui.GUIComponent;
import org.jutils.jhardware.HardwareInfo;
import org.jutils.jhardware.model.ProcessorInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SystemInfo extends AbstractDialog
{
    private GL2 gl;

    private final ProcessorInfo info;

    private JLabel iconLabel;

    private JLabel osNameLabel;

    private JLabel cpuNameLabel;
    private JLabel cpuCoresLabel;
    private JLabel cpuThreadsLabel;
    private JLabel cpuSpeedLabel;

    private JLabel memoryLabel;

    private JLabel gpuNameLabel;
    private JLabel gpuVendorLabel;
    private JLabel gpuMemoryLabel;

    private JButton closeButton;

    public SystemInfo(int width, int height, JComponent component)
    {
        super(width, height, "System Info", component);

        info = HardwareInfo.getProcessorInfo();
    }

    public void init(GL2 gl)
    {
        this.gl = gl;

        iconLabel = new JLabel(Icons.createIcon(Icons.PC_ICON_PATH, 64));

        osNameLabel = new JLabel("Name: " + getOperatingSystemName());

        cpuNameLabel = new JLabel("Name: " + getCPUName());
        cpuCoresLabel = new JLabel("Cores: " + getNumCPUCores());
        cpuThreadsLabel = new JLabel("Threads: " + getNumCPUThreads());
        cpuSpeedLabel = new JLabel("Speed: " + getCPUSpeed() + " MHz");

        memoryLabel = new JLabel("Memory: " + getMemory() + " MB");

        gpuNameLabel = new JLabel("Name: " + getGPUName());
        gpuVendorLabel = new JLabel("Vendor: " + getGPUVendor());
        gpuMemoryLabel = new JLabel("Memory: " + getGPUMemory() + " MB");

        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> close());

        createAndShowGUI();
    }

    private String getOperatingSystemName()
    {
        return System.getProperty("os.name");
    }

    private String getCPUName()
    {
        return info.getModelName();
    }

    private String getNumCPUCores()
    {
        return info.getNumCores();
    }

    private int getNumCPUThreads()
    {
        return Runtime.getRuntime().availableProcessors();
    }

    private String getCPUSpeed()
    {
        return info.getMhz();
    }

    private long getMemory()
    {
        return Runtime.getRuntime().totalMemory();
    }

    private String getGPUName()
    {
        return gl.glGetString(GL2.GL_RENDERER);
    }

    private String getGPUVendor()
    {
        return gl.glGetString(GL2.GL_VENDOR);
    }

    private String getGPUMemory()
    {
        return gl.glGetString(GL2.GL_GPU_MEMORY_INFO_TOTAL_AVAILABLE_MEMORY_NVX);
    }

    public void createAndShowGUI()
    {
        this.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1));
        
        // OS
        GroupPanel osPanel = new GroupPanel("Operating System");
        osPanel.add(osNameLabel);

        // CPU
        GroupPanel cpuPanel = new GroupPanel("CPU");

        cpuPanel.add(cpuNameLabel);
        cpuPanel.add(cpuCoresLabel);
        cpuPanel.add(cpuThreadsLabel);
        cpuPanel.add(cpuSpeedLabel);

        // Memory
        GroupPanel memoryPanel = new GroupPanel("Memory");
        
        memoryPanel.add(memoryLabel);

        // GPU
        GroupPanel gpuPanel = new GroupPanel("GPU");

        gpuPanel.add(gpuNameLabel);
        gpuPanel.add(gpuVendorLabel);
        gpuPanel.add(gpuMemoryLabel);

        mainPanel.add(osPanel);
        mainPanel.add(cpuPanel);
        mainPanel.add(memoryPanel);
        mainPanel.add(gpuPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);

        this.add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(closeButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        iconLabel.setBorder(new EmptyBorder(Icons.ICON_SIZE, Icons.ICON_SIZE, Icons.ICON_SIZE, Icons.ICON_SIZE));
        iconLabel.setVerticalAlignment(JLabel.NORTH);
        this.add(iconLabel, BorderLayout.WEST);
    }

    private class GroupPanel extends JPanel implements GUIComponent
    {
        public GroupPanel(String title)
        {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            TitledBorder titledBorder = new TitledBorder(title);
            titledBorder.setTitleFont(titledBorder.getTitleFont().deriveFont(Font.BOLD));
            titledBorder.setTitleJustification(TitledBorder.CENTER);
            titledBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
            titledBorder.setBorder(new EmptyBorder(1, 1, Icons.ICON_SIZE, 1));
            this.setBorder(titledBorder);
        }

        public void createAndShowGUI() {}
    }
}