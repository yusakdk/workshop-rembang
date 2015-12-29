package org.meruvian.workshop.form.activity;

/**
 * Created by merv on 6/3/15.
 */

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.meruvian.workshop.form.R;
import org.meruvian.workshop.form.adapter.NewsAdapter;
import org.meruvian.workshop.form.entity.News;

public class NewsActivity extends ActionBarActivity{

    private ListView listNews;

    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        listNews = (ListView)findViewById(R.id.list_news);

        newsAdapter = new NewsAdapter(this, News.data());
        listNews.setAdapter(newsAdapter);

        listNews.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                dialogAction(position);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.actions, menu);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView= (SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s){
                Toast.makeText(NewsActivity.this, "Searching : "+s, Toast.LENGTH_LONG).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.action_save){
            Toast.makeText(this, getString(R.string.save), Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.action_refresh){
            Toast.makeText(this, getString(R.string.refresh), Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.action_search){
            Toast.makeText(this, getString(R.string.search), Toast.LENGTH_SHORT).show();
        }
    return true;
    }

    public void dialogAction(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.action));
        builder.setItems(new String[] {getString(R.string.edit), getString(R.string.delete)}, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int location){
                News news = (News) newsAdapter.getItem(position);
                if(location == 0){
                    Toast.makeText(NewsActivity.this, "Edit News : " +news.getTitle(), Toast.LENGTH_LONG).show();
                }else if(location == 1){
                    confirmDelete(position);
                }
            }
        });
        builder.create().show();
    }

    private void confirmDelete(int position){
        final News news = (News)newsAdapter.getItem(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.delete));
        builder.setMessage(getString(R.string.confirm_delete));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                Toast.makeText(NewsActivity.this, "Delete News : "+news.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
