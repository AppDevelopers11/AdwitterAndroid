package com.smubator.adwitter.Promoter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.smubator.adwitter.CustomFonts.MyCustomTextView;
import com.smubator.adwitter.Models.CitiesModel;
import com.smubator.adwitter.Models.CountryModel;
import com.smubator.adwitter.Models.StatesModel;
import com.smubator.adwitter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

public class SelectLocationActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinnerCountry, spinnerState, spinnerCity;
    private Button btnSelect;
    private String Country="", State="", City="";
    private MyCustomTextView title;
    private ImageView ivArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        initViews();
        initListeners();
        try {
            fetchCountry();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListeners() {
        ivArrow.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
    }

    private void fetchCountry() throws IOException, JSONException {
        InputStream is = getResources().openRawResource(R.raw.countries);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            String jsonString = writer.toString();
            final JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jArr = jsonObject.optJSONArray("countries");
            final ArrayList<CountryModel> countryModels = new ArrayList<>();
            for (int i = 0; i < jArr.length(); i++) {
                CountryModel countryModel = new CountryModel();
                JSONObject obj = jArr.getJSONObject(i);
                countryModel.setCountryName(obj.optString("name"));
                countryModel.setId(obj.optInt("id"));
                countryModels.add(countryModel);
            }
            spinnerCountry.setAdapter(new MyCustomCountryAdapter(SelectLocationActivity.this, countryModels));
            spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    if (position == 0) {
                    } else {
                        try {
                            String selectedItem = adapterView.getSelectedItem().toString();
                            // int id = countryModels.get(position).getId();
                            int id = countryModels.get(position).getId();
                            fetchStates(id);
                            Country = countryModels.get(position).getCountryName();
                            Log.i("", "");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
    }

    private void fetchStates(int id) throws IOException {
        InputStream is = getResources().openRawResource(R.raw.states);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            String jsonString = writer.toString();
            final JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jArr = jsonObject.optJSONArray("states");
            final ArrayList<StatesModel> statesModels = new ArrayList<>();
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject obj = jArr.getJSONObject(i);
                if (id == obj.optInt("country_id")) {
                    StatesModel statesModel = new StatesModel();
                    statesModel.setStateName(obj.optString("name"));
                    statesModel.setId(obj.optInt("id"));
                    statesModel.setCountry_id(obj.optInt("country_id"));
                    statesModels.add(statesModel);
                } else {
                }
            }
            spinnerState.setAdapter(new MyCustomStatesAdapter(SelectLocationActivity.this, statesModels));
            State = statesModels.get(0).getStateName();

            spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    if (position == 0) {
                    } else {
                        try {
                            /*"location" cityID = 455; countryID = 5; stateID = 172; */
                            String selectedItem = adapterView.getSelectedItem().toString();
                            int id = statesModels.get(position).getCountry_id();
                            // fetchStates(id);
                            State = statesModels.get(position).getStateName();
                            fetchCities(id);
                            Log.i("", "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
    }

    private void fetchCities(int id) throws IOException {
        InputStream is = getResources().openRawResource(R.raw.cities);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            String jsonString = writer.toString();
            final JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jArr = jsonObject.optJSONArray("cities");
            final ArrayList<CitiesModel> citiesModels = new ArrayList<>();
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject obj = jArr.getJSONObject(i);
                if (id == obj.optInt("state_id")) {
                    CitiesModel citiesModel = new CitiesModel();
                    citiesModel.setId(obj.optInt("id"));
                    citiesModel.setCityName(obj.optString("name"));
                    citiesModel.setState_id(obj.optInt("state_id"));
                    citiesModels.add(citiesModel);
                } else {

                }
            }
            spinnerCity.setAdapter(new MyCustomCitiesAdapter(SelectLocationActivity.this, citiesModels));
            City = citiesModels.get(0).getCityName();
            spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    if (position == 0) {
                    } else {
                        try {
                            String selectedItem = adapterView.getSelectedItem().toString();
                            String id = citiesModels.get(position).getCityName();
                            Log.i("", "");
                            City = id;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
    }

    private void initViews() {
        ivArrow = findViewById(R.id.ivArrow);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerState = findViewById(R.id.spinnerState);
        spinnerCity = findViewById(R.id.spinnerCity);
        btnSelect = findViewById(R.id.btnSelect);
        title = findViewById(R.id.title);
        title.setText(getResources().getString(R.string.selectLocation));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivArrow:
                onBackPressed();
                break;
            case R.id.btnSelect:
                //String message=editText1.getText().toString();
//                if(Country.equalsIgnoreCase("") && State.equalsIgnoreCase("") && City.equalsIgnoreCase(""))
//                {
//                    Toast.makeText(this, "Please select all items", Toast.LENGTH_SHORT).show();
//                }
                if (Country.length() < 0 && State.length() < 0 && City.length() < 0) {
                    Toast.makeText(this, "Please select all items", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("country_name", Country);
                    intent.putExtra("state_name", State);
                    intent.putExtra("city_name", City);
                    setResult(1, intent);
                    finish();//finishing activity
                }

                break;
        }
    }

    private class MyCustomCountryAdapter extends BaseAdapter {
        private ArrayList<CountryModel> countryModels = new ArrayList<>();
        private Context context;
        private LayoutInflater inflater;

        public MyCustomCountryAdapter(Context context, ArrayList<CountryModel> countryModels) {
            this.context = context;
            this.countryModels = countryModels;
        }

        @Override
        public int getCount() {
            return countryModels.size();
        }

        @Override
        public Object getItem(int i) {
            return countryModels.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.item_select_location, null, false);
            MyCustomTextView myCustomTextView = row.findViewById(R.id.tvSelectLocation);
            myCustomTextView.setText(countryModels.get(position).getCountryName());
            return row;
        }
    }

    private class MyCustomStatesAdapter extends BaseAdapter {
        Context context;
        ArrayList<StatesModel> statesModels;
        private LayoutInflater inflater;

        MyCustomStatesAdapter(Context context, ArrayList<StatesModel> statesModels) {
            this.context = context;
            this.statesModels = statesModels;
        }

        @Override
        public int getCount() {
            return statesModels.size();
        }

        @Override
        public Object getItem(int i) {
            return statesModels.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.item_select_location, null, false);
            MyCustomTextView myCustomTextView = row.findViewById(R.id.tvSelectLocation);
            myCustomTextView.setText(statesModels.get(position).getStateName());
            return row;
        }
    }

    private class MyCustomCitiesAdapter extends BaseAdapter {
        Context context;
        ArrayList<CitiesModel> citiesModels;
        private LayoutInflater inflater;

        MyCustomCitiesAdapter(Context context, ArrayList<CitiesModel> citiesModels) {
            this.context = context;
            this.citiesModels = citiesModels;
        }

        @Override
        public int getCount() {
            return citiesModels.size();
        }

        @Override
        public Object getItem(int i) {
            return citiesModels.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.item_select_location, null, false);
            MyCustomTextView myCustomTextView = row.findViewById(R.id.tvSelectLocation);
            myCustomTextView.setText(citiesModels.get(position).getCityName());
            return row;
        }
    }
}