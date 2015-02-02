package com.yahoo.learn.android.imagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.yahoo.learn.android.imagesearch.activities.R;
import com.yahoo.learn.android.imagesearch.models.SearchSettings;

public class SettingsActivity extends ActionBarActivity {
    private Spinner         mSpImageType;
    private Spinner         mSpFileType;
    private Spinner         mSpImageSize;
    private EditText        mEtSite;
    private SearchSettings  mSearchSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Hide the action bar
//        getActionBar().hide();

        mSpImageSize = (Spinner) findViewById(R.id.spImageSize);
        mSpImageType = (Spinner) findViewById(R.id.spImageType);
        mSpFileType = (Spinner) findViewById(R.id.spFileType);
        mEtSite = (EditText) findViewById(R.id.etSite);

        Intent intent = getIntent();
//        mSearchSettings = (SearchSettings) intent.getSerializableExtra(SearchActivity.EXTRA_SETTINGS);
        mSearchSettings = (SearchSettings) intent.getParcelableExtra(SearchActivity.EXTRA_SETTINGS);

        // TODO : Initialize spinners from current settings
        mSpImageSize.setSelection(mSearchSettings.getImageSizeSpinnerPosition());
        mSpImageType.setSelection(mSearchSettings.getImageTypeSpinnerPosition());
        mSpFileType.setSelection(mSearchSettings.getFileTypeSpinnerPosition());

        // Set site
        mEtSite.setText(mSearchSettings.getSite());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
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

    public void settingsSubmit(View view) {
        mSearchSettings.setFileType(mSpFileType.getSelectedItem().toString());
        mSearchSettings.setFileTypeSpinnerPosition(mSpFileType.getSelectedItemPosition());
        mSearchSettings.setImageType(mSpImageType.getSelectedItem().toString());
        mSearchSettings.setImageTypeSpinnerPosition(mSpImageType.getSelectedItemPosition());
        mSearchSettings.setImageSize(mSpImageSize.getSelectedItem().toString());
        mSearchSettings.setImageSizeSpinnerPosition(mSpImageSize.getSelectedItemPosition());
        mSearchSettings.setSite(mEtSite.getText().toString());

        Intent data = new Intent();
        data.putExtra(SearchActivity.EXTRA_SETTINGS, mSearchSettings);
        setResult(RESULT_OK, data);
        finish();
    }
}
