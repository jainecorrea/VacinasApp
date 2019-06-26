package com.example.jaine.vacinacaoapp.activity.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.fragment.DatePickerFragment;
import com.example.jaine.vacinacaoapp.activity.fragment.TimePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class NovoLembreteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public EditText aliasHorario, aliasData;
    public DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_lembrete);

        aliasHorario = (EditText)findViewById(R.id.editHorario);
        aliasData = (EditText)findViewById(R.id.editAlterarDataNasc);
        aliasData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), " date picker ");
            }
        });
        // Comando para o teclado virtual não aparecer
        aliasData.setInputType(InputType.TYPE_NULL);

        aliasHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        // Comando para o teclado virtual não aparecer
        aliasHorario.setInputType(InputType.TYPE_NULL);
    }

    //Define a forma com que a data será exibida
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentSateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        EditText aliasData = findViewById(R.id.editAlterarDataNasc);
        aliasData.setText(currentSateString);
    }

    //Define a forma com que o horário será exibido
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView) findViewById(R.id.editHorario);
        textView.setText(hourOfDay+":"+minute);
    }
}
