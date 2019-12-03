package info.camposha.firebasedatabasecrud.Helpers;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import info.camposha.firebasedatabasecrud.Data.MyAdapter;
import info.camposha.firebasedatabasecrud.Data.Scientist;


public class FilterHelper extends Filter {
    static List<Scientist> currentList;
    static MyAdapter adapter;


    public static FilterHelper newInstance(List<Scientist> currentList, MyAdapter adapter) {
        FilterHelper.adapter=adapter;
        FilterHelper.currentList=currentList;
        return new FilterHelper();
    }
    /*
    - Perform actual filtering.
     */
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults=new FilterResults();

        if(constraint != null && constraint.length()>0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();

            //HOLD FILTERS WE FIND
            ArrayList<Scientist> foundFilters=new ArrayList<>();

            String galaxy,name,star,description;

            //ITERATE CURRENT LIST
            for (int i=0;i<currentList.size();i++)
            {
                galaxy= currentList.get(i).getGalaxy();
                name= currentList.get(i).getName();

                //SEARCH
                if(galaxy.toUpperCase().contains(constraint)){
                    foundFilters.add(currentList.get(i));
                }else if(name.toUpperCase().contains(constraint)){
                    foundFilters.add(currentList.get(i));
                }
            }

            //SET RESULTS TO FILTER LIST
            filterResults.count=foundFilters.size();
            filterResults.values=foundFilters;
        }else
        {
            //NO ITEM FOUND.LIST REMAINS INTACT
            filterResults.count=currentList.size();
            filterResults.values=currentList;
        }

        //RETURN RESULTS
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        adapter.scientists= (ArrayList<Scientist>) filterResults.values;
        adapter.notifyDataSetChanged();
    }
}
//end







