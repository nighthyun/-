//--------------------------------------------------------------//
// MyClient.java 2017年05月03日
//--------------------------------------------------------------//
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
//--------------------------------------------------------------//
//MyClient¥DÃþ§OÀÉ
//--------------------------------------------------------------//
public class MyClient extends JFrame implements ActionListener{
	//宣告區
	//設定名子及ip
	String	name,ip="";
	BufferedReader	reader;
	//BufferedReader  reader2;
	PrintStream	writer;
    //建立Socket變數
	Socket	sock;
	//Socket    sock2;
	//顯示區域
	JTextArea	incoming = new JTextArea(15,35);    //聊天顯示框(高,寬)
	JTextArea	mem = new JTextArea(15,10);    //成員顯示框(寬)
	//¿é¤J°Ï°ì
	JTextField	outgoing = new JTextField(20);     //文字輸入對話框(寬)
	JLabel	jlmane   = new JLabel("輸入帳號：");
	JLabel	jlip  = new JLabel("輸入密碼：");
	JTextField	jfmane   = new JTextField("帳號",10);
	JTextField	jfip   = new JTextField("127.0.0.1",11);
	JLabel	state  = new JLabel("請先登入");
 
	MenuBar mBar = new MenuBar();
	//File
	Menu mFile = new Menu("檔案");
    //Save
	MenuItem mFileSave=new MenuItem("儲存檔案");
	//--------------------------------------------------------------//
	//-1-主程式進入點
	//--------------------------------------------------------------//
	public static void main(String[] args){
		MyClient client = new MyClient();
	}
	//--------------------------------------------------------------//
	//-2-設定及宣告視窗物件
	//--------------------------------------------------------------//
	MyClient (){
		//設定及宣告視窗物件
		//建立視窗JFrame
		super("¦h¤J³s½uClientºÝ");
		//用來放mane及ip--設定區域
		JPanel maneipPanel  = new JPanel();
		//建登入按鍵
		JButton setmaneip = new JButton("登入");
		//建註冊按鍵
		JButton setmaneip2 = new JButton("註冊");
		//按下設定
		setmaneip.addActionListener(this);
		setmaneip2.addActionListener(this);
		//加入到JPanel
		maneipPanel.add(jlmane);
		//名字
		maneipPanel.add(jfmane);
		maneipPanel.add(jlip);
		//位子
		maneipPanel.add(jfip);
		//設定
		maneipPanel.add(setmaneip);
		maneipPanel.add(setmaneip2);
        //排版BorderLayout設定區域在上方----
		getContentPane().add(BorderLayout.NORTH,maneipPanel);
		
		//JButton("送出")
		JButton sendButton = new JButton("送出");
		//按下
		sendButton.addActionListener(this);
		//對話區域-----
		//設置為 true，則當行的長度大於所分派的寬度時，將換行
		incoming.setLineWrap(true);
		//設置為 true，則當行的長度大於所分派的寬度時，將在單詞邊界（空白）處換行
		incoming.setWrapStyleWord(true);
		//不可編輯的
		incoming.setEditable(false);
		//JScrollPane
		JScrollPane qScroller = new JScrollPane(incoming);
		//垂直滾動
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		//水平滾動
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JPanel mainPanel = new JPanel();
		mainPanel.add(qScroller);
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		//對話區域在中間------
		getContentPane().add(BorderLayout.CENTER,mainPanel);

		
		//設置為 true，則當行的長度大於所分派的寬度時，將在單詞邊界（空白）處換行
		mem.setLineWrap(true);
		//不可編輯的
		mem.setEditable(false);
		JScrollPane mScroller = new JScrollPane(mem);
		//垂直滾動
		mScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JPanel memPanel = new JPanel();
		memPanel.add(mScroller);
		//成員名單在右側
		getContentPane().add(BorderLayout.EAST,memPanel);
		
		//Menu事件
		mFileSave.addActionListener(this);
		//加入MenuItem
		mFile.add(mFileSave);
		//加入Menu
		mBar.add(mFile);
		//MenuBar
		setMenuBar(mBar);
		//狀態區域在下方----
		getContentPane().add(BorderLayout.SOUTH,state);
		setSize(550,450);
		setVisible(true);
		//離開
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e){
				System.out.println("離開聊天室");
				System.exit(0);
			}
		});
	}
	//--------------------------------------------------------------//
	//-3-建立連線
	//--------------------------------------------------------------//
	private void EstablishConnection(){
		try{
			//請求建立連線
			sock = new Socket(ip,8888);
			//sock2 = new Socket(ip,8888);
			//建立I/O資料流
			InputStreamReader streamReader = 
			//取得Socket的輸入資料流
			new InputStreamReader(sock.getInputStream());
			//放入暫存區
			reader = new BufferedReader(streamReader);
			//取得Socket的輸出資料流
			writer = new PrintStream(sock.getOutputStream());
			try{
				PrintStream out = new PrintStream(sock.getOutputStream());
				out.println("登入" + name + " " + ip);
				out.flush();
				//³s½u¦¨¥\
				state.setText("網路建立-登入成功");
				System.out.println("網路建立-連線成功");
			}
			catch(IOException ex ){
				System.out.println("建立連線失敗");
			}
		}catch(IOException ex ){
			System.out.println("建立連線失敗");
		}
	}
	private void EstablishConnection2(){
		try{
			//請求建立連線
			sock = new Socket(ip,8888);
			//sock2 = new Socket(ip,8888);
			//建立I/O資料流
			InputStreamReader streamReader = 
			//取得Socket的輸入資料流
			new InputStreamReader(sock.getInputStream());
			//放入暫存區
			reader = new BufferedReader(streamReader);
			//取得Socket的輸出資料流
			writer = new PrintStream(sock.getOutputStream());
			try{
				PrintStream out = new PrintStream(sock.getOutputStream());
				out.println("註冊" + name + " " + ip);
				out.flush();
				//³s½u¦¨¥\
				state.setText("網路建立-登入成功");
				System.out.println("網路建立-連線成功");
			}
			catch(IOException ex ){
				System.out.println("建立連線失敗");
			}
		}catch(IOException ex ){
			System.out.println("建立連線失敗");
		}
	}
	//--------------------------------------------------------------//
	//-4-接收資料
	//--------------------------------------------------------------//
	//接收聊天室資料
	public class IncomingReader implements Runnable{
		public void run(){
			String message;
			try{
				while ((message = reader.readLine()) != null){
					incoming.append(message+'\n');
				}
			}catch(Exception ex ){
				incoming.append("系統:無法連接至Server,詳情請洽客服");
				state.setText("網路斷線");
				System.out.println("網路斷線");
			}
		}
	}
	//--------------------------------------------------------------//
	//-5-按下之動作
	//--------------------------------------------------------------//
	public void actionPerformed(ActionEvent e){
		String str=e.getActionCommand();
		//按下設定
		if(str.equals("登入")){
			//設定名字
			name = jfmane.getText();
			//設定ip
			ip  = jfip.getText();
			//狀態
			if(name.equals("帳號")||name.equals("")){
				state.setText("請輸入正確的帳號");
			}
			else if(ip.equals("")){
				state.setText("請輸入正確的密碼");
			}
			else{
				state.setText("設定"+name+":"+ip);
				//建立連線----
				EstablishConnection();
				//建立接收資料執行緒----
				Thread readerThread = new Thread(new IncomingReader());
				readerThread.start();
				/*Thread readerThread2 = new Thread(new MemberReader());
				readerThread2.start();*/
			}
		}else if(str.equals("註冊")){
			//設定名字
			name = jfmane.getText();
			//設定ip
			ip  = jfip.getText();
			EstablishConnection2();
		}
			//按下送出
		else if(str.equals("送出")){
			//不可送出空白
			if(outgoing.getText()!="")
			{
				String str1 = outgoing.getText();
				for(int n = 0;n < str1.length() ; n++){
					str1 = str1.replace("fuck","****");
				}
				try{//送出資料
					writer.println((name+":"+str1));
					//刷新該串流的緩衝
					writer.flush();
				}catch(Exception ex ){
					System.out.println("送出資料失敗");
				}
				//清完輸入欄位
				outgoing.setText("");
			}
			else
			{
				incoming.append("系統:未輸入對話內容");
			}
		}
		else if (str.equals("儲存檔案")){
			try{
				FileWriter f = new FileWriter("save.txt");
				f.write(incoming.getText());
				f.close();
				state.setText("儲存檔案成功");
			}catch (IOException e2){
				state.setText("儲存檔案失敗");
			}
		}
	}
}