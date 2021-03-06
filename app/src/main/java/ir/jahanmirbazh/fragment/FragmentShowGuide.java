package ir.jahanmirbazh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.R;

public class FragmentShowGuide extends Fragment {

    @Bind(R.id.txtFragmentTitle)
    TextView txtFragmentTitle;
    @Bind(R.id.txtDescription)
    TextView txtDescription;

    public static FragmentShowGuide newInstance(String title, String description) {
        Bundle args = new Bundle();
        args.putString("TITLE",title);
        args.putString("DES",description);
        FragmentShowGuide fragment = new FragmentShowGuide();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_guide,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = getArguments().getString("TITLE");
        txtFragmentTitle.setText("" + title);

        String des = getArguments().getString("DES");
        txtDescription.setText("" + des);
    }
}
