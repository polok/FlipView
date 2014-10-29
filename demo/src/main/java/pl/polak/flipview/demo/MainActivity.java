package pl.polak.flipview.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.github.polok.flipview.FlipView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ((FlipView)findViewById(R.id.flip_listener_view_demo)).setFlipViewChangeListener(new FlipView.FlipViewChangeListener() {
            @Override
            public void onFlipViewClick(FlipView flipView, boolean isChecked) {
                Toast.makeText(MainActivity.this, "Clicked - " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
