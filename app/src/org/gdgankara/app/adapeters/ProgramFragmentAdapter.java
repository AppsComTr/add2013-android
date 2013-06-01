package org.gdgankara.app.adapeters;

import org.gdgankara.app.utils.Util;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import org.gdgankara.app.fragment.ProgramFragment;

public class ProgramFragmentAdapter extends FragmentPagerAdapter{
	
	
	 private String[] CONTENT ;
	 private int mCount;
	 private Context context;
	 private ProgramFragment f1,f2;

	    public ProgramFragmentAdapter(FragmentManager fm,Context context) {
	        super(fm);
	        this.context=context;
	        if(Util.getDefaultLanguage().equals("tr")){
	        	CONTENT = new String[] { "Cuma", "Cumartesi", };
	        }
	        else{
	        	CONTENT= new String[] { "Friday", "Saturday", };
	        }
	        mCount=CONTENT.length;
	        
	        f1=new ProgramFragment();
	        f1.setDay(14);
	        f1.setContext(context);
	        f1.listeleriHazirla();
	        
	        f2=new ProgramFragment();
	        f2.setDay(15);
	        f2.setContext(context);
	        f2.listeleriHazirla();
	    }

		public void setContext(Context context){
	    	this.context=context;
	    }
	    
	    @Override
	    public Fragment getItem(int position) {
	        if(position==0){
	        	return f1;
	        }
	        return f2;
	    }

	    @Override
	    public int getCount() {
	        return mCount;
	    }

	    @Override
	    public CharSequence getPageTitle(int position) {
	      return CONTENT[position % CONTENT.length];
	    }
	    
	    public void listeleriYenile(){
	    	f1.listeleriYenile();
	    	f2.listeleriYenile();
	    }


	    public void setCount(int count) {
	        if (count > 0 && count <= 10) {
	            mCount = count;
	            notifyDataSetChanged();
	        }
	    }

}
