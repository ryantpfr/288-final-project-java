package edu.mmatfb.cpre288.core;


public class BCETestMain {

    public static void main(String[] args){
        BufferedCommandExecutor bce = new BufferedCommandExecutor();

        
        bce.read((byte) 2);
        bce.read((byte) 6);
        bce.read((byte) 7);
        
        sendFromThreadIn100ms(bce,1);
        sendFromThreadIn100ms(bce,7);
        sendFromThreadIn100ms(bce,3);
        sendFromThreadIn100ms(bce,5);

        sendGroupFromThreadIn100ms(bce,2,30,11);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sendFromThreadIn100ms(BufferedCommandExecutor bce,int i){
        byte b = (byte) i;
        Runnable runnable = () -> {
            try {
                Thread.sleep(20);
                bce.read(b);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        runnable.run();
    }

    private static void sendGroupFromThreadIn100ms(BufferedCommandExecutor bce,int... i){
        for(int e : i){
            sendFromThreadIn100ms(bce,e);
        }
    }


}
