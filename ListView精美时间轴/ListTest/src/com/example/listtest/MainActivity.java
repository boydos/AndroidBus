package com.example.listtest;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity implements
AdapterView.OnItemClickListener, StickyListHeadersListView.OnHeaderClickListener,
StickyListHeadersListView.OnStickyHeaderOffsetChangedListener,
StickyListHeadersListView.OnStickyHeaderChangedListener{
	 private TimelineAdapter mAdapter;
//	    private DrawerLayout mDrawerLayout;
//	    private ActionBarDrawerToggle mDrawerToggle;
	    private boolean fadeHeader = true;

	    private StickyListHeadersListView stickyList;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);

	        mAdapter = new TimelineAdapter(this);

	        stickyList = (StickyListHeadersListView) findViewById(R.id.list);
	        stickyList.setOnItemClickListener(this);
	        stickyList.setOnHeaderClickListener(this);
	        stickyList.setOnStickyHeaderChangedListener(this);
	        stickyList.setOnStickyHeaderOffsetChangedListener(this);
	        stickyList.setDrawingListUnderStickyHeader(true);
	        stickyList.setAreHeadersSticky(true);
	        stickyList.setAdapter(mAdapter);

//	        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//	        mDrawerToggle = new ActionBarDrawerToggle(
//	                this,                  /* host Activity */
//	                mDrawerLayout,         /* DrawerLayout object */
//	                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
//	                R.string.drawer_open,  /* "open drawer" description */
//	                R.string.drawer_close  /* "close drawer" description */
//	        );

	        // Set the drawer toggle as the DrawerListener
//	        mDrawerLayout.setDrawerListener(mDrawerToggle);
	        stickyList.setStickyHeaderTopOffset(-20);
	    }

	    @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
//	        mDrawerToggle.syncState();
	    }

	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
//	        mDrawerToggle.onConfigurationChanged(newConfig);
	    }

//	    @Override
//	    public boolean onOptionsItemSelected(MenuItem item) {
//	        if (mDrawerToggle.onOptionsItemSelected(item)) {
//	            return true;
//	        }
//	        return super.onOptionsItemSelected(item);
//	    }
//	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        Toast.makeText(this, "Item " + position + " clicked!", Toast.LENGTH_SHORT).show();
	    }

	    @Override
	    public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
	        Toast.makeText(this, "Header " + headerId + " currentlySticky ? " + currentlySticky, Toast.LENGTH_SHORT).show();
	    }

	    @Override
	    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	    public void onStickyHeaderOffsetChanged(StickyListHeadersListView l, View header, int offset) {
	        if (fadeHeader && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	            header.setAlpha(1 - (offset / (float) header.getMeasuredHeight()));
	        }
	    }

	    @Override
	    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	    public void onStickyHeaderChanged(StickyListHeadersListView l, View header, int itemPosition, long headerId) {
	        header.setAlpha(1);
	    }
}
