package uom.android.dev.Fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uom.android.dev.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserSignUpFragment extends Fragment {

    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;
    private TextInputLayout emailWrapper;

    public UserSignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_sign_up, container, false);

        usernameWrapper = (TextInputLayout) rootView.findViewById(R.id.usernameWrapper);
        usernameWrapper.setHint("Username");

        passwordWrapper = (TextInputLayout) rootView.findViewById(R.id.passwordWrapper);
        passwordWrapper.setHint("Password");

        emailWrapper = (TextInputLayout) rootView.findViewById(R.id.emailWrapper);
        emailWrapper.setHint("Email");

        return rootView;
    }

}
