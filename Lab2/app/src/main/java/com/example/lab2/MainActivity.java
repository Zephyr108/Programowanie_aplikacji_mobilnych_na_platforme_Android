package com.example.lab2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.adapter.PhoneListAdapter;
import com.example.lab2.data.Phone;
import com.example.lab2.viewmodel.PhoneViewModel;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_PHONE_REQUEST = 1;
    private static final int EDIT_PHONE_REQUEST = 2;

    private PhoneViewModel mPhoneViewModel;
    private PhoneListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.topAppBar));

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        adapter = new PhoneListAdapter(this, new PhoneListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Phone phone) {
                // Otwórz edycję
                Intent intent = new Intent(MainActivity.this, AddPhoneActivity.class);
                intent.putExtra("id", phone.getId());
                intent.putExtra("producer", phone.getProducer());
                intent.putExtra("model", phone.getModel());
                intent.putExtra("version", phone.getAndroidVersion());
                intent.putExtra("www", phone.getWww());
                startActivityForResult(intent, EDIT_PHONE_REQUEST);
            }

            @Override
            public void onItemLongClick(Phone phone) {
                String url = phone.getWww();
                if (url != null && !url.isEmpty()) {
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "https://" + url;
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Brak adresu WWW", Toast.LENGTH_SHORT).show();
                }
            }

        });


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPhoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        mPhoneViewModel.getAllPhones().observe(this, adapter::setPhones);

        // FAB - dodawanie nowego telefonu
        findViewById(R.id.fabMain).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPhoneActivity.class);
            startActivityForResult(intent, ADD_PHONE_REQUEST);
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        mPhoneViewModel.delete(adapter.getPhoneAt(viewHolder.getAdapterPosition()));
                    }
                }
        );
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.clear_data) {
            mPhoneViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) return;

        String producer = data.getStringExtra("producer");
        String model = data.getStringExtra("model");
        String version = data.getStringExtra("version");
        String www = data.getStringExtra("www");

        if (requestCode == ADD_PHONE_REQUEST) {
            Phone phone = new Phone(producer, model, version, www);
            mPhoneViewModel.insert(phone);
        } else if (requestCode == EDIT_PHONE_REQUEST) {
            int id = data.getIntExtra("id", -1);
            if (id != -1) {
                Phone updated = new Phone(producer, model, version, www);
                updated.setId(id);
                mPhoneViewModel.update(updated);
            }
        }
    }
}
