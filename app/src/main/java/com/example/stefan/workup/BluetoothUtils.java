//package com.example.stefan.workup;
//
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothServerSocket;
//import android.bluetooth.BluetoothSocket;
//import android.content.Intent;
//import android.util.Log;
//
//import com.example.stefan.workup.models.Job;
//
//import java.io.IOException;
//import java.util.UUID;
//
//import static android.content.ContentValues.TAG;
//
//public class BluetoothUtils {
//    BluetoothAdapter mBluetoothAdapter;
//    private Listener listener;
//    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//
//    public BluetoothUtils(Listener listener) {
//        this.listener = listener;
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (mBluetoothAdapter.startDiscovery()) {
//            Log.e("test","te");
//        }
//
//        if (mBluetoothAdapter == null) {
//            return;
//        }
//        new AcceptThread().start();
//
//    }
//
//    public void connectToDevice(BluetoothDevice bluetoothDevice) {
//        new ConnectThread(bluetoothDevice).start();
//    }
//
//
//    private class ConnectThread extends Thread {
//        private final BluetoothSocket mmSocket;
//        private final BluetoothDevice mmDevice;
//
//        public ConnectThread(BluetoothDevice device) {
//            // Use a temporary object that is later assigned to mmSocket
//            // because mmSocket is final.
//            BluetoothSocket tmp = null;
//            mmDevice = device;
//
//            try {
//                // Get a BluetoothSocket to connect with the given BluetoothDevice.
//                // MY_UUID is the app's UUID string, also used in the server code.
//                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
//            } catch (IOException e) {
//                Log.e(TAG, "Socket's create() method failed", e);
//            }
//            mmSocket = tmp;
//        }
//
//        public void run() {
//            // Cancel discovery because it otherwise slows down the connection.
//            mBluetoothAdapter.cancelDiscovery();
//
//            try {
//                // Connect to the remote device through the socket. This call blocks
//                // until it succeeds or throws an exception.
//                mmSocket.connect();
//            } catch (IOException connectException) {
//                // Unable to connect; close the socket and return.
//                try {
//                    mmSocket.close();
//                } catch (IOException closeException) {
//                    Log.e(TAG, "Could not close the client socket", closeException);
//                }
//                return;
//            }
//
//            // The connection attempt succeeded. Perform work associated with
//            // the connection in a separate thread.
//            manageMyConnectedClientSocket(mmSocket);
//        }
//
//        private void manageMyConnectedClientSocket(BluetoothSocket socket) {
//            //send
//            try {
//                byte[] tmp = new byte[15];
//                socket.getOutputStream().write("1".getBytes());
//                int size = socket.getInputStream().read(tmp);
//                if (size > 0) {
//                    String s = new String(tmp, 0, size);
//                    if (listener != null) {
//                        listener.handleClientHandshake(s.equalsIgnoreCase("OK"), null);
//                    }
//
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            //rec
//        }
//
//        // Closes the client socket and causes the thread to finish.
//        public void cancel() {
//            try {
//                mmSocket.close();
//            } catch (IOException e) {
//                Log.e(TAG, "Could not close the client socket", e);
//            }
//        }
//    }
//
//    //server
//    private class AcceptThread extends Thread {
//        private final BluetoothServerSocket mmServerSocket;
//
//        public AcceptThread() {
//            // Use a temporary object that is later assigned to mmServerSocket
//            // because mmServerSocket is final.
//            BluetoothServerSocket tmp = null;
//            try {
//                // MY_UUID is the app's UUID string, also used by the client code.
//                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("545454", MY_UUID);
//            } catch (IOException e) {
//                Log.e(TAG, "Socket's listen() method failed", e);
//            }
//            mmServerSocket = tmp;
//        }
//
//        public void run() {
//            BluetoothSocket socket = null;
//            // Keep listening until exception occurs or a socket is returned.
//            while (true) {
//                try {
//                    socket = mmServerSocket.accept();
//                } catch (IOException e) {
//                    Log.e(TAG, "Socket's accept() method failed", e);
//                    break;
//                }
//
//                if (socket != null) {
//                    // A connection was accepted. Perform work associated with
//                    // the connection in a separate thread.
//                    manageMyConnectedSocket(socket);
//                    try {
//                        mmServerSocket.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//            }
//        }
//
//        private void manageMyConnectedSocket(BluetoothSocket socket) {
//            //rec
//            byte[] tmp = new byte[1024];
//            try {
//                int size = socket.getInputStream().read(tmp);
//                if (size == 1) {
//                    String s = new String(tmp).trim();
//                    if (s.equalsIgnoreCase("1")) {
//                        socket.getOutputStream().write("OK".getBytes());
//                        if (listener != null) {
//                            listener.handleServerHandshake(s.equalsIgnoreCase("OK"), null);
//                        }
//                    } else {
//                        socket.getOutputStream().write("Bad request".getBytes());
//                        if (listener != null) {
//                            listener.handleServerHandshake(s.equalsIgnoreCase("OK"), null);
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            //send
//        }
//
//        // Closes the connect socket and causes the thread to finish.
//        public void cancel() {
//            try {
//                mmServerSocket.close();
//            } catch (IOException e) {
//                Log.e(TAG, "Could not close the connect socket", e);
//            }
//        }
//    }
//
//    public interface Listener {
//        void handleClientHandshake(boolean response, Job job);
//
//        void handleServerHandshake(boolean response, Job job);
//    }
//}
