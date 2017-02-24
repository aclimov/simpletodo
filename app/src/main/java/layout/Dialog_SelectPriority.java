package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioButton;
import com.codepath.simpletodo.R;


public class Dialog_SelectPriority extends DialogFragment  {


    public Dialog_SelectPriority() {
        // Required empty public constructor
    }

    public static Dialog_SelectPriority newInstance(String priority) {
        Dialog_SelectPriority fragment = new Dialog_SelectPriority();
        Bundle args = new Bundle();
        args.putString("priority", priority);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_priority, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Fetch arguments from bundle and set title
        String priority = getArguments().getString("priority");
        if(!priority.isEmpty()) {
            RadioButton rbButton;
            switch (priority) {
                case "low":rbButton=(RadioButton) view.findViewById(R.id.radioButton);
                    break;
                case "normal":rbButton=(RadioButton) view.findViewById(R.id.radioButton2);
                    break;
                case "high":rbButton=(RadioButton) view.findViewById(R.id.radioButton3);
                    break;
                default:
                    rbButton=(RadioButton) view.findViewById(R.id.radioButton);
                    break;
            }
            rbButton.toggle();
        }

        // Show soft keyboard automatically and request focus to field

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }


}
