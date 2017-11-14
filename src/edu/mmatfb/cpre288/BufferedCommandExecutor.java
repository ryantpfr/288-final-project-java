package edu.mmatfb.cpre288;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BufferedCommandExecutor {

    private ConcurrentLinkedQueue<Byte> queue = new ConcurrentLinkedQueue<>();
    private volatile int unrecievedBytes = -1; //-1 == awaiting command bit

    //private volatile boolean busy = false;

    private static final List<Byte> startCharacters = Collections.unmodifiableList(Arrays.asList(new Byte("1"),new Byte("2")));

    public BufferedCommandExecutor(){ }

    public void read(Byte b) {
        queue.add(b);

        respondToCharacter();

    }

    private void respondToCharacter(){
        Byte b = queue.peek();
        if(unrecievedBytes == -1){
            unrecievedBytes = remainingBytesByCommand();
        }else if(unrecievedBytes == 1){
            unrecievedBytes = -1;
            runCommand(); //this will deque all bytes
            if(!queue.isEmpty()){
                respondToCharacter();
            }
        }else{
            unrecievedBytes--;
        }
    }

    private void runCommand(){
        switch(queue.remove()){
            case 1 : respondTo1(); return;
            case 2 : respondTo2(); return;
        }
        throw new IllegalStateException("Invalid command byte");
    }


    private void respondTo1(){

        //long t= System.currentTimeMillis();
        //long end = t+1000; //waste time
        //while(System.currentTimeMillis() < end);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        byte b1 = queue.remove();
        byte b2 = queue.remove();
        byte b3 = queue.remove();
        System.out.println(b1 + "+" + b2 + "-" + b3 + "="+(b1+b2-b3));
    }

    private void respondTo2(){
        byte b1 = queue.remove();
        byte b2 = queue.remove();
        System.out.println(b1 + "*" + b2+ "=" +(b1*b2));
    }


    private int remainingBytesByCommand(){
        switch(queue.peek()){
            case(1) : return 3;
            case(2) : return 2;
        }
        throw new IllegalStateException("Invalid command byte");
    }

}
