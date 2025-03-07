package com.example.cverdetotoo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EditProfile extends AppCompatActivity {

    private ImageView ivBack;
    private EditText etFirstName, etLastName, etUsername, etEmail, etBirthDate;
    private RadioGroup rgGender;
    private RadioButton rbFemale, rbMale, rbOthers;
    private TextView tvSaveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make sure your layout file name matches the one used here (for example, activity_edit_profile.xml)
        setContentView(R.layout.activity_edit_profile);

        initializeViews();
        setupListeners();
        loadUserData();
    }

    /**
     * Initialize all views from the layout.
     */
    private void initializeViews() {
        ivBack = findViewById(R.id.ivBack);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etBirthDate = findViewById(R.id.etBirthDate);
        rgGender = findViewById(R.id.rgGender);
        rbFemale = findViewById(R.id.rbFemale);
        rbMale = findViewById(R.id.rbMale);
        rbOthers = findViewById(R.id.rbOthers);
        tvSaveChanges = findViewById(R.id.tvSaveChanges);
    }

    /**
     * Set up click listeners for various interactive views.
     */
    private void setupListeners() {
        // Handle the back arrow click to finish the activity
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Go back to the previous screen
            }
        });

        // Show DatePicker when clicking on the birth date field
        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        // Handle the save changes button click
        tvSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfileChanges();
            }
        });
    }

    /**
     * Displays a DatePickerDialog to select a birth date.
     */
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Months are zero-indexed so add one for display purposes
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        etBirthDate.setText(date);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    /**
     * Validate the input fields and simulate saving the profile changes.
     */
    private void saveProfileChanges() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String birthDate = etBirthDate.getText().toString().trim();
        String gender = "";

        // Determine which gender option is selected
        int selectedId = rgGender.getCheckedRadioButtonId();
        if (selectedId == rbFemale.getId()) {
            gender = "Female";
        } else if (selectedId == rbMale.getId()) {
            gender = "Male";
        } else if (selectedId == rbOthers.getId()) {
            gender = "Others";
        }

        // Simple validation
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
                email.isEmpty() || birthDate.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Implement actual saving logic here (e.g., API call or saving to local storage)

        Toast.makeText(this, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Load existing user data if available.
     */
    private void loadUserData() {
        // Placeholder: Populate the fields with current user data if available.
        // For example:
        // etFirstName.setText("John");
        // etLastName.setText("Doe");
        // etUsername.setText("johndoe");
        // etEmail.setText("john@example.com");
        // etBirthDate.setText("1/1/1990");
        // rgGender.check(rbMale.getId());
    }
}