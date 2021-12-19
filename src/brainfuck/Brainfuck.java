package brainfuck;

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
    }

    private synchronized void log(char c) {
        console.log("" + c);
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
        if(!running) {
            Arrays.fill(buffer, (byte) 0);
            pointer = 0;
        }
    }

    public synchronized void execute(final String code) {
        if(!running) {
            running = true;
            new Thread(() -> {
                executeCode(code);
            }).start();
        }
    }

    public void executeCode(final String code) {
        console.reset();
        debugger.reset();
        for(int i = 0; i < code.length(); i ++) {
            if(!running) break;
            int brackets = 0;
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

                }
                break;
                case '.': {
                    char ch = (char) buffer[pointer];
                    log(ch);
                    output += ch;
                }
                break;
                default:
                    break;
            }
            debugger.setActive(pointer);
            debugger.setValue(pointer, buffer[pointer]);
        }
        setRunning(false);
    }

}
