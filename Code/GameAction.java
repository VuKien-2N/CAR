//Khai báo các trạng thái trò chơi
class GameAction 
{
    public static final int NORMAL = 0;

    public static final int DETECT_INITAL_PRESS_ONLY = 1;

    private static final int STATE_RELEASED = 0;
    private static final int STATE_PRESSED = 1;
    private static final int STATE_WAITING_FOR_RELEASE = 2;

    private String name; // Tên
    private int behavior; 
    private int amount;  // Số Lượng
    private int state; // Trạng thái

    public GameAction(String name) // Contructor truyền tham số vào tên
    {
        this(name, NORMAL);
    }


    public GameAction(String name, int behavior) {  // Contructor truyền tham số vào tên và behavior
        this.behavior = behavior;
        reset();
    }


    public String getName() { // Lấy tên
        return name;
    }


    public void reset() {
        state = STATE_RELEASED;
        amount = 0;
    }


    public synchronized void tap() { // Khi Click vào Tab
        press();
        release();
    }


    public synchronized void press() { // Khi Click
        press(1);
    }


    public synchronized void press(int amount) {
        if (state != STATE_WAITING_FOR_RELEASE) {
            this.amount+=amount;
            state = STATE_PRESSED;
        }

    }


    public synchronized void release() { // Khi Bỏ chuột
        state = STATE_RELEASED;
    }


    public synchronized boolean isPressed() {
        return (getAmount() != 0);
    }


    public synchronized int getAmount() {
        int retVal = amount;
        if (retVal != 0) {
            if (state == STATE_RELEASED) {
                amount = 0;
            }
            else if (behavior == DETECT_INITAL_PRESS_ONLY) {
                state = STATE_WAITING_FOR_RELEASE;
                amount = 0;
            }
        }
        return retVal;
    }
}
