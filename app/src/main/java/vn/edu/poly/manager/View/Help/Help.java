package vn.edu.poly.manager.View.Help;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vn.edu.poly.manager.R;
import vn.edu.poly.manager.View.Help.HelpDetais.RequestHelp;
import vn.edu.poly.manager.View.Help.HelpDetais.NKSHeader;

public class Help extends Fragment implements View.OnClickListener {
    View view;
    Button btn_infor_company, btn_tiket_company;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_help, container, false);
        initView();
        return view;
    }

    private void initView() {
        btn_infor_company = view.findViewById(R.id.btn_infor_company);
        btn_tiket_company = view.findViewById(R.id.btn_tiket_company);
        btn_infor_company.setOnClickListener(this);
        btn_tiket_company.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_infor_company:
                intentActivity(NKSHeader.class);
                break;
            case R.id.btn_tiket_company:
                intentActivity(RequestHelp.class);
                break;
        }
    }

    private void intentActivity(Class cl){
        Intent intent = new Intent(getContext(), cl);
        startActivity(intent);
    }
}
