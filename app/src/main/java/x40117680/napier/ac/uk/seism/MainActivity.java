package x40117680.napier.ac.uk.seism;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FrameLayout mContentFrame;
    private int mCurrentSelectedPosition;
    private ActionBarDrawerToggle mDrawerToggle;

    final String[] fragments ={"R.layout.record_fragment","R.layout.analyze_fragment"};

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.content_frame)!=null){
            if(savedInstanceState != null){
                return;
            }
            FragmentRecord recordFragment = new FragmentRecord();
            recordFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.content_frame,recordFragment).commit();
        }
        setUpToolbar();
        setUpNavDrawer();
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mContentFrame = (FrameLayout) findViewById(R.id.content_frame);
        navClicks();
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION, 0);
        Menu menu = mNavigationView.getMenu();
        menu.getItem(mCurrentSelectedPosition).setChecked(true);
    }

    private void setUpToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    private void setUpNavDrawer(){
        if(mToolbar!=null){
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
            mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.openDrawer,R.string.closeDrawer){
                @Override
                public void onDrawerOpened(View drawerView){
                    super.onDrawerOpened(drawerView);
                }
                @Override
                public void onDrawerClosed(View drawerView){
                    super.onDrawerClosed(drawerView);
                }
            };
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerLayout.setDrawerListener(mDrawerToggle);
        }
    }

    private void navClicks(){
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment newFragment = new Fragment();
                //Bundle args = new Bundle();
                //args.putInt(Fragment.ARG_POSITION, position);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.nav_item_record:
                        //Snackbar.make(mContentFrame, R.string.record, Snackbar.LENGTH_SHORT).show();
                        mCurrentSelectedPosition = 0;
                        newFragment = new FragmentRecord();
                        transaction.replace(R.id.content_frame, newFragment).commit();
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_item_history:
                        //Snackbar.make(mContentFrame, R.string.history, Snackbar.LENGTH_SHORT).show();
                        mCurrentSelectedPosition = 1;
                        newFragment = new FragmentHistory();
                        transaction.replace(R.id.content_frame, newFragment).commit();
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_item_analyze:
                        //Snackbar.make(mContentFrame, R.string.analyze, Snackbar.LENGTH_SHORT).show();
                        mCurrentSelectedPosition = 2;
                        newFragment = new FragmentAnalyze();
                        transaction.replace(R.id.content_frame, newFragment).commit();
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_item_settings:
                        //Snackbar.make(mContentFrame, R.string.settings, Snackbar.LENGTH_SHORT).show();
                        mCurrentSelectedPosition = 3;
                        mDrawerLayout.closeDrawers();
                        return true;
                    default:
                        return true;
                }
            }
        });
    }//end navClicks()

}//end main
