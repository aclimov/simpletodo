package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.codepath.simpletodo.R;

import java.util.Calendar;
import java.util.Date;

import static android.R.attr.data;
import static android.R.attr.y;

public class Dialog_SelectDateTime extends DialogFragment  {

    DatePicker datePicker;

    public interface SelectDateDialogListener {
        void onFinishDateDialog(Date date);
    }

    public Dialog_SelectDateTime() {
        // Required empty public constructor
    }

    public static Dialog_SelectDateTime newInstance(int year,int month,int day) {
        Dialog_SelectDateTime fragment = new Dialog_SelectDateTime();
        Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_datetime, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        int year = getArguments().getInt("year");
        int month = getArguments().getInt("month");
        int day = getArguments().getInt("day");

        if(year!=0) {
            datePicker.updateDate(year, month, day);
        }

        Button btnCancel= (Button)view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            } } );

        Button btnOk= (Button)view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnData();
            } } );
    }

    public void ReturnData()
    {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        //Date dt = new Date(year,month,day);
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, day);

        SelectDateDialogListener listener = (SelectDateDialogListener) getActivity();
        listener.onFinishDateDialog(newDate.getTime());

        dismiss();
    }
}
