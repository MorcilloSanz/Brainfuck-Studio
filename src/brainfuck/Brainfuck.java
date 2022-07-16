package brainfuck;

import gui.BrainfuckStudio;
import gui.Console;
import gui.Debugger;

import java.util.Arrays;

public class Brainfuck {

    public static final int BYTES = 30000;
    private volatile byte [] buffer;
    private volatile int pointer = 0;
    private String output = "";
    private volatile boolean running = false;
    private Console console;
    private Debugger debugger;
    private BrainfuckStudio brainfuckStudio;
    private volatile boolean wait = false;
    private String input;
    private int sleepMs = 0;
    private boolean sleep = true;
    private volatile boolean stop = false;

    public Brainfuck(int bytes) {
        buffer = new byte[bytes];
        Arrays.fill(buffer, (byte) 0);
    }

    public Brainfuck() {
        this(BYTES);
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public void setDebugger(Debugger debugger) {
        this.debugger = debugger;
        debugger.setBrainfuck(this);
    }

    public void setBrainfuckStudio(BrainfuckStudio brainfuckStudio) {
        this.brainfuckStudio = brainfuckStudio;
    }

    private synchronized void log(char c) {
        console.log("" + c);
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
        if(!running) {
            Arrays.fill(buffer, (byte) 0);
            pointer = 0;
            if(brainfuckStudio != null)
                brainfuckStudio.stopLoadingAnimation();
        }
    }

    public synchronized void execute(final String code, boolean debug) {
        if(!running) {
            running = true;
            new Thread(() -> {
                executeCode(code, debug);
            }).start();
        }
    }

    public void executeCode(final String code, boolean debug) {
        console.reset();
        debugger.reset();
        for(int i = 0; i < code.length(); i ++) {
            if(!running) break;
            int brackets = 0;
            sleep = true;
            switch(code.charAt(i)) {
                case '+':
                    buffer[pointer] ++;
                    break;
                case '-':
                    buffer[pointer] --;
                    break;
                case '>':
                    pointer = (pointer >= buffer.length - 1) ? 0 : pointer + 1;
                    break;
                case '<':
                    if(pointer > 0)
                        pointer --;
                    break;
                case '[': {
                    if(buffer[pointer] == 0) {
                        i++;
                        while (brackets > 0 || code.charAt(i) != ']') {
                            if (code.charAt(i) == '[')
                                brackets++;
                            else if (code.charAt(i) == ']')
                                brackets--;
                            i ++;
                        }
                    }
                }
                break;
                case ']': {
                    if (buffer[pointer] != 0) {
                        i --;
                        while (brackets > 0 || code.charAt(i) != '[') {
                            if (code.charAt(i) == ']')
                                brackets ++;
                            else if (code.charAt(i) == '[')
                                brackets --;
                            i --;
                        }
                        i --;
                    }
                }
                break;
                case ',': {
                    wait = true;
                    console.select();
                    do {/* wait for the user to give an input */} while(wait);
                    console.deselect();
                    byte value = (byte)input.charAt(0);
                    buffer[pointer] = value;
                }
                break;
                case '.': {
                    char ch = (char) buffer[pointer];
                    log(ch);
                    output += ch;
                    console.setCaretPosition(console.getDocument().getLength());
                }
                break;
                case '*' : {
                    stop = true;
                    if(debug) do {  } while(stop);
                }
                break;
                default:
                    sleep = false; // If its a comment dont sleep, dont waste time
                break;
            }
            if(debug && (pointer < debugger.length() - 1)) {
                debugger.setActive(pointer);
                debugger.setValue(pointer, buffer[pointer]);
            }
            // Sleep time
            if(sleep) {
                try {
                    Thread.sleep(sleepMs);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        setRunning(false);
    }

    public synchronized void setSleepMs(int sleepMs) {
        this.sleepMs = sleepMs;
    }

    public synchronized void setInput(final String input) {
        this.input = input;
        wait = false;
    }

    public synchronized void setWait(boolean wait) {
        this.wait = wait;
    }

    public int getPointer() {
        return pointer;
    }

    public synchronized void setStop(boolean stop) {
        this.stop = stop;
    }

    public byte[] getBuffer() {
        return buffer;
    }
}
