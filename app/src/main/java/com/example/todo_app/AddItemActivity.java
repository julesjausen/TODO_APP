package com.example.todo_app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.Slider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddItemActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextResponsiblePerson;
    private Slider sliderPriority;
    private EditText editTextDescription;
    private TimePicker timePicker;
    private TextView textViewSelectDay;
    private Calendar calendarDueDate;
    private int priority;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference itemRef = db.collection("items");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        editTextTitle = findViewById(R.id.edit_text_title_add);
        editTextResponsiblePerson = findViewById(R.id.edit_text_responsiblePerson_add);
        sliderPriority = findViewById(R.id.slider_priority);
        editTextDescription = findViewById(R.id.edit_text_description_add);
        textViewSelectDay = findViewById(R.id.text_view_select_day_add);
        Button buttonSave = findViewById(R.id.button_save_add);
        Button buttonDismiss = findViewById(R.id.button_dismiss_add);

        timePicker = findViewById(R.id.tp_time_picker_add);
        timePicker.setIs24HourView(true);

        calendarDueDate = Calendar.getInstance();
        updateCalendarTime(calendarDueDate);
        updateButtonDay(calendarDueDate);
        priority = 1;
        sliderPriority.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                priority = (int) value;
            }
        });

        textViewSelectDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddItemActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                calendarDueDate.set(Calendar.YEAR, year);
                                calendarDueDate.set(Calendar.MONTH, monthOfYear);
                                calendarDueDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateButtonDay(calendarDueDate);
                            }
                        }, calendarDueDate.get(Calendar.YEAR), calendarDueDate.get(Calendar.MONTH), calendarDueDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                updateButtonDay(calendarDueDate);
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                calendarDueDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarDueDate.set(Calendar.MINUTE, minute);
                calendarDueDate.set(Calendar.SECOND, 0);
                calendarDueDate.set(Calendar.MILLISECOND, 0);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });
        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
    private void updateButtonDay(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        String date = dateFormat.format(calendar.getTime());
        textViewSelectDay.setText(date);
    }
    private void updateCalendarTime(Calendar c) {
        timePicker.setHour(c.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(c.get(Calendar.MINUTE));
    }

    private void saveItem() {
        String title = editTextTitle.getText().toString();
        String responsiblePerson = editTextResponsiblePerson.getText().toString();
        String description = editTextDescription.getText().toString();

        Calendar calendarCreateDate = Calendar.getInstance();
        Long createDate = calendarCreateDate.getTimeInMillis();
        Long dueDate = calendarDueDate.getTimeInMillis();

        if (title.trim().isEmpty() || responsiblePerson.trim().isEmpty() ||
                description.trim().isEmpty()) {
            Toast.makeText(this, "Please fill out all fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        CollectionReference itemRef = FirebaseFirestore.getInstance()
                .collection("items");

        itemRef.add((new Item(title, priority, description, responsiblePerson,
                        dueDate, createDate)))
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(AddItemActivity.this, "Item added",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private void dismiss() {
        finish();
    }
}