package org.gdgankara.app.adapeters;

import org.gdgankara.app.utils.Util;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.viewpagerindicator.IconPagerAdapter;

public class ProgramFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter{
	
	private String[] tabs;
	private Fragment[] fragments;

	public ProgramFragmentAdapter(FragmentManager fm) {
		super(fm);
		tabs=new String[2];
		if(Util.getDefaultLanguage().equals("tr")){
			tabs[0]="Cuma";
			tabs[1]="Cumartesi";
		}
		else{
			tabs[0]="Friday";
			tabs[1]="Saturday";
		}
		
	}

	@Override
	public int getIconResId(int index) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
      return tabs[position];
    }

	@Override
	public Fragment getItem(int arg0) {
		return new Fragment();
	}

	@Override
	public int getCount() {
		return 2;
	}

}
