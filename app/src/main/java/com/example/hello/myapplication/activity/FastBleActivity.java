package com.example.hello.myapplication.activity;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.clj.fastble.utils.BleLog;
import com.example.hello.myapplication.R;
import com.example.hello.myapplication.common.bean.ByteContent;
import com.example.hello.myapplication.utils.ble.BtSdkManager;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;

public class FastBleActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "FastBleActivity";
    public static final UUID BLE_SERVICE_UUID = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_NOTIFICATION_CHARACTERIS_E1_UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
    public static final UUID BLE_NOTIFICATION_CHARACTERIS_E2_UUID = UUID.fromString("0000ffe2-0000-1000-8000-00805f9b34fb");
    public static final String BLE_SERVICE_STRING = "0000ffe0-0000-1000-8000-00805f9b34fb";
    public static final String BLE_NOTIFICATION_CHARACTERISTICS_E1_UUID = "0000ffe1-0000-1000-8000-00805f9b34fb";
    public static final String BLE_NOTIFICATION_CHARACTERISTICS_E2_UUID = "0000ffe2-0000-1000-8000-00805f9b34fb";
    public static final String BLE_WRITE_CHARACTERISTICS_UUID = "0000ffe1-0000-1000-8000-00805f9b34fb";
    private Button bt_bind_medic_box, bt_unbind_medic_box, bt_add_medic_box, bt_edit_medic_box, bt_delete_medic_box, bt_scan_connect_medic_box;

    private BleDevice mBleDevice;
    private static int mCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_ble);
        findViewById();
        setListener();
    }

    private void findViewById() {
        bt_bind_medic_box = findViewById(R.id.bt_bind_medic_box);
        bt_unbind_medic_box = findViewById(R.id.bt_unbind_medic_box);
        bt_add_medic_box = findViewById(R.id.bt_add_medic_box);
        bt_edit_medic_box = findViewById(R.id.bt_edit_medic_box);
        bt_delete_medic_box = findViewById(R.id.bt_delete_medic_box);
        bt_scan_connect_medic_box = findViewById(R.id.bt_scan_connect_medic_box);
    }

    private void setListener() {
        bt_bind_medic_box.setOnClickListener(this);
        bt_unbind_medic_box.setOnClickListener(this);
        bt_add_medic_box.setOnClickListener(this);
        bt_edit_medic_box.setOnClickListener(this);
        bt_delete_medic_box.setOnClickListener(this);
        bt_scan_connect_medic_box.setOnClickListener(this);
    }

    String bindJson = "{\"12\":\"860344045763837\",\"1\":18}";
    String unBindJson = "{\"12\":\"860344045748531\",\"1\":21,\"45\":\"0.0.1.190701\"}";

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_bind_medic_box:
                if (mBleDevice != null) {
                    initBleNotify(bindJson.getBytes(), mBleDevice);
                }
                break;
            case R.id.bt_unbind_medic_box:
                break;
            case R.id.bt_add_medic_box:
                break;
            case R.id.bt_edit_medic_box:
                break;
            case R.id.bt_delete_medic_box:
                break;
            case R.id.bt_scan_connect_medic_box:
                scanMedicBox();
                break;
            default:
                break;
        }
    }


    private void scanMedicBox() {
        initScanRule();
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                // 开始扫描（主线程）
                Log.d(TAG, "onScanStarted: ");
            }

            @Override
            public void onScanning(BleDevice result) {

            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                Log.d(TAG, "onScanFinished: " + scanResultList.size());
                if (!scanResultList.isEmpty()) {
                    mBleDevice = scanResultList.get(0);
                    //connectMedicBox(mBleDevice);

                    BtSdkManager.getInstance().connectDevice(mBleDevice.getMac())
                            .setConnectCallback(retCode -> {
                                //连接指定地址的蓝牙设备， 0连接成功，1设备断开，2设备不存在或无法连接，3服务无法启动 4:超时
                                Log.d(TAG, "============ retCode = " + retCode);
                            });

                    Toast.makeText(FastBleActivity.this, "搜索到药盒了！" + mBleDevice.getMac(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(FastBleActivity.this, "药盒没找到", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void connectMedicBox(BleDevice bleDevice) {
        BleManager.getInstance().connect(bleDevice, new BleGattCallback() {
            @Override
            public void onStartConnect() {
                // 开始连接
                Log.d(TAG, "onStartConnect: ");
            }

            @Override
            public void onConnectFail(BleException exception) {
                // 连接失败
                Log.d(TAG, "onConnectFail: ");
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    boolean ret = gatt.requestMtu(255);
                    Log.d(TAG, "requestMTU " + 255 + " ret=" + ret);
                }
                Log.d(TAG, "onConnectSuccess: ");
                Toast.makeText(FastBleActivity.this, "药盒连接成功！！！！", Toast.LENGTH_LONG).show();
                List<BluetoothGattService> services = gatt.getServices();

                for (BluetoothGattService bluetoothGattService : services) {
                    Log.d(TAG, bluetoothGattService.getUuid().toString());
                }
                // 连接成功，BleDevice即为所连接的BLE设备
                BluetoothGattService service = gatt.getService(BLE_SERVICE_UUID);
                if (service == null) {
                    return;
                }
                BluetoothGattCharacteristic characteristic_e1 = service.getCharacteristic(BLE_NOTIFICATION_CHARACTERIS_E1_UUID);
                if (characteristic_e1 == null) {
                    return;
                }
                gatt.setCharacteristicNotification(characteristic_e1, true);//激活通知

                BluetoothGattCharacteristic characteristic_e2 = service.getCharacteristic(BLE_NOTIFICATION_CHARACTERIS_E2_UUID);
                if (characteristic_e2 == null) {
                    return;
                }
                gatt.setCharacteristicNotification(characteristic_e2, true);//激活通知
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                // 连接中断，isActiveDisConnected表示是否是主动调用了断开连接方法
                Log.d(TAG, "onDisConnected: ");
            }
        });
    }

    /**
     * 设置扫描规则
     * <p>
     * BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
     * .setServiceUuids(serviceUuids)      // 只扫描指定的服务的设备，可选
     * .setDeviceName(true, names)         // 只扫描指定广播名的设备，可选
     * .setDeviceMac(mac)                  // 只扫描指定mac的设备，可选
     * .setAutoConnect(isAutoConnect)      // 连接时的autoConnect参数，可选，默认false
     * .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒；小于等于0表示不限制扫描时间
     * .build();
     */

    private void initScanRule() {
        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                // .setServiceUuid(AppConstant.BLE_SERVICE_UUID)     // 只扫描指定的服务的设备，可选
                //.setDeviceName(true, "MBox090AB295")         // 只扫描指定广播名的设备，可选
                .setDeviceMac("3C:A5:09:0A:B2:95")                  // 只扫描指定mac的设备，可选
                .setAutoConnect(true)      // 连接时的autoConnect参数，可选，默认false
                .setScanTimeOut(15000)              // 扫描超时时间，可选，默认10秒；小于等于0表示不限制扫描时间
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
    }


    /**
     * 设置notify监听,0:绑定，1解绑，2其他
     */

    public void initBleNotify(final byte[] jsonData, final BleDevice bleDevice) {
        BleManager.getInstance().notify(
                bleDevice,
                BLE_SERVICE_STRING,
                BLE_NOTIFICATION_CHARACTERISTICS_E1_UUID,
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        // 打开通知操作成功
                        Log.d(TAG, "onNotifySuccess: ");
                        writeCheckNetWorkJson(jsonData, bleDevice);
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        // 打开通知操作失败
                        Log.d(TAG, "onNotifyFailure: " + exception.getDescription().toString());
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] response) {
                        Log.d("FastBleActivity_call", byteToHexString(response));
                        // 打开通知后，设备发过来的数据将在这里出现
//                        ByteContent content = instanceWithResponse(response);
//                        if (content.data.length > 0) {
//                            String formData = String.format("%x", content.data[0]);
//                            if (Integer.parseInt(formData) >= 10) {
//                                if (formData.length() == 2 && 0 == content.getTaskId()) {
//                                    Log.d(TAG, "onCharacteristicChanged: success");
//                                } else {
//                                    Log.d(TAG, "onCharacteristicChanged: fail");
//                                }
//
//                            } else {
//                                //0或者1属于正常响应
//                                // byte[] sucByte = new byte[1];
//                                // sucByte[0] = 1;
//                                //Arrays.equals(content.data, sucByte) &&
//
//                            }
//
//
//                        } else {
//                            Log.d(TAG, "onCharacteristicChanged: fail");
//                        }
                    }
                });
    }


    private void writeCheckNetWorkJson(byte[] jsonData, final BleDevice bleDevice) {
        BleManager.getInstance().write(
                bleDevice,
                BLE_SERVICE_STRING,
                BLE_WRITE_CHARACTERISTICS_UUID,
                1,
                splitByte(jsonData, 492),
                new BleWriteCallback() {
                    @Override
                    public void onWriteSuccess() {
                        // 发送数据到设备成功
                        Log.d(TAG, "onWriteSuccess: ");
                    }

                    @Override
                    public void onWriteFailure(BleException exception) {
                        // 发送数据到设备失败
                        Log.d(TAG, "onWriteFailure: " + exception.getDescription().toString());
                    }
                });
    }

    private static ByteContent instanceWithResponse(byte[] response) {
        ByteContent content = new ByteContent();
        content.setHeader(response[0]);
        content.setLength(response[1]);
        content.setTaskId(response[2]);
        content.setStoreid(response[3]);
        content.setpSize(response[4]);
        content.setNumber(response[5]);
        content.setCheck(response[response.length - 2]);
        content.setEnd(response[response.length - 1]);
        int dataSize = response.length - 8;
        byte[] data = new byte[dataSize];
        for (int i = 0; i < dataSize; i++) {
            data[i] = response[6 + i];
        }
        content.data = data;
        return content;
    }
//
//    public static Queue<byte[]> splitResultByte(Queue<byte[]> bytes){
//        Queue<byte[]> byteQueue = new LinkedList<>();
//        byteQueue.
//    }


    public static Queue<byte[]> splitByte(byte[] data, int subSize) {
        if (subSize > 492) {
            BleLog.w("Be careful: split count beyond 20! Ensure MTU higher than 23!");
        }
        int pSize = data.length % subSize == 0 ? data.length / subSize : data.length / subSize + 1;
        Queue<byte[]> byteQueue = new LinkedList<>();
        if (data != null) {
            int index = 0;
            int position = 0;
            int taskId;

            taskId = 3;
            do {
                byte[] rawData = new byte[data.length - index];
                byte[] newData;
                byte[] request;
                System.arraycopy(data, index, rawData, 0, data.length - index);
                if (rawData.length <= subSize) {
                    newData = new byte[rawData.length];
                    System.arraycopy(rawData, 0, newData, 0, rawData.length);
                    index += rawData.length;
                } else {
                    newData = new byte[subSize];
                    System.arraycopy(data, index, newData, 0, subSize);
                    index += subSize;
                }
                request = createRequestByte(newData, pSize, position, taskId);
                position = position + 1;
                byteQueue.offer(request);
            } while (index < data.length);
        }
        return byteQueue;
    }

    /**
     * 构造包头，包尾，以及gen
     */

    private static byte[] createRequestByte(byte[] subAryItem, int pSize, int position, int taskId) {
        int byteSize = subAryItem.length + 11;
        byte[] request = new byte[byteSize];
        request[0] = (byte) 0xAA;
        request[1] = (byte) 0;
        request[2] = (byte) (subAryItem.length + 11);
        request[3] = (byte) taskId;
        request[4] = 2;
        request[5] = (byte) 0;
        request[6] = (byte) pSize;
        request[7] = (byte) 0;
        request[8] = (byte) position;
        for (int j = 0; j < subAryItem.length; j++) {
            request[9 + j] = subAryItem[j];
        }
        request[byteSize - 2] = checkGen(subAryItem, request[0], request[1], request[2], request[3], request[4], request[5], request[6], request[7], request[8]);
        if (mCount == pSize) {
            request[byteSize - 1] = (byte) 0x55;
        } else {
            request[byteSize - 1] = (byte) 0x66;
            mCount = mCount + 1;
        }
        return request;
    }

    private static byte checkGen(byte[] subAryItem, byte header, byte length, byte taskId, byte storeId, byte pSize, byte number, byte number1, byte number2, byte number3) {
        int checkNumber = header + length + taskId + storeId + pSize + number + number1 + number2 + number3;
        for (byte item : subAryItem) {
            checkNumber += item;
        }
        return (byte) (checkNumber & 0xff);
    }

    private final static int randomNumber() {
        Random random = new Random();
        return (random.nextInt(255) + 2);
    }

    public static String byteToHexString(byte[] src) {
        StringBuffer stringBuffer = new StringBuffer();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xff;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuffer.append(0);
            }
            stringBuffer.append(hv + " ");
        }
        return stringBuffer.toString();
    }

}
