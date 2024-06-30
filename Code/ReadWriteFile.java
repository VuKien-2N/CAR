import java.io.*;
import java.util.ArrayList;


public class ReadWriteFile {
	
	
	public ArrayList arrScore = new ArrayList(); // Tạo một mảng lưu tên người chơi
	private BufferedReader br; // Đối tượng đọc
    String ScoreFile = "Score.txt"; // File lưu lại điểm số người chơi
    String ScoreFile2 = "NamePlayer.txt"; // File lưu lại tên người chơi

	
    public  String readNamePlayer() // Trả về tên người chơi
    {
    	String _NamePlayer="";
    	try {
	      	br = new BufferedReader( new FileReader(ScoreFile2)); // Phương thức đọc  
	    	   _NamePlayer= br.readLine();
	   	   	br.close();
    	}
    	catch (Exception e) 
   	 	{ 
    		System.out.println("Error reading Score file: " + ScoreFile);
    	}
    	return _NamePlayer; // Trả về tên người chơi
    }
     public void writeFileNamePlayer(String name)  // Khi khởi tạo trò chơi sẽ lưu lại tên
	{ 
    	try 
    	{
	      	PrintWriter pw = new PrintWriter( new FileWriter(ScoreFile2,false));//Append=false sẽ không ghi thêm
	      	pw.print(name); // Ghi tên
			pw.close();
	   	   	System.out.println ("ghi file thanh cong");
    	}
    	catch (Exception e) 
   	 	{ 
    		System.out.println("Error write Score file: " + ScoreFile);
    	}
  	} 
    private void readHighScore() // Đọc tên người chơi và lưu vào Array
	{ 
    	try {
	      	br = new BufferedReader( new FileReader(ScoreFile));
	    	br.readLine();   // phương thức doc
	     	String line;
	    	while((line = br.readLine()) != null) // Đọc tên bao giờ hết file 
	    	{
	    		if(!line.startsWith("#")) // Không đọc dòng đầu cmt
	    	    arrScore.add(line); // Thêm vào mảng arrScore
	      	}
	   	   	br.close(); 
    	}
    	catch (Exception e) 
   	 	{ 
    		System.out.println("Error reading Score file: " + ScoreFile);
    	}
  	}  
  		
  		
  		
  	public void writeFileScore(String name) // Ghi tên người chơi và điểm vào file txt
	{ 
    	try 
    	{
	      	PrintWriter pw = new PrintWriter( new FileWriter(ScoreFile, true)); // Ghi thêm
	      	pw.println(name); // ghi thành từng dòng
			pw.close();
	   	   	System.out.println ("ghi file thanh cong");
    	}
    	catch (Exception e) 
   	 	{  
    		System.out.println("Error write Score file: " + ScoreFile);
    	}
  	}  
  		
  		
  	  	
  	private  boolean isScore(String name) //Kiem tra xem có phải là điểm không
	{ 
    	try {
    		
	      	br = new BufferedReader( new FileReader(ScoreFile));
	    	br.readLine();   
	     	String line;
	    	while((line = br.readLine()) != null) 
	    	{
	    		if(!line.startsWith("#"))
	    	    if(line.startsWith(name))
	    	    	return true;
	      	}
	   	   	br.close();
    	}
    	catch (Exception e) 
   	 	{ 
    		System.out.println("Error reading Score file: ");
    	}
    	
    	return false;
  	}  
 	
  	public String highScore() // Trả về một thông báo người chơi có điểm số cao được hiển thị ra
	{
		readHighScore(); // phương thức đọc điểm
		String str = "<html> \u0110i\u1ec3m cao :";
		int j = 0;
		for(int i = 0; i < arrScore.size(); i++)
        str += "<br>" + ++j + " : " + arrScore.get(i);	// Lấy nội dung mảng vừa lưu
        return str;

	}

}