package inf.uie.Limux.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import inf.uie.Limux.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Bluetooth extends Activity {

    TextView label;
    EditText editText;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        Button openButton = (Button) findViewById(R.id.open);
        Button closeButton = (Button) findViewById(R.id.close);
        Button sendButton = (Button) findViewById(R.id.send);
        Button offButton = (Button) findViewById(R.id.off);
        editText = (EditText) findViewById(R.id.editText);

        label = (TextView) findViewById(R.id.label);

        // open Button
        openButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    findBluetooth();
                    openBluetooth();
                } catch (IOException ex ) {

                }
            }
        });

        // close Button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    closeBluetooth();
                } catch (IOException ex ) {

                }
            }
        });

        // send Button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                try {
                    sendData(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // alle Lampen aus Button
        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendData("0#");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    void findBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            label.setText("No Bluetooth adapter available");
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0 ) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("HC-06")) {
                    mmDevice = device;
                    Log.v("ArduinoBT", "findBT found device named" + mmDevice.getName());
                    Log.v("ArduinoBT", "device address is" + mmDevice.getAddress());

                }
            }
        }



        label.setText("Bluetooth Device Found");
    }


    void openBluetooth() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();

        // label.setText(mmOutputStream.toString());

        // beginListenForData();
    }

    void beginListenForData() {
        final Handler handler = new Handler();
        final byte delimiter = 10; // this is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];


        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvailable = mmInputStream.available();
                        if (bytesAvailable > 0) {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for (int i = 0; i < bytesAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == delimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0,
                                            encodedBytes, 0,
                                            encodedBytes.length);
                                    final String data = new String(
                                            encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        public void run() {
                                            label.setText("HIER"+data);
                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (IOException ex) {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }


    void sendData(String message) throws IOException {
        Log.v("ArduinoBT", "message is" + message);
            byte[] msgBuffer = message.getBytes();
            Log.v("ArduinoBT", "message is" + msgBuffer);
            mmOutputStream.write(msgBuffer);
        label.setText("Data send" + message);
    }

    void onButton() throws IOException {
        mmOutputStream.write("1".getBytes());
    }

    void offButton() throws IOException {
        mmOutputStream.write("2".getBytes());
    }

    void closeBluetooth() throws IOException {
        stopWorker = true;
        sendData("0#");
        mmOutputStream.close();
        mmInputStream.close();
        mmSocket.close();
        label.setText("Bluetooth closed");
    }
}
