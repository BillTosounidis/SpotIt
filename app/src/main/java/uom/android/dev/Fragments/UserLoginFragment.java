package uom.android.dev.Fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uom.android.dev.MainActivity;
import uom.android.dev.R;
import uom.android.dev.UserInteractionActivity;


public class UserLoginFragment extends Fragment {


    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;
    private Button toSignupBtn;

    public UserLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_login, container, false);

        toSignupBtn = (Button) rootView.findViewById(R.id.to_signup_btn);
        toSignupBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((UserInteractionActivity) getActivity()).swapFragment(new UserSignUpFragment());
            }
        });

        usernameWrapper = (TextInputLayout) rootView.findViewById(R.id.usernameWrapper);
        usernameWrapper.setHint("Username");

        passwordWrapper = (TextInputLayout) rootView.findViewById(R.id.passwordWrapper);
        passwordWrapper.setHint("Password");


        return rootView;
    }

}
