package org.gdgankara.app.adapeters;

import org.gdgankara.app.utils.Util;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.viewpagerindicator.IconPagerAdapter;
import org.gdgankara.app.TestFragment;
import org.gdgankara.app.R;

public class ProgramFragmentAdapter extends FragmentPagerAdapter{
	
	
	 private String[] CONTENT ;
	 private int mCount;

	    public ProgramFragmentAdapter(FragmentManager fm) {
	        super(fm);
	        if(Util.getDefaultLanguage().equals("tr")){
	        	CONTENT = new String[] { "Cuma", "Cumartesi", };
	        }
	        else{
	        	CONTENT= new String[] { "Friday", "Saturday", };
	        }
	        mCount=CONTENT.length;
	    }

	    @Override
	    public Fragment getItem(int position) {
	        return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
	    }

	    @Override
	    public int getCount() {
	        return mCount;
	    }

	    @Override
	    public CharSequence getPageTitle(int position) {
	      return CONTENT[position % CONTENT.length];
	    }


	    public void setCount(int count) {
	        if (count > 0 && count <= 10) {
	            mCount = count;
	            notifyDataSetChanged();
	        }
	    }

}
