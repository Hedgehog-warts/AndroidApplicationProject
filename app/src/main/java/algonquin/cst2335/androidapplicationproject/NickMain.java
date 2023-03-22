package algonquin.cst2335.androidapplicationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
// m2
public class NickMain extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editTextWidth;
    private EditText editTextHeight;
    private Button button;
    private RecyclerView.Adapter adapter;
    private List<String> dimensions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_main);

        recyclerView = findViewById(R.id.recyclerView);
        editTextWidth = findViewById(R.id.editTextWidth);
        editTextHeight = findViewById(R.id.editTextHeight);
        button = findViewById(R.id.button);

        dimensions = new ArrayList<>();
//        adapter = new Adapter(dimensions) {
//        };
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String widthStr = editTextWidth.getText().toString().trim();
                String heightStr = editTextHeight.getText().toString().trim();
                if (widthStr.isEmpty() || heightStr.isEmpty()) {
                    Toast.makeText(NickMain.this, "Please enter width and height", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int width = Integer.parseInt(widthStr);
                        int height = Integer.parseInt(heightStr);
                        dimensions.add(width + " x " + height);
                        adapter.notifyDataSetChanged();
                    } catch (NumberFormatException e) {
                        Toast.makeText(NickMain.this, "Please enter valid width and height", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
