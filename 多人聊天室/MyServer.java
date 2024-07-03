//--------------------------------------------------------------//
// MyServer.java 2017年05月13日
//--------------------------------------------------------------//
import java.io.*;
import java.net.*;
import java.util.*;
//--------------------------------------------------------------//
//MyServer主類別檔
//--------------------------------------------------------------//
public class MyServer{
	Vector output;//output
	//--------------------------------------------------------------//
	//-1-主程式進入點
	//--------------------------------------------------------------//
	public static void main (String args[]) throws IOException {
		//抓檔資料
		FileReader fr = new FileReader("log.txt");
        BufferedReader br = new BufferedReader(fr);
		
		new MyServer().go(br);     
	}
		//--------------------------------------------------------------//
		//-2-建位連線
		//--------------------------------------------------------------//
	public void go(BufferedReader MembersInfor){
		//顯示目前所有註冊帳號
		
		//建立物件陣列
		output = new Vector();          
		try{
			//產生ServerSocket設定port:8888
			ServerSocket serverSock = new ServerSocket(8888);
			while(true){
				//等待連線的請求--串流
				Socket cSocket = serverSock.accept();
				//建立I/O管道
				PrintStream writer = 
				//取得Socket的輸出資料流
				new PrintStream(cSocket.getOutputStream());
				//元件加入Vector
				output.add(writer);
				//讀取使用者驗證資料
				BufferedReader infor;
				String information;
				InputStreamReader isInfor = new InputStreamReader(cSocket.getInputStream());
				infor = new BufferedReader(isInfor);
				information = infor.readLine();
				if(information.substring(0,2).equals("登入")){
					System.out.println("使用者資料:"+information+"要求登入");
					//傳入一個Runnable物件並分派一個新的執行緒
					//建立伺服器主執行緒
					Thread t = new Thread(new Process(cSocket));
					//啟動執行緒
					t.start();
					//取得連線的ip
					System.out.println( 
					//執行緒的在線次數
					"有"+(t.activeCount()-1)+
					//顯示連線人次
					"個連接");
				}
				else if(information.substring(0,2).equals("註冊")){
					System.out.println("使用者資料:"+information+"要求註冊");
					try{
						FileWriter f = new FileWriter("log.txt",true);
						f.write("\r\n"+information.substring(2));
						f.close();
					}catch (IOException e2){
					}
				}
				else{
					break;
				}
			}
		}catch(Exception ex){System.out.println("錯誤的連接");}
	}
	//--------------------------------------------------------------//
	//-3-Process處理程序
	//--------------------------------------------------------------//
	public class Process implements Runnable{
		//暫存資料的Buffered
		BufferedReader reader;
		//建立一個Socket變數
		Socket sock;
		//----------------------------------------------------------//
		//-3.1-由執行緒呼叫---建立接收
		//----------------------------------------------------------//
		public Process(Socket cSocket){
			try{
				sock = cSocket;
				//¨ú±oSocketªº¿é¤J¸ê®Æ¬y
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				
				reader = new BufferedReader(isReader);
				
			}catch(Exception ex){
				System.out.println("連接失敗Process");
			} 
		}
		//--------------------------------------------------------------//
		//-3.2-執行執行緒
		//--------------------------------------------------------------//
		public void run(){
			String message;
			try{
			//讀取資料
				while ((message = reader.readLine())!=null){
					System.out.println("收到"+message);
					tellApiece(message);
				}
			}catch(Exception ex){
				System.out.println("有一個連接離開");
				tellApiece("系統:有一個連接離開");
			}
		}
		//--------------------------------------------------------------//
		//-3.3-告訴每人
		//--------------------------------------------------------------//
		public void tellApiece(String message){
			//產生iterator可以存取集合內的元素資料
			Iterator it = output.iterator(); 
			//向下讀取元件
			while(it.hasNext()){
				try{
					//取集合內資料
					PrintStream writer = (PrintStream) it.next();
					//印出
					writer.println(message);
					//刷新該串流的緩衝。
					writer.flush();
				}
				catch(Exception ex){
					System.out.println("連接失敗Process");
				}
			}
		}
	}
}