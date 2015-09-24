package co.edu.udea.cmovil.gr03.yamba;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thenewcircle.yamba.client.YambaClient;
import com.thenewcircle.yamba.client.YambaClientException;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment {

    //instancio los objetos

    private static final String TAG = StatusFragment.class.getSimpleName();
    private Button mButtonTweet;
    private EditText mTextStatus;
    private TextView mTextCount;
    private int mDefaultColor;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int i, int i2, int i3) {
            checkFieldsForEmptyValues();

            mTextCount.setText(String.valueOf(140 - cs.length()));
            if ((140 - cs.length()) < 0) {
                mTextCount.setTextColor(Color.RED);
            } else {
                mTextCount.setTextColor(Color.BLACK);
            }

        }


        @Override
        public void afterTextChanged(Editable editable) {
        }
    };


    private void checkFieldsForEmptyValues() {
        String s1 = mTextStatus.getText().toString();

        if (s1.equals("")) {
            mButtonTweet.setEnabled(false);
        } else {
            mButtonTweet.setEnabled(true);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_status, container, false);
        mButtonTweet = (Button) v.findViewById(R.id.status_button_tweet);
        mTextStatus = (EditText) v.findViewById(R.id.status_text);
        mTextCount = (TextView) v.findViewById(R.id.status_text_count);

        mTextCount.setText(Integer.toString(140));
        mDefaultColor = mTextCount.getTextColors().getDefaultColor();
        mTextStatus.addTextChangedListener(textWatcher);
        mButtonTweet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String status = mTextStatus.getText().toString();
                PostTask postTask = new PostTask();
                postTask.execute(status);
                Log.d(TAG, "onClicked");
            }

        });

        return v;
    }

    /*private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getView();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }*/
    //}


    public void onClickPost(View view) {
        String status = mTextStatus.getText().toString();
        Log.d("Texto", status);
        PostTask postTask = new PostTask();
        Log.d("POST", "Va a comenzar");
        postTask.execute(status);
        Log.d(TAG, "Onclicked");
        //hideKeyboard();
        //removePhoneKeypad();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class PostTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progress;
        private String respuesta;

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(getActivity());
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setTitle("Posteando");
            progress.setMessage("Enviando---");
            progress.show();


        }

        @Override
        protected String doInBackground(String... params) {
            try {
                YambaClient cloud = new YambaClient("student", "password");
                cloud.postStatus(params[0]);
                Log.d(TAG, "Successfully posted to the cloud: " + params[0]);
                respuesta = "Successfully posted";
                return "Successfully posted";

            } catch (YambaClientException e) {
                Log.e(TAG, "Failed to post to the cloud", e);

                e.printStackTrace();
                respuesta = "Failed to post";
                return "Failed to post";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
            //try {
            Toast.makeText(getActivity(), respuesta, Toast.LENGTH_LONG).show();
            mTextStatus.setText("");
                /*if(progress != null){
                progress.dismiss();

                    Log.e(TAG, "Este es el ultimo resultado:"+result);
                Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
                    progress=null;
                }*/
            /*}catch(NullPointerException e){
               Log.e(TAG,"Unkwon error, ask the teacher");
                e.printStackTrace();
            }*/
        }
    }


}
