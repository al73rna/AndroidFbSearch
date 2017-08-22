package com.example.al73r.fbsearch2;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

public class resultsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);




        /////////
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("USER");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_users , 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("PAGE");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_pages , 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);
//
        TextView tabfour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabfour.setText("EVENT");
        tabfour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_events , 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabfour);

        TextView tabfive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabfive.setText("PLACE");
        tabfive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_places , 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabfive);

        TextView tabsix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabsix.setText("GROUP");
        tabsix.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_groups , 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabsix);


    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_results, container, false);
            String thisType = "";

            //user
            String src = getActivity().getIntent().getStringExtra("src");
            String value = getActivity().getIntent().getStringExtra("text");
            Context g = getActivity();
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                thisType = "user";
            }
            //page
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                thisType = "page";
            }
            //event
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                thisType = "event";
            }
            //place
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 4) {
                thisType = "place";
            }
            //group
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 5) {
                thisType = "group";
            }



            if (src.equals("search")) {
                new requester(value, thisType, "1", rootView, g).execute();
            }else {
                ArrayList<ListItem> tmpArray = new ArrayList<>();
                HashMap<String,tag> map = FavList.getInstance().get();
                for (HashMap.Entry<String, tag> entry : map.entrySet()) {
                    String id = entry.getKey();
                    tag tag = entry.getValue();
                    if(tag.type.equals(thisType)){
                        ListItem tmpitem = new ListItem(tag.name,id);
                        tmpArray.add(tmpitem);
                    }
                }
                ListView li = (ListView) rootView.findViewById(R.id.mylist);
                mylistadaptor adpt = new mylistadaptor(1,getActivity(),tmpArray,thisType);
                li.setAdapter(adpt);
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "USER";
                case 1:
                    return "PAGE";
                case 2:
                    return "EVENT";
                case 3:
                    return "PLACE";
                case 4:
                    return "GROUP";

            }
            return null;
        }
    }

}
