package com.example.school;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.school.Helper.DbHelper;
import com.example.school.PasswordRegex.PasswordUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout mEmailInputLayout,mPasswordInputLayout,mConfirmPasswordInputLayout,mName,mPhone;
    private TextInputEditText mDate;
    private Button Registerbtn;
    private DbHelper dbHelper;
    String name,phone,email,password, dob;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailInputLayout = findViewById(R.id.textinputlayout_registeremail);
        mPasswordInputLayout = findViewById(R.id.textinputlayout_registerpassword);
        mConfirmPasswordInputLayout = findViewById(R.id.textinputlayout_registerconfirmpassword);
        mName = findViewById(R.id. textinputlayout_registername);
        mPhone = findViewById(R.id. textinputlayout_registerphone);
        mDate = findViewById(R.id. selectdate);
        Registerbtn =(Button) findViewById(R.id.btn_register);

        dbHelper = new DbHelper(this);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        mDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateEmailAddress() | !validPassword() | !validName() | !validConfirmPassword()| !validPhone() | !validDate())
                {
                    return;
                }


                if(dbHelper.registerUser(name,dob,phone,email,getApplicationContext(),mEmailInputLayout,password))
                {
                    Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });



    }
    private boolean validateEmailAddress()
    {
        email= mEmailInputLayout.getEditText().getText().toString().trim();
        if(email.isEmpty())             //Using method isEmpty()
        {
            mEmailInputLayout.setError("Email is required. Can't be empty");                 //Setting up an error
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mEmailInputLayout.setError("Invalid Email Address. Enter valid Email Address");
            return false;
        }
        else
        {
            mEmailInputLayout.setError(null);
            return true;
        }

    }


    private boolean validPassword()
    {
        password= mPasswordInputLayout.getEditText().getText().toString().trim();


        if(password.isEmpty() )
        {
            mPasswordInputLayout.setError("Password is required. Can't be empty");
            return false;
        }
        else if(!PasswordUtils.PASSWORD_UPPERCASE_PATTERN.matcher(password).matches())
        {
            mPasswordInputLayout.setError("Password is weak.Mininmum one Upper-Case character is required.");
            return false;
        }
        else if(!PasswordUtils.PASSWORD_LOWERCASE_PATTERN.matcher(password).matches())
        {
            mPasswordInputLayout.setError("Password is weak.Mininmum one Lower-Case character is required.");
            return false;
        }
        else if(!PasswordUtils.PASSWORD_SPECIALCHARACTER_PATTERN.matcher(password).matches())
        {
            mPasswordInputLayout.setError("Password is weak.Mininmum one Special character is required.");
            return false;
        }
        else if(!PasswordUtils.PASSWORD_NUMBER_PATTERN.matcher(password).matches())
        {
            mPasswordInputLayout.setError("Password is weak.Mininmum one digit character is required.");
            return false;
        }
        else
        {
            mPasswordInputLayout.setError(null);
            return true;
        }

    }

    private boolean validName()
    {
        name= mName.getEditText().getText().toString().trim();
        if(name.isEmpty())
        {
            mName.setError("Name is required. Can't be empty");
            return false;
        }
        return true;
    }
    private boolean validPhone()
    {
        phone= mPhone.getEditText().getText().toString().trim();
        if(phone.isEmpty())
        {
            mPhone.setError("Phone Number is required. Can't be empty");
            return false;
        }else if(phone.length()!=8)
        {
            mPhone.setError("Enter a valid 8 digit phone number");
            return false;
        }
        return true;
    }
    private boolean validConfirmPassword(){
        String confirmpassword= mConfirmPasswordInputLayout.getEditText().getText().toString().trim();
        String password= mPasswordInputLayout.getEditText().getText().toString().trim();
        if(confirmpassword.isEmpty() )
        {
            mConfirmPasswordInputLayout.setError("No Field can be empty kindly confirm your password");
        } else if(!confirmpassword.equals(password))
        {
            mConfirmPasswordInputLayout.setError("Password don't match kindly try again");
            return false;
        }
        return true;
    }
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mDate.setText(sdf.format(myCalendar.getTime()));

    }

    private boolean validDate() {
        dob = mDate.getText().toString().trim();
        if(dob.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Kindly Select Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}