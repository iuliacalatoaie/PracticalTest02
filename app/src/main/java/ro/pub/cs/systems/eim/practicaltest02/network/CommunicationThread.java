package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import ro.pub.cs.systems.eim.practicaltest02.utils.Constants;
import ro.pub.cs.systems.eim.practicaltest02.utils.TimeSet;
import ro.pub.cs.systems.eim.practicaltest02.utils.Utilities;

public class CommunicationThread extends Thread {
    private ServerThread serverThread;
    private Socket socket;


    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket == null) {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
            return;
        }
        try {
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Buffered Reader / Print Writer are null!");
                return;
            }

            Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (command)");
            String arg = bufferedReader.readLine();
            if (arg == null || arg.isEmpty()) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Error receiving parameters from client (city / information type!");
                return;
            }

            List<String> arguments = Arrays.asList(arg.split(","));
            String clientIp = socket.getInetAddress().getHostAddress();
            String response = null;

            if (arguments.get(0).equals(Constants.SET)) {
                if (arguments.size() < 3) {
                    Log.e(Constants.TAG, "[COMMUNICATION THREAD] Needs set,hour,minute!");
                    return;
                }
                serverThread.addData(clientIp, Integer.valueOf(arguments.get(1)), Integer.valueOf(arguments.get(2)));
                response = "Alarm set";
            }
            if (arguments.get(0).equals(Constants.RESET)) {
                serverThread.removeData(clientIp);
                response = "Alarm reset";
            }
            if (arguments.get(0).equals(Constants.POLL)) {
                TimeSet timeSet = serverThread.getDataByIP(clientIp);
                if (timeSet == null) {
                    response = Constants.NONE;
                } else {
                    Socket tcpSocket = new Socket(Constants.WEBSITE_URL, Constants.WEBSITE_PORT);
                    BufferedReader newBufferedReader = Utilities.getReader(tcpSocket);
                    String buffer = newBufferedReader.readLine();
                    printWriter.println(buffer);

                    printWriter.println(timeSet.toString());
                }
            }

            printWriter.println(response);
        } catch (IOException exception) {
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }
    }
}
