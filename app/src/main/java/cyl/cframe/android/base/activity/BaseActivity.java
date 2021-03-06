package cyl.cframe.android.base.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cyl.cframe.android.R;
import cyl.cframe.library.widgets.BaseProgressDialog;

/**
 * Created by jerry on 2016/3/12.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private BaseProgressDialog mProgressDialog = null;
    private ImageView mNavigationBarBack;
    private TextView mNavigationBarTitle;
    private Button mNavigationBarRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView();
        mNavigationBarBack = (ImageView) findViewById(R.id.navigation_bar_back_iv);
        mNavigationBarTitle = (TextView) findViewById(R.id.navigation_bar_title_tv);
        mNavigationBarRight = (Button) findViewById(R.id.navigation_bar_right_btn);

        mNavigationBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findView();
        setListener();
        initData();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.base_push_right_in, R.anim.base_push_left_out);
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        if (mProgressDialog != null && mProgressDialog.isShowing()
                && mProgressDialog.cancelable()) {
            cancelProgressDialog();
            return;
        }
        super.finish();
        overridePendingTransition(R.anim.base_push_left_in, R.anim.base_push_right_out);
    }

    protected abstract void setContentView();

    protected abstract void findView();

    protected abstract void initData();

    protected abstract void setListener();

    /**
     * @author Jerry.Cao
     */


    /**
     * 设置导航栏标题
     *
     * @param title
     */
    protected void setNavigationBarTitle(String title) {
        if (mNavigationBarTitle == null) {
            mNavigationBarTitle = (TextView) findViewById(R.id.navigation_bar_title_tv);
        }

        mNavigationBarTitle.setText(title);
    }

    public void stopProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.stop();
        }
        mProgressDialog = null;
    }

    protected void cancelProgressDialog() {
        if (mProgressDialog.cancel()) {
            mProgressDialog = null;
        }
    }

    public void showProgressDialog(BaseProgressDialog.OnCancelListener cancelListener, boolean cancelable, String msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog = new BaseProgressDialog(this, msg);
        if (cancelListener != null) {
            mProgressDialog.setOnCancelListener(cancelListener);
        }
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.show();
    }

    public void showProgressDialog(boolean cancelable, String msg) {
        showProgressDialog(null, cancelable, msg);
    }

    public void showProgressDialog(String msg) {
        showProgressDialog(true, msg);
    }

    public void showProgressDialog(boolean cancelable) {
        showProgressDialog(cancelable, "");
    }

    /**
     * 检查网络连接
     *
     * @return
     */
    public boolean checkInternetConnection() {
        NetworkInfo info = null;
        if (info == null) {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            info = manager.getActiveNetworkInfo();
        }
        if (info != null && info.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param title
     * @param msg
     * @param oklistener
     * @param cancellistener
     */
    protected void showDialogForPrompt(String title, String msg,
                                       DialogInterface.OnClickListener oklistener,
                                       DialogInterface.OnClickListener cancellistener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setPositiveButton(
                getString(R.string.base_progress_dialog_ok), oklistener);
        if (cancellistener != null) {
            builder.setNegativeButton(getString(R.string.base_progress_dialog_cancel),
                    cancellistener);
        }
        AlertDialog dialog = builder.create();
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
    }

    /**
     * @param title
     * @param msg
     */
    protected void showDialogForPrompt(String title, String msg) {
        showDialogForPrompt(title, msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }

        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
    }

    public void exitApp() {
        String title = getString(R.string.base_progress_dialog_title);
        String msg = getString(R.string.base_exit_app);
        showDialogForPrompt(title, msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                System.exit(0);
            }

        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
    }
}
