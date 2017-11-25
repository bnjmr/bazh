package ir.jahanmirbazh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.bazh.ActivityLogin;
import ir.jahanmirbazh.bazh.V;

public class FragmentWizardLast extends Fragment {


    @Bind(R.id.btnGoToSearchPage)
    LinearLayout btnGoToSearchPage;
    DatabaseManager databaseManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wizard_last, container, false);
        ButterKnife.bind(this, view);
        databaseManager = new DatabaseManager();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btnGoToSearchPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), ActivityLogin.class));
                V.setting = databaseManager.readSetting();
                V.setting.setShowWizard(true);
                V.setting.save();
                getActivity().finish();
            }
        });
    }
}
