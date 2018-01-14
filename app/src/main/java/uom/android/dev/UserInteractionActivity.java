package uom.android.dev;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uom.android.dev.Fragments.UserLoginFragment;
import uom.android.dev.Fragments.UserSignUpFragment;

public class UserInteractionActivity extends AppCompatActivity {


    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interaction);

        fragmentManager = getSupportFragmentManager();
        UserLoginFragment userLoginFragment = new UserLoginFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.user_interaction_container, userLoginFragment).commit();
    }

    public void swapFragment(Fragment nextFragment){
        fragmentManager.beginTransaction()
                .replace(R.id.user_interaction_container, nextFragment).commit();
    }
}
