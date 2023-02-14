import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileFilter;
import java.awt.*;
import java.util.*;

import javax.swing.JPanel;

public class AssetRenamer extends JPanel{

    /**
     *
     */
    private static final long serialVersionUID = -8676022778238066112L;

    JButton chooseFileButton;
    JButton renameFilesButton;
    JFileChooser chooser;
    String chooserTitle;
    File directory;

    public AssetRenamer() {
        chooser = new JFileChooser();
        chooseFileButton = new JButton("Choose Folder");
        renameFilesButton = new JButton("Rename Files");
        
        directory = new File("Textures");
        System.out.println(chooser.getSelectedFile());
        ActionListener chooseFileActionListener=new ActionListener(){
            

			@Override
			public void actionPerformed(ActionEvent e) {
                
                chooser.setCurrentDirectory(new java.io.File(".\\Textures"));
                
                chooser.setDialogTitle(chooserTitle);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(AssetRenamer.this) == JFileChooser.APPROVE_OPTION) {
                    System.out.println(chooser.getSelectedFile().getPath());
                    directory = chooser.getSelectedFile();
        
                } else {
                    System.out.println("No selection");
                }				
			}
            
        };
        ActionListener renameFilesActionListener=new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                
                File[] files = directory.listFiles();
                for (File iFile : files) {
                    String str = iFile.getName();
                    boolean containsResources=str.contains("-resources") ;
                    boolean containsSharedAssets=str.contains("-sharedassets");
                    if (containsResources || containsSharedAssets) {
                        int st = 0;
                        if (containsResources) {
                            st = str.indexOf("-resources");
                        }
                        else if(containsSharedAssets){
                            st = str.indexOf("-sharedassets");
                        }
                        
                        
                        String subString = str.substring(st);
                        int typeIndex = str.lastIndexOf(".");
                        String typeSubString = str.substring(typeIndex);
                        System.out.println("---- FIle Name: "+str);
                        System.out.println(subString);
                        System.out.println(typeSubString);
                        subString=subString.replace(typeSubString, "");
                        String editedText = str.replace(subString, "");
                        System.out.println(editedText);
                        File exportFile = new File(directory.getAbsolutePath() + "\\" + editedText);
                        int i=1;
                        while (exportFile.exists()) {
                            String newString = editedText;
                            newString=newString.replace(typeSubString, "-" + i + typeSubString);
                            exportFile = new File(directory.getAbsolutePath() + "\\" + newString);
                            i++;
                        }
                        iFile.renameTo(exportFile);
                        
                    }
                }

            }

        };
        chooseFileButton.addActionListener(chooseFileActionListener);
        renameFilesButton.addActionListener(renameFilesActionListener);
        add(chooseFileButton);
        add(renameFilesButton);
    }
    

    public Dimension getPreferedSize() {
        return new Dimension(200, 200);
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("");
        AssetRenamer program = new AssetRenamer();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
                
            }
        });
        frame.getContentPane().add(program, "Center");
        frame.setSize(program.getPreferedSize());
        frame.setVisible(true);
    }   
}