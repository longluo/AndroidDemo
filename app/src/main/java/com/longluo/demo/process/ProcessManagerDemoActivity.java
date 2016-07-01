package com.longluo.demo.process;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.longluo.demo.R;

import java.util.ArrayList;
import java.util.List;

public class ProcessManagerDemoActivity extends ListActivity {
    private final int CONVERT = 1024;

    private PackageManager mPkgMngr;
    private List<RunningAppProcessInfo> display_process;
    private ActivityManager activity_man;
    private MyListAdapter delegate;
    private TextView availMem_label, numProc_label;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_process_manage_demo);
        mPkgMngr = getPackageManager();

        availMem_label = (TextView) findViewById(R.id.available_mem_label);
        numProc_label = (TextView) findViewById(R.id.num_processes_label);
        activity_man = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        display_process = new ArrayList<RunningAppProcessInfo>();
        update_list();

        delegate = new MyListAdapter();
        setListAdapter(delegate);
    }

    @Override
    protected void onListItemClick(ListView parent, View view, int position,
                                   long id) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[] options = {"Details", "Launch"};
        final int index = position;

        builder.setTitle("Process options");

        try {
            builder.setIcon(mPkgMngr.getApplicationIcon(display_process.get(position).processName));

        } catch (NameNotFoundException e) {
            builder.setIcon(R.drawable.processinfo);
        }

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {

                switch (choice) {
                    case 0:
                        Toast.makeText(ProcessManagerDemoActivity.this,
                                display_process.get(index).processName,
                                Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        Intent i = mPkgMngr.getLaunchIntentForPackage(display_process
                                .get(index).processName);

                        if (i != null) {
                            startActivity(i);
                        } else {
                            Toast.makeText(ProcessManagerDemoActivity.this, "Could not launch",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;

                }
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    /**
     *
     */
    private void update_labels() {
        MemoryInfo mem_info;
        double mem_size;

        mem_info = new ActivityManager.MemoryInfo();
        activity_man.getMemoryInfo(mem_info);
        mem_size = (mem_info.availMem / (CONVERT * CONVERT));

        availMem_label.setText(String.format("Available memory:\t %.2f Mb",
                mem_size));
        numProc_label.setText("Number of processes:\t "
                + display_process.size());
    }

    private void update_list() {
        List<RunningAppProcessInfo> total_process = activity_man
                .getRunningAppProcesses();
        int len;

        total_process = activity_man.getRunningAppProcesses();
        len = total_process.size();

        for (int i = 0; i < len; i++) {
            if (total_process.get(i).importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && total_process.get(i).importance != RunningAppProcessInfo.IMPORTANCE_SERVICE)
                display_process.add(total_process.get(i));
        }

        update_labels();
    }

    /*
     * (non-JavaDoc) private inner class to bind the listview and its data
     * source
     *
     * @author Joe Berria
     */
    private class MyListAdapter extends ArrayAdapter<RunningAppProcessInfo> {

        public MyListAdapter() {
            super(ProcessManagerDemoActivity.this, R.layout.layout_app_backup_item, display_process);
        }

        private String parse_name(String pkgName) {
            String[] items = pkgName.split("\\.");
            String name = "";
            int len = items.length;

            for (int i = 0; i < len; i++) {
                if (!items[i].equalsIgnoreCase("com")
                        && !items[i].equalsIgnoreCase("android")
                        && !items[i].equalsIgnoreCase("google")
                        && !items[i].equalsIgnoreCase("process")
                        && !items[i].equalsIgnoreCase("htc")
                        && !items[i].equalsIgnoreCase("coremobility"))
                    name = items[i];
            }
            return name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            String pkg_name = display_process.get(position).processName;

            if (view == null) {
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.layout_app_backup_item, parent, false);
            }

            TextView bottom_label = (TextView) view
                    .findViewById(R.id.bottom_view);
            TextView top_label = (TextView) view.findViewById(R.id.top_view);
            ImageView icon = (ImageView) view.findViewById(R.id.row_image);
            icon.setAdjustViewBounds(true);
            icon.setMaxHeight(40);

            top_label.setText(parse_name(pkg_name));
            bottom_label.setText(String.format("%s, pid: %d",
                    display_process.get(position).processName,
                    display_process.get(position).pid));

            try {
                icon.setImageDrawable(mPkgMngr.getApplicationIcon(pkg_name));

            } catch (NameNotFoundException e) {
                icon.setImageResource(R.drawable.processinfo);
            }

            return view;
        }
    }
}

