package shashib.taskhelper;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {


    public TodayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context c = getActivity().getApplicationContext();
        final View view = inflater.inflate(R.layout.fragment_today, container, false);
        final utility.util ut = new utility.util();
        ut.populatelistview(1, view);
        ListView lv= (ListView) view.findViewById(R.id.listView);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
              ut.OnclickforListviews(view, 0);
                return false;
            }
        });
       final ListView lv2= (ListView) view.findViewById(R.id.listView2);
        lv2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ut.OnclickforListviews(view,98); // 98 for completed just like that
                Log.d("TAG", "INSIDE THE SECOND CLICK");
                return false;
            }
        });
       final TextView tv = (TextView) view.findViewById(R.id.completedtext);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lv2.getVisibility() == ListView.VISIBLE) {
                    lv2.setVisibility(ListView.GONE);
                    tv.setText(getResources().getText(R.string.completetasktxtshow));
                }
                    else {
                    tv.setText(getResources().getText(R.string.completetasktxt));
                    lv2.setVisibility(ListView.VISIBLE);
                }
            }
        });
        return view;
    }
}