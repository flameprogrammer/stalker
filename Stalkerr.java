package org.flame.util.stalker;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public final class Stalkerr {

    private static final Logger LOG = Logger.getLogger(Stalkerr.class.getName());

    private static final int MAX_PORT_SIZE = 1024 * 64; // 65536
    private static final int DEFAULT_THREAD_COUNT = 4;

    private final InetAddress add;
    private final List<Port> openPorts = new ArrayList<Port>();

    public Stalkerr(final InetAddress add){
        this.add = add;
    }

    private synchronized void addOpenPort(Port p){
        openPorts.add(p);
    }

    public List<Port> scan(){
        return scan(DEFAULT_THREAD_COUNT);
    }

    public List<Port> scan(final int threadCount){
        // Threading logic:
        int chunkSize = MAX_PORT_SIZE / threadCount;

        Thread[] tArr = new Thread[threadCount];
        for(int i=0; i<threadCount; i++){
            int startPort = (i * chunkSize) + 1;
            int endPort;
            if((threadCount - 1)==i){ // Last iteration
                endPort = MAX_PORT_SIZE - 1;
            }
            else{
                endPort = (startPort-1) + chunkSize;
            }
            LOG.log(Level.INFO, "Thread {0} scanning from {1} to {2}", new Object[]{i, startPort, endPort});
            tArr[i] = new Thread(new PortScanThread(startPort, endPort));
            tArr[i].start();
        }

        for(Thread t: tArr){
            try{
                t.join();
            }
            catch(InterruptedException ex){

            }
        }

        Collections.sort(openPorts);
        return openPorts;
    }

    private class PortScanThread implements Runnable{
        private final int startPort;
        private final int endPort;

        PortScanThread(int startPort, int endPort){
            this.startPort = startPort;
            this.endPort = endPort;
        }
        @Override
        public void run() {
            for(int i=startPort; i<endPort; i++){
                try{
                    Socket s = new Socket(add, i);
                    s.close();
                    addOpenPort(new Port(i, Type.TCP));
                }
                catch(IOException ex){
                    // Port not open: Do nothing!
                }
            }
        }

    }
}