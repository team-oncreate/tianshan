package me.yuruekis.tianshan;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button_bilibili);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://live.bilibili.com/51223"));
                startActivity(intent);
            }
        });

        CircleImageView avatar = (CircleImageView) findViewById(R.id.image_avatar);
        avatar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                    AlertDialog.Builder easteregg = new AlertDialog.Builder(MainActivity.this);
                    easteregg.setTitle("这是个彩蛋喵");
                    easteregg.setMessage("你点到了个彩蛋hhhh");
                    easteregg.setCancelable(false);
                    easteregg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                         @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    easteregg.show();

                return true;
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://space.bilibili.com/7417985"));
                startActivity(intent);
            }
        });

        Button buttonComment = (Button) findViewById(R.id.button_comment);
        buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentComment = new Intent(MainActivity.this, CommentActivity.class);
                startActivity(intentComment);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                showAboutDialog();
                break;
            default:
        }
        return true;
    }

    public void showAboutDialog(){
                android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(getString(R.string.about_title));   //setTitle
                dialog.setMessage(getString(R.string.about_message));   //setMessage
                dialog.setCancelable(true);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
        }
    }


