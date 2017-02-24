package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.codepath.simpletodo.R;


public class Dialog_SelectPriority extends DialogFragment  {

    EditText etName;
    public Dialog_SelectPriority() {
        // Required empty public constructor
    }

    public static Dialog_SelectPriority newInstance() {
        Dialog_SelectPriority fragment = new Dialog_SelectPriority();
       /* Bundle args = new Bundle();
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog__edit_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        etName = (EditText) view.findViewById(R.id.etName);
        // Fetch arguments from bundle and set title
       /* String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
       */ // Show soft keyboard automatically and request focus to field
        etName.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


}
