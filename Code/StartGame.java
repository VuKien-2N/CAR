import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;


public class StartGame extends GameCore implements ActionListener // Phần này giới thiệu và khởi tạo trò chơi
{
    public static void main(String[] args) {
        new StartGame().run(); 
        
    }
    
    private final static String INSTRUCTIONS =     // Hướng dẫn của game
    	"<html> Để di chuyển xe bạn sử dụng các nút điều hướng trên bàn phím:" +
        "<br> phím lên, xuống để tiến hoặc lùi xe" +
        "<br> phím trái, phải để ... đó hiểu mà ('3')" +
        "<br> còn các phím khác thì để thôi, ấn không được gì đâu" +
        "<br> đến đích trước thời gian quy định sẽ qua màn,"+
        "<br> hoàn thành 3 màn sẽ hoàn thành game"+
        "<br> nếu quá thời gian sẽ quay lại màn 1 ";
         
    private final static String ABOUT =  // Thông tin thành viên
   	"<html>Các thành viên của nhóm:" +
        "<br> 1: Vũ Trung Kiên" +
        "<br> 2: Nguyễn Tiến Đồng" +
    	"<br> 3: Lê Vũ Thanh Giang" +
        "<br> 4: Trần Khánh Linh";

  // Khai báo các Control được sử dụng
	private String label; 
    private JButton highScoreButton;
    private JButton configButton;
    private JButton quitButton;
    private JButton aboutButton;
    private JButton newGameButton;
    
    private JButton startGameButton;
	private Image bgImage;
	private JTextField namePlayer;
	
  // Các  dialog
    private JPanel dialogAbout = new JPanel();
    private JPanel dialogInstruction = new JPanel();
    private JPanel dialogHighScore = new JPanel();
    private JPanel dialogNew = new JPanel();
    
    private ReadWriteFile rwf = new ReadWriteFile(); // Gọi đến lớp đọc và ghi vào file txt
    
    
    public void init() {   // Khởi tạo
        super.init();
       
        NullRepaintManager.install(); // Cắt bỏ những mẫu dư thừa không thần thiết
       
        quitButton = createButton("quit", "Thoát");  // Tạo các nút trên
        aboutButton = createButton("about", "Thành viên");
        configButton = createButton("help", "Hướng dẫn");
        highScoreButton = createButton("score", "Điểm cao");
        newGameButton = createButton("new", "Trò chơi mới");
        

        JFrame frame = super.screen.getFullScreenWindow();  //
        Container contentPane = frame.getContentPane(); //
        contentPane.setLayout(new GridLayout(3,3));

        if (contentPane instanceof JComponent) {
            ((JComponent)contentPane).setOpaque(false);
        }
        
        JPanel content = new JPanel(); //  gọi đến lớp Jpanel có sẵn
        
        if (content instanceof JComponent) {
            ((JComponent)content).setOpaque(false);
        }
        
        content.setLayout(new GridLayout(5, 1)); 
        content.add(highScoreButton);
        content.add(aboutButton);
        content.add(configButton);
        content.add(newGameButton);
        content.add(quitButton);   
        // Thiết lập các nút còn lại khi nhận đc 1 click
        JButton bt1 = new JButton();
        bt1.setVisible(false);  
        JButton bt2 = new JButton();
        bt2.setVisible(false);
        JButton bt3 = new JButton();
        bt3.setVisible(false);
        JButton bt4 = new JButton();
        bt4.setVisible(false);
        JButton bt5 = new JButton();
        bt5.setVisible(false);
        JButton bt7 = new JButton();
        bt7.setVisible(false);
        JButton bt8 = new JButton();
        bt8.setVisible(false);
        JButton bt9 = new JButton();
        bt9.setVisible(false);
        
        // hiển thị các nút
        contentPane.add(bt1);
        contentPane.add(bt2);
        contentPane.add(bt3);
        contentPane.add(bt4);
        contentPane.add(bt5);
        contentPane.add(content);
        contentPane.add(bt7);
        contentPane.add(bt8);
        contentPane.add(bt9);
         // Tạo ra khung hiển thị các tùy chọn
        frame.validate();
        creatPanelNew(); 
        creatPanel(dialogAbout,ABOUT);
        creatPanel(dialogInstruction,INSTRUCTIONS);
        creatPanel(dialogHighScore,rwf.highScore());

    }

