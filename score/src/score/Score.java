package score;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;  
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
public class Score {
    public static void main(String[] args) {
        // TODO code application logic here
        Dimension s=Toolkit.getDefaultToolkit().getScreenSize();   //获取屏幕分辨率
        Font font = new Font("楷体", Font.PLAIN, 16);        
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("Menu.font", font);
        analyse test=new analyse(s.width,s.height);
        test.setLocationRelativeTo(null);
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.setVisible(true);
    }
}
// 画柱状图
class barchart { 
      barchart(int[] count){
      DefaultCategoryDataset dataset=new DefaultCategoryDataset();
      dataset.addValue(count[0],"" ,"<60分" );
      dataset.addValue(count[1],"" ,"60-69分" );
      dataset.addValue(count[2],"" ,"70-79分" );
      dataset.addValue(count[3],"" ,"80-89分" );
      dataset.addValue(count[4],"" ,">=90分" );
      StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
      standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));  
      standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));  
      standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));  
      ChartFactory.setChartTheme(standardChartTheme); 
      JFreeChart chart=ChartFactory.createBarChart("成绩分析","分数", "人数", dataset, PlotOrientation.VERTICAL, true, true, true);
      ChartFrame frame =new ChartFrame("成绩分析",chart);
      frame.setSize(500,300);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
}
}
//画饼图
class piechart {
      piechart(int[] count){
      DefaultPieDataset dataset=new DefaultPieDataset();
      dataset.setValue("<60分",count[0]);                           //向数据集插入数据
      dataset.setValue("60-69分",count[1]);
      dataset.setValue("70-79分",count[2]);
      dataset.setValue("80-89分",count[3]);
      dataset.setValue(">=90分",count[4]);
      StandardPieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(("{0}={1}({2})"), NumberFormat.getNumberInstance(),new DecimalFormat("0.00%")); //显示百分比
      StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
      standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));  
      standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));  
      standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));  
      ChartFactory.setChartTheme(standardChartTheme); 
      JFreeChart chart=ChartFactory.createPieChart("成绩分析", dataset, true, true, true);
      PiePlot pieplot=(PiePlot)chart.getPlot();                     
      pieplot.setLabelGenerator(generator);
      ChartFrame frame =new ChartFrame("成绩分析",chart);
      frame.setSize(500,300);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
}
}
//班级类
class classes implements Serializable{
     student[] students=new student[50];
     String course=null;
     String className=null;
     int length=0;
}
//学生类
class student implements Serializable{
     String name=null;
     String number=null;
     int score=0;
}
class fenxi extends JFrame implements ActionListener{
     private JButton b1=new JButton("保存修改");
     private JButton b4=new JButton("成绩分析");
     private JButton b5=new JButton("柱状图分析");
     private JButton b6=new JButton("饼图分析");
     private JPanel p3=new JPanel();
     static final int column1 = 0;
     static final int column2 = 1;
     static final int column3 = 4;
     private int row=0;
     private barchart chart1;
     private piechart chart2;
     private JTable jtb;
     private TableModel tm=null;
     private classes cla=new classes();
     fenxi(final classes c){
         cla=c;
         jtb=new JTable(40,5);
         p3.add(b1);
         p3.add(b4);
         p3.add(b5);
         p3.add(b6);
         b1.addActionListener(this);
         b4.addActionListener(this);
         b5.addActionListener(this);
         b6.addActionListener(this);
         p3.add(jtb); 
         jtb.getColumnModel().getColumn(0).setPreferredWidth(100);
         tm=jtb.getModel();
         jtb.setValueAt("学号", row, column1);
	 jtb.setValueAt("姓名", row, column2);
         jtb.setValueAt("成绩", row++, column3); 
         tm.addTableModelListener(new TableModelListener(){
               public void tableChanged(TableModelEvent e){
               if (0<jtb.getSelectedRow()&&jtb.getSelectedRow()<=cla.length&& jtb.getSelectedColumn() == column3) {
		   cla.students[jtb.getSelectedRow()-1].score=Integer.parseInt(jtb.getValueAt(jtb.getSelectedRow(),jtb.getSelectedColumn()).toString());
//                 System.out.println(cla.students[0].score);
               }
               }
         });
         for(int i=0;i<cla.length;i++) {  
			jtb.setValueAt(cla.students[i].number, row, column1);
                        jtb.setValueAt(cla.students[i].name, row, column2);
			jtb.setValueAt(cla.students[i].score, row++, column3);
         }
         add(p3);
         setTitle(cla.className+"-"+cla.course+"成绩统计");
         setSize(450, 400);
         setLocationRelativeTo(null);
         setVisible(true);
     }
     public void actionPerformed(ActionEvent e){
         if(e.getSource()==b4||e.getSource()==b5||e.getSource()==b6)
          {
              int i=0;
              int max=0;
              int min=cla.students[0].score;
              int sum=0;
              double average=0;
              int[] count={0,0,0,0,0};
              double[] percentage={0,0,0,0,0};
              for(i=0;i<cla.length;i++)
              {
                  sum+=cla.students[i].score;
                  if(cla.students[i].score<min)min=cla.students[i].score;
                  if(cla.students[i].score>max)max=cla.students[i].score;
                  if(cla.students[i].score<60)count[0]++;
                  else if(cla.students[i].score>=60&&cla.students[i].score<70)count[1]++;
                  else if(cla.students[i].score>=70&&cla.students[i].score<80)count[2]++;
                  else if(cla.students[i].score>=80&&cla.students[i].score<90)count[3]++;
                  else count[4]++;
              }
              average=1.0*sum/cla.length;
              for(i=0;i<5;i++)
                  percentage[i]=1.0*count[i]/cla.length*100;
              if(e.getSource()==b4)
                      JOptionPane.showMessageDialog(this,("最高分："+max+"分,  最低分："+min+"分,  平均分："+average+"分\n"
                            + "不及格（分数<60）："+count[0]+"人，占"+percentage[0]+"%\n"+
                              "及格（60<=分数<70）："+count[1]+"人，占"+percentage[1]+"%\n"+
                              "中等（70<=分数<80）："+count[2]+"人，占"+percentage[2]+"%\n"+
                              "良好（80<=分数<90）："+count[3]+"人，占"+percentage[3]+"%\n"+
                              "优秀（90<=分数<100）："+count[4]+"人，占"+percentage[4]+"%\n"));
               if(e.getSource()==b5)
                   chart1=new barchart(count);
               if(e.getSource()==b6)
                   chart2=new piechart(count);
          }
         if(e.getSource()==b1)
          {     try {
//                  System.out.println(cla.students[0].score);
                  savefile(cla);
              } catch (IOException ex) {
                 System.out.println("保存文件错误");
              }
             row=1;  
          }
     }
     public void savefile(classes c)throws IOException{
          try{
              File file=new File("src/"+c.className+"-"+c.course+".dat");
              ObjectOutputStream ops=new ObjectOutputStream(new FileOutputStream(file));
              ops.writeObject(c);
              ops.close();
          }catch(IOException e){
              System.out.println("保存文件错误");
          }
     }
     @Override
      public String toString(){
         return (String)(jtb.getValueAt(jtb.getSelectedRow(),jtb.getSelectedColumn()));
      }
}
class analyse extends JFrame implements ActionListener{
     private JButton b2=new JButton("新建成绩单");
     private JButton b3=new JButton("保存");
     private JPanel p;
     private JPanel p1=new JPanel();
     private JPanel p2=new JPanel();
     private JPanel p3=new JPanel();
     private JPanel p4=new JPanel();
     private JPanel p5=new JPanel();
     private JPanel p6=new JPanel();
     private JMenuBar m=new JMenuBar();                 //菜单条
     private JMenu menu=new JMenu("菜单");
     private JMenuItem m1=new JMenuItem("首页");
     private JMenuItem m2=new JMenuItem("新建课程考试成绩单");     //菜单项
     private JMenuItem m3=new JMenuItem("读取课程考试成绩单");
     private JComboBox jcb;
     private JComboBox jcb1;
     private JFileChooser filechooser=new JFileChooser();
     private CardLayout card;
     private String[] lineTxt=new String[3];
     private String coursename=null;
     private classes cla=null;
     private classes sc=null;
     analyse(int w,int h){
         card=new CardLayout(5,5);
         p=new JPanel(card);
         menu.add(m1);
         menu.add(m2);
         menu.add(m3);
         m.add(menu);
         p1.add(new JLabel("欢迎使用学生成绩分析系统"));
         p.add(p1,"a1");
         p.add(p2,"a2");
         p.add(p3,"a3");
         p.add(p4,"a4");
         p.add(p5,"a5");
         p2.add(new JLabel("请选择课程："));
         String s=new String("src/course.txt");
              try {
                  File file=new File(s);
                  readfile(file,1);
              } catch (IOException ex) {
                 System.out.println("读取文件错误");
              }
         jcb=new JComboBox(lineTxt);
         coursename=(String)(jcb.getSelectedItem());
         jcb.addItemListener(new ItemListener(){
             public void itemStateChanged(ItemEvent e){
                coursename=(String)(jcb.getSelectedItem());
             }
         });
         p2.add(jcb);
         p2.add(b2);
         b2.addActionListener(this);
         add(m,BorderLayout.NORTH);
         add(p,BorderLayout.CENTER);       
         m1.addActionListener(this);
         m2.addActionListener(this); 
         m3.addActionListener(this); 
         setTitle("学生成绩分析系统");
         setBounds((w-78-50*9)/2,(h-59-50*9)/2,78+50*9,59+50*9);
         this.setSize(450, 400);
         validate();
         setResizable(false);                         //窗体不可以最大化
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     }
     public void actionPerformed(ActionEvent e){
          if(e.getSource()==m1)
          {   card.show(p,"a1");
          }
          if(e.getSource()==m2)
          {    
              card.show(p,"a2");
          }
          if(e.getSource()==m3)
          {   
               
               if(filechooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
              {
                  File file=filechooser.getSelectedFile();
                  try {
                      readfile(file,3);
//                      System.out.println("123");
                  } catch (IOException ex) {
                      System.out.println("读取文件错误");
                  }
              }
              fenxi fx=new fenxi(cla);
              card.show(p,"a3");
          }
          if(e.getSource()==b2)
          {   
              int i=0;
               if(filechooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
              {
                  File file=filechooser.getSelectedFile();
                  try {
                      readfile(file,2);
                  } catch (IOException ex) {
                      System.out.println("读取文件错误");
                  }                  
              }
              File file=new File("src/"+sc.className+"-"+sc.course+".dat");
              if(!file.exists()){
              while(sc.students[i]!=null)
              {sc.students[i].score=Integer.parseInt(JOptionPane.showInputDialog(sc.students[i].number+sc.students[i].name));
              i++;
              }
              b3.addActionListener(this);
              p5.add(b3);
              card.show(p,"a5");
              }
              else{ JOptionPane.showMessageDialog(null,"该班该课程成绩已存在，无需输入");
              }
          }
          if(e.getSource()==b3)
          {   try {
                  savefile(sc);
              } catch (IOException ex) {
                 System.out.println("保存文件错误");
              }
          }
          
     }
     public void readfile(File file,int n)throws IOException{
          try{
               String encoding="GBK";
               if(file.isFile() && file.exists()){ 
                    if(n==1)                     //读课程
                    {
                        InputStreamReader read=new InputStreamReader(
                    new FileInputStream(file),encoding);
                    BufferedReader bufferedReader=new BufferedReader(read);
                    int i=0;
                    while(i<3&&((lineTxt[i]=bufferedReader.readLine()) != null)){
                        i++;
                    }read.close();
                    }
                    if(n==2)                      //读学生名单
                    {
                        InputStreamReader read=new InputStreamReader(
                    new FileInputStream(file),encoding);
                    BufferedReader bufferedReader=new BufferedReader(read);                   
                    int i=0;    
                    String a=new String();
                    sc=new classes();
                    sc.course=coursename;
                    sc.className=file.getName().substring(0, file.getName().lastIndexOf("."));
                    while((a=bufferedReader.readLine()) != null){
                        String s[]=a.split(" ");
                        sc.students[i]=new student();
                        sc.students[i].number=s[0];
                        sc.students[i].name=s[1];
                        i++;
                    }
                    sc.length=i;
                    read.close();
                    }
                    else if(n==3)                //读成绩单
                    {
                       ObjectInputStream read=new ObjectInputStream(new FileInputStream(file));
                       cla=new classes(); 
                       cla=(classes)(read.readObject());
                       read.close();
                    }
               }     
               else{
               System.out.println("找不到指定的文件");
               }
         }
          catch(IOException e){
              System.out.println("读取文件错误");
         }
          catch(ClassNotFoundException ex) {
              System.out.println("读取文件错误");
         }
     }
     public void savefile(classes c)throws IOException{
          try{
              File file=new File("src/"+c.className+"-"+c.course+".dat");
              ObjectOutputStream ops=new ObjectOutputStream(new FileOutputStream(file));
              ops.writeObject(c);
              ops.close();
          }catch(IOException e){
              System.out.println("保存文件错误");
          }
     }
}
