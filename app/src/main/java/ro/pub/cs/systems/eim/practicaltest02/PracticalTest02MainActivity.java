package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest02.network.ServerThread;
import ro.pub.cs.systems.eim.practicaltest02.utils.Constants;

public class PracticalTest02MainActivity extends AppCompatActivity {
    private TextView serverPortEditText;
    private TextView clientPortEditText;
    private TextView clientAddressEditText;
    private TextView clientCommandEditText;

    private Button serverConnectButton;
    private Button clientConnectButton;

    private ServerThread serverThread = null;

    private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() == null) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
        clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
        clientAddressEditText = (EditText)findViewById(R.id.client_address_edit_text);
        clientCommandEditText = (EditText)findViewById(R.id.command_id);

        serverConnectButton = (Button)findViewById(R.id.connect_button);
        serverConnectButton.setOnClickListener(connectButtonClickListener);

        clientConnectButton = (Button)findViewById(R.id.client_connect_button);
//        serverConnectButton.setOnClickListener(connectButtonClickListener);

    }
}