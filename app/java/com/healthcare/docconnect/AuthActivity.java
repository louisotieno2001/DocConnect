package com.healthcare.docconnect;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.content.Intent;

import com.parse.ParseUser;
import com.parse.LogInCallback;
import com.parse.SignUpCallback;
import com.parse.ParseException;

import android.util.Log;

import android.view.View;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {
  LinearLayout signupLayout;
  LinearLayout signinLayout;
  Button btnOrSignIn;
  Button btnOrSignUp;
  Button btnSignIn;
  Button btnSignUp;

  // signup 
  EditText signupUsername;
  EditText signupFirstName;
  EditText signupLastName;
  EditText signupEmail;
  EditText signupPhone;
  EditText signupPassword;
  EditText signupConfirmPassword;

  // signin 
  EditText signinUsername;
  EditText signinPassword;

  // errors 
  TextView signupErrors;
  TextView signinErrors;

  // Progress bars 
  ProgressBar signinProgress;
  ProgressBar signupProgress;
  
  static final int BTN_OR_SIGNUP = 0x01;
  static final int BTN_OR_SIGNIN = 0x02;
  static final int BTN_SIGNUP = 0x04;
  static final int BTN_SIGNIN = 0x08;


  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auth);
    signinLayout = (LinearLayout) findViewById(R.id.signin_layout);
    signupLayout = (LinearLayout) findViewById(R.id.signup_layout);
    btnOrSignIn = (Button) findViewById(R.id.btnOrSignIn);
    btnOrSignUp = (Button) findViewById(R.id.btnOrSignUp);
    btnSignUp = (Button) findViewById(R.id.btnSignUp);
    btnSignIn = (Button) findViewById(R.id.btnSignIn);


    signupUsername = (EditText) findViewById(R.id.signup_username);
    signupFirstName = (EditText) findViewById(R.id.signup_firstName);
    signupLastName = (EditText) findViewById(R.id.signup_lastName);
    signupPhone = (EditText) findViewById(R.id.signup_phoneNumber);
    signupEmail = (EditText) findViewById(R.id.signup_email);
    signupPassword = (EditText) findViewById(R.id.signup_password);
    signupConfirmPassword = (EditText) findViewById(R.id.signup_confirm_password);

    signinUsername = (EditText) findViewById(R.id.signin_username);
    signinPassword = (EditText) findViewById(R.id.signin_password);

    signinErrors = (TextView) findViewById(R.id.signin_errors);
    signupErrors = (TextView) findViewById(R.id.signup_errors);

    signinProgress = (ProgressBar) findViewById(R.id.signin_progress);
    signupProgress = (ProgressBar) findViewById(R.id.signup_progress);


    btnOrSignIn.setTag(BTN_OR_SIGNIN);
    btnOrSignUp.setTag(BTN_OR_SIGNUP);
    btnSignIn.setTag(BTN_SIGNIN);
    btnSignUp.setTag(BTN_SIGNUP);
    
    btnOrSignIn.setOnClickListener(this);
    btnOrSignUp.setOnClickListener(this);
    btnSignIn.setOnClickListener(this);
    btnSignUp.setOnClickListener(this);
    
  }

  private void showSignup(boolean show){
    if(show){
      signupLayout.setVisibility(View.VISIBLE);
      signinLayout.setVisibility(View.GONE);
    }else{
      signupLayout.setVisibility(View.GONE);
      signinLayout.setVisibility(View.VISIBLE);
    }
  }

  private void showSignupProgress(boolean show){
    if(show){
      signupProgress.setVisibility(View.VISIBLE);
    }else{
      signupProgress.setVisibility(View.INVISIBLE);
    }
  }

  private void showSigninProgress(boolean show) {
    if(show){
      signinProgress.setVisibility(View.VISIBLE);
    }else{
      signinProgress.setVisibility(View.INVISIBLE);
    }
  }

  @Override
  public void onClick(View v){
    int TAG = (int) v.getTag();
    
    switch(TAG){
      case BTN_OR_SIGNUP:
        showSignup(true);
        break;
      case BTN_OR_SIGNIN:
        showSignup(false);
        break;
      case BTN_SIGNIN:
        login();
        break;
      case BTN_SIGNUP:
        signup();
        break;
      default:
        break;
    }

  }

  public void login(){
    signinErrors.setText("");
    showSigninProgress(true);
    String username = signinUsername.getText().toString().trim();
    String password = signinPassword.getText().toString().trim();
    ParseUser.logInInBackground(username, password, new LogInCallback(){
      @Override 
      public void done(ParseUser user, ParseException e){
        showSigninProgress(false);
        if (e == null){
           Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
           startActivity(intent);
        }else{
           signinErrors.setText(e.getMessage());
        }

      }
    });

  }
  public void signup(){
    signupErrors.setText("");
    String username = signupUsername.getText().toString().trim();
    String firstName = signupFirstName.getText().toString().trim();
    String lastName = signupLastName.getText().toString().trim();
    String email = signupEmail.getText().toString().trim();
    String password = signupPassword.getText().toString().trim();
    String confirmPassword = signupConfirmPassword.getText().toString().trim();
    String phoneNumber = signupPhone.getText().toString().trim();

    if(username == ""){
      signupErrors.setText("Username cannot be null");
      return;
    } else if(firstName == "" || lastName == ""){
      signupErrors.setText("First and Last name is required.");
      return;
    }else if(email == ""){
      signupErrors.setText("Email is required. ");
      return;
    }else if(password == ""){
      signupErrors.setText("password is required");
      return;
    }else if(!password.equals(confirmPassword)){
      signupErrors.setText("Passwords do not match. Please Confirm.");
      return;
    }else if(phoneNumber == ""){
      signupErrors.setText("Phone number is required.");
      return;
    }
    ParseUser user = new ParseUser();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(password);
    user.put("firstname", firstName);
    user.put("lastname", lastName);
    user.put("phonenumber", phoneNumber);
    showSignupProgress(true);

    user.signUpInBackground(new SignUpCallback(){
      @Override
      public void done(ParseException e){
        showSignupProgress(false);
        if(e == null){
           Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
           startActivity(intent);
        }else{
           signupErrors.setText(e.getMessage());
        }
      }
    });
  }
}
