package com.canchala.julio.practica7;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Info extends Fragment {

    private TextView nombre;
    private TextView correo;


    public Info() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_info, container, false);

        nombre=(TextView) rootView.findViewById(R.id.textViewName);
        correo=(TextView) rootView.findViewById(R.id.textViewUsername);


        Bundle bundle=getArguments();
        String name=bundle.getString("nam");
        String email=bundle.getString("cor");

        nombre.setText(name);
        correo.setText(email);




        return rootView;




    }

}
