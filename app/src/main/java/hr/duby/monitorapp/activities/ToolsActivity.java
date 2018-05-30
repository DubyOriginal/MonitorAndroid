package hr.duby.monitorapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import hr.duby.monitorapp.R;

public class ToolsActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvTerminalResult;
    private Button btnExecuteCmd, btnClearCmd;
    private Spinner spinnerCmds;
    private EditText etInputCommand;

    private String mActiveCommand = "";

    private String[] mCommandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        //prevent showing keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        tvTerminalResult = (TextView) findViewById(R.id.tvTerminalResult);
        btnExecuteCmd = (Button) findViewById(R.id.btnExecuteCmd);
        btnClearCmd =  (Button) findViewById(R.id.btnClearCmd);
        etInputCommand = (EditText) findViewById(R.id.etInputCommand);
        spinnerCmds = (Spinner) findViewById(R.id.spinnerCmds);

        btnExecuteCmd.setOnClickListener(this);
        btnClearCmd.setOnClickListener(this);

        mCommandList =  getResources().getStringArray(R.array.command_list);
        mActiveCommand = mCommandList[0];
        etInputCommand.setText(mActiveCommand);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.command_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCmds.setAdapter(adapter);
        spinnerCmds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mActiveCommand = mCommandList[position];
                etInputCommand.setText(mActiveCommand);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnExecuteCmd:
                mActiveCommand = etInputCommand.getText().toString();
                executeAndShowResult(mActiveCommand);

                break;

            case R.id.btnClearCmd:
                etInputCommand.setText("");
                mActiveCommand = "";

                default:
                    DLog("Unknown event trigger!!!");
                    break;
        }
    }

    private void executeAndShowResult(String command){
        //String executionResult = executeCommand(command);
        runOnMainThreadExecuteCommand("duby", "duby@238", "93.142.58.38", 22);
        //String executionResult = getThreadResult();
        String executionResult = "";

        if (executionResult == null){
            tvTerminalResult.setText("some error!");
        }else if (executionResult.length() == 0){
            tvTerminalResult.setText("no response");
        }else {
            tvTerminalResult.setText(executionResult);
        }
    }

    private String executeCommand(String command){
        String result = null;
        if (command != null){
            try {
                Process process = Runtime.getRuntime().exec(command);
                InputStreamReader reader = new InputStreamReader(process.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);
                int numRead;
                char[] buffer = new char[5000];
                StringBuffer commandOutput = new StringBuffer();
                while ((numRead = bufferedReader.read(buffer)) > 0) {
                    commandOutput.append(buffer, 0, numRead);
                }
                bufferedReader.close();
                process.waitFor();

                result = commandOutput.toString();
            } catch (IOException e) {
                DLogE("executeCommand -> IOException -> " + e.toString());
                result = e.toString();
            } catch (InterruptedException e) {
                DLogE("executeCommand -> InterruptedException -> " + e.toString());
                result = e.toString();
            }
        }

        return result;
    }

    private void runOnMainThreadExecuteCommand(final String username, final String password, final String hostname, final int port){
        Thread thread = new Thread(new Runnable() {
            String result = "";

            @Override
            public void run() {
                try  {
                    result = executeRemoteCommand(username, password, hostname, port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public String getThreadResult(){
                return result;
            }
        });

        thread.start();

    }

    private String executeRemoteCommand(String username, String password, String hostname, int port) {

        String result = "";

        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(username, hostname, port);
            session.setPassword(password);

            // Avoid asking for key confirmation
            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);

            session.connect();

            // SSH Channel
            ChannelExec channelssh = (ChannelExec) session.openChannel("exec");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            channelssh.setOutputStream(baos);

            // Execute command
            channelssh.setCommand("ls");
            channelssh.connect();
            channelssh.disconnect();

            result = baos.toString();
        } catch (JSchException e) {
            e.printStackTrace();
            return null;
        }

        return result;

    }

    //**********************************************************************************************
    private void DLog(String msg) {
        String className = this.getClass().getSimpleName();
        Log.d("DTag", className + ": " + msg);
    }

    private void DLogE(String msg) {
        String className = this.getClass().getSimpleName();
        Log.e("DTag", className + ": " + msg);
    }

}