	public void creatPanel(JPanel dialog,String label)
	{
		JLabel lb = new JLabel(label);
		lb.setFont(new Font("Times New Roman",Font.PLAIN,15)); // Font được sử dụng
		
		dialog.add(lb,BorderLayout.CENTER);
		dialog.setVisible(false);
		dialog.setSize(dialog.getPreferredSize());
		dialog.setLocation((screen.getWidth() - dialog.getWidth())/2,
						   (screen.getHeight() - dialog.getHeight())/2);
		screen.getFullScreenWindow().getLayeredPane().add(dialog,
							JLayeredPane.MODAL_LAYER); // thêm các dialog và set FullScreen .
		
	}
	
	
	public void creatPanelNew() // Tạo khung điền tên cho người chơi khởi tạo
	{
		
		dialogNew.setLayout(new FlowLayout());
		JLabel lb = new JLabel("Tên người chơi mới");
		namePlayer = new JTextField(15);
		startGameButton = new JButton("Ok");
		
		lb.setFont(new Font("Times New Roman",Font.PLAIN,15));
		startGameButton.addActionListener(this);
		dialogNew.add(lb);
		dialogNew.add(namePlayer);
		dialogNew.add(startGameButton);
		dialogNew.setVisible(false);
		dialogNew.setSize(dialogNew.getPreferredSize());
		dialogNew.setLocation((screen.getWidth() - dialogNew.getWidth())/2,
						   (screen.getHeight() - dialogNew.getHeight())/2);
		screen.getFullScreenWindow().getLayeredPane().add(dialogNew,
							JLayeredPane.MODAL_LAYER);
		
	}
	
	


    public void draw(Graphics2D g) // Load ảnh nền cho giao diện
    {
        bgImage = loadImage("images/background.jpg");
        g.drawImage(bgImage, 0, 0, null);
        JFrame frame = super.screen.getFullScreenWindow();
        frame.getLayeredPane().paintComponents(g);
    }		
    	
    // Cac Event khj nguoi choi Click vao cac Button	
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == quitButton) {
        	System.exit(0);
        }
        else if (src == configButton) {
            boolean enable = ! quitButton.isEnabled();
        	aboutButton.setEnabled(enable);
        	highScoreButton.setEnabled(enable);
        	quitButton.setEnabled(enable);
        	newGameButton.setEnabled(enable);
    		dialogInstruction.setVisible(!enable);
        }
        else if (src == aboutButton) 
        {
            boolean enable = ! quitButton.isEnabled();
    		configButton.setEnabled(enable);
        	highScoreButton.setEnabled(enable);
	    	quitButton.setEnabled(enable);
			newGameButton.setEnabled(enable);
			dialogAbout.setVisible(!enable);			 
        }
        else if(src == highScoreButton)
        {
        	boolean enable = ! quitButton.isEnabled();
    		configButton.setEnabled(enable);
        	aboutButton.setEnabled(enable);
	    	quitButton.setEnabled(enable);
	    	newGameButton.setEnabled(enable);
			dialogHighScore.setVisible(!enable);
        }
        else if(src == newGameButton)
        {
        	boolean enable = ! quitButton.isEnabled();
    		configButton.setEnabled(enable);
        	aboutButton.setEnabled(enable);
	    	quitButton.setEnabled(enable);
	    	highScoreButton.setEnabled(enable);
			dialogNew.setVisible(!enable);
        }
        else if(src == startGameButton)
        {
        	if(namePlayer.getText().trim() != "")
        	{
        		System.out.println(namePlayer.getText());
        	rwf.writeFileNamePlayer(namePlayer.getText());
        	System.exit(0);
        
        	}
        }

    }
    
  // Tao cac nut bang Code 
    
    public JButton createButton(String name, String toolTip) {

        String imagePath = "images/" + name + ".png";
        ImageIcon iconRollover = new ImageIcon(imagePath);
        int w = iconRollover.getIconWidth();
        int h = iconRollover.getIconHeight();

        Cursor cursor =
            Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

        Image image = screen.createCompatibleImage(w, h,
            Transparency.TRANSLUCENT);
        Graphics2D g = (Graphics2D)image.getGraphics();
        Composite alpha = AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, .5f);
        g.setComposite(alpha);
        g.drawImage(iconRollover.getImage(), 0, 0, null);
        g.dispose();
        ImageIcon iconDefault = new ImageIcon(image);

        image = screen.createCompatibleImage(w, h,
            Transparency.TRANSLUCENT);
        g = (Graphics2D)image.getGraphics();
        g.drawImage(iconRollover.getImage(), 2, 2, null);
        g.dispose();
        ImageIcon iconPressed = new ImageIcon(image);

        JButton button = new JButton();
        button.addActionListener(this);
        button.setIgnoreRepaint(true);
        button.setFocusable(false);
        button.setToolTipText(toolTip);
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setCursor(cursor);
        button.setIcon(iconDefault);
        button.setRolloverIcon(iconRollover);
        button.setPressedIcon(iconPressed);

        return button;
    }

}
