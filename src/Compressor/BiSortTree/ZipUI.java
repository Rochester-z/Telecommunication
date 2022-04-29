package Compressor.BiSortTree;

import BiSortTree.StringCoding.BjPanel;
import BiSortTree.StringCoding.FileUnZip;
import BiSortTree.StringCoding.FileZip;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ZipUI{
    Image image = new ImageIcon("src/comzq1103/Image/bg.PNG").getImage();

    public static void main(String[] args) {
        ZipUI zipUI = new ZipUI();
        zipUI.startUI();
    }


    public void startUI(){
        JFrame jf = new JFrame();
        jf.setTitle("解压大师");
        jf.setSize(400,350);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(3);

        BjPanel jpanel = new BjPanel();
        jpanel.setLayout(new FlowLayout());
        //创建文本区域，用于显示相关信息
        JTextArea msgTextArea1 = new JTextArea(2,18);
        JTextArea msgTextArea2 = new JTextArea(2,18);
        JTextArea msgTextArea3 = new JTextArea(2,18);
        JTextArea msgTextArea4 = new JTextArea(2,18);




        JButton addBtn = new JButton("添加");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileOpenDialog(jf, msgTextArea1);
            }
        });

        JButton zipBtn = new JButton("压缩到");
        zipBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileSaveDialog(jf,msgTextArea2);
                String fileaddress = msgTextArea1.getText();
                String filezipaddress = msgTextArea2.getText();
                if(fileaddress.length()==0 || filezipaddress.length()==0){
                    return;
                }
                FileZip fileZip = new FileZip();
                fileZip.setFileAddress(fileaddress);
                fileZip.setFileZipAddress(filezipaddress);
                fileZip.compression();
            }
        });


        JButton openBtn = new JButton("打开");
        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileOpenDialog(jf,msgTextArea3);
            }
        });

        JButton unzipBtn = new JButton("解压到");
        unzipBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileSaveDialog(jf,msgTextArea4);
                String fileaddress = msgTextArea3.getText();
                String fileunzipaddress = msgTextArea4.getText();
                if(fileaddress.length()==0 || fileunzipaddress.length()==0){
                    return;
                }
                FileUnZip fileUnZip = new FileUnZip();
                fileUnZip.setFileaddress(fileaddress);
                fileUnZip.setFileunzipaddress(fileunzipaddress);
                fileUnZip.unZip();
            }
        });
        Dimension dim = new Dimension(100,30);
        addBtn.setPreferredSize(dim);
        zipBtn.setPreferredSize(dim);
        openBtn.setPreferredSize(dim);
        unzipBtn.setPreferredSize(dim);
        jpanel.add(msgTextArea1);
        jpanel.add(addBtn);
        jpanel.add(msgTextArea2);
        jpanel.add(zipBtn);
        jpanel.add(msgTextArea3);
        jpanel.add(openBtn);
        jpanel.add(msgTextArea4);
        jpanel.add(unzipBtn);

        jf.add(jpanel);



        jf.setVisible(true);
    }




    public void showFileOpenDialog(Component parent, JTextArea msgTextArea){

        //创建一个文件选择器
        JFileChooser filechooser = new JFileChooser();
        //设置默认显示的文件夹为当前文件夹
        filechooser.setCurrentDirectory(new File("."));

        //设置文件的选择模式，文件和文件夹皆可选
        filechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //设置是否允许多选
        filechooser.setMultiSelectionEnabled(true);

        //添加可用的文件过滤器，第一个参数是描述，第二个参数是需要过滤的文件扩展名
        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("zip","zip","rar"));
//        //设置默认的使用的文件过滤器
        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("image","jpg","png","gif"));
        filechooser.setFileFilter(new FileNameExtensionFilter("txt","txt"));


        //打开文件选择框（线程将被阻塞，直到选择框parent被关闭）
        int result = filechooser.showOpenDialog(parent);
        if(result == JFileChooser.APPROVE_OPTION) {
            //如果点击了“确定”，则获取选择的文件路径

            File file = filechooser.getSelectedFile();
            System.out.println("file" + file);
            msgTextArea.append(file.getAbsolutePath());

        }

    }



    public void showFileSaveDialog(Component parent, JTextArea msgTextArea){
        //创建一个默认的文件选择器
        JFileChooser fileChooser = new JFileChooser();
        //设置打开

        //打开文件选择框（线程将被阻塞，直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);
//        fileChooser.setSelectedFile(new File("测试文件.zip"));
        if(result == JFileChooser.APPROVE_OPTION){
            //如果点击了保存，则获取选择的保存路径
            File file = fileChooser.getSelectedFile();
            msgTextArea.append(file.getAbsolutePath());
        }
    }












}



