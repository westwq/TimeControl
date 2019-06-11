package sg.edu.np.twq2.timecontrol;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TimeActivity extends AppCompatActivity {
    int player1;
    int player2;
    TextView tvPlayer1;
    TextView tvPlayer2;
    TextView tvUpdate;
    CountDownTimer cdt;
    ArrayList<String> data = new ArrayList<>();

    boolean isP2;

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        Intent i = getIntent();
        int duration = Integer.parseInt(i.getStringExtra("duration"));

        player1 = duration*1000;
        player2 = duration*1000;

        tvPlayer1 = findViewById(R.id.textPlayer1);
        tvPlayer1.setText("" + duration);
        tvPlayer2 = findViewById(R.id.textPlayer2);
        tvPlayer2.setText("" + duration);

        ListView list = findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        list.setAdapter(adapter);

        tvUpdate = tvPlayer1; //for update
        startTimer(player1);
    }

    private void startTimer(int dur)
    {
        cdt = new CountDownTimer(dur, 1000 ) {
            @Override
            public void onTick(long l) {
                tvUpdate.setText("" + l / 1000);
                if(isP2) {
                    player2 = (int)l;
                }
                else {
                    player1 = (int)l;
                }
            }

            @Override
            public void onFinish() {
                String p = "1";
                tvUpdate.setText("0");
                if(isP2){
                    player2 = 0;
                    p = "2";
                }
                else {
                    player1 = 0;
                }

                Toast tt = Toast.makeText(TimeActivity.this, "Player " + p + "'s time runs out", Toast.LENGTH_LONG);
                tt.show();
            }
        };
        cdt.start();
    }

    public void onClick(View v)
    {
        cdt.cancel(); //stop the timer
        isP2 = !isP2;
        if(isP2) {
            data.add("Player 1 left " + player1/1000 + " sec");
            tvUpdate = tvPlayer2;
            startTimer(player2);
        }
        else {
            data.add("Player 2 left " + player2/1000 + " sec");
            tvUpdate = tvPlayer1;
            startTimer(player1);
        }

        adapter.notifyDataSetChanged();

    }
}
