package layout;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;

import com.codepath.simpletodo.R;

import java.util.Date;

import static com.codepath.simpletodo.R.id.datePicker;


public class Dialog_SelectTime extends DialogFragment  {

    TimePicker timePicker;

    public interface SelectTimeDialogListener {
        void onFinishTimeDialog(int hours, int minutes);
    }

    public Dialog_SelectTime() {
        // Required empty public constructor
    }

    public static Dialog_SelectTime newInstance(int hours,int minutes) {
        Dialog_SelectTime fragment = new Dialog_SelectTime();
        Bundle args = new Bundle();
        args.putInt("hours", hours);
        args.putInt("minutes", minutes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_time, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timePicker=(TimePicker)view.findViewById(R.id.timePicker);

        int hours = getArguments().getInt("hours");
        int minutes = getArguments().getInt("minutes");

        if(hours!=0) {
            timePicker.setCurrentHour(hours);
            timePicker.setCurrentMinute(minutes);
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
        int hour;
        int minutes;

        if (Build.VERSION.SDK_INT >= 23 ) {
            hour = timePicker.getHour();
            minutes = timePicker.getMinute();
        }
        else {
            hour = timePicker.getCurrentHour();
            minutes=timePicker.getCurrentMinute();
        }

        SelectTimeDialogListener listener = (SelectTimeDialogListener) getActivity();
        listener.onFinishTimeDialog(hour,minutes);

        dismiss();
    }


}
