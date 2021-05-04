package com.example.myapplication.nav;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.userDetails;
import org.json.JSONArray;


/**
 * @author amana1
 */
public class NotificationFragment extends Fragment {


    ListView listView;
    private RequestQueue req;
    String url ="http://10.24.227.48:8080/Notifications/Users/";
    String date[] = {};
    String Des[]= {};
    boolean RU[]={};
    userDetails user;
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.notifications, container, false);
        user = (userDetails) getArguments().getSerializable("user");
        makePostRequest(user.username);
        listView = v.findViewById(R.id.list_view);
        System.out.println("notification here");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                       RU[position]= true;
                        openDialog(Des[position]);
                        ImageView img2 = view.findViewById(R.id.read);
                        img2.setImageResource(R.drawable.empty);


            }
        });
        return v;
    }

    /**
     * makes a post req. to send a notification.
     * @param username
     */
    private void makePostRequest(String username) {
        req = Volley.newRequestQueue(this.getActivity());
        JSONArray user_object = new JSONArray();

        JsonArrayRequest objRequest = new JsonArrayRequest(Request.Method.GET, url+ username, user_object, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                date = new String[response.length()];
                Des = new String[response.length()];
                RU = new boolean[response.length()];

                for (int i = 0; i < response.length(); i++) {
                    try {
                        date[i] = response.getJSONObject(i).getString("dateCreated");
                        Des[i] = response.getJSONObject(i).getString("message");
                        RU[i] = response.getJSONObject(i).getBoolean("hasRead");
                    } catch (Exception e) {
                        e.printStackTrace();
                        //    Toast.makeText(getApplicationContext(),"Please enter a correct email-password combination!",Toast.LENGTH_SHORT).show();
                    }
                }
                MyAdapter adapter = new MyAdapter(getContext(), date, Des, RU);
                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            }
        });

        req.add(objRequest);
    }
    private void openDialog(String s) {
        Dialog_notification dialog = new Dialog_notification();
        Bundle b = new Bundle();
        b.putString("msg",s);
        dialog.setArguments(b);
        dialog.show(getChildFragmentManager(),"notification");
    }

    public class MyAdapter extends ArrayAdapter<String>{


        Context context;
        String date[];
        String des[];
      ;
        boolean RU[];
        public MyAdapter(Context c, String[] date, String[] des, boolean[] RU) {
            super(c,R.layout.notification_box,R.id.titleN,date);
            this.context = c;
            this.date = date;
            this.des = des;
            this.RU = RU;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View row = layoutInflater.inflate(R.layout.notification_box,parent,false);
            ImageView img2 = row.findViewById(R.id.read);
            TextView mt = row.findViewById(R.id.titleN);
            TextView md = row.findViewById(R.id.desN);
            if(!RU[position])
            {
                img2.setImageResource(R.drawable.read);
            }
            mt.setText(des[position]);
            md.setText(date[position]);
            return row;
        }
    }
}
