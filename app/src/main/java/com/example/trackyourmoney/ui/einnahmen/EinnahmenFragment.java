package com.example.trackyourmoney.ui.einnahmen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.trackyourmoney.MainActivity;
import com.example.trackyourmoney.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EinnahmenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EinnahmenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EinnahmenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EinnahmenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EinnahmenFragment newInstance(String param1, String param2) {
        EinnahmenFragment fragment = new EinnahmenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         String[] einnahmenlisteArray = {
                "Arbeit - Betrag: 500€ - Wann: 12.02.2020",
                "Freund - Betrag: 40€ - Wann: 12.02.2020",
                "Geliehen - Betrag: 2000€ - Wann: 12.02.2020",
                "Arbeit - Betrag: 800€ - Wann: 12.02.2020",
                "Freund - Betrag: 500€ - Wann: 12.02.2020",
                "Arbeit - Betrag: 800€ - Wann: 12.02.2020",
                "Arbeit - Betrag: 800€ - Wann: 12.02.2020",
                "Schwarzarbeit - Betrag: 500€ - Wann: 12.02.2020",
                "PayPal - Betrag: 500€ - Wann: 12.02.2020",
                "Arbeit - Betrag: 800€ - Wann: 12.02.2020",
                "Geld zurück - Betrag: 500€ - Wann: 12.02.2020",
                "Arbeit - Betrag: 800€ - Wann: 12.02.2020",
                "Arbeit - Betrag: 800€ - Wann: 12.02.2020",
                "Arbeit - Betrag: 800€ - Wann: 12.02.2020",
                "Essen ausgegeben - Betrag: 500€ - Wann: 12.02.2020",
                "Freund - Betrag: 500€ - Wann: 12.02.2020",
                "Arbeit - Betrag: 800€ - Wann: 12.02.2020",
                "Meister - Betrag: 500€ - Wann: 12.02.2020"
        };

        List<String> einnahmenListe = new ArrayList<>(Arrays.asList(einnahmenlisteArray));

        ArrayAdapter<String> einnahmelisteAdapter =
                new ArrayAdapter<>(
                        getActivity(), // Die aktuelle Umgebung (diese Activity)
                        R.layout.list_item_einnahmenliste, // ID der XML-Layout Datei
                        R.id.list_item_einnahmenliste_textview, // ID des TextViews
                        einnahmenListe); // Beispieldaten in einer ArrayList

        //ListView einnahmelisteListView = (ListView) rootView.findViewById(R.id.listview_einnahmenliste);
        //einnahmelisteListView.setAdapter(einnahmelisteAdapter);

        return inflater.inflate(R.layout.fragment_einnahmen, container, false);
    }
}