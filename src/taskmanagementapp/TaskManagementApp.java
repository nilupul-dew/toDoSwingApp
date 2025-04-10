package taskmanagementapp;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class TaskManagementApp {
    
    
    private JFrame frame;
    private JPanel titleBar;
    private JLabel titleLable;
    private JLabel closeLable;
    private JLabel minimizeLabel;
    private JPanel dashboardpanel;
    private JButton addTaskButton;
    private ArrayList<String> tasks = new ArrayList<>();

    //variables for dragging the form
    private boolean isDragging = false;
    private Point mouseoffset;
    
    //constructor
    public TaskManagementApp(){
        //create the main JFrame
        frame = new JFrame();
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        //frame.setBackground(new Color(255,255,55));
        frame.getRootPane().setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(7,7,7,7,new Color(211,84,0)),new EmptyBorder(0,0,0,0)));
        
        //create the title bar
        titleBar= new JPanel();
        titleBar.setLayout(null);
        titleBar.setBackground(new Color(12,22,30));
        titleBar.setPreferredSize(new Dimension(frame.getWidth(),30));
        titleBar.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                isDragging = true;
                mouseoffset = e.getPoint();
            }
            
            @Override
            public void mouseReleased (MouseEvent e){
                isDragging = false;
                
            }
        });
        
        titleBar.addMouseMotionListener(new MouseAdapter(){
                
            @Override
            public void mouseDragged(MouseEvent e){
                if (isDragging){
                    Point newLocation = e.getLocationOnScreen();
                    newLocation.translate(-mouseoffset.x, -mouseoffset.y);
                    frame.setLocation(newLocation);
                }
            }
            });
        
        frame.add(titleBar, BorderLayout.NORTH);
        
        //create and configure the title label
        titleLable = new JLabel("Task Manager");
        titleLable.setForeground(Color.white);
        titleLable.setFont(new Font("Arial", Font.BOLD,17));
        titleLable.setBounds(10,0,250,30);
        titleBar.add(titleLable);
       
        //create and configure the close label/exit button
        closeLable = new JLabel("x");
        closeLable.setForeground(Color.white);
        closeLable.setFont(new Font("arial",Font.BOLD,16));
        closeLable.setHorizontalAlignment(SwingConstants.CENTER);
        closeLable.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeLable.setBounds(frame.getWidth()-50,0,30,30);
        closeLable.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e)
            {System.exit(0);
        
            }
            
            
            @Override
            public void mouseEntered(MouseEvent e){
                closeLable.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e){
                closeLable.setForeground(Color.white);
            }

        });
        titleBar.add(closeLable);
        
        //create and configure the minimize label
        minimizeLabel=new JLabel("-");
        minimizeLabel.setForeground(Color.white);
        minimizeLabel.setFont(new Font("arial",Font.BOLD,17));
        minimizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        minimizeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        minimizeLabel.setBounds(frame.getWidth()-75,0,30,30);
        minimizeLabel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                frame.setState(JFrame.ICONIFIED);
            }
            
            @Override
            public void mouseEntered(MouseEvent e){
                minimizeLabel.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e){
                minimizeLabel.setForeground(Color.white);
            }

        });
        
        titleBar.add(minimizeLabel);
        
        //create the dashboard panel
        dashboardpanel = new JPanel();
        dashboardpanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        dashboardpanel.setBackground(new Color(206,214,224));
        frame.add(dashboardpanel,BorderLayout.CENTER);
        
        //create and config add tsask button
        addTaskButton = new JButton("Add task");
        addTaskButton.setBackground(new Color(16, 172, 132));
        addTaskButton.setForeground(Color.white);
        addTaskButton.setFont(new Font("Arial",Font.BOLD,16));
        addTaskButton.setFocusPainted(false);
        addTaskButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        //action listener for task button
        addTaskButton.addActionListener((e)->{
        
            //limit the max tasks to 12
            if(tasks.size()<12){
                String task = JOptionPane.showInputDialog(frame, "Enter a new task: ", "add task",JOptionPane.PLAIN_MESSAGE);
                tasks.add(task);
                updateTaskPanel();
               
            }
            
            else{
                //JOptionPane.showMessageDialog(frame, "you cannot add more than 12 tasks, delete some to add a new one" ,"task limit exceed");
                JOptionPane.showMessageDialog(frame, "You cannot add more than 12 tasks, Delete some to add a new one", "Task Limit Exceeded", JOptionPane.WARNING_MESSAGE);
            }
    });
        frame.add(addTaskButton, BorderLayout.SOUTH);
        
        
     
        frame.setVisible(true);
        
    }
    
    
    //update the task panel with the current list of tasks
    private void updateTaskPanel(){
        dashboardpanel.removeAll();
        for (String task: tasks){
            addtaskPanel(task);
        }
        
        dashboardpanel.revalidate();
        dashboardpanel.repaint();
    }
    
    //add a task panel with the delete button to the dashboard panel
    private void addtaskPanel(String task){
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BorderLayout());
        taskPanel.setPreferredSize(new Dimension(240,80));
        taskPanel.setBackground(Color.WHITE);
        taskPanel.setBorder(new LineBorder(Color.black,1));
        
        JLabel taskLabel = new JLabel(task);
        taskLabel.setHorizontalAlignment(SwingConstants.CENTER);
        taskPanel.add(taskLabel, BorderLayout.CENTER);
        
        //add mouse listener to task dialog
        
        taskPanel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                showTaskDetails(task);
            }
        });
        
        
        
        //button remove task
        JButton deletebutton = new JButton("delete");
        deletebutton.setBackground(new Color(231, 76, 60));
        deletebutton.setForeground(Color.red);
        deletebutton.setBorderPainted(false);
        deletebutton.setFocusPainted(false);
        //deletebutton.setCursor(Cursor.HAND_CURSOR);
        //remove task action
        deletebutton.addActionListener((e)->{
            tasks.remove(task);
            updateTaskPanel();
        });
        
        
        taskPanel.add(deletebutton, BorderLayout.SOUTH);
        dashboardpanel.add(taskPanel); 
        
    }
    
    //show task details in a custom dialog
            private void showTaskDetails (String task){
                CustomDialog customDialog = new CustomDialog(frame, "Task Details", task);
                customDialog.setVisible(true);
            }
    
    
    //create a custom dialog class
    class CustomDialog extends JDialog{
        public CustomDialog(JFrame parent, String title, String content){
            super(parent, title, true);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(300,200);
            setLocationRelativeTo(null);
            
            JPanel panel = new JPanel(new BorderLayout());
            JTextArea textArea = new JTextArea(content);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(new Font("Arial",Font.PLAIN, 17));
            JScrollPane scrollPane = new JScrollPane(textArea);
            panel.add(scrollPane, BorderLayout.CENTER);
            
            JButton closeButton = new JButton ("close");
            closeButton.setPreferredSize(new Dimension(120,30));
            closeButton.setFont(new Font("arial", Font.PLAIN, 13));
            closeButton.setBackground(Color.red);
            closeButton.setForeground(Color.white);
            closeButton.setFocusPainted (false);
            closeButton.setBorderPainted (false);

            
            closeButton.addActionListener((e->{
                dispose();
            }));
            
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(closeButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);
            getContentPane().add(panel);
            
            
        }
    }

    public static void main(String[] args) {
        
        new TaskManagementApp();
    }
    
}
