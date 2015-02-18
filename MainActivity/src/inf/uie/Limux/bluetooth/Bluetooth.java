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
import inf.uie.Limux.model.House;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Bluetooth {
	
	private static Bluetooth instance;

    static BluetoothAdapter mBluetoothAdapter;
    static BluetoothSocket mmSocket;
    static BluetoothDevice mmDevice;
    static OutputStream mmOutputStream;
    static InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    
    private Bluetooth() throws IOException {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            // label.setText("No Bluetooth adapter available");
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            // startActivityForResult(enableBluetooth, 0);
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
        
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();
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


    public void sendData(String message) throws IOException {
        Log.v("ArduinoBT", "Nachricht ist " + message);
            byte[] msgBuffer = message.getBytes();
            Log.v("ArduinoBT", "message is" + msgBuffer);
            mmOutputStream.write(msgBuffer);
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
     }
    
    // ---------- STATIC METHODS ----------

    /**
     * Get the instance of the singleton
     *
     * @return instance
     * @throws IOException 
     */
    public static Bluetooth getInstance() throws IOException {
        if (Bluetooth.instance == null) {
        	Bluetooth.instance = new Bluetooth();
        }
        return Bluetooth.instance;
    }
}
