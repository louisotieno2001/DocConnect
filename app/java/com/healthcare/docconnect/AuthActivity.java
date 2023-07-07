package com.healthcare.docconnect;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;

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
    String username = signinUsername.getText().toString().trim();
    String password = signinPassword.getText().toString().trim();
    ParseUser.logInInBackground(username, password, new LogInCallback(){
      @Override 
      public void done(ParseUser user, ParseException e){
        if (e == null){
          Log.v("DocconnectDebug", ""+user);

        }else{
          Log.v("DocconnectDebug", ""+e);
        }

      }
    });

  }
  public void signup(){
    String username = signupUsername.getText().toString().trim();
    String firstName = signupFirstName.getText().toString().trim();
    String lastName = signupLastName.getText().toString().trim();
    String email = signupEmail.getText().toString().trim();
    String password = signupPassword.getText().toString().trim();
    String confirmPassword = signupConfirmPassword.getText().toString().trim();
    String phoneNumber = signupPhone.getText().toString().trim();

    ParseUser user = new ParseUser();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(password);
    user.put("firstname", firstName);
    user.put("lastname", lastName);
    user.put("phonenumber", phoneNumber);
    user.signUpInBackground(new SignUpCallback(){
      @Override
      public void done(ParseException e){
        if(e == null){
          Log.v("DocconnectDebug", "Sign Up success");

        }else{
          Log.e("DocconnectDebug", ""+e);

        }
      }
    });
  }
}
